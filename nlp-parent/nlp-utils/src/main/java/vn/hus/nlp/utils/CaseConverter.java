/* -- vnTokenizer 2.0 --
 *
 * Copyright information:
 *
 * LE Hong Phuong, NGUYEN Thi Minh Huyen,
 * Faculty of Mathematics Mechanics and Informatics, 
 * Hanoi University of Sciences, Vietnam.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * 
 * Last update : 04/2005
 * 
 */

package vn.hus.nlp.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * This class is a tool to convert between lowercase and uppercase of
 * Vietnamese characters.
 * @author LE Hong Phuong
 * 
 */
public class CaseConverter {

	public static CaseConverter caseConverter = new CaseConverter(); 
	/**
	 * Instantiate a CaseConverter object
	 */
	private CaseConverter() {
		lower2UpperMap = new HashMap<Character, Character>();
		upper2LowerMap = new HashMap<Character, Character>();
		// init the maps.
		init();
	}

	/**
	 * Test if a lower case is valid or not
	 * 
	 * @param c
	 *            a lower case
	 * @return true/false
	 */
	public static boolean isValidLower(char c) {
		Character ch = new Character(c);
		return (lower2UpperMap.containsKey(ch));
	}

	/**
	 * Test if a upper case is valid or not
	 * 
	 * @param c
	 *            a upper case
	 * @return true/false
	 */
	public static boolean isValidUpper(char c) {
		Character ch = new Character(c);
		return (upper2LowerMap.containsKey(ch));
	}

	
	
	/**
	 * Convert a lowercase character to an uppercase one
	 * 
	 * @param c
	 *            character to convert
	 * @return an uppercase character
	 */
	public static char toUpper(char c) {
		Character upper = (Character) lower2UpperMap.get(new Character(c));
		return upper.charValue();
	}

	/**
	 * Convert an uppercase character to a lowercase one
	 * 
	 * @param c
	 *            character to convert
	 * @return a lowercase character
	 */
	public static char toLower(char c) {
		Character lower = (Character) upper2LowerMap.get(new Character(c));
		return lower.charValue();
	}

	/**
	 * Convert a string to lower case
	 * 
	 * @param st
	 *            a string to convert
	 * @return a lower case string
	 */
	public static String toLower(String st) {
		StringBuffer lowerSt = new StringBuffer(st);
		// convert all char of st to lower case
		for (int i = 0; i < st.length(); i++) {
			char c = st.charAt(i);
			char lowerC = c;
			if ('A' <= c && c <= 'Z') {
				lowerC = Character.toLowerCase(c);
			} else if (CaseConverter.isValidUpper(c))
				lowerC = CaseConverter.toLower(c);
			lowerSt.setCharAt(i, lowerC);
		}
		return lowerSt.toString();

	}
	/**
	 * Initializes the maps
	 *
	 */
	private void init() {
		for (int i = 0; i < lowerCharacters.length; i++) {
			String lowerSt = lowerCharacters[i];
			String upperSt = upperCharacters[i];
			Character lower = new Character((char) Integer.parseInt(
					lowerSt, 16));
			Character upper = new Character((char) Integer.parseInt(
					upperSt, 16));
			lower2UpperMap.put(lower, upper);
			upper2LowerMap.put(upper, lower);
		}
	}
	/**
	 * Create a map file with characters in UTF-8 encoding.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void convert(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(writer);
		Iterator<Character> it = lower2UpperMap.keySet().iterator();
		while (it.hasNext()) {
			Character key = it.next();
			Character value = (Character) lower2UpperMap.get(key);
			// bw.write(Integer.toHexString((int)key.charValue()));
			bw.write(key.toString());
			bw.write("\t");
			// bw.write(Integer.toHexString((int)value.charValue()));
			bw.write(value.toString());
			bw.write("\n");
		}
		bw.close();
	}

	/**
	 * Check to see if a string contains a Vietnamese uppercase character or not.
	 * @param string
	 * @return <tt>true/false</tt>
	 */
	@SuppressWarnings("static-access")
	public static boolean containsUppercase(String string) {
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (Character.isUpperCase(c) || CaseConverter.caseConverter.isValidUpper(c))
				return true;
		}
		return false;
	}
	
	/**
	 * A map between lowercase and uppercase letters
	 */
	private static Map<Character, Character> lower2UpperMap;

	/**
	 * A map between uppercase and lowercase letters
	 */
	private static Map<Character, Character> upper2LowerMap;
	/**
	 * An array of all Vietnamese lowercase characters
	 */
	private String[] lowerCharacters = { "1b0", "1ecb", "1ef1", "1ea7", "1ee7",
			"1edd", "1eb9", "1eb7", "1ef7", "e1", "1ed7", "1eed", "e0", "129",
			"1ecd", "1ea5", "ea", "1eeb", "111", "1ecf", "fa", "1ed9", "e2",
			"1ebd", "f5", "1ea3", "103", "1ee9", "1ec9", "1ea9", "1eb3",
			"1edb", "f9", "e8", "e3", "1ed5", "1eaf", "1eef", "1ef9", "1ec3",
			"f4", "1ead", "169", "e9", "1ee5", "f3", "fd", "1ec5", "1a1",
			"1ebf", "1edf", "1eb5", "1ef5", "1eab", "f2", "1ed1", "1ec7",
			"1ee3", "ec", "ed", "1ea1", "1ef3", "1ed3", "1eb1", "1ebb", "1ec1",
			"1ee1"
			
	};
	/**
	 * An array of all Vietnamese upper case characters that correspond
	 * to the <code>lowerCharacters</code> array.
	 */
	private String[] upperCharacters = { "1af", "1eca", "1ef0", "1ea6", "1ee6",
			"1edc", "1eb8", "1eb6", "1ef6", "c1", "1ed6", "1eec", "c0", "128",
			"1ecc", "1ea4", "ca", "1eea", "110", "1ece", "da", "1ed8", "c2",
			"1ebc", "d5", "1ea2", "102", "1ee8", "1ec8", "1ea8", "1eb2",
			"1eda", "d9", "c8", "c3", "1ed4", "1eae", "1eee", "1ef8", "1ec2",
			"d4", "1eac", "168", "c9", "1ee4", "d3", "dd", "1ec4", "1a0",
			"1ebe", "1ede", "1eb4", "1ef4", "1eaa", "d2", "1ed0", "1ec6",
			"1ee2", "cc", "cd", "1ea0", "1ef2", "1ed2", "1eb0", "1eba", "1ec0",
			"1ee0"
	};
}
