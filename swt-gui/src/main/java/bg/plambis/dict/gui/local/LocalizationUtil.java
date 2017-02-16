package bg.plambis.dict.gui.local;

import java.io.InputStream;
import java.util.Locale;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * The Class LocalizationUtil.
 *
 * @author plambis
 */
public final class LocalizationUtil {

	/** The resources. */
	private static TextResource resources;

	/** The current locale. */
	private static Locale currentLocale;

	/**
	 * Instantiates a new localization util.
	 */
	private LocalizationUtil() {
		// cannot create util
	}

	/**
	 * Gets the i 18 n text.
	 *
	 * @param searchedText
	 *            the searched text
	 * @param locale
	 *            the locale
	 * @return the i 18 n text
	 */
	public static String getI18nText(String searchedText, Locale locale) {
		if (resources == null || currentLocale == null || !currentLocale.equals(locale)) {
			currentLocale = locale;
			loadResources(locale);
		}
		return resources == null ? searchedText : resources.getValue(searchedText);
	}

	/**
	 * Load resources.
	 *
	 * @param locale
	 *            the locale
	 * @return true, if successful
	 */
	public static boolean loadResources(Locale locale) {
		boolean result = false;
		if (locale.getLanguage() != null && locale.getCountry() != null) {
			result = loadResources("_" + locale.getLanguage() + "_" + locale.getCountry().toUpperCase());
		}
		if (locale.getLanguage() != null && !result) {
			result = loadResources("_" + locale.getLanguage());
		}

		if (!result) {
			result = loadResources("");
		}
		return result;
	}

	/**
	 * Load resources.
	 *
	 * @param ext
	 *            the ext
	 * @return true, if successful
	 */
	private static boolean loadResources(String ext) {
		try (InputStream in = LocalizationUtil.class.getResourceAsStream("i18n" + ext + ".xml")) {
			if (in == null) {
				return false;
			}
			Serializer serializer = new Persister();
			resources = serializer.read(TextResource.class, in);
		} catch (Exception e) {
			// do nothing
			return false;
		}
		return true;
	}
}
