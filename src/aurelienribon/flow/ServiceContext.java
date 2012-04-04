package aurelienribon.flow;

import aurelienribon.flow.contexts.ModelsContext;
import aurelienribon.flow.contexts.UiContext;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceContext {
	public final String input;
	public final ServiceProvider services;
	public final ModelsContext models;
	public final UiContext ui;

	public ServiceContext(String input, ServiceProvider services, ModelsContext models, UiContext ui) {
		this.input = input;
		this.services = services;
		this.ui = ui;
		this.models = models;
	}
}
