package bg.plambis.dict.gui.local;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
/** {@inheritDoc} 
 * @author plambis
 * */
public class LocalizedComponentsImpl implements LocalizedComponents {
	private Set<Localizer> controls = new HashSet<Localizer>();

	/** {@inheritDoc} */
	@Override
	public void addFontExec(Control component) {
		controls.add(new FontLocalizer(component));
	}

	/** {@inheritDoc} */
	@Override
	public void addTextExec(Widget component, String textNon) {
		controls.add(new TextLocalizer(component,textNon));		
	}

	/** {@inheritDoc} */
	@Override
	public void addTitleExec(Control component, String titleNon) {
		controls.add(new TitleLocalizer(component,titleNon));
	}
	/** {@inheritDoc} */
	@Override
	public void addToolTipText(Control component, String titleNon) {
		controls.add(new TitleLocalizer(component,titleNon));
	}

	/** {@inheritDoc} */
	@Override
	public void localizeAll() {
		for(Localizer localizer : controls){
			if(localizer!=null) {
				localizer.internationalize();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeFontExec(Control component) {
		controls.remove(new FontLocalizer(component));
	}

	/** {@inheritDoc} */
	@Override
	public void removeTextExec(Widget component, String textNon) {
		controls.remove(new TextLocalizer(component,textNon));	
	}

	/** {@inheritDoc} */
	@Override
	public void removeTitleExec(Control component, String titleNon) {
		controls.remove(new TitleLocalizer(component,titleNon));
	}

	/** {@inheritDoc} */
	@Override
	public void removeToolTipText(Control component, String titleNon) {
		controls.remove(new TitleLocalizer(component,titleNon));
	}

}
