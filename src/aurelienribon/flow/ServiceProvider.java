package aurelienribon.flow;

import aurelienribon.flow.contexts.ModelsContext;
import aurelienribon.flow.contexts.UiContext;
import aurelienribon.flow.services.compile.CompileService;
import aurelienribon.flow.services.edit.EditService;
import aurelienribon.flow.services.graphplot.PlotGraphService;
import aurelienribon.flow.services.setupapp.SetupAppService;
import aurelienribon.flow.services.setupgraphlab.SetupGraphlabService;
import aurelienribon.flow.services.welcome.WelcomeService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceProvider {
	public static final String SETUP_APP = "SETUP_APP";
	public static final String SETUP_GRAPHLAB = "SETUP_GRAPHLAB";
	public static final String SHOW_WELCOME = "SHOW_WELCOME";
	public static final String EDIT = "EDIT";
	public static final String PLOT_GRAPH = "PLOT_GRAPH";
	public static final String COMPILE = "COMPILE";

	private final Map<String, Service> services = new HashMap<>();
	private final List<EventListener> listeners = new CopyOnWriteArrayList<>();
	private final ModelsContext modelsCtx;
	private final UiContext uiCtx;

	public ServiceProvider(JFrame window) throws IOException {
		this.modelsCtx = new ModelsContext();
		this.uiCtx = new UiContext(window);

		modelsCtx.initilialize();
		uiCtx.initilialize();

		services.put(SETUP_APP, new SetupAppService());
		services.put(SETUP_GRAPHLAB, new SetupGraphlabService());
		services.put(SHOW_WELCOME, new WelcomeService());
		services.put(EDIT, new EditService());
		services.put(PLOT_GRAPH, new PlotGraphService());
		services.put(COMPILE, new CompileService());
	}

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	public void launchSync(final String serviceName, final String input) {
		if (!services.containsKey(serviceName)) throw new RuntimeException("Service not found: " + serviceName);

		final Service service = services.get(serviceName);

		service.callback = new Service.Callback() {
			@Override public void logRequested(String msg) {fireServiceLog(serviceName, service, msg);}
			@Override public void showRequested(String title, JPanel panel, Icon icon) {fireServiceShow(serviceName, service, title, panel, icon);}
			@Override public void progressUpdateRequested(float progress, String description) {fireServiceProgressUpdate(serviceName, service, progress, description);}
		};

		fireServiceCall(serviceName, service, input);

		try {
			ServiceContext ctx = new ServiceContext(input, this, modelsCtx, uiCtx);
			service.process(ctx);
		} catch (ServiceExecutionException ex) {
			fireServiceError(serviceName, service, ex);
		}

		fireServiceComplete(serviceName, service);
		service.callback = null;
	}

	public void launchAsync(final String serviceName, final String input, final Callback callback) {
		if (!services.containsKey(serviceName)) throw new RuntimeException("Service not found: " + serviceName);

		final Service service = services.get(serviceName);

		service.callback = new Service.Callback() {
			@Override public void logRequested(String msg) {fireServiceLog(serviceName, service, msg);}
			@Override public void showRequested(String title, JPanel panel, Icon icon) {fireServiceShow(serviceName, service, title, panel, icon);}
			@Override public void progressUpdateRequested(float progress, String description) {fireServiceProgressUpdate(serviceName, service, progress, description);}
		};

		Runnable runnable = new Runnable() {
			@Override public void run() {
				fireServiceCall(serviceName, service, input);
				if (callback != null) callback.begin();

				try {
					ServiceContext ctx = new ServiceContext(input, ServiceProvider.this, modelsCtx, uiCtx);
					service.process(ctx);
				} catch (ServiceExecutionException ex) {
					fireServiceError(serviceName, service, ex);
				}

				if (callback != null) callback.complete();
				fireServiceComplete(serviceName, service);
				service.callback = null;
			}
		};

		Thread th = new Thread(runnable, "Service - " + serviceName);
		th.start();
	}

	// -------------------------------------------------------------------------
	// Accessors
	// -------------------------------------------------------------------------

	public ModelsContext getModelsCtx() {
		return modelsCtx;
	}

	public UiContext getUiCtx() {
		return uiCtx;
	}

	// -------------------------------------------------------------------------
	// Callback
	// -------------------------------------------------------------------------

	public static interface Callback {
		public void begin();
		public void complete();
	}

	// -------------------------------------------------------------------------
	// Events
	// -------------------------------------------------------------------------

	public void addListener(EventListener listener) {
		assert !listeners.contains(listener);
		listeners.add(listener);
	}

	public static interface EventListener {
		public void serviceCall(String serviceName, Service service, Object input);
		public void serviceComplete(String serviceName, Service service);
		public void serviceProgressUpdate(String serviceName, Service service, float progress, String description);
		public void serviceLog(String serviceName, Service service, String msg);
		public void serviceError(String serviceName, Service service, ServiceExecutionException ex);
		public void serviceShow(String serviceName, Service service, String title, JPanel panel, Icon icon);
	}

	private void fireServiceCall(final String serviceName, final Service service, final Object input) {
		for (EventListener listener : listeners) listener.serviceCall(serviceName, service, input);
	}

	private void fireServiceComplete(final String serviceName, final Service service) {
		for (EventListener listener : listeners) listener.serviceComplete(serviceName, service);
	}

	private void fireServiceProgressUpdate(final String serviceName, final Service service, final float progress, final String description) {
		for (EventListener listener : listeners) listener.serviceProgressUpdate(serviceName, service, progress, description);
	}

	private void fireServiceLog(final String serviceName, final Service service, final String msg) {
		for (EventListener listener : listeners) listener.serviceLog(serviceName, service, msg);
	}

	private void fireServiceError(final String serviceName, final Service service, final ServiceExecutionException ex) {
		for (EventListener listener : listeners) listener.serviceError(serviceName, service, ex);
	}

	private void fireServiceShow(final String serviceName, final Service service, final String title, final JPanel panel, final Icon icon) {
		for (EventListener listener : listeners) listener.serviceShow(serviceName, service, title, panel, icon);
	}
}
