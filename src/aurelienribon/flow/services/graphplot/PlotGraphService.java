package aurelienribon.flow.services.graphplot;

import aurelien.utils.GraphUtils;
import aurelienribon.common.GlabUtils;
import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceContext;
import aurelienribon.flow.ServiceExecutionException;
import graph.Graph;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PlotGraphService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		if (ctx.input != null) {
			Graph oldG = GlabUtils.getCurrentGraph();

			File file = new File(ctx.input);
			if (!file.isFile()) exit("File '" + ctx.input + "' does not exist.");

			try {
				String str = FileUtils.readFileToString(file);
				Graph g = GraphUtils.deserialize(str);
				GlabUtils.setCurrentGraph(g);
				GlabUtils.executeSilently("plot");
				GlabUtils.setCurrentGraph(oldG);
			} catch (IOException ex) {
				exit(ex);
			}

		} else {
			GlabUtils.executeSilently("plot");
		}
	}
}
