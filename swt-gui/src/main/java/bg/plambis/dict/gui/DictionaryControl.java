package bg.plambis.dict.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import bg.plambis.data.api.DataSourceProvider;
import bg.plambis.data.api.Dictionary;
import bg.plambis.data.api.Word;
import bg.plambis.data.impl.DataSourceProviderImpl;
import bg.plambis.data.impl.DatabaseDictionaryImpl;
import bg.plambis.dict.gui.local.LocalizationUtil;
import bg.plambis.dict.gui.local.LocalizedComponents;
import bg.plambis.dict.gui.local.LocalizedComponentsImpl;
import bg.plambis.dict.gui.pref.PrefChangeObserver;
import bg.plambis.dict.gui.pref.Preferences;
import bg.plambis.dict.gui.pref.PreferencesManager;

/**
 * Control that contains all dictionary controls and logic for normal dictionary
 * work
 * 
 * @author pivanov
 */
public class DictionaryControl extends Composite implements PrefChangeObserver, ChangeClipboard {

	private static final String DICT_NEXT = "DICT_NEXT";
	private static final String DICT_PREV = "DICT_PREV";
	private static final String DICT_DIALOG = "DICT_DIALOG";
	private static final String PREV_PATH = "images/prev.png";
	private static final String NEXT_PATH = "images/next.png";
	private Combo searchText;
	private List foundList;
	private StyledText description;
	private final Dictionary dictionary;

	private int shownWordsListId = 0;

	private LocalizedComponents locComponents = new LocalizedComponentsImpl();

	private CopyClipboard clipboard;

	public DictionaryControl(Composite parent, int style) {
		super(parent, style);
		init();
		DataSourceProvider provider = new DataSourceProviderImpl("org.h2.Driver", "jdbc:h2:./plambis/plambis", "plambis", "plambis");
		dictionary = new DatabaseDictionaryImpl(provider.createDataSource());
	}

