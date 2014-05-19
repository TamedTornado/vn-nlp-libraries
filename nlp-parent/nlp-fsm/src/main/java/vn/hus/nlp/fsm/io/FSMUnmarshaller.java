/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm.io;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import vn.hus.nlp.fsm.FSM;
import vn.hus.nlp.fsm.IConstants;
import vn.hus.nlp.fsm.State;
import vn.hus.nlp.fsm.Transition;
import vn.hus.nlp.fsm.fsa.DFA;
import vn.hus.nlp.fsm.fst.FST;
import vn.hus.nlp.fsm.jaxb.Fsm;
import vn.hus.nlp.fsm.jaxb.ObjectFactory;
import vn.hus.nlp.fsm.jaxb.S;
import vn.hus.nlp.fsm.jaxb.States;
import vn.hus.nlp.fsm.jaxb.T;
import vn.hus.nlp.fsm.jaxb.Transitions;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.fsm
 * <p>
 * Nov 6, 2007, 10:32:24 PM
 * <p>
 */
public class FSMUnmarshaller {
	
	private JAXBContext jaxbContext; 
	
	private Unmarshaller unmarshaller;
	
	/**
	 * Default constructor.
	 */
	public FSMUnmarshaller() {
		// create JAXB context
		//
		createContext();
	}
	
	private void createContext() {
		jaxbContext = null;
		try {
			ClassLoader cl = ObjectFactory.class.getClassLoader();
			jaxbContext = JAXBContext.newInstance(IConstants.JAXB_CONTEXT, cl);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get the marshaller object.
	 * @return the marshaller object.
	 */
	public Unmarshaller getUnmarshaller() {
		if (unmarshaller == null) {
			try {
				// create the marshaller
				unmarshaller = jaxbContext.createUnmarshaller();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return unmarshaller;
	}

	/**
	 * Unmarshal a fsm from a file.
	 * @param filename a file.
	 * @return a state machine
	 */
	public FSM unmarshal(String filename, String machineType) {
		FSM fsm;
		if (machineType.equalsIgnoreCase(IConstants.FSM_DFA)) 
			fsm = new DFA();
		else {
//			System.out.println("Unmarshal a FST");
			fsm = new FST();
		}
		
		getUnmarshaller();
		try {
			
			InputStream stream = getClass().getResourceAsStream(filename);
			
			Object obj = unmarshaller.unmarshal(stream);
			if (obj != null) {
				Fsm fsm2 = (Fsm)obj;
				// fill the states 
				States states = fsm2.getStates();
				for (Iterator<S> it = states.getS().iterator(); it.hasNext();) {
					S s = it.next();
					State state = new State(s.getId()); 
					state.setType(s.getType());
					fsm.addState(state);
				}
				// fill the transitions
				Transitions transitions = fsm2.getTransitions();
				for (Iterator<T> it = transitions.getT().iterator(); it.hasNext();) {
					T t = it.next();
					char input = t.getInp().charAt(0);
					String output = t.getOut();
					Transition transition;
					if (output != null && output.equals(IConstants.EMPTY_STRING)) {
						transition = new Transition(t.getSrc(), t.getTar(), input);
					} else {
						transition = new Transition(t.getSrc(), t.getTar(), input, output);
					}
					fsm.addTransition(transition);
				}
				
			}
		} catch (JAXBException e) {
			System.out.println("Error when unmarshalling the machine.");
			e.printStackTrace();
		}
		return fsm;
	}
	
}
