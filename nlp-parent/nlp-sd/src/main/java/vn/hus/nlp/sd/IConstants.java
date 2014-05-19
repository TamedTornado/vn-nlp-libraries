/**
 * (C) LE HONG Phuong, phuonglh@gmail.com
 */
package vn.hus.nlp.sd;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 * <p>
 * Jan 16, 2008, 12:29:09 AM
 * <p>
 * Some constants for use in the package.
 */
public interface IConstants {
	/**
	 * The French language
	 */
	public static final String LANG_FRENCH = "french";
	/**
	 * The model filename for French.
	 */
	public static final String MODEL_NAME_FRENCH = "models/sentDetection/FrenchSD.bin.gz";
	
	/**
	 * Training data for French
	 */
	public static final String TRAINING_DATA_FRENCH = "data/lang/fr/training.xml"; 
	
	/**
	 * The Vietnamese language
	 */
	public static final String LANG_VIETNAMESE = "vietnamese";
	
	/**
	 * The model filename for Vietnamese 
	 */
	static final String MODEL_NAME_VIETNAMESE = "/models/sentDetection/VietnameseSD.zip";
	
	/**
	 * Training data for Vietnamese
	 */
	public static final String TRAINING_DATA_VIETNAMESE = "training-vn.txt";
}

