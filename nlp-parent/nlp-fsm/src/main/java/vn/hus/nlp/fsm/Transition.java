/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 *         <p>
 *         vn.hus.fsm
 *         <p>
 *         Nov 5, 2007, 9:00:42 PM
 *         <p>
 *         Transition of finite state machines. A transition connects two states
 *         and provides one or more labels.
 */
public class Transition {
	/**
	 * The id of source state
	 */
	private int source;
	/**
	 * The id of target state
	 */
	private int target;
	/**
	 * The input character.
	 */
	private final char input;
	/**
	 * The output string.
	 */
	private final String output;

	public Transition() {
		source = -1;
		target = -1;
		input = IConstants.BLANK_CHARACTER;
		output = IConstants.EMPTY_STRING;
	}

	public Transition(int src, int tar) {
		source = src;
		target = tar;
		input = IConstants.BLANK_CHARACTER;
		output = IConstants.EMPTY_STRING;
	}

	public Transition(int src, int tar, char inp) {
		source = src;
		target = tar;
		input = inp;
		output = IConstants.EMPTY_STRING;
	}

	public Transition(int src, int tar, char inp, String out) {
		source = src;
		target = tar;
		input = inp;
		output = out;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param t
	 */
	public Transition(Transition t) {
		source = t.getSource();
		target = t.getTarget();
		input = t.getInput();
		output = t.getOutput();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transition))
			return false;
		Transition t = (Transition) obj;
		return (source == t.getSource()) && (target == t.getTarget())
				&& (input == t.getInput());
	}

	/**
	 * @return the source state
	 */
	public int getSource() {
		return source;
	}

	/**
	 * @return the target state
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * Set the source state of the transition.
	 * @param source the source state
	 */
	public void setSource(int source) {
		this.source = source;
	}

	/**
	 * Set the target state of the transition.
	 * @param target the target state
	 */
	public void setTarget(int target) {
		this.target = target;
	}

	/**
	 * @return the input character
	 */
	public char getInput() {
		return input;
	}

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return ("[" + source + "," + input + ":" + output + "," + target + "]");
	}
}
