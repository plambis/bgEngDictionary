package bg.plambis.dict.gui;

import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import bg.plambis.dict.gui.local.LocalizationUtil;
import bg.plambis.dict.gui.local.LocalizedComponents;
import bg.plambis.dict.gui.local.LocalizedComponentsImpl;
import bg.plambis.dict.gui.pref.PrefChangeObserver;
import bg.plambis.dict.gui.pref.Preferences;
import bg.plambis.dict.gui.pref.PreferencesImpl;
import bg.plambis.dict.gui.pref.PreferencesManager;
import bg.plambis.dict.gui.trans.GoogleLanguage;

/**
 * About modal dialog
 * 
 * @author pivanov
 */
public class PreferencesDialog extends Dialog implements PrefChangeObserver {
	private static final String DICT_PREF_TRANSATE_TO = "DICT_PREF_TRANSATE_TO";
	private static final String DICT_PREF_TRANSATE_FROM = "DICT_PREF_TRANSATE_FROM";
	private static final String DICT_PREF_TRANSATE_PROXY_PORT = "DICT_PREF_TRANSATE_PROXY_PORT";
	private static final String DICT_PREF_TRANSATE_PROXY_SRV = "DICT_PREF_TRANSATE_PROXY_SRV";
	private static final String DICT_PREF_TRANSLATION = "DICT_PREF_TRANSLATION";
	private static final String DICT_PREF_LANG = "DICT_PREF_LANG";
	private static final String DICT_PREF_INTERFACE = "DICT_PREF_INTERFACE";
	private static final String DICT_PREF_FONT = "DICT_PREF_FONT";
	private static final String DICT_PREF_TRANS_EVERY_KEY = "DICT_PREF_TRANS_EVERY_KEY";
	private static final String DICT_PREF_TRANS_LIST_NUM_WORD = "DICT_PREF_TRANS_LIST_NUM_WORD";
	private static final String DICT_PREF_TRANSLATE = "DICT_PREF_TRANSLATE";
	private static final String DICT_PREF_TITLE = "DICT_PREF_TITLE";
	private static final Locale[] SUPPORTED_LOCALS = new Locale[] { new Locale("bg"), Locale.ENGLISH };
	private static final String PREF_DIALOG = "PREF_DIALOG";

	private Shell dialog;
	private Font font;

	private Text listWordNumber;
	private Text fontName;
	private Text fontSize;
	private Combo intLang;
	private Button transEveryKey;
	// google translate config
	private Text proxyServer;
	private Text proxyPort;
	private Combo fromLanguage;
	private Combo toLanguage;

	private LocalizedComponents locComponents = new LocalizedComponentsImpl();

	public PreferencesDialog(Shell parent, int style) {
		super(parent, style);
	}

	public PreferencesDialog(Shell parent) {
		this(parent, 0); // your default style bits go here (not the Shell's
		// style bits)
	}

	public void open() {
		Shell parent = getParent();
		Display display = parent.getDisplay();
		dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		locComponents.addTextExec(dialog, DICT_PREF_TITLE);
		dialog.setImage(GUIUtil.loadImage(display, DictionaryGUI.LOGO_PATH));

		GridLayout layout = new GridLayout(2, false);
		dialog.setLayout(layout);

		createTranslateGroup();

		createInterfaceGroup();

		createTranslationGroup();

		createSaveClose();
		changePreferences();
		PreferencesManager.getInstance().addPrefObserver(PREF_DIALOG, this);

		dialog.pack();

		dialog.open();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Preferences getPreferences() {
		return PreferencesManager.getInstance().getCurrentPreferences();
	}

	private void createTranslateGroup() {
		GridLayout layout = new GridLayout(3, false);

		Group group = new Group(dialog, SWT.SHADOW_ETCHED_IN);
		group.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANSLATE, getPreferences().getIntLang()));
		locComponents.addTextExec(group, DICT_PREF_TRANSLATE);
		group.setLayout(layout);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = SWT.FILL;
		group.setLayoutData(data);

