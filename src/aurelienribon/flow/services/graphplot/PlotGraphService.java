package aurelienribon.flow.services.graphplot;

import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceContext;
import aurelienribon.flow.services.ServiceExecutionException;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PlotGraphService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		PlotGraphPanel canvas = new PlotGraphPanel();
		show("Graph", canvas, Res.getImage("gfx/ic_node.png"));
	}
}
