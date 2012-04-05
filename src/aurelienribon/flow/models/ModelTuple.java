package aurelienribon.flow.models;

import java.util.Objects;

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

	public String getName() {
		return model.getName(constraintName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelTuple) {
			ModelTuple oo = (ModelTuple) obj;
			return model == oo.model && constraintName.equals(oo.constraintName);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 89 * hash + Objects.hashCode(this.model);
		hash = 89 * hash + Objects.hashCode(this.constraintName);
		return hash;
	}
}
