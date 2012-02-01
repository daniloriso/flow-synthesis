/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aurelienribon.flow.ui.modelstree;

import aurelienribon.flow.services.ServiceProvider;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelsTreeMouseListener extends MouseAdapter {
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
			if (path != null && path == tree.getSelectionPath()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				if (node.getUserObject() instanceof File) {
					File file = (File) node.getUserObject();
					services.launchSync(ServiceProvider.EDIT, file.getAbsolutePath());
				}
			}
		}
	}
}
