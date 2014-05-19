package vn.hus.nlp.sd.test;

import vn.hus.nlp.sd.SentenceDetector;
import vn.hus.nlp.sd.SentenceDetectorFactory;

/**
 * 
 * @author Le Hong Phuong, phuonglh@gmail.com
 * <p>
 * Test the sentence detector.
 */
public class SentenceDetectorTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// create a Vietnamese sd
		SentenceDetector sDetector = SentenceDetectorFactory.create("vietnamese");
		
		String inputFile = "src/test/resources/samples/test0.txt";
		String outputFile = "src/test/resources/samples/test0.sd.txt";
		
		sDetector.detectSentences(inputFile, outputFile);
		System.out.println("Done");
	}

}
