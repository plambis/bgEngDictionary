package bg.plambis.dict.gui.local;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 * Resources representation
 * 
 * @author plambis
 */
@Root(name = "properties")
public class TextResource implements Iterable<String>{
	@ElementMap(entry = "property", key = "key", attribute = true, inline = true)
	private Map<String, String> map = new HashMap<String, String>();

	@Attribute
	private String lang;
	
	protected TextResource(){
		//do nothing
	}
	
	public TextResource(Locale locale, Map<String,String> map){
		this.lang = locale.getLanguage();
		this.map = map;
	}

	public String getLang() {
		return lang;
	}

	public String getValue(String key) {
		if (key == null)
			return null;
		return map.get(key) == null ? key : map.get(key);
	}

	public Iterator<String> iterator() {
		return map.keySet().iterator();
	}
}
