/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm.builder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.hus.nlp.fsm.Configuration;
import vn.hus.nlp.fsm.State;
import vn.hus.nlp.fsm.Transition;
import vn.hus.nlp.fsm.fsa.DFAConfiguration;
import vn.hus.nlp.fsm.fst.FSTConfiguration;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 *         <p>
 *         Created: Jan 29, 2008, 1:27:30 PM
 *         <p>
 *         This class implements the incremental construction algorithm of a
 *         minimal acyclic finite-state machine.
 */
public final class MinimalFSMBuilder extends FSMBuilder {

	/**
	 * A register of states. All states in the register are states of the
	 * resulting minimal automaton.
	 */
	private Set<State> register = null;

	
	/**
	 * Build a minimal machine
	 */
	public MinimalFSMBuilder(String machineType) {
		// init the machine
		super(machineType);
		// init the register
		register = new HashSet<State>();
	}
	
	/**
	 * @return the register
	 */
	public Set<State> getRegister() {
		return register;
	}
	
	
	/**
	 * Get the set of inputs on outtransitions of a state. 
	 * @param s a state
	 * @return the set of inputs on outtransitions of s.
	 */
	private Set<Character> getOutTransitionInputs(State s) {
		Set<Character> set = new HashSet<Character>();
		char[] outTransitionInputs = s.getOutTransitionInputs();
		for (int i = 0; i < outTransitionInputs.length; i++) {
			set.add(new Character(outTransitionInputs[i]));
		}
		return set;
	}
	/**
	 * Test the equivalence of two states.
	 * <p>
	 * Two states are equivalent if and only if:
	 * <ol>
	 * <li>they are either both final or both nonfinal; and
	 * <li>they have the same number of outgoing transitions; and
	 * <li>corresponding outgoing transitions have the same labels; and
	 * <li>corresponding transitions lead to the same right languages.
	 * </ol>
	 * Because the postorder method ensures that all states reachable from the
	 * states already visited are unique representative of their classes (i.e.,
	 * their right languages are unique in the visited part of the automaton),
	 * we can rewrite the last condition as: "corresponding transitions lead to
	 * the same states". This condition is very useful to avoid an endless all
	 * in the recursion process.
	 * 
	 * Note that the testing algorithm implemented here is correct for only
	 * acyclic finite-state automata.
	 * 
	 * @param p
	 *            a state
	 * @param q
	 *            a state
	 * @return <code>true</code> or <code>false</code>
	 */
	private boolean isEquivalent(State p, State q) {
		// we do not consider null states
		if (p == null || q == null)
			return false;
		// if a state is a final state, while the other is not the final state,
		// then the two states
		// are not equivalent
		if (p.isFinalState() && !q.isFinalState())
			return false;
		if (!p.isFinalState() && q.isFinalState())
			return false;
		// if two states is equal then they are obviously equivalent
		if (p.equals(q))
			return true;
		// if the sets of inputs on the outtransitions of two states are not equal,
		// then the two states are not equivalent
		Set<Character> pInputs = getOutTransitionInputs(p);
		Set<Character> qInputs = getOutTransitionInputs(q);
		if (!pInputs.equals(qInputs))
			return false;
		// iterate the set of labels on the transition of the states to get
		// another 2 states, and determine the equivalence of these states
		for (Iterator<Character> iter = pInputs.iterator(); iter.hasNext();) {
			Character input = iter.next();
			State nextP = machine.getNextState(p, input);
			State nextQ = machine.getNextState(q, input);
			// if nextP is not equal to nextQ, return false
			if (nextP != null && !nextP.equals(nextQ))
				return false;
		}
		return true;
	}
	
	/**
	 * Redirect all incoming transitions of a state to another one.
	 * 
	 * @param oldState
	 * @param newState
	 */
	private void redirectTransitions(State oldState, State newState) {
		Map<Integer, List<Transition>> intransitionMap = machine.getIntransitionMap();
		List<Transition> transitions = intransitionMap.get(oldState.getId());
		for (Iterator<Transition> i = transitions.iterator(); i.hasNext();) {
			Transition t = i.next();
			if (t.getTarget() == oldState.getId()) {
//				 System.out.print(t + " -> "); // DEBUG
				t.setTarget(newState.getId());
//				 System.out.println(t);  // DEBUG
			}
		}
	}

	/**
	 * Returns the last child state of a state. Since the
	 * input data is lexicographically sorted, the method returns the 
	 * state that has maximal id amongst other child states.
	 * 
	 * @param s
	 *            a state
	 * @return the last child state
	 */
	private State lastChild(State s) {
		// get the outgoing transition map from the state
		List<Transition> outTransitions = s.getOutTransitions();
		int maxId = -1;
		for (Iterator<Transition> it = outTransitions.iterator(); it.hasNext();) {
			Transition t = it.next();
			if (maxId < t.getTarget()) {
				maxId = t.getTarget();
			}
		}
		if (maxId == s.getId()) {
			System.err.println("Error: infinite loop!!! Id = " + maxId);
			printMachine();
			System.exit(1);
		}
		return machine.getState(maxId);
	}

