package bg.plambis.dict.gui;

import java.io.InputStream;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author plambis
 */
public final class GUIUtil {
	private GUIUtil() {
		// cannot instantiate util class
	}

	public static InputStream getImageStream(String imagePath) {
		return GUIUtil.class.getClassLoader().getResourceAsStream(imagePath);
	}

	public static Image loadImage(Device display, String path) {
		return new Image(display, GUIUtil.getImageStream(path));
	}
}
