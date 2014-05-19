
package vn.hus.nlp.sd;

import opennlp.tools.sentdetect.AbstractEndOfSentenceScanner;

/**
 * The default end of sentence scanner implements all of the
 * EndOfSentenceScanner methods in terms of the getPositions(char[])
 * method.  It scans for <tt>. ? ! ) and "</tt>.
 * @author phuonglh
 */

public class EndOfSentenceScanner extends AbstractEndOfSentenceScanner {

    public static final char[] eosCharacters =  {'.','?','!'};
    
    /**
     * Creates a new <code>DefaultEndOfSentenceScanner</code> instance.
     *
     */
    public EndOfSentenceScanner() {
        super();
    }
    
    public char[] getEndOfSentenceCharacters() {
      return eosCharacters;
    }
}
