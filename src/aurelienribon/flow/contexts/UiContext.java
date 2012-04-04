package aurelienribon.flow.contexts;

import javax.swing.JFrame;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class UiContext {
	private final JFrame window;

	public UiContext(JFrame window) {
		this.window = window;
	}

	public void initilialize() {
	}

	public JFrame getWindow() {
		return window;
	}
}
