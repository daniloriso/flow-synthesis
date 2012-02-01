package aurelienribon.flow.ui;

import aurelienribon.flow.models.Model;
import aurelienribon.flow.models.ModelUtils;
import aurelienribon.flow.services.ServiceProvider;
import gfx.Gfx;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.io.FilenameUtils;
import org.ini4j.Wini;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelsPanel extends javax.swing.JPanel {
	private final List<Model> models = new ArrayList<Model>();
	private File modelsDir;
	private File resultsDir;
	private File tempDir;
	private File scriptFile;
	private ServiceProvider services;

    public ModelsPanel() {
        initComponents();
    }

	public void setup(ServiceProvider services) {
		this.services = services;

		modelsTree.addMouseListener(new ModelsTreeMouseListener(modelsTree, services));
		modelsTree.setCellRenderer(new ModelsTreeCellRenderer());
		ToolTipManager.sharedInstance().registerComponent(modelsTree);

		String modelsDirStr = "";
		String resultsDirStr = "";
		String tempDirStr = "";
		String scriptFileStr = "";

		try {
			Wini ini = new Wini(new File("config.ini"));
			modelsDirStr = ini.get("paths", "modelsDir");
			resultsDirStr = ini.get("paths", "resultsDir");
			tempDirStr = ini.get("paths", "tempDir");
			scriptFileStr = ini.get("paths", "scriptFile");
		} catch (IOException ex) {
			System.err.println("ModelsPanel - Cannot read config.ini");
		}

		modelsDir = new File(modelsDirStr != null ? modelsDirStr : "");
		resultsDir = new File(resultsDirStr != null ? resultsDirStr : "");
		tempDir = new File(tempDirStr != null ? tempDirStr : "");
		scriptFile = new File(scriptFileStr != null ? scriptFileStr : "");

		if (!modelsDir.exists()) modelsDir.mkdirs();
		if (!resultsDir.exists()) resultsDir.mkdirs();

		load();
	}

	private void load() {
		models.clear();

		for (File file : modelsDir.listFiles()) {
			if (FilenameUtils.getExtension(file.getName()).equals("m")) {
				String name = FilenameUtils.getBaseName(file.getName());
				models.add(new Model(name, modelsDir, resultsDir));
			}
		}

		Collections.sort(models, new Comparator<Model>() {
			@Override public int compare(Model o1, Model o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		buildTree();
	}

	private void buildTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		for (Model model : models) {
			for (String constraintName : model.getConstraintsNames()) {
				DefaultMutableTreeNode modelNode = new DefaultMutableTreeNode(new ModelTuple(model, constraintName));
				root.add(modelNode);

				modelNode.add(new DefaultMutableTreeNode(model.getModelFile()));
				modelNode.add(new DefaultMutableTreeNode(model.getConstraintsFile(constraintName)));
				modelNode.add(new DefaultMutableTreeNode(model.getVhdlFile(constraintName)));
				modelNode.add(new DefaultMutableTreeNode(model.getMetaFile(constraintName)));
			}
		}

		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		modelsTree.setModel(treeModel);
	}

	// -------------------------------------------------------------------------
	// Inner class
	// -------------------------------------------------------------------------

	private static class ModelTuple {
		public final Model model;
		public final String constraintName;

		public ModelTuple(Model model, String constraintName) {
			this.model = model;
			this.constraintName = constraintName;
		}
	}

	private static class ModelsTreeCellRenderer extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

			if (node.getUserObject() instanceof ModelTuple) {
				ModelTuple mt = (ModelTuple) node.getUserObject();
				label.setText(ModelUtils.getName(mt.model, mt.constraintName, " (", ")"));

				if (mt.model.isSourceValid(mt.constraintName) && mt.model.isResultValid(mt.constraintName)) {
					label.setIcon(Gfx.IC_BULLET_GREEN);
				} else if (mt.model.isSourceValid(mt.constraintName)) {
					label.setIcon(Gfx.IC_BULLET_ORANGE);
					String txt = mt.model.getResultError(mt.constraintName);
					txt = "<html>" + txt.replaceAll("\n", "<br/>");
					label.setToolTipText(txt);
				} else {
					label.setIcon(Gfx.IC_BULLET_RED);
					String txt = mt.model.getSourceError(mt.constraintName);
					txt = "<html>" + txt.replaceAll("\n", "<br/>");
					label.setToolTipText(txt);
				}

			} else if (node.getUserObject() instanceof File) {
				final File file = (File) node.getUserObject();
				label.setIcon(Gfx.IC_FILE);
				String ext = FilenameUtils.getExtension(file.getName());

				if (ext.equals("m")) label.setText("Model file");
				else if (ext.equals("constraints")) label.setText("Constraints file");
				else if (ext.equals("vhd")) label.setText("(Generated) Vhdl file");
				else if (ext.equals("meta")) label.setText("(Generated) Meta file");
			}

			return label;
		}
	}

	private static class ModelsTreeMouseListener extends MouseAdapter {
		private final JTree tree;
		private final ServiceProvider services;

		public ModelsTreeMouseListener(JTree tree, ServiceProvider services) {
			this.tree = tree;
			this.services = services;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (path == tree.getSelectionPath()) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					if (node.getUserObject() instanceof File) {
						File file = (File) node.getUserObject();
						services.launchSync(ServiceProvider.EDIT, file.getAbsolutePath());
					}
				}
			}
		}
	}

	// -------------------------------------------------------------------------
	// Generated stuff
	// -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        modelsTree = new javax.swing.JTree();

        jScrollPane1.setBorder(null);

        modelsTree.setRootVisible(false);
        modelsTree.setShowsRootHandles(true);
        jScrollPane1.setViewportView(modelsTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree modelsTree;
    // End of variables declaration//GEN-END:variables
}
