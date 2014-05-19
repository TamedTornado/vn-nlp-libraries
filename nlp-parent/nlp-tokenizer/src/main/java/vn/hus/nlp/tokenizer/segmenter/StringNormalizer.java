/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.tokenizer.segmenter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.nlp.segmenter
 * <p>
 * Nov 15, 2007, 11:51:08 PM
 * <p>
 * An accent normalizer for Vietnamese string. The purpose of
 * this class is to convert a syllable like "hòa" to "hoà",
 * since the lexicon contains only the later form.
 */
public final class StringNormalizer {
	
	private static Map<String, String> map;
	
	private StringNormalizer(String mapFile) {
		map = new HashMap<String, String>();
		init(mapFile);
	}
	
	
	private void init(String mapFile) {
		
		InputStream stream = getClass().getResourceAsStream(mapFile);
		List<String> rules;
		try
		{
			rules = IOUtils.readLines(stream, "UTF-8");

			for (int i = 0;i<rules.size();i++)
			{
				String rule = rules.get(i);
				
				String[] s = rule.split("\\s+");
				if (s.length == 2) {
					map.put(s[0], s[1]);
				} else {
					System.err.println("Wrong syntax in the map file " + mapFile + " at line " + i);
				}
			}
		
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * @return an instance of the class.
	 */
	public static StringNormalizer getInstance() {
		return new StringNormalizer(IConstants.NORMALIZATION_RULES);
	}

	/**
	 * @param properties
	 * @return an instance of the class.
	 */
	public static StringNormalizer getInstance(Properties properties) {
		return new StringNormalizer(properties.getProperty("normalizationRules"));
	}
	
	/**
	 * Normalize a string.
	 * @return a normalized string
	 * @param s a string
	 */
	public String normalize(String s) {
		String result = new String(s);
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String from = it.next();
			String to = map.get(from);
			if (result.indexOf(from) >= 0) {
				result = result.replace(from, to);
			}
		}
		return result;
	}
	
}
