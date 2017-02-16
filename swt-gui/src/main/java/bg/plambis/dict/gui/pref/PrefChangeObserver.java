package bg.plambis.dict.gui.pref;

/**
 * Observer that will be notified when preferences are changed.
 *
 * @author plambis
 */
public interface PrefChangeObserver {

	/**
	 * This method is called when information about an PrefChange which was
	 * previously requested using an asynchronous interface becomes available.
	 */
	public void changePreferences();
}
