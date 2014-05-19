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
 * Nov 5, 2007, 10:21:47 PM
 * <p>
 * Listener for simulator. Simulation keeps a chain of 
 */
public interface ISimulatorListener {
	/**
	 * Performs some operation based on a configuration event.
	 * @param configurationEvent
	 */
	public void update(ConfigurationEvent configurationEvent);
}
