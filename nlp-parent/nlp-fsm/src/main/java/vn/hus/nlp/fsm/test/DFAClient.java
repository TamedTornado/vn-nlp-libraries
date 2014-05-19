/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm.test;

import java.util.Arrays;

import vn.hus.nlp.fsm.FSM;
import vn.hus.nlp.fsm.IConstants;
import vn.hus.nlp.fsm.builder.FSMBuilder;
import vn.hus.nlp.fsm.builder.MinimalFSMBuilder;
import vn.hus.nlp.fsm.builder.SimpleFSMBuilder;
import vn.hus.nlp.fsm.io.FSMUnmarshaller;
import vn.hus.nlp.fsm.util.FSMUtilities;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.fsm
 * <p>
 * Nov 9, 2007, 5:16:23 PM
 * <p>
 */
public class DFAClient {
	
	/**
	 * Test some operations on fsm.
	 */
	public static void testOperations() {
		String[] items = {"ab", "abc", "de", "df"};
		FSMBuilder  builder = new SimpleFSMBuilder(IConstants.FSM_DFA);
		builder.create(items);
		builder.printMachine();
		
		// TEST TRANSITION REMOVAL
		
//		System.out.println("Remove transition (4,5): ");
//		FSM dfa = builder.getDFA();
//		dfa.removeTransition(dfa.getTransition(4, 5));
//		builder.printDFA();
//		System.out.println("Remove transition (2,3): ");
//		dfa.removeTransition(dfa.getTransition(2, 3));
//		builder.printDFA();
		
		// TEST STATE REMOVAL
		System.out.println("Remove state 3: ");
		FSM dfa = builder.getMachine();
		dfa.removeState(dfa.getState(3));
		builder.printMachine();
		
		
		System.out.println("Remove state 4: ");
		dfa.removeState(dfa.getState(4));
		builder.printMachine();
		
		builder.dispose();
	}
	
	public static void testSimpleDFABuilder() {
//		String[] inputs = {};
//		String[] inputs = {"a"};
//		String[] inputs = {"ab"};
//		String[] inputs = {"a b"};
//		String[] inputs = {"a b", "c d"};
		String[] inputs = {"ab", "abc", "de", "df"};
//		String[] inputs = {"ab", "a"};
		FSMBuilder  builder = new SimpleFSMBuilder(IConstants.FSM_DFA);
		builder.create(inputs);
		builder.printMachine();
//		builder.create(IConstants.VIETNAMESE_LEXICON);
//		builder.encode(IConstants.VIETNAMESE_LEXICON_DFA_SIMPLE);
		builder.dispose();
	}
	
	public static void testMinimalDFABuilder1() {
//		String[] inputs = {"ab", "abc", "d", "dc"};
//		String[] inputs = {"ab", "ac", "db", "dc"};
		String[] inputs = {"bo", "ba"};
		// make sure that the input is sorted
		Arrays.sort(inputs, 0, inputs.length);
		FSMBuilder  builder = new MinimalFSMBuilder(IConstants.FSM_DFA);
		builder.create(inputs);
		builder.printMachine();
		builder.dispose();
	}

	/**
	 * Build the minimal automaton representing the days of 
	 * a week. 
	 */
	public static void testMinimalDFABuilder2() {
		FSMBuilder  builder = new MinimalFSMBuilder(IConstants.FSM_DFA);
//		builder.create("samples/days.txt");
//		builder.create("samples/months.txt");
//		builder.create("samples/aimer.txt");
//		builder.create("samples/sample0.txt");
//		builder.create("samples/sample1.txt");
//		builder.printMachine();
		builder.create(IConstants.VIETNAMESE_LEXICON);
//		builder.encode(IConstants.VIETNAMESE_LEXICON_DFA_MINIMAL);
		builder.dispose();
	}
	
	public static void testFSMUnmarshaller() {
		FSMUnmarshaller unmarshaller = new FSMUnmarshaller();
		FSM dfa;
//		dfa = unmarshaller.unmarshal("samples/months.xml");
//		new FSMMarshaller().marshal(dfa, System.out);
//		dfa = unmarshaller.unmarshal(IConstants.VIETNAMESE_LEXICON_DFA_SIMPLE, IConstants.FSM_DFA);
		dfa = unmarshaller.unmarshal(IConstants.VIETNAMESE_LEXICON_DFA_MINIMAL, IConstants.FSM_DFA);
		FSMUtilities.statistic(dfa);
	}
	
	public static void main(String[] args) {
//		testOperations();
//		testSimpleDFABuilder();
		testMinimalDFABuilder1();
//		testMinimalDFABuilder2();
//		testFSMUnmarshaller();
	}
}
