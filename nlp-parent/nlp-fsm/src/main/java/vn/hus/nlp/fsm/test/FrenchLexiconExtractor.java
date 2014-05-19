/**
 * (C) LE HONG Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.fsm.test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import vn.hus.nlp.utils.UTF8FileUtility;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 * <p>
 * Created: Feb 17, 2008, 2:52:59 PM
 * <p>
 */
public class FrenchLexiconExtractor {

	static String INPUT_FILE = "samples/dicts/fr/morph.txt";
	
	static String OUTPUT_FILE = "samples/dicts/fr/fr.txt";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> lexicon = new TreeSet<String>();
		String[] lines = UTF8FileUtility.getLines(INPUT_FILE);
		for (int i = 0; i < lines.length; i++) {
			lexicon.add(lines[i]);
		}
		UTF8FileUtility.createWriter(OUTPUT_FILE);
		for (Iterator<String> iterator = lexicon.iterator(); iterator.hasNext(); ) {
			String word = iterator.next();
			UTF8FileUtility.write(word + "\n");
			
		}
		UTF8FileUtility.closeWriter();
		System.out.println("Done");
	}

}
