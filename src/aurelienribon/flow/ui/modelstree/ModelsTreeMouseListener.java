package aurelienribon.flow.ui.modelstree;

import aurelienribon.flow.services.ServiceProvider;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
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
		DefaultMutableTreeNode node = getMouseOverSelectionNode(e);
		if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
			if (node != null && node.getUserObject() instanceof File) {
				File file = (File) node.getUserObject();
				services.launchSync(ServiceProvider.EDIT, file.getAbsolutePath());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if (path != null) tree.setSelectionPath(path);
		
		DefaultMutableTreeNode node = getMouseOverSelectionNode(e);
		if (e.isPopupTrigger() && node != null) {
			JPopupMenu popup = ModelsTreePopup.create(services, node.getUserObject());
			if (popup != null) popup.show(tree, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		DefaultMutableTreeNode node = getMouseOverSelectionNode(e);
		if (e.isPopupTrigger() && node != null) {
			JPopupMenu popup = ModelsTreePopup.create(services, node.getUserObject());
			if (popup != null) popup.show(tree, e.getX(), e.getY());
		}
	}
	
	private DefaultMutableTreeNode getMouseOverSelectionNode(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if (path != null && path == tree.getSelectionPath())
			return (DefaultMutableTreeNode) path.getLastPathComponent();
		return null;
	}
}
