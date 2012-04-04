package aurelienribon.common;

import graph.Graph;
import graphbox.parser.CommandEvaluator;
import gui.jterm.InterpreterResult;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class GlabUtils {
	private static final ReentrantLock lock = new ReentrantLock();
	private static boolean isInitialized = false;
	private static Callback callback;

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	public static void initialize() throws Exception {
		if (!isInitialized) {
			graphbox.MainWindow.main(new String[] {"-nownd"});
			isInitialized = true;
		}
	}

	public static Graph getCurrentGraph() {
		if (!isInitialized) return null;
		return getEvaluator().HACK_AURE_currentGraph();
	}

	public static void setCurrentGraph(Graph g) {
		if (!isInitialized) return;
		getEvaluator().HACK_AURE_currentGraph(g);
	}

	public static void setCallback(Callback callback) {
		GlabUtils.callback = callback;
	}

	public static void execute(String cmd, String... args) {
		if (!isInitialized) return;
		String command = getCommand(cmd, args);

		if (callback != null) callback.commandCalled(command);
		InterpreterResult cmdRes = getEvaluator().CommandExecute(command);

		String text = "";
		for (String msg : cmdRes.getMessages()) text += msg + "\n";
		text = text.trim();

		Result result = new Result(command, text, cmdRes.isErrorMessage());
		if (callback != null) callback.commandReturned(result);
	}

	public static void executeSilently(String cmd, String... args) {
		if (!isInitialized) return;
		String command = getCommand(cmd, args);
		getEvaluator().CommandExecute(command);
	}

	public static void executeAsync(String cmd, String... args) {
		if (!isInitialized) return;
		final String command = getCommand(cmd, args);

		new Thread(new Runnable() {
			@Override public void run() {
				lock.lock();

				try {
					if (callback != null) callback.commandCalled(command);
					InterpreterResult cmdRes = getEvaluator().CommandExecute(command);

					String text = "";
					for (String msg : cmdRes.getMessages()) text += msg + "\n";
					text = text.trim();

					Result result = new Result(command, text, cmdRes.isErrorMessage());
					if (callback != null) callback.commandReturned(result);

				} finally {
					lock.unlock();
				}
			}
		}).start();
	}

	// -------------------------------------------------------------------------
	// Inner classes
	// -------------------------------------------------------------------------

	public static interface Callback {
		public void commandCalled(String cmd);
		public void commandReturned(Result result);
	}

	public static class Result {
		public final String cmd;
		public final String text;
		public final boolean isError;

		public Result(String cmd, String text, boolean isError) {
			this.cmd = cmd;
			this.text = text;
			this.isError = isError;
		}
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static CommandEvaluator getEvaluator() {
		return graphbox.MainWindow.HACK_AURE_getCommandEvaluator();
	}

	private static String getCommand(String cmd, String... args) {
		if (args.length == 0) return cmd;

		cmd += "(" + args[0];
		for (int i=1; i<args.length; i++) cmd += ", " + args[i];
		cmd += ")";
		return cmd;
	}
}
