package aurelienribon.flow.services;

import javax.swing.JFrame;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceContext {
	public final String input;
	public final ServiceProvider services;
	public final JFrame window;

	public ServiceContext(String input, ServiceProvider services, JFrame window) {
		this.input = input;
		this.services = services;
		this.window = window;
	}
}
