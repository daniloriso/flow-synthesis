
import aurelienribon.flow.ui.MainWindow;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException ex) {
				} catch (InstantiationException ex) {
				} catch (IllegalAccessException ex) {
				} catch (UnsupportedLookAndFeelException ex) {
				}

				MainWindow mw = new MainWindow();
				mw.setSize(trimSize(1200, 800));
				mw.setLocationRelativeTo(null);
				mw.setVisible(true);
			}
		});
	}

	private static Dimension trimSize(int w, int h) {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		if (size.width < w) w = size.width - 100;
		if (size.height < h) h = size.height - 100;
		return new Dimension(w, h);
	}
}
