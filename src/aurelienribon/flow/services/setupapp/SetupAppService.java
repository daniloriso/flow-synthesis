package aurelienribon.flow.services.setupapp;

import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceContext;
import aurelienribon.flow.ServiceExecutionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.ini4j.Wini;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SetupAppService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		File configFile = new File("config.ini");

		try {
			if (configFile.exists()) {
				Wini ini = new Wini(configFile);
				setupProxy(ini);
			} else {
				URL url = Res.getUrl("ini/config.ini");
				FileUtils.copyURLToFile(url, configFile);
			}
		} catch (IOException ex) {
			exit(ex);
		}
	}

	private void setupProxy(Wini ini) {
		String host = ini.get("proxy", "host");
		String port = ini.get("proxy", "port");

		if (host != null) {
			host = host.trim();
			if (!host.equals("")) System.setProperty("http.proxyHost", host);
		}

		if (port != null) {
			port = port.trim();
			if (!port.equals("")) System.setProperty("http.proxyPort", port);
		}
	}
}
