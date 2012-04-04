package aurelienribon.flow;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ServiceExecutionException extends Exception {
	public ServiceExecutionException(String msg) {
		super(msg);
	}

	public ServiceExecutionException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
