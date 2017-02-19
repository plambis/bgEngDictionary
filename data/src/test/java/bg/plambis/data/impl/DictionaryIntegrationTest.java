package bg.plambis.data.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import bg.plambis.data.api.DataSourceProvider;
import bg.plambis.data.api.Dictionary;
import bg.plambis.data.api.Word;

public class DictionaryIntegrationTest {
	private static final int DEAFULT_NUMBER_OF_WORDS = 10;
	private static final String DB_DRIVER_STRING = "org.h2.Driver";
	private static final String EXT_DB_URL_STRING = "jdbc:h2:../plambis/plambis";
	private static final String EXT_DB_USER_NAME = "plambis";
	private static final String EXT_DB_PASSWORD = "plambis";

	@Test
	public void testReadAll() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		List<Word> allWords = dict.getAllWords();
		Assert.assertNotNull(allWords);
	}

	@Test
	public void testReadWord() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		Word testTranslation = dict.getWordTranslation("test");
		Assert.assertNotNull(testTranslation);
	}

	@Test
	public void testReadBGWord() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		Word testTranslation = dict.getWordTranslation("тест");
		Assert.assertNotNull(testTranslation);
	}

	@Test
	public void testReadUsingLetter() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		Word testTranslation = dict.getLetterTranslation("a");
		Assert.assertNotNull(testTranslation);
	}

	@Test
	public void testReadBeforeFirst() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		List<Word> words = dict.getPreviousWords(1, DEAFULT_NUMBER_OF_WORDS);
		Assert.assertNotNull(words);
		// just the first word
		Assert.assertEquals(words.size(), 1);
	}

	@Test
	public void testReadAfterLast() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		List<Word> words = dict.getNextWords(88325, DEAFULT_NUMBER_OF_WORDS);
		Assert.assertNotNull(words);
		// just the first word
		Assert.assertEquals(words.size(), 1);
	}

	@Test
	public void testReadManyTimesFromOneDataSource() {
		Dictionary dict = new DatabaseDictionaryImpl(getDataSourceProvider().createDataSource());
		for (int i = 0; i < 100; i++) {
			Word testTranslation = dict.getWordTranslation("test");
			Assert.assertNotNull(testTranslation);
		}
	}

	private DataSourceProvider getDataSourceProvider() {
		return new DataSourceProviderImpl(DB_DRIVER_STRING, EXT_DB_URL_STRING, EXT_DB_USER_NAME, EXT_DB_PASSWORD);
	}
}
