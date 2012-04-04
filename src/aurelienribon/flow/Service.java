package aurelienribon.flow;

import javax.swing.Icon;
import javax.swing.JPanel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public abstract class Service {
	// -------------------------------------------------------------------------
	// Abstract API
	// -------------------------------------------------------------------------

	public abstract void process(ServiceContext ctx) throws ServiceExecutionException;

	// -------------------------------------------------------------------------
	// Protected API
	// -------------------------------------------------------------------------

	protected void log(String msg) {
		assert callback != null;
		callback.logRequested(msg);
	}

	protected void progress(float progress, String description) {
		assert callback != null;
		callback.progressUpdateRequested(progress, description);
	}

	protected void show(String title, JPanel panel, Icon icon) {
		assert callback != null;
		callback.showRequested(title, panel, icon);
	}

	// -------------------------------------------------------------------------
	// Callback
	// -------------------------------------------------------------------------

	Callback callback;

	void initialize(Callback callback) {
		this.callback = callback;
	}

	static interface Callback {
		public void logRequested(String msg);
		public void showRequested(String title, JPanel panel, Icon icon);
		public void progressUpdateRequested(float progress, String description);
	}
}
