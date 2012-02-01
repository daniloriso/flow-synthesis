package aurelienribon.flow.models;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelTuple {
	private final Model model;
	private final String constraintName;

	public ModelTuple(Model model, String constraintName) {
		this.model = model;
		this.constraintName = constraintName;
	}

	public Model getModel() {
		return model;
	}

	public String getConstraintName() {
		return constraintName;
	}
}
