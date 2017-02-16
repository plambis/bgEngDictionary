package bg.plambis.dict.gui;

/**
 * Interface used for notification if clipboard is changed.
 *
 * @author pivanov
 */
public interface ChangeClipboard {

	/**
	 * Change clipboard.
	 *
	 * @param clipboard the clipboard
	 */
	public void changeClipboard(CopyClipboard clipboard);
}
