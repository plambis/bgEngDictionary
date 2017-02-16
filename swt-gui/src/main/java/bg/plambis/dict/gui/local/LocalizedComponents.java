package bg.plambis.dict.gui.local;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

/**
 * Components that have to be localized.
 *
 * @author plambis
 */
public interface LocalizedComponents {

	/**
	 * Adds the text exec.
	 *
	 * @param component the component
	 * @param textNon the text non
	 */
	public void addTextExec(Widget component,String textNon);

	/**
	 * Adds the title exec.
	 *
	 * @param component the component
	 * @param titleNon the title non
	 */
	public void addTitleExec(Control component,String titleNon);

	/**
	 * Adds the tool tip text.
	 *
	 * @param component the component
	 * @param titleNon the title non
	 */
	public void addToolTipText(Control component,String titleNon);

	/**
	 * Adds the font exec.
	 *
	 * @param component the component
	 */
	public void addFontExec(Control component);

	/**
	 * Removes the text exec.
	 *
	 * @param component the component
	 * @param textNon the text non
	 */
	public void removeTextExec(Widget component,String textNon);

	/**
	 * Removes the title exec.
	 *
	 * @param component the component
	 * @param titleNon the title non
	 */
	public void removeTitleExec(Control component,String titleNon);

	/**
	 * Removes the tool tip text.
	 *
	 * @param component the component
	 * @param titleNon the title non
	 */
	public void removeToolTipText(Control component,String titleNon);

	/**
	 * Removes the font exec.
	 *
	 * @param component the component
	 */
	public void removeFontExec(Control component);

	/**
	 * Localize all.
	 */
	public void localizeAll();
}
