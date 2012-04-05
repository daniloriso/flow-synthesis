package aurelienribon.flow.services.setupgraphlab;

import aurelienribon.common.GlabUtils;
import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceContext;
import aurelienribon.flow.ServiceExecutionException;
import javax.swing.SwingUtilities;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SetupGraphlabService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		final LaunchingGraphlabDialog dialog = new LaunchingGraphlabDialog(ctx.ui.getWindow(), true);
		dialog.setLocationRelativeTo(ctx.ui.getWindow());

		SwingUtilities.invokeLater(new Runnable() {@Override public void run() {dialog.setVisible(true);}});

		try {
			Thread.sleep(500);
			GlabUtils.initialize();
		} catch (Exception ex) {
		}

		SwingUtilities.invokeLater(new Runnable() {@Override public void run() {dialog.dispose();}});
	}
}
