/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.utils;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * vn.hus.nlp.utils
 * <p>
 * Oct 5, 2007, 3:45:53 PM
 * <p>
 * A file listener that provides information during the process of reading a file.
 * For example, with a text file, it reports what line has been read. 
 */
public interface FileListener {
	/**
	 * Report the read line.
	 * @param line the line
	 * @param lineNumber the line number
	 */
	public void processed(String line, int lineNumber);
}
