/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 *         <p>
 *         Nov 7, 2007, 11:16:24 PM
 *         <p>
 *         Abstract base class for simulator interface. DFAClient should subclass
 *         this class for convienence of management of simulator listeners and
 *         other facilities.
 */
public abstract class Simulator implements ISimulator {


	/**
	 * List of simulator listeners
	 */
	protected List<ISimulatorListener> listeners;
	
	/**
	 * Default constructor
	 */
	public Simulator() {
		listeners = new ArrayList<ISimulatorListener>();
	}
	
	/**
	 * Add a simulator listener.
	 * @param simulatorListener
	 */
	public void addSimulatorListener(ISimulatorListener simulatorListener) {
		listeners.add(simulatorListener);
	}
	
	/**
	 * Remove a simulator listener.
	 * @param simulatorListener
	 */
	public void removeSimulatorListener(ISimulatorListener simulatorListener) {
		listeners.remove(simulatorListener);
	}
	

	/**
	 * Notify all registered listeners about a configuration.
	 * @param configEvent
	 */
	public void notify(ConfigurationEvent configEvent) {
		for (Iterator<ISimulatorListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ISimulatorListener simulatorListener = iterator.next();
			simulatorListener.update(configEvent);
		}
	}
	
	/**
	 * Dispose the simulator. Remove all registered listeners.
	 */
	public void dispose() {
		listeners.clear();
		listeners = null;
	}
	

	/* (non-Javadoc)
	 * @see vn.hus.fsm.ISimulator#accept(java.lang.String)
	 */
	public boolean accept(String input) {
		return false;
	}

	/* (non-Javadoc)
	 * @see vn.hus.fsm.ISimulator#run(java.lang.String)
	 */
	public String run(String input) {
		return null;
	}

	/* (non-Javadoc)
	 * @see vn.hus.fsm.ISimulator#track(java.lang.String)
	 */
	public Configuration track(String input) {
		return null;
	}
	
}
