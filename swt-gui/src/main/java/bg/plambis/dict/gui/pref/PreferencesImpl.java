package bg.plambis.dict.gui.pref;

import java.util.Locale;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import bg.plambis.dict.gui.trans.GoogleLanguage;

/**
 * Dictionary preferences. They could modified by Preferences Dialog
 * 
 * @author plambis
 */
@Root(name = "preferences")
public class PreferencesImpl implements Preferences {

	/** The int lang. */
	@Element(name = "interface")
	private Locale intLang;

	/** The translate every word. */
	@Element(name = "translateEveryWord")
	private boolean translateEveryWord;

	/** The font name. */
	@Element(name = "fontName")
	private String fontName;

	/** The font size. */
	@Element(name = "fontSize")
	private int fontSize;

	/** The list word number. */
	@Element(name = "listWordNumber")
	private int listWordNumber;

	/** The proxy server. */
	@Element(name = "proxyServer", required = false)
	private String proxyServer;

	/** The proxy port. */
	@Element(name = "proxyPort", required = false)
	private String proxyPort;

	/** The from language. */
	@Element(name = "fromLanguage")
	private GoogleLanguage fromLanguage;

	/** The to language. */
	@Element(name = "toLanguage")
	private GoogleLanguage toLanguage;

	/**
	 * Instantiates a new preferences impl.
	 */
	protected PreferencesImpl() {
		// need for serialization
	}

	/**
	 * Instantiates a new preferences impl.
	 *
	 * @param intLang
	 *            the int lang
	 * @param translateEveryWord
	 *            the translate every word
	 * @param fontName
	 *            the font name
	 * @param fontSize
	 *            the font size
	 * @param listWordNumber
	 *            the list word number
	 * @param proxyServer
	 *            the proxy server
	 * @param proxyPort
	 *            the proxy port
	 * @param fromLanguage
	 *            the from language
	 * @param toLanguage
	 *            the to language
	 */
	public PreferencesImpl(Locale intLang, boolean translateEveryWord, String fontName, int fontSize, int listWordNumber, String proxyServer,
			String proxyPort, GoogleLanguage fromLanguage, GoogleLanguage toLanguage) {
		this.intLang = intLang;
		this.translateEveryWord = translateEveryWord;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.listWordNumber = listWordNumber;
		this.proxyServer = proxyServer;
		this.proxyPort = proxyPort;
		loadProxy();
		this.fromLanguage = fromLanguage;
		this.toLanguage = toLanguage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#loadProxy()
	 */
	@Override
	public void loadProxy() {
		if (proxyPort != null && proxyServer != null) {
			System.setProperty("http.proxyHost", proxyServer);
			System.setProperty("http.proxyPort", proxyPort);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getIntLang()
	 */
	@Override
	public Locale getIntLang() {
		return intLang;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#isTranslateEveryWord()
	 */
	@Override
	public boolean isTranslateEveryWord() {
		return translateEveryWord;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getFontSize()
	 */
	@Override
	public int getFontSize() {
		return fontSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getFontName()
	 */
	@Override
	public String getFontName() {
		return fontName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Locale: " + intLang.toString() + " TranslateEveryWord: " + translateEveryWord + " fontSize: " + fontSize + " FontName: " + fontName
				+ " listWordNumber: " + listWordNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getListWordNumber()
	 */
	@Override
	public int getListWordNumber() {
		return listWordNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getFromLanguage()
	 */
	@Override
	public GoogleLanguage getFromLanguage() {
		return fromLanguage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getProxyPort()
	 */
	@Override
	public String getProxyPort() {
		return proxyPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getProxyServer()
	 */
	@Override
	public String getProxyServer() {
		return proxyServer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bg.plambis.dict.gui.pref.Preferences#getToLanguage()
	 */
	@Override
	public GoogleLanguage getToLanguage() {
		return toLanguage;
	}

}