	/**
	 * Add to a state a chain of states that would recognize a suffix. This
	 * method creates a branch extending out of the dictionary, which represents
	 * the suffix of the word being added. The last state of this branch is
	 * marked as final.
	 * 
	 * @param state
	 *            a state
	 * @param suffixInput
	 *            a suffix input
	 */
	private void addSuffix(State state, String suffixInput) {
		for (int k = 0; k < suffixInput.length(); k++) {
			State newState = new State(machine.getStates().size());
			machine.addState(newState);
			char input = suffixInput.charAt(k);
			machine.addTransition(new Transition(state.getId(), newState.getId(),input));
			state = newState;
		}
		// the last created state is a final one
		state.setType((byte) 2);
	}
	
	/**
	 * Add to a state a chain of states that would recognize a suffix. This
	 * method creates a branch extending out of the dictionary, which represents
	 * the suffix of the word being added. The last state of this branch is
	 * marked as final.
	 * 
	 * @param state
	 *            a state
	 * @param suffixInput
	 *            a suffix input
	 * @param suffixOutput
	 * 			  a suffix output
	 */
	private void addSuffix(State state, String suffixInput, String[] suffixOutput) {
		for (int k = 0; k < suffixInput.length(); k++) {
			State newState = new State(machine.getStates().size());
			machine.addState(newState);
			char input = suffixInput.charAt(k);
			machine.addTransition(new Transition(state.getId(), newState.getId(),
					input, suffixOutput[k]));
			state = newState;
		}
		// the last created state is a final one
		state.setType((byte) 2);
	}
	
	/**
	 * The minimization process. 
	 * 
	 * @param s
	 *            a state to create or register
	 */
	private void replaceOrRegister(State s) {
		State child = lastChild(s);
		if (child == null) {
			return;
		}
		// the method is recursively call itself
		// until it reaches the end of the path of the previously added input
		if (child.getOutTransitions().size() > 0) { // child has children
			replaceOrRegister(child);
		}
		// returning from each recursive call, we check whether a state
		// equivalent to the current state can be found in the register. 
		// If this is true, the state is replaced with the equivalent 
		// state found in the register. If not, the state is registered 
		// as a representative of a new class.
		boolean test = false;
		for (Iterator<State> it = register.iterator(); it.hasNext();) {
			State q = it.next();
			if (isEquivalent(q, child)) {
				test = true;
				// redirect all intransitions to child to transitions to q,
				redirectTransitions(child, q);
//				System.out.println(child.getId() + " is equivalent to " + q.getId()); // DEBUG
				// remove child
				// this will also remove all transitions of the state.
				machine.removeState(child);
				break;
			}
		}
		if (!test) {
			register.add(child);
		}
	}

	/* (non-Javadoc)
	 * @see vn.hus.fsm.builder.FSMBuilder#addItem(java.lang.String, java.lang.String[])
	 */
	@Override
	protected void addItem(String input, String[] output) {
		input = input.trim();
		if (input.length() == 0)
			return;
		// track the item to find stop configuration
		Configuration configuration = getSimulator().track(input);
		State state = configuration.getCurrentState();
		String unprocessedInput = "";
		if (configuration instanceof DFAConfiguration) {
			DFAConfiguration config = (DFAConfiguration)configuration; 
			unprocessedInput = config.getUnprocessedInput();
		}
		if (configuration instanceof FSTConfiguration) {
			FSTConfiguration config = (FSTConfiguration) configuration;
			unprocessedInput = config.getUnprocessedInput();
		}
		// minimize the current machine
		if (state.getOutTransitions().size() > 0) {
			replaceOrRegister(state);
		}
		// add unprocessed input to the machine with or without its corresponding output
		if (output == null) {
			addSuffix(state, unprocessedInput);
		} else {
			// prepare the outputSuffix
			// the output suffix has the same length with th unprocessed input
			int lenSuffix = unprocessedInput.length();
			String[] outputSuffix = new String[lenSuffix];
			int len = output.length;
			for (int i = 0; i < lenSuffix; i++) {
				outputSuffix[i] = output[len - lenSuffix + i];
			}
			addSuffix(state, unprocessedInput, outputSuffix);
		}
		
	}

	/**
	 * This builder build incrementally the automaton on a sorted list of items.
	 * We must be sure that the items are sorted lexicographically before
	 * performing the algorithm to create the minimal machine.
	 * 
	 * @see vn.hus.nlp.fsm.builder.FSMBuilder#create(java.lang.String[],
	 *      java.lang.String[][])
	 */
	@Override
	public void create(String[] inputs, String[][] outputs) {
		System.out.println("Building the minimal machine...");
		synchronized (machine) {
			long beginTime = System.currentTimeMillis();
			// build the minimal automaton
			for (int i = 0; i < inputs.length; i++) {
				addItem(inputs[i], outputs[i]);
				if (i % 1000 == 0) {
					System.out.println(" i = " + i);
				}
			}
			// IMPORTANT: call the last step of the minimization process:
			//
			finalize();
			long endTime = System.currentTimeMillis();
			long time = (endTime - beginTime);
			System.out.println("Time to build the minimal machine = " + time + " (ms)");
		}
	}
	
	/* (non-Javadoc)
	 * @see vn.hus.fsm.builder.FSMBuilder#finalize()
	 */
	@Override
	protected void finalize() {
		// last step of the minimization process.
		replaceOrRegister(machine.getInitialState());
	}
	
	/* (non-Javadoc)
	 * @see vn.hus.fsm.fsa.DFABuilder#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		// delete the register to save space.
		register.clear();
		register = null;
	}
}
