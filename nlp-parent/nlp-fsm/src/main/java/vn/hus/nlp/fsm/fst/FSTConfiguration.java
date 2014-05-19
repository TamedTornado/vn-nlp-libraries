/**
 * (C) LE HONG Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.fsm.fst;

import vn.hus.nlp.fsm.State;
import vn.hus.nlp.fsm.fsa.DFAConfiguration;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 *         <p>
 *         Jan 25, 2008, 12:04:26 AM
 *         <p>
 *         Configuration of the FST.
 */
public class FSTConfiguration extends DFAConfiguration {
	/**
	 * The current output of the fst.
	 */
	private String currentOutput;
	
	/**
	 * @param state the current state
	 * @param parent the parent configuration
	 * @param totalInput the total input
	 * @param unprocessedInput the unprocessed input
	 */
	public FSTConfiguration(State state, FSTConfiguration parent,
			String totalInput, String unprocessedInput) {
		super(state, parent, totalInput, unprocessedInput);
		currentOutput = "";
	}
	/**
	 * @param state the current state
	 * @param parent the parent configuration
	 * @param totalInput the total input
	 * @param unprocessedInput the unprocessed input
	 * @param currentOutput the current output
	 */
	public FSTConfiguration(State state, FSTConfiguration parent,
			String totalInput, String unprocessedInput, String currentOutput) {
		this(state, parent, totalInput, unprocessedInput);
		this.currentOutput = currentOutput;
	}
	
	/**
	 * Set the current output
	 * @return the current output
	 */
	public String getCurrentOutput() {
		return currentOutput;
	}
}
