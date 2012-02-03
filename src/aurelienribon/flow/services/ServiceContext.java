package aurelienribon.flow.services;

import aurelienribon.flow.ui.Theme;
import javax.swing.JFrame;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceContext {
	public final Object input;
	public final ServiceProvider services;
	public final Ui ui;

	public ServiceContext(Object input, ServiceProvider services, JFrame window, Theme theme) {
		this.input = input;
		this.services = services;
		this.ui = new Ui(window, theme);
	}
	
	public static class Ui {
		public final JFrame window;
		public final Theme theme;
		
		public Ui(JFrame window, Theme theme) {
			this.window = window;
			this.theme = theme;
		}
	}
}
