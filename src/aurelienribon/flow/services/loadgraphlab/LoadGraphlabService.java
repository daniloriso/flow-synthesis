package aurelienribon.flow.services.loadgraphlab;

import aurelienribon.common.GlabUtils;
import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceExecutionException;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class LoadGraphlabService extends Service {
	@Override
	public void process(String input) throws ServiceExecutionException {
		try {
			GlabUtils.initialize();
		} catch (Exception ex) {
			throw new ServiceExecutionException("Initialization failed.", ex);
		}
	}
}
