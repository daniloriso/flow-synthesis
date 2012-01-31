package aurelienribon.flow.services.edit;

import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceContext;
import aurelienribon.flow.services.ServiceExecutionException;
import java.io.File;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class EditService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		File file = new File(ctx.input);
		EditView view = new EditView();
		view.setup(file);
		show(file.getName(), view);
	}
}
