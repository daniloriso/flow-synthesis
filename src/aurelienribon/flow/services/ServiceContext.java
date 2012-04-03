package aurelienribon.flow.services;

import javax.swing.JFrame;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceContext {
	public final String input;
	public final ServiceProvider services;
	public final Ui ui;

	public ServiceContext(String input, ServiceProvider services, JFrame window) {
		this.input = input;
		this.services = services;
		this.ui = new Ui(window);
	}

	public static class Ui {
		public final JFrame window;

		public Ui(JFrame window) {
			this.window = window;
		}
	}
}
