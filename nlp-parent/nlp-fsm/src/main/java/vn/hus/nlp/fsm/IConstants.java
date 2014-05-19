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
 * Nov 7, 2007, 8:52:51 PM
 * <p>
 * Some predefined constants for the package.
 */
public interface IConstants {
	
	/**
	 * Blank character.
	 */
	public static final char BLANK_CHARACTER = ' ';
	
	/**
	 * An empty string.
	 */
	public static final String EMPTY_STRING = "";
	
	/**
	 * The package name for JAXB context.
	 */
	public static final String JAXB_CONTEXT = "vn.hus.nlp.fsm.jaxb";

	/**
	 * The Vietnamese lexicon.
	 */
	public final static String VIETNAMESE_LEXICON = "samples/lexicon_v3_set.txt";
	/**
	 * The file that encode Vietnamese lexicon in the form of
	 * a simple DFA.
	 */
	public static final String VIETNAMESE_LEXICON_DFA_SIMPLE = "samples/lexicon_dfa_simple.xml";
	/**
	 * The file that encode Vietnamese lexicon in the form of
	 * a minimal DFA.
	 */
	public static final String VIETNAMESE_LEXICON_DFA_MINIMAL = "samples/lexicon_dfa_minimal.xml";
	
	/**
	 * DFA type.
	 */
	public static final String FSM_DFA = "DFA";
	/**
	 * FST type.
	 */
	public static final String FSM_FST = "FST";
}
