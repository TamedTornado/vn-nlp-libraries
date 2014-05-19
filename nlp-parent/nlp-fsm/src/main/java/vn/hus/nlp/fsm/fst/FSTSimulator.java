/**
 * (C) LE HONG Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.fsm.fst;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.hus.nlp.fsm.ConfigurationEvent;
import vn.hus.nlp.fsm.ISimulatorListener;
import vn.hus.nlp.fsm.Simulator;
import vn.hus.nlp.fsm.State;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 *         <p>
 *         Jan 24, 2008, 11:50:42 PM
 *         <p>
 *         An implementation of deterministic FST simulator. It is similar to a
 *         DFA simulator but need to specify method {@link #run(String)}. This
 *         method returns the output of the machine.
 */
public class FSTSimulator extends Simulator {

	/**
	 * The underlying fst on which the simulator runs.
	 */
	protected FST fst;
	
	/**
	 * The configuration the machine could possibly be in at a given moment in
	 * the simulation.
	 */
	protected FSTConfiguration configuration = null;
	
	/**
	 * A simple logger for the simulator.
	 */
	protected SimulatorLogger logger = null;

	/**
	 * Print out the trace of the simulator or not (DEBUG mode).
	 */
	private final boolean DEBUG = false;
	/**
	 * @author LE HONG Phuong, phuonglh@gmail.com
	 * <p>
	* Jan 25, 2008, 9:45:28 PM
	* <p>
	* A simple logger for the {@link FSTSimulator} to log its processing.
	 */
	class SimulatorLogger implements ISimulatorListener {

		private final Logger logger;
		
		public SimulatorLogger() {
			logger = Logger.getLogger(FSTSimulator.class.getName());
			// use a console handler to trace the log
			logger.addHandler(new ConsoleHandler());
			logger.setLevel(Level.INFO);
		}
		
		
		public void update(ConfigurationEvent configurationEvent) {
			// log the configuration event
			logger.log(Level.INFO, configurationEvent.toString());
		}
		
	}
	
	/**
	 * @param fst the fst that this simulator operates on.
	 */
	public FSTSimulator(FST fst) {
		super();
		this.fst = fst;
		if (DEBUG) {
			logger = new SimulatorLogger();
			addSimulatorListener(logger);
		}
	}

	/**
	 * Find the next configuration of the FST.
	 * 
	 * @param configuration
	 * @return The next configuration of current configuration or null if the
	 *         simulator cannot go further.
	 */
	protected FSTConfiguration next(FSTConfiguration configuration) {
		FSTConfiguration nextConfiguration = null;
		// get information of current configuration
		State currentState = configuration.getCurrentState();
		String unprocessedInput = configuration.getUnprocessedInput();
		String currentOutput = configuration.getCurrentOutput();
		int len = unprocessedInput.length();
		if (len > 0) {
			// get all inputs of outtransitions of the current state
			char[] outTransitionInputs = currentState.getOutTransitionInputs();
			// get the first character of the unprocessed input
			char nextInput = unprocessedInput.charAt(0);
			// find the next configuration
			for (int i = 0; i < outTransitionInputs.length; i++) {
				if (outTransitionInputs[i] == nextInput) {
					// get the next state (possible null)
					State nextState = fst.getNextState(currentState, nextInput);
					// get the output
					String nextOutput = fst.getNextOutput(currentState, nextInput);
					if (nextState != null) {
						// create the next configuration
						if (unprocessedInput.length() > 0) {
							unprocessedInput = unprocessedInput.substring(1);
							currentOutput += nextOutput; 
						}
						nextConfiguration = new FSTConfiguration(nextState, configuration, 
								configuration.getTotalInput(), unprocessedInput, currentOutput);
						// create a configuration event and notify all registered listeners
						if (DEBUG) {
							notify(new ConfigurationEvent(configuration, 
									nextConfiguration, nextInput, nextOutput)); // DEBUG
						}
					}
				}
			}
		}
		return nextConfiguration;
	}
	
	/**
	 * Track an input on the FST.
	 * 
	 * @param input
	 *            an input
	 * @return the configuration at which the machine cannot go further on the
	 *         input.
	 */
	public FSTConfiguration track(String input) {
		// create the initial configuration of the simulation
		// that start at the initial state of the machine, has no parent
		// (null), input, and output.
		configuration = new FSTConfiguration(fst.getInitialState(), null, input, input, "");
		
		while (configuration != null) {
			// get the next configuration
			FSTConfiguration nextConfiguration = next(configuration);
			// if the simulator cannot go further
			if (nextConfiguration == null) {
				return configuration;
			}
			configuration = nextConfiguration;
		}
		// return the initial state if
		// there is not any part of the input that is accepted by
		// the machine
		return configuration;
	}
	
	@Override
	public boolean accept(String input) {
		// first track the input
		FSTConfiguration configuration = track(input);
		// the input is accepted if the current state is final
		// and there is no unprocessed input
		return (configuration.getCurrentState().isFinalState() && 
				(configuration.getUnprocessedInput().length() == 0));
	}
	/* (non-Javadoc)
	 * @see vn.hus.fsm.fsa.DFASimulator#run(java.lang.String)
	 */
	@Override
	public String run(String input) {
		// track the input
		FSTConfiguration configuration = track(input);
		// get the output
		return configuration.getCurrentOutput();
	}
}
