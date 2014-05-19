package vn.hus.nlp.fsm;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author   LE Hong Phuong
 * <p>
 * A state of finite state machines.
 */
public class State implements Comparable<State> {
	/**
	 * The id of a state, each state in the dfa has an unique id.
	 */
	private int id;
	
	/**
	 * The type of state, the initial state has type 0, a normal state has type
	 * 1 and the final state has type 2. State that has type -1 is undefined.
	 */
	private byte type;
	
	/**
	 * Outgoing transition from the state.
	 */
	private final List<Transition> outTransitions;
	/**
	 * Instantiate a new state
	 * @param id 
	 */
	public State(int id) {
	    this.id = id;
		type = 1; // normal state
	    outTransitions = new ArrayList<Transition>();
	}
	/**
	 * Copy constructor
	 * @param s
	 */
	public State(State s) {
		this.id = s.getId();
		this.setType(s.getType());
		this.outTransitions= s.getOutTransitions();
	}
	/**
	 * Set the type of state 
	 * @param type   the type to set
	 * 
	 */
	public void setType(byte type) {
		this.type = type;
	}
	/**
	 * Get the type of state
	 * @return   the type of state
	 * 
	 */
	public byte getType() {
		return type;
	}
	
	/**
	 * Get the id of state
	 * @return   the state id
	 * 
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the id of state
	 * @param id   the state id
	 * 
	 */
	public void setId(int id) {
		this.id=id;
	}
	
	/**
	 * Get the number of outtransitions of the state.
	 * @return the number of outtransitions of the state
	 */
	public int getNumberOfOutTransitions() {
		return outTransitions.size();
	}
	
	/**
	 * Get outtransitions.
	 * @return outtransitions.
	 */
	public List<Transition> getOutTransitions() {
		return outTransitions;
	}
	
	/**
	 * Get inputs of its outtransitions.
	 * @return an array of inputs.
	 */
	public char[] getOutTransitionInputs() {
		char[] inputs = new char[outTransitions.size()];
		 Iterator<Transition> it = outTransitions.iterator();
		 int i = 0;
		 while (it.hasNext()) {
		 	inputs[i++] = it.next().getInput(); 
		 }
		 return inputs;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "(" + getId() + "," + getType() + ")";
		s += outTransitions.toString();
		return s;
	}
	
	/**
	 * Get the name of the state
	 * @return the name of the state
	 */
	public String getName() {
		return "q_{" + getId() + "}";
	}
  
	/**
	 * Test if this state is equal to another state
	 * @param q
	 * @return <code>true</code> or <code>false</code>
	 */
	@Override
	public boolean equals(Object q) {
		if (q instanceof State) {
			return getId() == ((State)q).getId();
		}
		return false;
	}
	/**
	 * Test if state is final
	 * @return <CODE>true</CODE> if state is final, <CODE>false</CODE> otherwise
	 */
	public boolean isFinalState() {
		return (type == 2);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(State s) {
		return getId() - s.getId(); 
	}
	
	
}
