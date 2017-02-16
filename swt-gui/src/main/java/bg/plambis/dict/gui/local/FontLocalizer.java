package bg.plambis.dict.gui.local;

import org.eclipse.swt.widgets.Control;

import bg.plambis.dict.gui.pref.PreferencesManager;

public class FontLocalizer implements Localizer{
	private Control ctrl;
			
	public FontLocalizer(Control ctrl) {
		this.ctrl = ctrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctrl == null) ? 0 : ctrl.hashCode());		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FontLocalizer other = (FontLocalizer) obj;
		if (ctrl == null) {
			if (other.ctrl != null)
				return false;
		} else if (!ctrl.equals(other.ctrl))
			return false;		
		return true;
	}

	public void internationalize() {
		if(ctrl == null)return;
		ctrl.setFont(PreferencesManager.getInstance().getCurrentFont());
	}

}
