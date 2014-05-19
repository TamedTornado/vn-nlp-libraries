/**
 * Le Hong Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * 29 juin 2009, 00:58:26
 * <p>
 * This utility iterates all the text file (with suffix .txt) in a directory. The files 
 * are recursively browsed. 
 */
public class FileIterator {

	private FileIterator() {}
	
	/**
	 * Get all files in a directory which satisfy a given file filter.
	 * @param directory a directory to look for files
	 * @param fileFilter a file filter
	 * @return an array of file
	 */
	public static File[] listFiles(File directory, FileFilter fileFilter) {
		List<File> result = new ArrayList<File>();
		if (directory.isDirectory()) {
			// get all sub directories and files in this directory 
			File[] files = directory.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					// recursively get files
					result.addAll(Arrays.asList(listFiles(f, fileFilter)));
				} else {
					if (fileFilter.accept(f)) {
						result.add(f);
					}
				}
			}
		}
		return result.toArray(new File[result.size()]);
	}
	                     
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileFilter textFileFilter = new TextFileFilter();
		File directory = new File("samples");
		File[] files = FileIterator.listFiles(directory, textFileFilter);
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
	}

}
