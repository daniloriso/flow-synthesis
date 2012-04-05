package aurelienribon.flow.contexts;

import aurelienribon.flow.models.Model;
import aurelienribon.flow.models.ModelTuple;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.ini4j.Wini;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelsContext {
	private final List<Model> models = new ArrayList<>();
	private File modelsDir;
	private File resultsDir;
	private File scriptFile;

	public void initilialize() throws IOException {
		Wini ini = new Wini(new File("config.ini"));

		String modelsDirStr = ini.get("paths", "modelsDir");
		String resultsDirStr = ini.get("paths", "resultsDir");
		String scriptFileStr = ini.get("paths", "scriptFile");

		modelsDir = new File(modelsDirStr != null ? modelsDirStr : "");
		resultsDir = new File(resultsDirStr != null ? resultsDirStr : "");
		scriptFile = new File(scriptFileStr != null ? scriptFileStr : "");

		if (!modelsDir.exists()) modelsDir.mkdirs();
		if (!resultsDir.exists()) resultsDir.mkdirs();
	}

	public File getModelsDir() {
		return modelsDir;
	}

	public File getResultsDir() {
		return resultsDir;
	}

	public File getScriptFile() {
		return scriptFile;
	}

	public List<Model> getModels() {
		return Collections.unmodifiableList(models);
	}

	public List<Model> getModelsUnsafe() {
		return models;
	}

	public ModelTuple getModel(String name) {
		for (Model model : models) {
			for (String constraintName : model.getConstraintsNames()) {
				if (model.getName(constraintName).equals(name)) {
					return new ModelTuple(model, constraintName);
				}
			}
		}

		return null;
	}
}
