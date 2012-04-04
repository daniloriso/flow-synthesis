package aurelienribon.flow.models;

import aurelien.utils.GraphUtils;
import graph.Graph;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelUtils {
	public static String getConstraint(File constraintsFile, String param) {
		if (constraintsFile == null || !constraintsFile.exists()) return null;

		String content;
		try {
			content = FileUtils.readFileToString(constraintsFile);
		} catch (IOException ex) {
			return null;
		}

		Pattern p = Pattern.compile(param + "\\s*\\{([^}]*)\\}", Pattern.DOTALL);
		Matcher m = p.matcher(content);

		if (m.find()) return m.group(1).replaceAll("(?m)^[ \t]+", "").trim();
		else return null;
	}

	public static void clearResult(Model model, String constraintName) {
		FileUtils.deleteQuietly(model.getVhdlFile(constraintName));
		FileUtils.deleteQuietly(model.getMetaFile(constraintName));
		FileUtils.deleteQuietly(model.getGraphFile(constraintName));
	}

	public static Graph deserializeGraph(Model model, String constraintName) {
		if (!model.getGraphFile(constraintName).exists()) return null;

		try {
			String graphStr = FileUtils.readFileToString(model.getGraphFile(constraintName));
			Graph graph = GraphUtils.deserialize(graphStr);
			return graph;

		} catch (IOException ex) {
			return null;
		}
	}
}
