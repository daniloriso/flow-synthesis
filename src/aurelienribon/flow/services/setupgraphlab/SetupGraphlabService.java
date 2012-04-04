package aurelienribon.flow.services.setupgraphlab;

import aurelienribon.common.GlabUtils;
import aurelienribon.common.GlabUtils.Result;
import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceContext;
import aurelienribon.flow.ServiceExecutionException;
import javax.swing.SwingUtilities;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SetupGraphlabService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		final GlabView view = new GlabView();
		show("Console", view, Res.getImage("gfx/ic_terminal.png"));

		GlabUtils.setCallback(new GlabUtils.Callback() {
			@Override public void commandCalled(String cmd) {
				view.appendCommand(cmd);
			}

			@Override public void commandReturned(Result result) {
				view.appendAnswer(result.text);
			}
		});

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
