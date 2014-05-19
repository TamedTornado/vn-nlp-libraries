/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.fsm
 * <p>
 * Nov 9, 2007, 3:49:33 PM
 * <p>
 * A configuration event.
 */
public final class ConfigurationEvent {
	
	/**
	 * The parent configuration
	 */
	Configuration parentConf;
	
	/**
	 * The current configuration
	 */
	Configuration conf;
	
	/**
	 * The involved input that take a simulator from parent configuration to the current
	 * configuration. 
	 */
	char input;
	
	/**
	 * The involved output.
	 */
	String output;
	
	/**
	 * Constructor. 
	 * @param parentConf the parent configuration
	 * @param conf current configuration
	 * @param input current input
	 */
	public ConfigurationEvent(Configuration parentConf, Configuration conf, char input) {
		this.parentConf = parentConf;
		this.conf = conf;
		this.input = input;
		this.output = IConstants.EMPTY_STRING;
	}
	/**
	 * Constructor. 
	 * @param parentConf the parent configuration
	 * @param conf configuration
	 * @param input current input
	 * @param output current output
	 */
	public ConfigurationEvent(Configuration parentConf, Configuration conf, char input, String output) {
		this(parentConf, conf, input);
		this.output = output;
	}

	
	public Configuration getParentConfiguration() {
		return parentConf;
	}
	
	public Configuration getConfiguration() {
		return conf;
	}
	
	/**
	 * @return the input
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
		return "[" + parentConf.getCurrentState().getId() + "," + 
			input + ":" + output + "," + conf.getCurrentState().getId() + "]";
	}
}
