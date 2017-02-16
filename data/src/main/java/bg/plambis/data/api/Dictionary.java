package bg.plambis.data.api;

import java.util.List;

/**
 * add responsibility to find words in dictionary
 * 
 * @author pivanov
 */
public interface Dictionary {
	Word getWordTranslation(String searchWord);

	Word getLetterTranslation(String searchLetter);

	List<Word> getPreviousWords(int id, int numberOfWords);

	List<Word> getNextWords(int id, int numberOfWords);

	List<Word> getAllWords();
}
