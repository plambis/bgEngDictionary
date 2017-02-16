package bg.plambis.dict.gui.pref;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import bg.plambis.dict.gui.trans.GoogleLanguage;

public class TestPreferencesPersists {

	@Test
	public void testPreferencesPersists() {
		Preferences pref = PreferencesManager.getInstance().getCurrentPreferences();
		Preferences newPref = new PreferencesImpl(Locale.ENGLISH, false, pref.getFontName(), pref.getFontSize(), PreferencesManager.MAX_WORDS, "", "",
				GoogleLanguage.ENGLISH, GoogleLanguage.BULGARIAN);
		PreferencesManager.getInstance().savePreferences(newPref);
		pref = PreferencesManager.getInstance().getCurrentPreferences();
		Assert.assertEquals(pref.getIntLang(), newPref.getIntLang());
		Assert.assertEquals(pref.isTranslateEveryWord(), newPref.isTranslateEveryWord());
		Assert.assertEquals(pref.getFontName(), newPref.getFontName());
		Assert.assertEquals(pref.getFontSize(), newPref.getFontSize());
		Assert.assertEquals(pref.getListWordNumber(), newPref.getListWordNumber());
		Assert.assertEquals(pref.getFromLanguage(), newPref.getFromLanguage());
		Assert.assertEquals(pref.getToLanguage(), newPref.getToLanguage());
	}

}