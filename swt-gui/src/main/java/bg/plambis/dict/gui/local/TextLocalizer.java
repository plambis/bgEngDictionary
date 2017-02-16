package bg.plambis.dict.gui.local;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import bg.plambis.dict.gui.pref.Preferences;
import bg.plambis.dict.gui.pref.PreferencesManager;

/**
 * The Class TextLocalizer.
 */
public class TextLocalizer implements Localizer {

	/** The ctrl. */
	private Widget ctrl;

	/** The text non I 18 n. */
	private String textNonI18n;

	/**
	 * Instantiates a new text localizer.
	 *
	 * @param ctrl the ctrl
	 * @param textNonI18n the text non I 18 n
	 */
	public TextLocalizer(Widget ctrl, String textNonI18n) {
		this.ctrl = ctrl;
		this.textNonI18n = textNonI18n;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctrl == null) ? 0 : ctrl.hashCode());
		result = prime * result + ((textNonI18n == null) ? 0 : textNonI18n.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TextLocalizer other = (TextLocalizer) obj;
		if (ctrl == null) {
			if (other.ctrl != null) {
				return false;
			}
		} else if (!ctrl.equals(other.ctrl)) {
			return false;
		}
		if (textNonI18n == null) {
			if (other.textNonI18n != null) {
				return false;
			}
		} else if (!textNonI18n.equals(other.textNonI18n)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the preferences.
	 *
	 * @return the preferences
	 */
	private Preferences getPreferences() {
		return PreferencesManager.getInstance().getCurrentPreferences();
	}

	/* (non-Javadoc)
	 * @see bg.plambis.dict.gui.local.Localizer#internationalize()
	 */
	@Override
	public void internationalize() {
		if (ctrl == null) {
			return;
		}
		if (ctrl instanceof Button) {
			if (((Button) ctrl).getText() != null) {
				((Button) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			} else {
				((Button) ctrl).setToolTipText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			}
			((Button) ctrl).getParent().layout();
			return;
		}
		if (ctrl instanceof Text) {
			((Text) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			((Text) ctrl).getParent().layout();
			return;
		}
		if (ctrl instanceof Label) {
			((Label) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			((Label) ctrl).getParent().layout();
			return;
		}
		if (ctrl instanceof MenuItem) {
			((MenuItem) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			return;
		}
		if (ctrl instanceof TabItem) {
			((TabItem) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			((TabItem) ctrl).getParent().layout();
			return;
		}
		if (ctrl instanceof Group) {
			((Group) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			((Group) ctrl).getParent().layout();
			return;
		}
		if (ctrl instanceof Shell) {
			((Shell) ctrl).setText(LocalizationUtil.getI18nText(textNonI18n, getPreferences().getIntLang()));
			return;
		}
	}

}
