/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm.test;

import vn.hus.nlp.fsm.IConstants;
import vn.hus.nlp.fsm.builder.FSMBuilder;
import vn.hus.nlp.fsm.builder.MinimalFSMBuilder;
import vn.hus.nlp.fsm.util.FSMUtilities;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.fsm
 * <p>
 * Nov 9, 2007, 5:16:23 PM
 * <p>
 * Test minimal DFA builder.
 */
public class MinimalDFAClient {
	
	public static String ENGLISH_LEXICON_TXT = "samples/dicts/en/en.txt";
	public static String ENGLISH_LEXICON_XML = "samples/dicts/en/en.xml";
	public static String FRENCH_LEXICON_TXT = "samples/dicts/fr/fr.txt";
	public static String FRENCH_LEXICON_XML = "samples/dicts/fr/fr.xml";
	public static String VIETNAMESE_LEXICON_TXT = "samples/dicts/vn/vn.txt";
	public static String VIETNAMESE_LEXICON_XML = "samples/dicts/vn/vn.xml";
	/**
	 * Create a minimal DFA from a dictionary file and encode the result
	 * as an XML file.
	 * @param dictionary a dictionary file
	 * @param dfa the output dictionary XML file
	 */
	public static void createMinimalDFA(String dictionary, String dfa) {
		// create an FSM builder of type DFA.
		//
		FSMBuilder  builder = new MinimalFSMBuilder(IConstants.FSM_DFA);
		builder.create(dictionary);
		// encode the result 
		builder.encode(dfa);
		// print some statistic of the DFA:
		FSMUtilities.statistic(builder.getMachine());
		// dispose the builder to save memory
		builder.dispose();
	}
	
	public static void createMinimalEnglishDFA() {
		System.out.println("Encode English lexicon...");
		createMinimalDFA(ENGLISH_LEXICON_TXT, ENGLISH_LEXICON_XML);
	}
	public static void createMinimalFrenchDFA() {
		System.out.println("Encode French lexicon...");
		createMinimalDFA(FRENCH_LEXICON_TXT, FRENCH_LEXICON_XML);
	}
	
	public static void createMinimalVietnameseDFA() {
		System.out.println("Encode Vietnamese lexicon...");
		createMinimalDFA(VIETNAMESE_LEXICON_TXT, VIETNAMESE_LEXICON_XML);
	}
	
	public static void main(String[] args) {
//		createMinimalEnglishDFA();
//		createMinimalFrenchDFA();
		createMinimalVietnameseDFA();
	}
}
