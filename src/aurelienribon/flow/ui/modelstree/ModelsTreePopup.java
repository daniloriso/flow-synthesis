package aurelienribon.flow.ui.modelstree;

import aurelienribon.flow.models.ModelTuple;
import aurelienribon.flow.services.ServiceProvider;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ModelsTreePopup extends JPopupMenu {
	public static JPopupMenu create(ServiceProvider services, Object obj) {
		if (obj instanceof File) return new ModelsTreePopupFile(services, (File) obj);
		if (obj instanceof ModelTuple) return new ModelsTreePopupModelTuple(services, (ModelTuple) obj);
		return null;
	}

	// -------------------------------------------------------------------------
	// Popup -- File
	// -------------------------------------------------------------------------

	private static class ModelsTreePopupFile extends ModelsTreePopup {
		public ModelsTreePopupFile(ServiceProvider services, File file) {
			add(createEditAction("Edit", services, file));
		}
	}

	// -------------------------------------------------------------------------
	// Popup -- ModelTuple
	// -------------------------------------------------------------------------

	private static class ModelsTreePopupModelTuple extends ModelsTreePopup {
		public ModelsTreePopupModelTuple(ServiceProvider services, ModelTuple mt) {
			File[] files = new File[] {
				mt.getModel().getModelFile(), mt.getModel().getConstraintsFile(mt.getConstraintName()),
				mt.getModel().getVhdlFile(mt.getConstraintName()), mt.getModel().getMetaFile(mt.getConstraintName())
			};

			String[] names = new String[] {
				"Edit model file", "Edit constraint file",
				"Edit generated vhdl file", "Edit generated meta file"
			};

			for (int i=0; i<files.length; i++) {
				if (files[i].exists()) add(createEditAction(names[i], services, files[i]));
			}
		}
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static Action createEditAction(String name, final ServiceProvider services, final File file) {
		return new AbstractAction(name, Res.getImage("gfx/ic_edit.png")) {
			@Override public void actionPerformed(ActionEvent e) {
				services.launchSync(ServiceProvider.EDIT, file);
			}
		};
	}
}
