/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.sd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import vn.hus.nlp.utils.UTF8FileUtility;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 *         <p>
 *         Jan 15, 2008, 11:50:28 PM
 *         <p>
 *         This is the general sentence detector for texts. It uses a maximum
 *         entropy model pretrained on an ensemble of texts. All the texts are supposed
 *         to be encoded in UTF-8 encoding.
 */
public class SentenceDetector extends SentenceDetectorME {

	/**
	 * Loads a new sentence detector using the model specified by the model
	 * name.
	 * 
	 * @param modelName
	 *            The name of the maxent model trained for sentence detection.
	 * @throws IOException
	 *             If the model specified can not be read.
	 */
	public SentenceDetector(String modelName) throws IOException {
		
		// NOTE: this may not be right!
		super(new SentenceModel(new File(modelName)));
	}

	public SentenceDetector(InputStream stream) throws IOException
	{
		super(new SentenceModel(stream));
	}
	
	/**
	 * @param properties
	 * @throws IOException
	 */
	public SentenceDetector(Properties properties) throws IOException {
		this(properties.getProperty("sentDetectionModel"));
//		System.out.println("Sentence detection model = " + properties.getProperty("sentDetectionModel"));
	}

	/**
	 * Performs sentence detection on a reader, returns an array of detected sentences.
	 * @param reader a reader
	 * @return an array of sentences
	 * @throws IOException 
	 */
	public String[] detectSentences(Reader reader) throws IOException {
		BufferedReader bufReader = new BufferedReader(reader);
		List<String> sentences = new ArrayList<String>();
		for (String line = bufReader.readLine(); line != null; line = bufReader.readLine()) {
			if (line.trim().length() > 0) {
				// detect the sentences composing the line
				String[] sents = sentDetect(line);
				// add them to the list of results
				for (String s : sents) {
					sentences.add(s.trim());
				}
			}
		}
		// close the reader
		if (reader != null)
			reader.close();
		return sentences.toArray(new String[sentences.size()]);
	}
	
	/**
	 * Performs sentence detection a text file, returns an array of detected sentences.
	 * @param inputFile input file name
	 * @return an array of sentences
	 * @throws IOException 
	 */
	public String[] detectSentences(String inputFile) throws IOException {
		return detectSentences(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
	}
	
	public String[] detectSentences(InputStream stream) throws IOException
	{
		return detectSentences(new InputStreamReader(stream, "UTF-8"));
	}
	
	/**
	 * Detects sentences of a text file, write results to an output file.
	 * @param inputFile an input file
	 * @param outputFile the result of the detection.
	 */
	public void detectSentences(String inputFile, String outputFile) {
		try {
			UTF8FileUtility.createWriter(outputFile);
			String[] sentences = detectSentences(inputFile);
			for (int i = 0; i < sentences.length; i++) {
				String s = sentences[i];
				UTF8FileUtility.write(s + "\n");
			}
			UTF8FileUtility.closeWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	

	public static void main(String[] args) {
		Options options = new Options();
		
		// create obligatory input/output options
		Option inpOpt = new Option("i", true, "Input filename");
		options.addOption(inpOpt);
		
		Option outOpt = new Option("o", true, "Output filename");
		options.addOption(outOpt);

		
		// a help formatter
		HelpFormatter formatter = new HelpFormatter();;
		
		if (args.length < 2) {
			// automatically generate the help statement
			formatter.printHelp("vnSentDetector", options);
			System.exit(1);
		}
		
		CommandLineParser commandLineParser = new PosixParser();
		try {
			CommandLine commandLine = commandLineParser.parse(options, args);
			
			String inputFile = commandLine.getOptionValue("i");
			if (inputFile == null) {
				System.err.println("Input filename is required.");
				formatter.printHelp( "vnSentDetector:", options );
				System.exit(1);
			}
			
			String outputFile = commandLine.getOptionValue("o");
			if (outputFile == null) {
				System.err.println("Output filename is required.");
				formatter.printHelp( "vnSentDetector:", options );
				System.exit(1);
			}
			
			// create the sent detector
			SentenceDetector sDetector = SentenceDetectorFactory.create("vietnamese");
			// detect sentences
			sDetector.detectSentences(inputFile, outputFile);
			
			System.out.println("Done.");
		} catch (ParseException exp) {
			System.err.println( "Parsing failed.  Reason: " + exp.getMessage());
			formatter.printHelp("vnSentDetector", options);
		}
		
	}
}
