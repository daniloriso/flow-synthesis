package aurelienribon.flow.services.welcome;

import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceContext;
import aurelienribon.flow.services.ServiceExecutionException;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class WelcomeService extends Service {
	@Override public void process(ServiceContext ctx) throws ServiceExecutionException {
		show("Welcome", new WelcomePanel(), null);
	}
}
