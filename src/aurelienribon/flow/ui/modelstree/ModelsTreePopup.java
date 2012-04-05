package aurelienribon.flow.ui.modelstree;

import aurelienribon.flow.models.ModelTuple;
import aurelienribon.flow.ServiceProvider;
import aurelienribon.flow.models.ModelUtils;
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
		if (obj instanceof ModelTuple) return new ModelsTreePopupModelTuple(services, (ModelTuple) obj);
		if (obj instanceof File) return new ModelsTreePopupFile(services, (File) obj);
		return null;
	}

	// -------------------------------------------------------------------------
	// Popup -- ModelTuple
	// -------------------------------------------------------------------------

	private static class ModelsTreePopupModelTuple extends ModelsTreePopup {
		public ModelsTreePopupModelTuple(ServiceProvider services, ModelTuple mt) {
			File file;
			Action action;

			action = createCompileAction("Compile", services, mt);
			action.setEnabled(mt.getModel().isSourceValid(mt.getConstraintName()));
			add(action);

			action = createClearResultsAction("Clear", mt);
			action.setEnabled(true);
			add(action);

			add(new Separator());

			file = mt.getModel().getModelFile();
			action = createEditAction("Edit model file", services, file);
			action.setEnabled(file.exists());
			add(action);

			file = mt.getModel().getConstraintsFile(mt.getConstraintName());
			action = createEditAction("Edit constraint file", services, file);
			action.setEnabled(file.exists());
			add(action);

			file = mt.getModel().getVhdlFile(mt.getConstraintName());
			action = createEditAction("Edit generated vhdl file", services, file);
			action.setEnabled(file.exists());
			add(action);

			file = mt.getModel().getMetaFile(mt.getConstraintName());
			action = createEditAction("Edit generated meta file", services, file);
			action.setEnabled(file.exists());
			add(action);

			add(new Separator());

			file = mt.getModel().getGraphFile(mt.getConstraintName());
			action = createShowGraphAction("Show graph", services, file);
			action.setEnabled(file.exists());
			add(action);
		}
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
	// Helpers
	// -------------------------------------------------------------------------

	private static Action createEditAction(String name, final ServiceProvider services, final File file) {
		return new AbstractAction(name, Res.getImage("gfx/ic_edit.png")) {
			@Override public void actionPerformed(ActionEvent e) {
				services.launchSync(ServiceProvider.EDIT, file.getPath());
			}
		};
	}

	private static Action createShowGraphAction(String name, final ServiceProvider services, final File file) {
		return new AbstractAction(name, Res.getImage("gfx/ic_graph.png")) {
			@Override public void actionPerformed(ActionEvent e) {
				services.launchSync(ServiceProvider.PLOT_GRAPH, file.getPath());
			}
		};
	}

	private static Action createCompileAction(String name, final ServiceProvider services, final ModelTuple mt) {
		return new AbstractAction(name, Res.getImage("gfx/ic_play.png")) {
			@Override public void actionPerformed(ActionEvent e) {
				services.launchSync(ServiceProvider.COMPILE, mt.getName());
			}
		};
	}

	private static Action createClearResultsAction(String name, final ModelTuple mt) {
		return new AbstractAction(name, Res.getImage("gfx/ic_bin.png")) {
			@Override public void actionPerformed(ActionEvent e) {
				ModelUtils.clearResult(mt.getModel(), mt.getConstraintName());
			}
		};
	}
}
