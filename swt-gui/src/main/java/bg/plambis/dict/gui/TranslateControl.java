package bg.plambis.dict.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import bg.plambis.dict.gui.local.LocalizationUtil;
import bg.plambis.dict.gui.local.LocalizedComponents;
import bg.plambis.dict.gui.local.LocalizedComponentsImpl;
import bg.plambis.dict.gui.pref.PrefChangeObserver;
import bg.plambis.dict.gui.pref.Preferences;
import bg.plambis.dict.gui.pref.PreferencesManager;
import bg.plambis.dict.gui.trans.GoogleLanguage;

/**
 * Control that contains all dictionary controls and logic for normal dictionary
 * work
 * 
 * @author pivanov
 */
public class TranslateControl extends Composite implements PrefChangeObserver {

	private static final String TRANSLATE_DIALOG = "TRANSLATE_DIALOG";
	private static final String SWAP_PATH = "images/swap.png";
	private Combo fromLanguage;
	private Combo toLanguage;
	private StyledText inText;
	private StyledText outText;

	private LocalizedComponents locComponents = new LocalizedComponentsImpl();

	public TranslateControl(Composite parent, int style) {
		super(parent, style);
		init();
	}

	private void init() {
		GridLayout layout = new GridLayout(6, false);
		setLayout(layout);

		createInText();

		createTranslatePanel();

		createOutText();

		PreferencesManager.getInstance().addPrefObserver(TRANSLATE_DIALOG, this);

	}

	private void createInText() {
		inText = createText();
	}

	private StyledText createText() {
		StyledText text = new StyledText(this, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.BORDER);

		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 6;
		text.setLayoutData(data);
		// inText.setEditable(false);
		locComponents.addFontExec(text);
		createContextMenu(getDisplay(), text);
		return text;
	}

	private Preferences getPreferences() {
		return PreferencesManager.getInstance().getCurrentPreferences();
	}