		Label listWordsLabel = new Label(group, SWT.RIGHT);
		data = new GridData();
		data.horizontalSpan = 2;
		listWordsLabel.setLayoutData(data);
		listWordsLabel.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANS_LIST_NUM_WORD, getPreferences().getIntLang()));
		locComponents.addTextExec(listWordsLabel, DICT_PREF_TRANS_LIST_NUM_WORD);

		listWordNumber = new Text(group, SWT.RIGHT);
		listWordNumber.setText("" + getPreferences().getListWordNumber());

		transEveryKey = new Button(group, SWT.CHECK);
		data = new GridData();
		data.horizontalSpan = 3;
		locComponents.addTextExec(transEveryKey, DICT_PREF_TRANS_EVERY_KEY);
		transEveryKey.setLayoutData(data);
		transEveryKey.setSelection(getPreferences().isTranslateEveryWord());

		fontName = new Text(group, SWT.LEFT);
		fontName.setText("" + getPreferences().getFontName());
		fontName.setEnabled(false);
		locComponents.addFontExec(fontName);
		data = new GridData();
		data.widthHint = 150;
		fontName.setLayoutData(data);

		fontSize = new Text(group, SWT.LEFT);
		fontSize.setText("" + getPreferences().getFontSize());
		fontSize.setEditable(false);
		locComponents.addFontExec(fontSize);
		data = new GridData();
		data.widthHint = 50;
		fontSize.setLayoutData(data);

		Button butDialog = new Button(group, SWT.PUSH);
		locComponents.addTextExec(butDialog, DICT_PREF_FONT);
		butDialog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				// Create the color-change dialog
				FontDialog dlg = new FontDialog(dialog);

				// Pre-fill the dialog with any previous selection
				if (font != null) {
					dlg.setFontList(getFont().getFontData());
				}

				if (dlg.open() != null) {
					// Dispose of any fonts or colors we have created
					if (font != null) {
						font.dispose();
					}

					// Create the new font and set it into the label
					font = new Font(dialog.getDisplay(), dlg.getFontList());
					fontName.setText(font.getFontData()[0].getName());
					fontSize.setText("" + font.getFontData()[0].getHeight());

					// Call pack() to resize the window to fit the new font
					dialog.pack();
				}
			}
		});

		group.pack();
	}

	private void createInterfaceGroup() {
		GridLayout layout = new GridLayout(2, false);

		Group group = new Group(dialog, SWT.SHADOW_ETCHED_IN);
		group.setText(LocalizationUtil.getI18nText(DICT_PREF_INTERFACE, getPreferences().getIntLang()));
		locComponents.addTextExec(group, DICT_PREF_INTERFACE);
		group.setLayout(layout);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = SWT.FILL;
		group.setLayoutData(data);

		Label intLabel = new Label(group, SWT.RIGHT);
		locComponents.addTextExec(intLabel, DICT_PREF_LANG);

		intLang = new Combo(group, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		int selIndex = 0;
		for (int i = 0; i < SUPPORTED_LOCALS.length; i++) {
			Locale locale = SUPPORTED_LOCALS[i];
			intLang.add(LocalizationUtil.getI18nText("DICT_PREF_LANG_" + locale.getLanguage(), getPreferences().getIntLang()));
			if (locale.equals(getPreferences().getIntLang())) {
				selIndex = i;
			}
		}
		intLang.select(selIndex);
		group.pack();
	}

	private void createTranslationGroup() {
		GridLayout layout = new GridLayout(2, false);

		Group group = new Group(dialog, SWT.SHADOW_ETCHED_IN);
		group.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANSLATION, getPreferences().getIntLang()));
		locComponents.addTextExec(group, DICT_PREF_TRANSLATION);
		group.setLayout(layout);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = SWT.FILL;
		group.setLayoutData(data);

		Label proxyServerLbl = new Label(group, SWT.RIGHT);
		proxyServerLbl.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANSATE_PROXY_SRV, getPreferences().getIntLang()));
		proxyServerLbl.setLayoutData(fillAllCell());
		locComponents.addTextExec(proxyServerLbl, DICT_PREF_TRANSATE_PROXY_SRV);

		proxyServer = new Text(group, SWT.LEFT | SWT.BORDER);
		if (getPreferences().getProxyServer() != null) {
			proxyServer.setText("" + getPreferences().getProxyServer());
		}
		proxyServer.setLayoutData(fillAllCell());

		Label proxyPortLbl = new Label(group, SWT.RIGHT);
		proxyPortLbl.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANSATE_PROXY_PORT, getPreferences().getIntLang()));
		proxyPortLbl.setLayoutData(fillAllCell());
		locComponents.addTextExec(proxyPortLbl, DICT_PREF_TRANSATE_PROXY_PORT);

		proxyPort = new Text(group, SWT.LEFT | SWT.BORDER);
		if (getPreferences().getProxyPort() != null) {
			proxyPort.setText("" + getPreferences().getProxyPort());
		}
		proxyPort.setLayoutData(fillAllCell());

		Label fromLbl = new Label(group, SWT.RIGHT);
		fromLbl.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANSATE_FROM, getPreferences().getIntLang()));
		fromLbl.setLayoutData(fillAllCell());
		locComponents.addTextExec(fromLbl, DICT_PREF_TRANSATE_FROM);

		fromLanguage = new Combo(group, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		addLanguagesToCombo(fromLanguage, getPreferences().getFromLanguage());

		Label toLbl = new Label(group, SWT.RIGHT);
		toLbl.setText(LocalizationUtil.getI18nText(DICT_PREF_TRANSATE_TO, getPreferences().getIntLang()));
		toLbl.setLayoutData(fillAllCell());
		locComponents.addTextExec(toLbl, DICT_PREF_TRANSATE_TO);

		toLanguage = new Combo(group, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		addLanguagesToCombo(toLanguage, getPreferences().getToLanguage());

		group.pack();
	}

	private GridData fillAllCell() {
		GridData data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		return data;
	}

	private void createSaveClose() {

		Button saveButton = new Button(dialog, SWT.PUSH);
		locComponents.addTextExec(saveButton, "DICT_PREF_SAVE");
		GridData data = new GridData();
		data.horizontalAlignment = SWT.CENTER;
		saveButton.setLayoutData(data);
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Locale locale = SUPPORTED_LOCALS[intLang.getSelectionIndex()];
				if (fontName.getText() == null) {
					fontName.setText(getPreferences().getFontName());
				}
				if (fontSize.getText() == null || "0".equals(fontSize.getText())) {
					fontSize.setText("" + getPreferences().getFontSize());
				}
				int listWords = getPreferences().getListWordNumber();
				try {
					int tmpValue = Integer.parseInt(listWordNumber.getText());
					listWords = tmpValue > 5 && tmpValue <= 100 ? tmpValue : listWords;
				} catch (NumberFormatException nfe) {
					// do nothing;
				}

				GoogleLanguage fromLang = GoogleLanguage.getByAlias(fromLanguage.getText());
				GoogleLanguage toLang = GoogleLanguage.getByAlias(toLanguage.getText());

				Preferences pref = new PreferencesImpl(locale, transEveryKey.getSelection(), fontName.getText(), Integer.parseInt(fontSize.getText()),
						listWords, proxyServer.getText(), proxyPort.getText(), fromLang, toLang);
				MessageBox msgBox = new MessageBox(dialog);

				if (PreferencesManager.getInstance().savePreferences(pref)) {
					msgBox.setMessage(LocalizationUtil.getI18nText("DICT_PREF_SAVE_MSG", getPreferences().getIntLang()));
				} else {
					msgBox.setMessage(LocalizationUtil.getI18nText("DICT_PREF_SAVE_MSG_UNS", getPreferences().getIntLang()));
				}
				msgBox.open();

			}
		});

		Button cancelButton = new Button(dialog, SWT.PUSH);
		locComponents.addTextExec(cancelButton, "DICT_PREF_CLOSE");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				dialog.close();
			}
		});
		data.horizontalAlignment = SWT.END;
		cancelButton.setLayoutData(data);
	}

	private Font getFont() {
		if (fontName.getText() == null) {
			return PreferencesManager.getInstance().getCurrentFont();
		}

		String name = fontName.getText();
		int size = Integer.parseInt(fontSize.getText());
		Font newFont = new Font(dialog.getDisplay(), name, size, SWT.NORMAL);

		return newFont;
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

	@Override
	public void changePreferences() {
		locComponents.localizeAll();
		dialog.redraw();
	}
}
