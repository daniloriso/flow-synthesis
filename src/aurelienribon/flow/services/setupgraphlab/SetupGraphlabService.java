package aurelienribon.flow.services.setupgraphlab;

import aurelienribon.common.GlabUtils;
import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceContext;
import aurelienribon.flow.services.ServiceExecutionException;
import javax.swing.SwingUtilities;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SetupGraphlabService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		final LaunchingGraphlabDialog dialog = new LaunchingGraphlabDialog(ctx.ui.window, true);
		dialog.setLocationRelativeTo(ctx.ui.window);

		SwingUtilities.invokeLater(new Runnable() {@Override public void run() {dialog.setVisible(true);}});

		try {
			Thread.sleep(500);
			GlabUtils.initialize();
		} catch (Exception ex) {
			throw new ServiceExecutionException("Initialization failed.", ex);
		}

		SwingUtilities.invokeLater(new Runnable() {@Override public void run() {dialog.setVisible(false);}});
	}
}
