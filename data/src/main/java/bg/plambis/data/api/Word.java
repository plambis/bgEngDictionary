package bg.plambis.data.api;

/**
 * Base interface for every word in the dictionary
 * 
 * @author pivanov
 */
public interface Word {
	int getId();

	String getWord();

	String getTranslation();
}
