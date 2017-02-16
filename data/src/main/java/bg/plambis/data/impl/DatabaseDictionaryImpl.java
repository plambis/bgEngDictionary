package bg.plambis.data.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bg.plambis.data.api.DataAccessException;
import bg.plambis.data.api.Dictionary;
import bg.plambis.data.api.Word;
import bg.plambis.data.util.DictionaryUtil;

/**
 * 
 * @author plambis
 */
public class DatabaseDictionaryImpl implements Dictionary {
	private static final Log LOG = LogFactory.getLog(DatabaseDictionaryImpl.class);

	private static final String SQL_PREV_NEXT_DATA = "select * from WORDS where id >= ? and id <= ?";
	private static final String SQL_SEL_WORD = "select * from WORDS where WORD like ucase(?)||'%'";
	private static final String SQL_SEL_LETTER = "select * from WORDS where WORD=ucase(?)";
	private static final String SELECT_ALL_WORDS = "select * from WORDS";

	private final DataSource dataSource;

	public DatabaseDictionaryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public List<Word> getAllWords() {
		return executeSqlStatement((Connection conn) -> {
			PreparedStatement ps = conn.prepareStatement(SELECT_ALL_WORDS);
			ps.setFetchSize(10000);
			return ps;
		});
	}

	private List<Word> executeSqlStatement(PreparedStatementCreator psCreator) {
		List<Word> foundWords = new ArrayList<Word>();
		Connection connection = null;
		try {
			connection = getConnection();
			ResultSet res = psCreator.create(connection).executeQuery();
			while (res.next()) {
				int wordIndex = res.getInt(1);
				String word = res.getString(2);
				String translation = res.getString(3);
				foundWords.add(new WordImpl(wordIndex, word, DictionaryUtil.resolveSpecialSymbols(translation)));
			}

		} catch (SQLException ex) {
			LOG.debug("Read data error", ex);
			throw new DataAccessException("Cannot read data!", ex);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {
					LOG.error("Cannot disconnect to database", ex);
					throw new DataAccessException("Cannot disconnect to database", ex);
				}
			}
		}
		return foundWords;
	}

	@Override
	public Word getWordTranslation(String searchWord) {
		return translateWord(searchWord, SQL_SEL_WORD);
	}

	@Override
	public Word getLetterTranslation(String searchLetter) {
		return translateWord(searchLetter, SQL_SEL_LETTER);
	}

	private Word translateWord(String searchWord, String sqlStatement) {
		List<Word> results = executeSqlStatement((Connection con) -> {
			PreparedStatement ps = con.prepareStatement(sqlStatement, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, searchWord);
			ps.setFetchSize(1);
			return ps;
		});
		return results != null && !results.isEmpty() ? results.get(0) : null;
	}

	@Override
	public List<Word> getNextWords(int id, int numberOfWords) {
		return wordsBetween(id, id + numberOfWords, numberOfWords);
	}

	@Override
	public List<Word> getPreviousWords(int id, int numberOfWords) {
		return wordsBetween(id - numberOfWords, id, numberOfWords);
	}

	private List<Word> wordsBetween(int startIndex, int lastIndex, int numberOfWords) {
		return executeSqlStatement((Connection con) -> {
			PreparedStatement ps = con.prepareStatement(SQL_PREV_NEXT_DATA, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, startIndex);
			ps.setInt(2, lastIndex);
			ps.setFetchSize(numberOfWords);
			return ps;
		});
	}

	@FunctionalInterface
	private static interface PreparedStatementCreator {
		PreparedStatement create(Connection connection) throws SQLException;
	}
}
