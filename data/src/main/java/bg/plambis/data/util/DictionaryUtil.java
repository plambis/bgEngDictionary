package bg.plambis.data.util;

import org.apache.commons.lang3.StringUtils;

public final class DictionaryUtil {
	private static final String[] SEARCH_FOR = new String[] { "<br />", "&#xe6;", "&#x259;", "&#x283;", "&theta;", "&eth;", "&#x254;", "&#x292;",
			"&#x28c;", "&#x14b;", "&apos;" };
	private static final String[] REPLACE_WITH = new String[] { "\n", "æ", "ə", "ʃ", "θ", "ð", "ɔ", "ʒ", "ʌ", "ŋ", "'" };

	private static final String DB_PATH = "/plambis/plambis";
	public static final String PATH = DictionaryUtil.revertSlashes(DictionaryUtil.getCurrentDir()) + DB_PATH;

	/**
	 * 
	 * @return used os
	 */
	public static int getOS() {
		if ("Linux".equals(System.getProperty("os.name"))) {
			return 1;
		}
		return 0;
	}

	public static String getCurrentDir() {
		return System.getProperty("user.dir");
	}

	public static String revertSlashes(String str) {
		return str.replaceAll("\\\\", "/");
	}

	public static String resolveSpecialSymbols(String word) {
		return StringUtils.replaceEachRepeatedly(word, SEARCH_FOR, REPLACE_WITH);
	}

	public static String getH2ConnectionString(String dbPath) {
		return "jdbc:h2:" + dbPath + ";ACCESS_MODE_DATA=r";
	}
}
