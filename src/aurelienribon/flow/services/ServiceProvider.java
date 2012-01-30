package aurelienribon.flow.services;

import aurelienribon.flow.services.loadgraphlab.LoadGraphlabService;
import aurelienribon.flow.services.welcome.WelcomeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceProvider {
	public static final String LOAD_GRAPHLAB = "LOAD_GRAPHLAB";
	public static final String SHOW_WELCOME = "SHOW_WELCOME";
	private static final Map<String, Service> services = new HashMap<String, Service>();

	static {
		services.put(LOAD_GRAPHLAB, new LoadGraphlabService());
		services.put(SHOW_WELCOME, new WelcomeService());
	}

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	public static void launchSync(final String serviceName, final String input) {
		if (!services.containsKey(serviceName)) throw new RuntimeException("Service not found: " + serviceName);

		final Service service = services.get(serviceName);
		final String serviceInput = input == null ? "" : input;

		service.callback = new Service.Callback() {
			@Override public void logRequested(String msg) {fireServiceLog(serviceName, service, msg);}
			@Override public void showRequested(String title, ServiceUi panel) {fireServiceShow(serviceName, service, title, panel);}
			@Override public void progressUpdateRequested(float progress, String description) {fireServiceProgressUpdate(serviceName, service, progress, description);}
		};

		fireServiceCall(serviceName, service, input);

		try {
			service.process(serviceInput);
		} catch (ServiceExecutionException ex) {
			fireServiceError(serviceName, service, ex);
		}

		fireServiceComplete(serviceName, service);
		service.callback = null;
	}

	public static void launchAsync(final String serviceName, final String input, final Callback callback) {
		if (!services.containsKey(serviceName)) throw new RuntimeException("Service not found: " + serviceName);

		final Service service = services.get(serviceName);
		final String serviceInput = input == null ? "" : input;

		service.callback = new Service.Callback() {
			@Override public void logRequested(String msg) {fireServiceLog(serviceName, service, msg);}
			@Override public void showRequested(String title, ServiceUi panel) {fireServiceShow(serviceName, service, title, panel);}
			@Override public void progressUpdateRequested(float progress, String description) {fireServiceProgressUpdate(serviceName, service, progress, description);}
		};

		Runnable runnable = new Runnable() {
			@Override public void run() {
				fireServiceCall(serviceName, service, input);
				synchronize(new Runnable() {@Override public void run() {if (callback != null) callback.begin();}});

				try {
					service.process(serviceInput);
				} catch (ServiceExecutionException ex) {
					fireServiceError(serviceName, service, ex);
				}

				synchronize(new Runnable() {@Override public void run() {if (callback != null) callback.complete();}});
				fireServiceComplete(serviceName, service);
				service.callback = null;
			}
		};

		Thread th = new Thread(runnable, "Service - " + serviceName);
		th.start();
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

	private static final List<EventListener> listeners = new CopyOnWriteArrayList<EventListener>();

	public static void addListener(EventListener listener) {
		assert !listeners.contains(listener);
		listeners.add(listener);
	}

	public static interface EventListener {
		public void serviceCall(String serviceName, Service service, String input);
		public void serviceComplete(String serviceName, Service service);
		public void serviceProgressUpdate(String serviceName, Service service, float progress, String description);
		public void serviceLog(String serviceName, Service service, String msg);
		public void serviceError(String serviceName, Service service, ServiceExecutionException ex);
		public void serviceShow(String serviceName, Service service, String title, ServiceUi panel);
	}

	private static void fireServiceCall(final String serviceName, final Service service, final String input) {
		synchronize(new Runnable() {
			@Override public void run() {
				for (EventListener listener : listeners)
					listener.serviceCall(serviceName, service, input);
			}
		});
	}

	private static void fireServiceComplete(final String serviceName, final Service service) {
		synchronize(new Runnable() {
			@Override public void run() {
				for (EventListener listener : listeners)
					listener.serviceComplete(serviceName, service);
			}
		});
	}

	private static void fireServiceProgressUpdate(final String serviceName, final Service service, final float progress, final String description) {
		synchronize(new Runnable() {
			@Override public void run() {
				for (EventListener listener : listeners)
					listener.serviceProgressUpdate(serviceName, service, progress, description);
			}
		});
	}

	private static void fireServiceLog(final String serviceName, final Service service, final String msg) {
		synchronize(new Runnable() {
			@Override public void run() {
				for (EventListener listener : listeners)
					listener.serviceLog(serviceName, service, msg);
			}
		});
	}

	private static void fireServiceError(final String serviceName, final Service service, final ServiceExecutionException ex) {
		synchronize(new Runnable() {
			@Override public void run() {
				for (EventListener listener : listeners)
					listener.serviceError(serviceName, service, ex);
			}
		});
	}

	private static void fireServiceShow(final String serviceName, final Service service, final String title, final ServiceUi panel) {
		synchronize(new Runnable() {
			@Override public void run() {
				for (EventListener listener : listeners)
					listener.serviceShow(serviceName, service, title, panel);
			}
		});
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static void synchronize(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}
}
