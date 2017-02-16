package bg.plambis.dict.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import bg.plambis.dict.gui.local.LocalizationUtil;
import bg.plambis.dict.gui.pref.Preferences;
import bg.plambis.dict.gui.pref.PreferencesManager;

/**
 * About modal dialog
 * 
 * @author pivanov
 */
public class AboutDialog extends Dialog {
	private static final String DICT_ABOUT_TITLE = "DICT_ABOUT_TITLE";
	private static final String DICT_VERSION = "DICT_VERSION";
	private Shell dialog;

	public AboutDialog(Shell parent, int style) {
		super(parent, style);
	}

	public AboutDialog(Shell parent) {
		this(parent, 0); // your default style bits go here (not the Shell's
		// style bits)
	}

	private Preferences getPreferences() {
		return PreferencesManager.getInstance().getCurrentPreferences();
	}

	private String getVersion() {
		return LocalizationUtil.getI18nText(DICT_VERSION, getPreferences().getIntLang());
	}

	public void open() {
		Shell parent = getParent();
		Display display = parent.getDisplay();
		dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText(LocalizationUtil.getI18nText(DICT_ABOUT_TITLE, getPreferences().getIntLang()));
		dialog.setImage(GUIUtil.loadImage(display, DictionaryGUI.LOGO_PATH));

		GridLayout layout = new GridLayout(2, false);
		dialog.setLayout(layout);

		GridData data = new GridData();
		data.widthHint = 50;
		data.heightHint = 50;
		data.verticalSpan = 2;
		ImageData imageData = new ImageData(GUIUtil.getImageStream(DictionaryGUI.LOGO_PATH));
		imageData = imageData.scaledTo(50, 50);
		Button logo = new Button(dialog, SWT.PUSH | SWT.CENTER);
		logo.setImage(new Image(display, imageData));
		logo.setLayoutData(data);

		data = new GridData();
		data.widthHint = 250;
		Label label = new Label(dialog, SWT.LEFT);
		FontData fd = label.getFont().getFontData()[0];
		fd.setHeight(10);
		label.setFont(new Font(dialog.getDisplay(), fd));
		label.setText(LocalizationUtil.getI18nText("DICT_NAME", getPreferences().getIntLang()) + " v. " + getVersion() + "\n "
				+ LocalizationUtil.getI18nText("DICT_AUTHOR", getPreferences().getIntLang()) + "plambis\n "
				+ LocalizationUtil.getI18nText("DICT_AUTHOR_EMAIL", getPreferences().getIntLang()) + "plameniviv@gmail.com");
		label.setLayoutData(data);

		data = new GridData();
		data.widthHint = 50;
		data.horizontalAlignment = SWT.CENTER;
		Button close = new Button(dialog, SWT.PUSH | SWT.CENTER);
		close.setText("OK");
		close.setLayoutData(data);
		close.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				dialog.close();
			}

			@Override
			public void mouseDown(MouseEvent e) {
				dialog.close();
			}

			@Override
			public void mouseUp(MouseEvent e) {
				dialog.close();
			}
		});

		dialog.pack();
		dialog.open();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
