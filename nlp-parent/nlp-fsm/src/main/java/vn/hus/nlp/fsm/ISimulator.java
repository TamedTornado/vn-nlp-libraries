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
 * Nov 5, 2007, 9:50:16 PM
 * <p>
 * A finite state machine simulator. 
 */
public interface ISimulator {

	/**
	 * Track an input on the machine to find a stop configuration.
	 * @param input an input to track
	 * @return the stop configuration of a track.
	 */
	public Configuration track(String input);
	/**
	 * Run the FSM on an input string and get the result.
	 * @param input
	 * @return the result of the run.
	 */
	public String run(String input);
	
	/**
	 * An input is accepted by the machine that the simulator operates on or not. 
	 * @param input an input string
	 * @return <tt>true/false</tt>
	 */
	public boolean accept(String input);
	
}
