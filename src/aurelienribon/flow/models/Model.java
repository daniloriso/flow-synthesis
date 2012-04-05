package aurelienribon.flow.models;

import java.io.File;
import java.util.*;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Model {
	static final String EMPTY_CONSTRAINT_NAME = "";

	private final String name;
	private final File modelFile;
	private final List<String> constraintsNames = new ArrayList<>();
	private final Map<String, File> constraintsFilesMap = new HashMap<>();
	private final Map<String, File> vhdlFilesMap = new HashMap<>();
	private final Map<String, File> metaFilesMap = new HashMap<>();
	private final Map<String, File> graphsFilesMap = new HashMap<>();

	public Model(String name, File modelsDir, File resultsDir) {
		assert modelsDir.isDirectory();
		assert resultsDir.isDirectory();

		this.name = name;
		this.modelFile = new File(modelsDir, name + ".m");

		for (File file : modelsDir.listFiles()) {
			String[] names = file.getName().split("\\.");
			if (names.length < 2 || names.length > 3) continue;
			if (!names[names.length-1].equals("constraints")) continue;
			if (!names[0].equals(name)) continue;
			constraintsNames.add(names.length == 2 ? EMPTY_CONSTRAINT_NAME : names[1]);
		}

		for (String constraintName : constraintsNames) {
			String fullname = constraintName.equals(EMPTY_CONSTRAINT_NAME) ? name : name + "." + constraintName;
			constraintsFilesMap.put(constraintName, new File(modelsDir, fullname + ".constraints"));
			vhdlFilesMap.put(constraintName, new File(resultsDir, fullname + ".vhd"));
			metaFilesMap.put(constraintName, new File(resultsDir, fullname + ".meta"));
			graphsFilesMap.put(constraintName, new File(resultsDir, fullname + ".graph"));
		}

		Collections.sort(constraintsNames);
	}

	public String getName() {
		return name;
	}

	public String getName(String constraintName) {
		return name + "-" + constraintName;
	}

	public String getHumanReadableName(String constraintName) {
		return constraintName.equals(Model.EMPTY_CONSTRAINT_NAME) ? getName() : getName() + " - " + constraintName;
	}

	public List<String> getConstraintsNames() {
		return Collections.unmodifiableList(constraintsNames);
	}

	public File getModelFile() {
		return modelFile;
	}

	public File getConstraintsFile(String constraintName) {
		return constraintsFilesMap.get(constraintName);
	}

	public File getVhdlFile(String constraintName) {
		return vhdlFilesMap.get(constraintName);
	}

	public File getMetaFile(String constraintName) {
		return metaFilesMap.get(constraintName);
	}

	public File getGraphFile(String constraintName) {
		return graphsFilesMap.get(constraintName);
	}

	public boolean isSourceValid(String constraintName) {
		return getModelFile().exists() && getConstraintsFile(constraintName).exists();
	}

	public boolean isResultValid(String constraintName) {
		return getVhdlFile(constraintName).exists() && getMetaFile(constraintName).exists() && getGraphFile(constraintName).exists();
	}

	public String getSourceError(String constraintName) {
		String error = "";
		if (!getModelFile().exists()) error += "No source model file found.\n";
		if (!getConstraintsFile(constraintName).exists()) error += "No source constraint file found.\n";
		return error.trim();
	}

	public String getResultError(String constraintName) {
		String error = "";
		if (!getVhdlFile(constraintName).exists()) error += "No generated vhdl file found.\n";
		if (!getMetaFile(constraintName).exists()) error += "No generated meta file found.\n";
		if (!getGraphFile(constraintName).exists()) error += "No generated graph file found.\n";
		return error.trim();
	}
}