	private void createTranslatePanel() {
		GridData data = new GridData(GridData.END);
		data.verticalAlignment = SWT.CENTER;
		Label fromLabel = new Label(this, SWT.RIGHT);
		fromLabel.setLayoutData(data);
		fromLabel.setText(LocalizationUtil.getI18nText("DICT_TRANS_FROM", getPreferences().getIntLang()));
		locComponents.addTextExec(fromLabel, "DICT_TRANS_FROM");

		fromLanguage = new Combo(this, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		addLanguagesToCombo(fromLanguage, getPreferences().getFromLanguage());

		data = new GridData(GridData.CENTER);
		Button swapButton = new Button(this, SWT.PUSH);
		swapButton.setLayoutData(data);
		Image nextImage = GUIUtil.loadImage(getDisplay(), SWAP_PATH);
		if (nextImage != null) {
			swapButton.setImage(nextImage);
			swapButton.setToolTipText(LocalizationUtil.getI18nText("DICT_TRANS_SWAP", getPreferences().getIntLang()));
			locComponents.addToolTipText(swapButton, "DICT_TRANS_SWAP");
		}
		swapButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if ("".equals(fromLanguage.getText()) || "".equals(toLanguage.getText())) {
					return;
				}
				if (fromLanguage.getText().equals(toLanguage.getText())) {
					return;
				}

				GoogleLanguage fromLang = GoogleLanguage.getByAlias(fromLanguage.getText());
				GoogleLanguage toLang = GoogleLanguage.getByAlias(toLanguage.getText());
				selectLanguagesCombo(fromLanguage, toLang);
				selectLanguagesCombo(toLanguage, fromLang);
			}
		});
		swapButton.setLayoutData(data);

		data = new GridData(GridData.END);
		data.verticalAlignment = SWT.CENTER;
		Label toLabel = new Label(this, SWT.RIGHT);
		toLabel.setLayoutData(data);
		toLabel.setText(LocalizationUtil.getI18nText("DICT_TRANS_TO", getPreferences().getIntLang()));
		locComponents.addTextExec(toLabel, "DICT_TRANS_TO");

		toLanguage = new Combo(this, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		addLanguagesToCombo(toLanguage, getPreferences().getToLanguage());

		data = new GridData(GridData.CENTER);
		Button translateButton = new Button(this, SWT.PUSH);
		translateButton.setLayoutData(data);
		translateButton.setText(LocalizationUtil.getI18nText("DICT_TRANS_TRANSLATE", getPreferences().getIntLang()));
		locComponents.addTextExec(translateButton, "DICT_TRANS_TRANSLATE");
		translateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if ("".equals(fromLanguage.getText()) || "".equals(toLanguage.getText()) || inText.getText() == null) {
					return;
				}
				if (fromLanguage.getText().equals(toLanguage.getText())) {
					return;
				}

				GoogleLanguage fromLang = GoogleLanguage.getByAlias(fromLanguage.getText());
				GoogleLanguage toLang = GoogleLanguage.getByAlias(toLanguage.getText());

				(new Thread(new TranslateThread(getShell(), fromLang, toLang, inText.getText().trim()))).start();

			}
		});
		translateButton.setLayoutData(data);
	}

	private void addLanguagesToCombo(Combo emptyCombo, GoogleLanguage targetLang) {
		int index = 0, selIndex = 0;
		for (GoogleLanguage lang : GoogleLanguage.values()) {
			emptyCombo.add(lang.getAlias());
			if (lang.getLang().equals(targetLang.getLang())) {
				selIndex = index;
			}
			index++;
		}
		emptyCombo.select(selIndex);
	}

	private void selectLanguagesCombo(Combo combo, GoogleLanguage targetLang) {
		int index = 0, selIndex = 0;

		for (String lang : combo.getItems()) {
			if (lang.equals(targetLang.getAlias())) {
				selIndex = index;
				break;
			}
			index++;
		}
		combo.select(selIndex);
	}

	private void createOutText() {
		outText = createText();
	}

	@Override
	public void changePreferences() {
		locComponents.localizeAll();
		getParent().getShell().redraw();
	}

	/** create context menu for text fields */
	private void createContextMenu(Display display, StyledText text) {
		Menu menuBar = new Menu(getParent().getShell(), SWT.POP_UP);
		// Copy
		createMenuItem(menuBar, "DICT_CTX_MENU_COPY", new CopyItemListener(display, text), SWT.CTRL + 'C');
		// Cut
		createMenuItem(menuBar, "DICT_CTX_MENU_CUT", new CutItemListener(display, text), SWT.CTRL + 'X');
		// Paste
		createMenuItem(menuBar, "DICT_CTX_MENU_PASTE", new PasteItemListener(display, text), SWT.CTRL + 'V');
		// Select All
		createMenuItem(menuBar, "DICT_CTX_MENU_SELECT_ALL", new SelectAllItemListener(text), SWT.CTRL + 'A');

		text.setMenu(menuBar);
	}

	private void createMenuItem(Menu menuBar, String label, SelectionListener selectionListener, int shortcut) {
		MenuItem copyMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		copyMenuItem.setText(LocalizationUtil.getI18nText(label, getPreferences().getIntLang()));
		locComponents.addTextExec(copyMenuItem, label);
		copyMenuItem.addSelectionListener(selectionListener);
		copyMenuItem.setAccelerator(shortcut);
	}

	/**
	 * Listener class that support Copy button
	 * 
	 * @author pivanov
	 */
	class CopyItemListener implements SelectionListener {
		private Display display;
		private StyledText text;

		public CopyItemListener(Display display, StyledText text) {
			this.display = display;
			this.text = text;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {

			String plainText = text.getSelectionText();
			if (plainText == null || plainText.trim().length() < 1) {
				return;
			}
			TextTransfer textTransfer = TextTransfer.getInstance();
			Clipboard clipboard = new Clipboard(display);
			clipboard.setContents(new String[] { plainText }, new Transfer[] { textTransfer });
			clipboard.dispose();
		}
	}

	/**
	 * Listener class that support Cut button
	 * 
	 * @author pivanov
	 */
	class CutItemListener implements SelectionListener {
		private Display display;
		private StyledText text;

		public CutItemListener(Display display, StyledText text) {
			this.display = display;
			this.text = text;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			String plainText = text.getSelectionText();
			if (plainText == null || plainText.trim().length() < 1) {
				return;
			}
			text.insert("");
			TextTransfer textTransfer = TextTransfer.getInstance();
			Clipboard clipboard = new Clipboard(display);
			clipboard.setContents(new String[] { plainText }, new Transfer[] { textTransfer });
			clipboard.dispose();
		}
	}

	/**
	 * Listener class that support Paste button
	 * 
	 * @author pivanov
	 */
	class PasteItemListener implements SelectionListener {
		private Display display;
		private StyledText text;

		public PasteItemListener(Display display, StyledText text) {
			this.display = display;
			this.text = text;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			TextTransfer transfer = TextTransfer.getInstance();
			Clipboard clipboard = new Clipboard(display);
			String data = (String) clipboard.getContents(transfer);
			clipboard.dispose();
			if (data != null) {
				text.insert(data);
			}
		}
	}

	/**
	 * Listener class that support SelectAll button
	 * 
	 * @author pivanov
	 */
	class SelectAllItemListener implements SelectionListener {

		private StyledText text;

		public SelectAllItemListener(StyledText text) {
			this.text = text;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {

			if (text == null) {
				return;
			}
			text.selectAll();
		}
	}

	private class TranslateThread implements Runnable {
		private static final String DICT_TRANS_CONN_PROBLEM = "DICT_TRANS_CONN_PROBLEM";
		private GoogleLanguage from;
		private GoogleLanguage to;
		private String in;
		private Shell shell;
		private String translatedText;

		public TranslateThread(Shell shell, GoogleLanguage from, GoogleLanguage to, String in) {
			this.shell = shell;
			this.from = from;
			this.to = to;
			this.in = in;
		}

		@Override
		public void run() {
			try {
				/*
				 * // Set the HTTP referrer to your website address.
				 * GoogleAPI.setHttpReferrer("BGENGDICTIONARY");
				 * 
				 * // Set the Google Translate API key // See: //
				 * http://code.google.com/apis/language/translate/v2/
				 * getting_started.html //
				 * AIzaSyAzI62umls2_j1IBL3WYPg4OZxgWqwIWL //
				 * AIzaSyDox974xmURuWYzFGazHjnH6G7W-8TJ8f8 //
				 * AIzaSyACJybEm6lyelnYHZzXfydtk-V6-Uz48bQ //
				 * AIzaSyBA7y7rUxU9fpAlzztD6DW8WOadnB3RYM4
				 * GoogleAPI.setKey("AIzaSyACJybEm6lyelnYHZzXfydtk-V6-Uz48bQ");
				 * 
				 * translatedText = Translate.DEFAULT.execute(saveLayout(in),
				 * Language.valueOf(from.name()), Language.valueOf(to.name()));
				 * shell.getDisplay().asyncExec(new Runnable() {
				 * 
				 * @Override public void run() {
				 * outText.setText(reloadLayout(translatedText)); }
				 * 
				 * });
				 */

			} catch (Exception e) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						MessageBox mb = new MessageBox(shell);
						mb.setMessage(LocalizationUtil.getI18nText(DICT_TRANS_CONN_PROBLEM, getPreferences().getIntLang()));
						mb.open();
					}
				});

				e.printStackTrace();
			}
		}

		private String saveLayout(String text) {
			if (text == null) {
				return text;
			}
			text = text.replaceAll("\n", "<br />");
			text = text.replaceAll("\t", "<pre />");
			text = text.replaceAll("    ", "<pre />");
			return text;
		}

		private String reloadLayout(String text) {
			if (text == null) {
				return text;
			}
			text = text.replaceAll("<br />", "\n");
			text = text.replaceAll("<pre />", "\t");
			return text;
		}
	}

}
