package bg.plambis.dict.gui.local;

import org.eclipse.swt.widgets.Control;

import bg.plambis.dict.gui.pref.PreferencesManager;

public class TitleLocalizer implements Localizer {
	private Control ctrl;
	private String titleNonI18n;

	public TitleLocalizer(Control ctrl, String titleNonI18n) {
		this.ctrl = ctrl;
		this.titleNonI18n = titleNonI18n;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctrl == null) ? 0 : ctrl.hashCode());
		result = prime * result + ((titleNonI18n == null) ? 0 : titleNonI18n.hashCode());
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
		TitleLocalizer other = (TitleLocalizer) obj;
		if (ctrl == null) {
			if (other.ctrl != null) {
				return false;
			}
		} else if (!ctrl.equals(other.ctrl)) {
			return false;
		}
		if (titleNonI18n == null) {
			if (other.titleNonI18n != null) {
				return false;
			}
		} else if (!titleNonI18n.equals(other.titleNonI18n)) {
			return false;
		}
		return true;
	}

	@Override
	public void internationalize() {
		if (ctrl == null) {
			return;
		}
		ctrl.setToolTipText(LocalizationUtil.getI18nText(titleNonI18n, PreferencesManager.getInstance().getCurrentPreferences().getIntLang()));
	}

}
