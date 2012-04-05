package aurelienribon.flow.ui.modelstree;

import aurelienribon.flow.models.Model;
import aurelienribon.flow.models.ModelTuple;
import java.io.File;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelsTree extends JTree {
	public ModelsTree() {
		setRootVisible(false);
		setShowsRootHandles(true);
	}

	public void build(List<Model> models) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		for (Model model : models) {
			for (String constraintName : model.getConstraintsNames()) {
				DefaultMutableTreeNode modelNode = new DefaultMutableTreeNode(new ModelTuple(model, constraintName));
				root.add(modelNode);

				File[] files = new File[] {
					model.getModelFile(), model.getConstraintsFile(constraintName),
					model.getVhdlFile(constraintName), model.getMetaFile(constraintName)
				};

				for (File file : files) if (file.exists()) modelNode.add(new DefaultMutableTreeNode(file));
			}
		}

		setModel(new DefaultTreeModel(root));
	}
}
