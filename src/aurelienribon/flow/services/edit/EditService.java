package aurelienribon.flow.services.edit;

import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceContext;
import aurelienribon.flow.ServiceExecutionException;
import java.io.File;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class EditService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		File file = new File(ctx.input);
		EditView view = new EditView();
		view.setup(file);
		show(file.getName(), view, Res.getImage("gfx/ic_file.png"));
	}
}
