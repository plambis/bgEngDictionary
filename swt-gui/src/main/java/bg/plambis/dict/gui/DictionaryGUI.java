package bg.plambis.dict.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import bg.plambis.dict.gui.local.LocalizationUtil;
import bg.plambis.dict.gui.local.LocalizedComponents;
import bg.plambis.dict.gui.local.LocalizedComponentsImpl;
import bg.plambis.dict.gui.pref.PrefChangeObserver;
import bg.plambis.dict.gui.pref.Preferences;
import bg.plambis.dict.gui.pref.PreferencesManager;

/**
 * @author pivanov
 */
public class DictionaryGUI implements PrefChangeObserver {
	private static final String DICT_MENU_ABOUT = "DICT_MENU_ABOUT";
	private static final String DICT_MENU_PREF = "DICT_MENU_PREF";
	private static final String DICT_MENU_HELP = "DICT_MENU_HELP";
	private static final String DICT_MENU_EXIT = "DICT_MENU_EXIT";
	private static final String DICT_MENU_FILE = "DICT_MENU_FILE";
	private static final String DICT_TTAB_TITLE = "DICT_TTAB_TITLE";
	private static final String DICT_TITLE = "DICT_TITLE";
	private static final String DICT_DTAB_TITLE = "DICT_DTAB_TITLE";
	private static final String MAIN_WINDOW = "MAIN_WINDOW";
	public static final String LOGO_PATH = "images/bulgaria.png";
	private DictionaryControl dict;
	private LocalizedComponents locComponents = new LocalizedComponentsImpl();

	Display display;
	Shell shell;

	public static void main(String[] args) {
		new DictionaryGUI().go();
	}

	private Preferences getPreferences() {
		return PreferencesManager.getInstance().getCurrentPreferences();
	}

	private void go() {
		// Creates a new display object for the example to go into
		display = new Display();

		// Creates a new shell object
		shell = new Shell(display);
		// Sets the layout for the shell
		shell.setText(LocalizationUtil.getI18nText(DICT_TITLE, getPreferences().getIntLang()));
		shell.setImage(GUIUtil.loadImage(display, LOGO_PATH));
		locComponents.addTextExec(shell, DICT_TITLE);

		createMenu(display, shell);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		changePreferences();
		TabFolder mainTabFolder = new TabFolder(shell, SWT.FILL);
		GridData data = new GridData(GridData.FILL_BOTH);
		mainTabFolder.setLayoutData(data);
		TabItem dictTabItem = new TabItem(mainTabFolder, SWT.DEFAULT);
		dictTabItem.setText(LocalizationUtil.getI18nText(DICT_DTAB_TITLE, getPreferences().getIntLang()));
		locComponents.addTextExec(dictTabItem, DICT_DTAB_TITLE);
		dict = new DictionaryControl(mainTabFolder, SWT.NO_SCROLL);
		dictTabItem.setControl(dict);

		TabItem translateTabItem = new TabItem(mainTabFolder, SWT.DEFAULT);
		translateTabItem.setText(LocalizationUtil.getI18nText(DICT_TTAB_TITLE, getPreferences().getIntLang()));
		locComponents.addTextExec(translateTabItem, DICT_TTAB_TITLE);
		TranslateControl translate = new TranslateControl(mainTabFolder, SWT.NO_SCROLL);
		translateTabItem.setControl(translate);

		PreferencesManager.getInstance().addPrefObserver(MAIN_WINDOW, this);

		shell.open();
		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {
			// read the next OS event queue and transfer it to a SWT event
			if (!display.readAndDispatch()) {
				// if there are currently no other OS event to process sleep
				// until the next OS event is available
				display.sleep();
			}
		}

		// disposes all associated windows and their components
		display.dispose();
	}

	/** create main menu */
	private void createMenu(Display display, Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		locComponents.addTextExec(fileMenuHeader, DICT_MENU_FILE);

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		locComponents.addTextExec(fileExitItem, DICT_MENU_EXIT);

		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		locComponents.addTextExec(helpMenuHeader, DICT_MENU_HELP);

		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		MenuItem prefItem = new MenuItem(helpMenu, SWT.PUSH);
		locComponents.addTextExec(prefItem, DICT_MENU_PREF);
		prefItem.addSelectionListener(new PrefItemListener(shell));

		MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
		locComponents.addTextExec(aboutItem, DICT_MENU_ABOUT);
		aboutItem.addSelectionListener(new AboutItemListener(shell));

		fileExitItem.addSelectionListener(new FileExitItemListener(display, shell));

		shell.setMenuBar(menuBar);
	}

	/**
	 * Listener class that support about button
	 * 
	 * @author pivanov
	 */
	class AboutItemListener implements SelectionListener {
		private Shell shell;

		public AboutItemListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			AboutDialog aboutDialog = new AboutDialog(shell);
			aboutDialog.open();
		}
	}

	/**
	 * Listener class that support preference menu button button
	 * 
	 * @author pivanov
	 */
	class PrefItemListener implements SelectionListener {
		private Shell shell;

		public PrefItemListener(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			PreferencesDialog prefDialog = new PreferencesDialog(shell);
			prefDialog.open();
		}
	}

	/**
	 * Listener class that support exit button
	 * 
	 * @author pivanov
	 */
	class FileExitItemListener implements SelectionListener {
		private Display display;
		private Shell shell;

		public FileExitItemListener(Display display, Shell shell) {
			this.display = display;
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			shell.close();
			display.dispose();
		}
	}

	@Override
	public void changePreferences() {
		locComponents.localizeAll();
		shell.redraw();
	}
}
