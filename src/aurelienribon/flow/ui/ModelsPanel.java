package aurelienribon.flow.ui;

import aurelienribon.flow.models.Model;
import aurelienribon.flow.services.ServiceProvider;
import aurelienribon.flow.ui.modelstree.ModelsTreeCellRenderer;
import aurelienribon.flow.ui.modelstree.ModelsTreeMouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ToolTipManager;
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

		initilialize();
		reload();
	}
	
	private void initilialize() {
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
	}
	
	private void load() {
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
	}

	private void reload() {
		models.clear();
		load();
		modelsTree.build(models);
	}

	// -------------------------------------------------------------------------
	// Generated stuff
	// -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        modelsTree = new aurelienribon.flow.ui.modelstree.ModelsTree();

        jScrollPane2.setBorder(null);

        modelsTree.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        jScrollPane2.setViewportView(modelsTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private aurelienribon.flow.ui.modelstree.ModelsTree modelsTree;
    // End of variables declaration//GEN-END:variables
}
