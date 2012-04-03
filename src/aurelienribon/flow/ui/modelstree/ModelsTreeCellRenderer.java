package aurelienribon.flow.ui.modelstree;

import aurelienribon.flow.models.ModelTuple;
import aurelienribon.flow.models.ModelUtils;
import java.awt.Component;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.apache.commons.io.FilenameUtils;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelsTreeCellRenderer extends DefaultTreeCellRenderer {
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		if (node.getUserObject() instanceof ModelTuple) {
			ModelTuple mt = (ModelTuple) node.getUserObject();
			label.setText(ModelUtils.getName(mt.getModel(), mt.getConstraintName(), " (", ")"));

			if (mt.getModel().isSourceValid(mt.getConstraintName()) && mt.getModel().isResultValid(mt.getConstraintName())) {
				label.setIcon(Res.getImage("gfx/ic_project_green.png"));
			} else if (mt.getModel().isSourceValid(mt.getConstraintName())) {
				label.setIcon(Res.getImage("gfx/ic_project_orange.png"));
				String txt = mt.getModel().getResultError(mt.getConstraintName());
				txt = "<html>" + txt.replaceAll("\n", "<br/>");
				label.setToolTipText(txt);
			} else {
				label.setIcon(Res.getImage("gfx/ic_project_red.png"));
				String txt = mt.getModel().getSourceError(mt.getConstraintName());
				txt = "<html>" + txt.replaceAll("\n", "<br/>");
				label.setToolTipText(txt);
			}

		} else if (node.getUserObject() instanceof File) {
			final File file = (File) node.getUserObject();
			label.setIcon(Res.getImage("gfx/ic_file.png"));

			String ext = FilenameUtils.getExtension(file.getName());
			if (ext.equals("m")) label.setText("Model file");
			else if (ext.equals("constraints")) label.setText("Constraints file");
			else if (ext.equals("vhd")) label.setText("(Generated) Vhdl file");
			else if (ext.equals("meta")) label.setText("(Generated) Meta file");
		}

		return label;
	}
}
