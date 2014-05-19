package vn.hus.nlp.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LE Hong Phuong
 *         <p>
 *         22 mars 07
 *         </p>
 *         A file that facilitates input/output stuff of an UTF8 text file.
 *         Typically, you should use this utility to read all lines of a text
 *         file in UTF8 encoding to an array of strings by method
 *         {@link #getLines(String)}. If each line contains information that
 *         separated by a tab character (for example each line in the list of
 *         Vietnamese words contains two parts: the word and its part-of-speech
 *         information), you can set the tabSeparator on. 
 * 
 */
public final class UTF8FileUtility {
	/**
	 * A buffered writer
	 */
	public static BufferedWriter writer = null;
	/**
	 * A buffered reader
	 */
	public static BufferedReader reader = null;

	/**
	 * All registered file listeners
	 */
	static List<FileListener> fileListeners = new ArrayList<FileListener>();
	/**
	 * @author Le Hong Phuong, phuonglh@gmail.com
	 * <p>
	 * vn.hus.utils
	 * <p>
	 * Oct 2, 2007, 9:02:02 PM
	 * <p>
	 * A lexical entry (item/pos)
	 */
	class Item {
		private String item;
		private String pos;
		
		public Item() {
			item = "";
			pos = "";
		}
		
		public Item(String item, String pos) {
			this.item = item;
			this.pos = pos;
		}
		
		public String getItem() {
			return item;
		}
		
		public String getPos() {
			return pos;
		}
	}

	/**
	 * Use static methods only.
	 */
	private UTF8FileUtility() {
	}

	/**
	 * Create a buffered reader to read from a UTF-8 text file.
	 * 
	 * @param filename
	 */
	public static void createReader(String filename) {
		try {
			createReader(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a buffered reader to read from an input stream.
	 * 
	 * @param inputStream
	 */
	private static void createReader(InputStream inputStream) {
		try {
			// first, try to close the reader if it has already existed and has not been closed
			closeReader();
			// create the reader
			Reader iReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(iReader);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close the reader
	 */
	public static void closeReader() {
		try {
			if (reader != null) {
				reader.close();
				reader = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a buffered writer to write to a UTF-8 text file.
	 * 
	 * @param filename
	 */
	public static void createWriter(String filename) {
		try {
			createWriter(new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a buffered writer given an output stream
	 * 
	 * @param outputStream
	 */
	private static void createWriter(OutputStream outputStream) {
		try {
			// first, try to close the writer if it has already existed and has not been closed
			closeWriter();
			Writer oWriter = new OutputStreamWriter(outputStream, "UTF-8");
			writer = new BufferedWriter(oWriter);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close the writer
	 */
	public static void closeWriter() {
		try {
			// flush and close the writer
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Process a line (remove the substring after the tab character if
	 * neccesary)
	 * 
	 * @param line
	 *            a line
	 * @return the first part (before the tab character) of the line
	 */
	private static String processTab(String line) {
		int tabIndex = line.indexOf('\t');
		if (tabIndex > 0) {
			return line.substring(0, tabIndex).trim();
		} else {
			return line.trim();
		}
	}

	/**
	 * Get all lines of a file. This creates the reader, read all non-empty
	 * lines to an array of string and close the reader. If there is a POS
	 * in a file, bypass it by calling method {@link #processTab(String)}.
	 * 
	 * @param fileName
	 * @return an array of strings
	 */
	public static String[] getLinesWithPOS(String fileName) {
		List<String> lines = new ArrayList<String>();
		if (reader == null)
			createReader(fileName);
		String line = null;
		int lineNumber = 0;
		try {
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (line.trim().length() > 0) {
					String item = processTab(line); 
					if (item.length() > 0) 
						lines.add(item);
				}
				// notify listeners about the current line
				//
				notify(line, lineNumber);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeReader();
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Gets a list of lines from a file.
	 * @param fileName
	 * @return a list of strings
	 */
	public static List<String> getLineList(String fileName) {
		List<String> list = new ArrayList<String>();
		String[] lines = getLines(fileName);
		for (String line : lines) {
			list.add(line);
		}
		return list;
	}
	
	/**
	 * Get all lines of the file (including POS information).
	 * @param fileName
	 * @return
	 */
	public static String[] getLines(String fileName) {
		List<String> lines = new ArrayList<String>();
		if (reader == null)
			createReader(fileName);
		String line = null;
		int lineNumber = 0;
		try {
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (line.trim().length() > 0) {
					lines.add(line.trim());
				}
				// notify listeners about the current line
				//
				notify(line, lineNumber);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeReader();
		return lines.toArray(new String[lines.size()]);
	}
	
	/**
	 * Gets a list of lines of a file. 
	 * @param fileName
	 * @return a list of strings
	 */
	public static List<String> getLinesAsList(String fileName) {
		String[] lines = getLines(fileName);
		return  Arrays.asList(lines);
	}
	
	/**
	 * Return next line of the reader
	 * 
	 * @return a string
	 */
	public static String readLine() {
		try {
			return reader.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Write a line to the writer without a new line.
	 * 
	 * @param line
	 */
	public static void write(String line) {
		try {
			writer.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write a line to the writer, a new line is added at the end. 
	 * @param line
	 */
	public static void writeln(String line) {
		try {
			writer.write(line);
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write an array of lines to the writer, each on a new line.
	 * 
	 * @param lines
	 */
	public static void write(String[] lines) {
		for (int j = 0; j < lines.length; j++) {
			writeln(lines[j]);
		}
	}
	
	/**
	 * Write an array of objects to the writer, each on a new line.
	 * @param objects an array of objects.
	 */
	public static void write(Object[] objects) {
		for (int j = 0; j < objects.length; j++) {
			writeln(objects.toString());
		}
	}

	/**
	 * Add a file listener.
	 * @param listener
	 */
	public static void addFileListener(FileListener listener) {
		fileListeners.add(listener);
	}
	
	/**
	 * 
	 * Remove a file listener.
	 * @param listener
	 */
	public static void removeFileListener(FileListener listener) {
		if (fileListeners.contains(listener))
			fileListeners.remove(listener);
	}
	
	/**
	 * Notify all registered listeners about the current
	 * processed line. 
	 * @param line
	 * @param lineNumber
	 */
	public static void notify(String line, int lineNumber) {
		for (FileListener listener : fileListeners) {
			listener.processed(line, lineNumber);
		}
	}
}
