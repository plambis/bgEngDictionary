package bg.plambis.dict.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;

/**
 * Listen clipboard to new Copy word to searched text.
 *
 * @author plambis
 */
public class CopyClipboard implements Runnable {
	private static final Log LOG = LogFactory.getLog(CopyClipboard.class);
	/** The display. */
	private Display display;

	/** The listener. */
	private ChangeClipboard listener;

	/** The cb. */
	private Clipboard cb;

	/** The prev word. */
	private String prevWord = "";

	/** The current word. */
	private String currentWord = "";

	/** The finalize listening. */
	private boolean finalizeListening = false;

	/**
	 * Instantiates a new copy clipboard.
	 *
	 * @param display
	 *            the display
	 * @param listener
	 *            the listener
	 */
	public CopyClipboard(Display display, ChangeClipboard listener) {
		cb = new Clipboard(display);
		this.listener = listener;
		this.display = display;
	}

	/**
	 * Gets the current word.
	 *
	 * @return the current word
	 */
	public String getCurrentWord() {
		return currentWord;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			while (!finalizeListening) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (checkClipboard()) {
							invokeListener();
						}
					}
				});
				Thread.sleep(1000);
			}
		} catch (InterruptedException ie) {
			LOG.debug("error on listen clipboard", ie);
		}
	}

	/**
	 * Check clipboard.
	 *
	 * @return true, if successful
	 */
	public boolean checkClipboard() {
		currentWord = readClipboard();
		if (currentWord != null && !currentWord.equals(prevWord)) {
			prevWord = currentWord;
			if (currentWord.length() > 2 && currentWord.length() < 20) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Read clipboard.
	 *
	 * @return the string
	 */
	private String readClipboard() {
		String data = (String) cb.getContents(TextTransfer.getInstance());
		if (prevWord == null) {
			prevWord = data;
		}
		return data;
	}

	/**
	 * Invoke listener.
	 */
	private void invokeListener() {
		listener.changeClipboard(this);
	}

	/**
	 * Stop listening.
	 */
	public synchronized void stopListening() {
		finalizeListening = true;
	}
}
