package aurelienribon.flow.services;

import aurelienribon.flow.ui.Theme;
import javax.swing.JPanel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServicePanel extends JPanel {
	private Theme theme = new Theme();

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Theme getTheme() {
		return theme;
	}
}
