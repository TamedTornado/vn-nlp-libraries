package vn.hus.nlp.fsm.fsa;

import vn.hus.nlp.fsm.Configuration;
import vn.hus.nlp.fsm.State;

/**
 * @author LE Hong Phuong, phuonglh@gmail.com
 *         <p>
 *         Nov 7, 2007, 9:13:00 PM
 *         <p>
 *         An <code>DFAConfiguration</code> object is a {@link Configuration}
 *         object with an additional field for the input string. The current
 *         state of the dfa and the unprocessed input are the only
 *         necessary data for the simulation of an DFA.
 */
public class DFAConfiguration extends Configuration {

	/**
	 * The total input. The total input is an array of characters or a string.
	 */
	private final String totalInput;

	/**
	 * The unprocessed input.
	 */
	private String unprocessedInput;

	/**
	 * Instantiates a new DFAConfiguration.
	 * 
	 * @param state
	 *            the state the dfa is currently in.
	 * @param parent
	 *            the configuration that is the immediate ancestor of this
	 *            configuration
	 * @param totalInput
	 *            the total input
	 * @param unprocessedInput
	 *            the unprocessed input
	 * 
	 */
	public DFAConfiguration(State state, DFAConfiguration parent,
			String totalInput, String unprocessedInput) {
		super(state, parent);
		this.totalInput = totalInput;
		this.unprocessedInput = unprocessedInput;
	}

	/**
	 * Returns the total input.
	 */
	public String getTotalInput() {
		return totalInput;
	}

	/**
	 * Returns the unprocessed input.
	 * 
	 * @return the unprocessed input.
	 */
	public String getUnprocessedInput() {
		return unprocessedInput;
	}

	/**
	 * Changes the unprocessed input.
	 * 
	 * @param unprocessedInput
	 *            the string that will represent the unprocessed input of the
	 *            DFA.
	 */
	public void setUnprocessedInput(String unprocessedInput) {
		this.unprocessedInput = unprocessedInput;
	}

	/**
	 * Returns a string representation of this object.
	 * 
	 * @return a string representation of this object.
	 */
	@Override
	public String toString() {
		return super.toString() + ": " + getUnprocessedInput();
	}

	/**
	 * Checks for equality. Two DFAConfigurations are equal if they have the
	 * same unprocessed input, and satisfy the .equals() test of the base
	 * <code>Configuration</code> class.
	 * 
	 * @param configuration
	 *            the configuration to check against this one for equality
	 * @return <code>true</code> if the two configurations are equal,
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object configuration) {
		if (configuration == this)
			return true;
		try {
			return super.equals(configuration)
					&& unprocessedInput.equals(((DFAConfiguration) configuration).unprocessedInput);
		} catch (ClassCastException e) {
			return false;
		}
	}

	/**
	 * Returns a hash code for this object.
	 * 
	 * @return a hashcode for this object
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ unprocessedInput.hashCode();
	}

}
