/**
 * (C) LE HONG Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.fsm.fst;

import vn.hus.nlp.fsm.FSM;
import vn.hus.nlp.fsm.ISimulator;
import vn.hus.nlp.fsm.Simulator;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 * <p>
 * Jan 24, 2008, 11:43:49 PM
 * <p>
 * Finite-state transducer.
 */
public class FST extends FSM {

	/**
	 * Default constructor.
	 */
	public FST() {
		super();
	}

	/* (non-Javadoc)
	 * @see vn.hus.fsm.FSM#getSimulator()
	 */
	@Override
	public ISimulator getSimulator() {
		return new FSTSimulator(this);
	}

	/* (non-Javadoc)
	 * @see vn.hus.fsm.FSM#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		((Simulator)getSimulator()).dispose();
	}
}
