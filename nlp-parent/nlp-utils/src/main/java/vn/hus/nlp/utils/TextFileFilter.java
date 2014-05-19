/**
 * Le Hong Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * 29 juin 2009, 00:50:31
 * <p>
 * Text file filter.
 */
public class TextFileFilter implements FileFilter {

	private String extension = ".txt";
	
	/**
	 * Default constructor.
	 */
	public TextFileFilter() {
		// do nothing, use the default extension
	}
	
	/**
	 * Constructs a text file filter given an extension.
	 * @param extension
	 */
	public TextFileFilter(String extension) {
		this.extension = extension;
	}
	
	
	/* (non-Javadoc)
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File pathname) {
		if (!pathname.isFile()) {
			return false;
		}
		if (pathname.getName().endsWith(extension)) {
			return true;
		}
		return false;
	}

}
