package bg.plambis.dict.gui.local;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class TestTextResource {

	@Test
	public void testCreateTextResource() throws Exception {
		Map<String, String> words = new HashMap<String, String>();
		words.put("DICT_TITLE", "Dictionary");
		words.put("DICT_MENU_FILE", "&File");
		words.put("DICT_MENU_EXIT", "E&xit");
		words.put("DICT_MENU_PREF", "&Preferences");
		words.put("DICT_MENU_HELP", "&Help");
		words.put("DICT_MENU_ABOUT", "&Help");

		Serializer serializer = new Persister();
		TextResource textResource = new TextResource(Locale.ENGLISH, words);
		File result = new File("testRes.xml");

		serializer.write(textResource, result);
	}

	@Test
	public void testReadTextResource() {
		Serializer serializer = new Persister();
		TextResource resources = null;
		File in = new File("testRes.xml");
		try {
			resources = serializer.read(TextResource.class, in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull("Resource is null", resources);
		for (String key : resources) {
			System.out.println("Key: " + key + " value: " + resources.getValue(key));
		}
	}

	public void testLocalizationUtil() {
		System.out.println("DICT_NAME : " + LocalizationUtil.getI18nText("DICT_NAME", Locale.ENGLISH));
	}
}