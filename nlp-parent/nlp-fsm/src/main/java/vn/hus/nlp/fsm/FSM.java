/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.fsm
 * <p>
 * Nov 5, 2007, 8:48:06 PM
 * <p>
 * Abstract finite state machine.
 */
public abstract class FSM {
	
	/**
	 * A state map that maps a state id to itself. We use
	 *  a map instead of a set to speed up state search given its id.
	 */
	protected Map<Integer, State> states;
	
	/**
	 * A map that stores all intransition to a state. This help 
	 * speed up the search of all intransions to a state, we don't need 
	 * to iterate through all states to find intransitions to a state. 
	 */
	protected Map<Integer, List<Transition>> intransitionMap;
	/**
	 * Number of transitions.
	 */
	private int nTransitions;
	
	/**
	 * Default constructor.
	 */
	public FSM() {
		states = new HashMap<Integer, State>();
		// create the initial state
		createInitialState();
		nTransitions = 0;
		intransitionMap = new HashMap<Integer, List<Transition>>();
	}
	
	private void createInitialState() {
		State s = new State(0);
		s.setType((byte)0);
		states.put(0, s);
	}
	
	/**
	 * Add a state to the machine.
	 * @param s
	 */
	public void addState(State s) {
		states.put(s.getId(), s);
	}
	
	/**
	 * Remove a state from the machine. A state to be removed must not be
	 * the initial state. 
	 * @param s
	 */
	public void removeState(State s) {
		// cannot remove the initial state.
		if (s.getId() <= 0) 
			return;
		// update the number of transitions of the machine
		List<Transition> outTransitions = s.getOutTransitions();
		nTransitions -= outTransitions.size();
		// remove all outtransitions of this state
		outTransitions.clear();
		// remove the state itself
		states.remove(s.getId());
	}
	
	/**
	 * Add a transition to the intransition map.
	 * @param t
	 */
	private void addIntransitionMap(Transition t) {
		List<Transition> list = intransitionMap.get(t.getTarget());
		if (list == null) {
			list = new ArrayList<Transition>();
			intransitionMap.put(t.getTarget(), list);
		}
		list.add(t);
	}

	/**
	 * Remove a transition from the intransition map.
	 * @param t
	 */
	private void removeIntransitionMap(Transition t) {
		List<Transition> list = intransitionMap.get(t.getTarget());
		list.remove(t);
	}
	
	/**
	 * Add a transition to the machine.
	 * @param t
	 */
	public void addTransition(Transition t) {
		if (t != null) {
			// update outtransitions of the source state
			State source = states.get(t.getSource());
			source.getOutTransitions().add(t);
			nTransitions++;
			// update the intransition map
			addIntransitionMap(t);
		}
	}
	
	

	/**
	 * Remove a transition from the machine.
	 * @param t
	 */
	public void removeTransition(Transition t) {
		if (t != null) {
			// update outtransitions of the source state
			State source = states.get(t.getSource());
			source.getOutTransitions().remove(t);
			nTransitions--;
			// update the intransition map
			removeIntransitionMap(t);
		}
	}
	
	/**
	 * Get the states.
	 * @return the states
	 */
	public Map<Integer, State> getStates() {
		return states;
	}
	
	/**
	 * Get a state given its id.
	 * @param id an id
	 * @return a state
	 */
	public State getState(int id) {
		if (id < 0 || id > states.size()) 
			return null;
		return states.get(new Integer(id));
	}
	
	/**
	 * Get a transition of the machine given source and target vertices.
	 * @param src
	 * @param tar
	 * @return a transition or <tt>null</tt>.
	 */
	public Transition getTransition(int src, int tar) {
		State s = getState(src);
		List<Transition> outTransitions = s.getOutTransitions();
		for (Iterator<Transition> it = outTransitions.iterator(); it.hasNext();) {
			Transition t = it.next();
			if (t.getTarget() == tar) {
				return t;
			}
		}
		return null;
	}
	/**
	 * The initial state of the machine.
	 * @return The initial state of the machine. By convention, 
	 * the initial state always has id zero. 
	 */
	public State getInitialState() {
		return states.get(new Integer(0));
	}
	/**
	 * Get the number of transitions of the machine.
	 * @return the number of transitions of the machine.
	 */
	public int getNTransitions() {
		return nTransitions;
	}
	
	/**
	 * @return The intransition map
	 */
	public Map<Integer, List<Transition>> getIntransitionMap() {
		return intransitionMap;
	}
	
	/**
	 * Get the next state of a state given an input.
	 * @param currentState the current state
	 * @param input a input
	 * @return the next state or <tt>null</tt>.
	 */
	public State getNextState(State currentState, char input) {
		for (Iterator<Transition> it = currentState.getOutTransitions().iterator(); it.hasNext();) {
			Transition t = it.next();
			if (t.getInput() == input)
				return getState(t.getTarget());
		}
		return null;
	}
	
	/**
	 * Get the output of a state given an input.
	 * @param currentState the current state
	 * @param input a input
	 * @return the output 
	 */
	public String getNextOutput(State currentState, char input) {
		for (Iterator<Transition> it = currentState.getOutTransitions().iterator(); it.hasNext();) {
			Transition t = it.next();
			if (t.getInput() == input)
				return t.getOutput();
		}
		return IConstants.EMPTY_STRING;
	}
	
	/**
	 * Get the simulator of the machine. The simulator defines the type 
	 * of the machine (deterministic, non-deterministic).
	 * @return the simulator of the machine.
	 */
	public abstract ISimulator getSimulator();
	
	/**
	 * Dispose the state machine. All states will be removed to save
	 * space.
	 */
	public void dispose() {
		states.clear();
		states = null;
		intransitionMap.clear();
		intransitionMap = null;
	}
	
}