	private void init() {
		GridLayout layout = new GridLayout(3, false);
		setLayout(layout);

		GridData data = new GridData();
		data.horizontalSpan = 3;
		data.widthHint = 200;
		createSearchCombo().setLayoutData(data);

		createPrevNextButtons();

		description = new StyledText(this, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.BORDER);
		data = new GridData(GridData.FILL_BOTH);
		data.verticalSpan = 2;
		description.setLayoutData(data);
		description.setEditable(false);
		locComponents.addFontExec(description);
		createContextMenu(getParent().getDisplay());
		setAppropriateColors();
		description.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				copyToClipboard(getDisplay(), description.getSelectionText());
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		createFoundList();
		data = new GridData(GridData.FILL_VERTICAL);
		data.widthHint = 150;
		data.horizontalSpan = 2;
		foundList.setLayoutData(data);

		// clipboard listener
		clipboard = new CopyClipboard(getDisplay(), this);
		(new Thread(clipboard)).start();
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				clipboard.stopListening();
			}
		});

		changePreferences();

		PreferencesManager.getInstance().addPrefObserver(DICT_DIALOG, this);

	}

	private Color getOrangeTextColor() {
		return new Color(getParent().getDisplay(), 255, 127, 0);
	}

	/** set transcription color and latin text with bold */
	private void setAppropriateColors() {

		// bold and orange transcription
		if (description.getText().indexOf('[') != -1 && description.getText().indexOf(']') != -1) {
			StyleRange styleRange = new StyleRange();
			styleRange.start = description.getText().indexOf('[');
			styleRange.length = description.getText().indexOf(']') - description.getText().indexOf('[') + 1;
			styleRange.fontStyle = SWT.BOLD;
			styleRange.foreground = getOrangeTextColor();
			description.setStyleRange(styleRange);
		}

		// Bold every latin word
		int index = -1;
		String text = description.getText();
		for (int i = 0; i < text.length(); i++) {
			char symbol = text.charAt(i);
			if ((symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z')) {
				if (index == -1 && (i < description.getText().indexOf('[') || i > description.getText().indexOf(']'))) {
					index = i;
				}
			} else {
				if (index != -1) {
					StyleRange styleRange = new StyleRange();
					styleRange.start = index;
					styleRange.length = i - index;
					styleRange.fontStyle = SWT.BOLD;
					description.setStyleRange(styleRange);
					index = -1;
				}
			}
		}
	}

	/** create combo box for typing searching word */
	private Combo createSearchCombo() {
		int style = SWT.DROP_DOWN;

		searchText = new Combo(this, style);
		searchText.addKeyListener(new SearchComboKeyListener());
		searchText.addSelectionListener(new SearchComboSelectionListener());
		locComponents.addFontExec(searchText);
		searchText.forceFocus();
		return searchText;
	}

	private class SearchComboKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// do nothing
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			char typed = arg0.character;
			// System.out.println("Pressed Key: " + (int)typed);
			boolean everyKey = getPreferences().isTranslateEveryWord();
			if (everyKey && ((typed >= 'a' && typed <= 'z') || (typed >= 'A' && typed <= 'Z') || (typed >= 'а' && typed <= 'я')
					|| (typed >= 'А' && typed <= 'Я') || (typed == 8 && searchText.getText() != null)
					|| (typed == 13 && searchText.getText() != null))) {
				// long startTime = System.currentTimeMillis();
				findWord(searchText.getText().trim(), true);
			} else if (!everyKey && (typed == 13 && searchText.getText() != null)) {
				findWord(searchText.getText().trim(), true);
			}
		}
	}

	private Preferences getPreferences() {
		return PreferencesManager.getInstance().getCurrentPreferences();
	}

	/**
	 * Search word in database and reload data for it
	 * 
	 * @param selected
	 * @param reloadList
	 *            - reload
	 */
	private boolean findWord(String selected, boolean reloadList) {
		Word found;
		if (selected.length() == 1) {
			found = dictionary.getLetterTranslation(selected.trim());
		} else {
			found = dictionary.getWordTranslation(selected.trim());
		}
		if (found == null) {
			return false;
		}

		description.setText(found.getTranslation());
		setAppropriateColors();

		if (searchText.indexOf(found.getWord()) == -1) {
			searchText.add(found.getWord());
		}

		if (!reloadList) {
			return false;
		}
		loadFoundList(dictionary.getNextWords(found.getId(), getPreferences().getListWordNumber()));
		foundList.select(0);
		shownWordsListId = found.getId();
		return true;
	}

	/** create buttons for word navigation */
	private void createPrevNextButtons() {
		Button prevButton = new Button(this, SWT.PUSH);

		Image prevImage = GUIUtil.loadImage(getDisplay(), PREV_PATH);
		if (prevImage != null) {
			prevButton.setImage(prevImage);
			prevButton.setToolTipText(LocalizationUtil.getI18nText(DICT_PREV, getPreferences().getIntLang()));
			locComponents.addToolTipText(prevButton, DICT_PREV);
		}
		GridData data = new GridData();
		data.widthHint = 75;
		prevButton.setLayoutData(data);
		prevButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				// load previous words
				java.util.List<Word> prevList = dictionary.getPreviousWords(shownWordsListId, getPreferences().getListWordNumber());
				if (prevList == null) {
					return;
				}
				shownWordsListId -= prevList.size();
				loadFoundList(prevList);
			}
		});

		Button nextButton = new Button(this, SWT.PUSH);
		Image nextImage = GUIUtil.loadImage(getDisplay(), NEXT_PATH);
		if (nextImage != null) {
			nextButton.setImage(nextImage);
			nextButton.setToolTipText(LocalizationUtil.getI18nText(DICT_NEXT, getPreferences().getIntLang()));
			locComponents.addToolTipText(nextButton, DICT_NEXT);
		}
		data = new GridData();
		data.widthHint = 75;
		nextButton.setLayoutData(data);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				// load previous words
				java.util.List<Word> nextList = dictionary.getPreviousWords(shownWordsListId, getPreferences().getListWordNumber());
				if (nextList == null) {
					return;
				}
				shownWordsListId += nextList.size();
				loadFoundList(nextList);
			}
		});
	}

	/**
	 * load found list
	 * 
	 * @param items
	 *            - list with Items
	 */
	private void loadFoundList(java.util.List<Word> items) {
		foundList.removeAll();
		for (Word word : items) {
			foundList.add(word.getWord().toLowerCase());
		}
	}

	private class SearchComboSelectionListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			String selected = searchText.getText();
			if (selected == null) {
				return;
			}
			findWord(selected, true);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			//
		}
	}

	/** create list with all searched words */
	private List createFoundList() {
		foundList = new List(this, SWT.BORDER);
		locComponents.addFontExec(foundList);
		// foundList.setItems(new String[] { "proba", "tertain",
		// "tesselation","tessera", "test", "testable", "testaceous" });
		foundList.addMouseListener(new FoundListClickListener());

		return foundList;
	}

	private class FoundListClickListener implements MouseListener {
		@Override
		public void mouseDoubleClick(MouseEvent evn) {
			select();
		}

		private void select() {
			String[] sel = foundList.getSelection();
			if (sel != null && sel.length > 0) {
				findWord(sel[0], false);
				searchText.setText(sel[0]);
				searchText.forceFocus();
			}
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			// do nothing
		}

		@Override
		public void mouseUp(MouseEvent arg0) {
			select();
		}
	}

	/** create context menu for description text field */
	private void createContextMenu(Display display) {
		Menu menuBar = new Menu(getParent().getShell(), SWT.POP_UP);
		MenuItem copyMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		locComponents.addTextExec(copyMenuItem, "DICT_CTX_MENU_COPY");
		copyMenuItem.addSelectionListener(new CopyItemListener(display));
		description.setMenu(menuBar);
	}

	/**
	 * Listener class that support Copy button
	 * 
	 * @author pivanov
	 */
	class CopyItemListener implements SelectionListener {
		private Display display;

		public CopyItemListener(Display display) {
			this.display = display;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			widgetDefaultSelected(event);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			copyToClipboard(display, description.getSelectionText());
		}
	}

	@Override
	public void changePreferences() {
		locComponents.localizeAll();
		getParent().getShell().redraw();
	}

	@Override
	public void changeClipboard(CopyClipboard clipboard) {
		boolean found = findWord(clipboard.getCurrentWord(), true);
		searchText.setText(clipboard.getCurrentWord());
		searchText.forceFocus();
		// activate current window if word is found
		if (found) {
			getParent().getShell().forceActive();
		}
	}

	public void copyToClipboard(Display currentDisplay, String plainText) {
		if (plainText == null || plainText.trim().length() < 1) {
			return;
		}
		Clipboard clipboard = new Clipboard(currentDisplay);
		TextTransfer textTransfer = TextTransfer.getInstance();
		clipboard.setContents(new String[] { plainText }, new Transfer[] { textTransfer });
		clipboard.dispose();
	}
}
