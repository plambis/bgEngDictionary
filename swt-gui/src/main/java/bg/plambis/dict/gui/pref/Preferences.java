package bg.plambis.dict.gui.pref;

import java.util.Locale;

import bg.plambis.dict.gui.trans.GoogleLanguage;

/**
 * Dictionary preferences. They could modified by Preferences Dialog
 * 
 * @author plambis
 */
public interface Preferences {

	/**
	 * Gets the int lang.
	 *
	 * @return the int lang
	 */
	public Locale getIntLang();

	/**
	 * Checks if is translate every word.
	 *
	 * @return true, if is translate every word
	 */
	public boolean isTranslateEveryWord();

	/**
	 * Gets the font name.
	 *
	 * @return the font name
	 */
	public String getFontName();

	/**
	 * Gets the font size.
	 *
	 * @return the font size
	 */
	public int getFontSize();

	/**
	 * Gets the list word number.
	 *
	 * @return the list word number
	 */
	public int getListWordNumber();

	/**
	 * Gets the proxy server.
	 *
	 * @return the proxy server
	 */
	public String getProxyServer();

	/**
	 * Gets the proxy port.
	 *
	 * @return the proxy port
	 */
	public String getProxyPort();

	/**
	 * Gets the from language.
	 *
	 * @return the from language
	 */
	public GoogleLanguage getFromLanguage();

	/**
	 * Gets the to language.
	 *
	 * @return the to language
	 */
	public GoogleLanguage getToLanguage();

	/**
	 * Load proxy.
	 */
	public void loadProxy();
}
