package aurelienribon.flow.services;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public abstract class Service {
	// -------------------------------------------------------------------------
	// Abstract API
	// -------------------------------------------------------------------------

	public abstract void process(String input) throws ServiceExecutionException;

	// -------------------------------------------------------------------------
	// Protected API
	// -------------------------------------------------------------------------

	protected void log(String msg) {
		assert callback != null;
		callback.logRequested(msg);
	}

	protected void updateProgress(float progress, String description) {
		assert callback != null;
		callback.progressUpdateRequested(progress, description);
	}

	protected void show(String title, ServiceUi panel) {
		assert callback != null;
		callback.showRequested(title, panel);
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
		public void showRequested(String title, ServiceUi panel);
		public void progressUpdateRequested(float progress, String description);
	}
}
