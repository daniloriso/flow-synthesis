package aurelienribon.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TemplateManager {
	private static final Map<String, String> replacementMap = new HashMap<String, String>();

	public static void register(String variable, String replacement) {
		replacementMap.put(variable, replacement);
	}

	public static void reset() {
		replacementMap.clear();
	}

	public static String build(File file) {
		try {
			String content = FileUtils.readFileToString(file);
			for (String variable : replacementMap.keySet())
				content = content.replaceAll(Pattern.quote("@{" + variable + "}"), replacementMap.get(variable));
			return content;
		} catch (IOException ex) {
			return null;
		}
	}

	public static String build(URL url) {
		try {
			return IOUtils.toString(url);
		} catch (IOException ex) {
			return null;
		}
	}
}
