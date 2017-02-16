package bg.plambis.dict.gui.pref;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import bg.plambis.dict.gui.trans.GoogleLanguage;

/**
 * Singleton dictionary preferences manager.
 *
 * @author plambis
 */
public final class PreferencesManager {

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(PreferencesManager.class);

	/** The Constant CONF_FILE_PATH. */
	private static final String CONF_FILE_PATH = "conf.xml";

	/** The Constant MAX_WORDS. */
	public final static int MAX_WORDS = 20;

	/** The Constant PREFERENCES. */
	private static final Preferences PREFERENCES = new PreferencesImpl(Locale.ENGLISH, true,
			Display.getDefault().getSystemFont().getFontData()[0].getName(), Display.getDefault().getSystemFont().getFontData()[0].getHeight(),
			MAX_WORDS, "", "", GoogleLanguage.ENGLISH, GoogleLanguage.BULGARIAN);

	/** The Constant INSTANCE. */
	private static final PreferencesManager INSTANCE = new PreferencesManager();

	/** The current pref. */
	private Preferences currentPref = null;

	/** The font. */
	private Font font = Display.getDefault().getSystemFont();

	/** flag that help to reload new font. */
	private boolean reloadFont;

	/** The pref change observers. */
	// observers for preference change
	private Map<String, PrefChangeObserver> prefChangeObservers = new HashMap<String, PrefChangeObserver>();

	/**
	 * Instantiates a new preferences manager.
	 */
	private PreferencesManager() {
		// do nothing
	}

	/**
	 * Gets the single instance of PreferencesManager.
	 *
	 * @return single instance of PreferencesManager
	 */
	public static PreferencesManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets the current preferences.
	 *
	 * @return the current preferences
	 */
	public Preferences getCurrentPreferences() {
		if (currentPref == null) {
			if (!loadPreferences()) {
				currentPref = PREFERENCES;
			}
		}
		return currentPref;
	}

	/**
	 * Save preferences.
	 *
	 * @param pref
	 *            the pref
	 * @return true, if successful
	 */
	public boolean savePreferences(Preferences pref) {
		File result = new File(CONF_FILE_PATH);
		try {
			Serializer serializer = new Persister();
			serializer.write(pref, result);
		} catch (Exception e) {
			LOG.debug("Cannot parse XML", e);
			return false;
		}
		return loadPreferences();
	}

	/**
	 * Load preferences.
	 *
	 * @return true, if successful
	 */
	private boolean loadPreferences() {
		File in = new File(CONF_FILE_PATH);
		if (!in.exists()) {
			return false;
		}
		try {
			Serializer serializer = new Persister();
			currentPref = serializer.read(PreferencesImpl.class, in);
			currentPref.loadProxy();
			reloadFont = true;
			getCurrentFont();
			notifyPrefObservers();
		} catch (Exception e) {
			LOG.debug("Cannot parse XML", e);
			return false;
		}
		return currentPref != null;
	}

	/**
	 * Gets the current font.
	 *
	 * @return the current font
	 */
	public Font getCurrentFont() {
		if (font == null || reloadFont) {
			reloadFont = false;
			font = Display.getDefault().getSystemFont();
			if (getCurrentPreferences().getFontName() != null) {
				try {
					font = new Font(Display.getDefault(), getCurrentPreferences().getFontName(), getCurrentPreferences().getFontSize(), SWT.NORMAL);
					if (font == null) {
						font = Display.getDefault().getSystemFont();
					}
				} catch (Exception ex) {
					LOG.debug("Font not loaded.  Using serif font.", ex);
				}
			}
		}
		return font;
	}

	/**
	 * Add new observer.
	 *
	 * @param obsName
	 *            the obs name
	 * @param observer
	 *            the observer
	 */
	public void addPrefObserver(String obsName, PrefChangeObserver observer) {
		prefChangeObservers.put(obsName, observer);
	}

	/**
	 * remove unused observer.
	 *
	 * @param obsName
	 *            the obs name
	 */
	public void removePrefObserver(String obsName) {
		prefChangeObservers.remove(obsName);
	}

	/**
	 * Notify pref observers.
	 */
	private void notifyPrefObservers() {
		for (String key : prefChangeObservers.keySet()) {
			prefChangeObservers.get(key).changePreferences();
		}
	}
}
