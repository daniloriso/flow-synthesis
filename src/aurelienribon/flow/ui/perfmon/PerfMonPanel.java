package aurelienribon.flow.ui.perfmon;

import com.sun.management.OperatingSystemMXBean;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PerfMonPanel extends JPanel {
	private static final Color BACKGROUND_COLOR = new Color(0xFFDABB);
	private static final Color BACKGROUND_LINES_COLOR = new Color(0xDFB593);
	private static final Color CPU_STROKE_COLOR = new Color(0x4B86E9);
	private static final Color CPU_FILL_COLOR = new Color(0x884B86E9, true);
	private static final Color MEM_STROKE_COLOR = new Color(0xE43CA6);
	private static final Color MEM_FILL_COLOR = new Color(0x88E43CA6, true);
	private static final int POINTS_CNT = 100;
	private final float[] cpuBuffer = new float[POINTS_CNT];
	private final float[] memBuffer = new float[POINTS_CNT];
	private int bufferLength = 0;

	public PerfMonPanel() {
		Timer timer = new Timer(1000, new ActionListener() {@Override public void actionPerformed(ActionEvent e) {update();}});
		timer.setInitialDelay(0);
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g.create();
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		if (getBorder() != null) {
			Insets ins = getBorder().getBorderInsets(this);
			w -= ins.left + ins.right;
			h -= ins.top + ins.bottom;
			gg.translate(ins.left, ins.top);
		}

		gg.setColor(BACKGROUND_COLOR);
		gg.fillRect(0, 0, w, h);

		gg.setColor(BACKGROUND_LINES_COLOR);
		for (int i=1, n=10; i<n; i++) gg.drawLine(0, h*i/n, w, h*i/n);
		for (int i=1, n=10; i<n; i++) gg.drawLine(w*i/n, 0, w*i/n, h);

		int[] xs = new int[bufferLength+2];
		xs[0] = w*(POINTS_CNT-bufferLength+1)/POINTS_CNT;
		xs[1] = w;
		for (int i=2; i<xs.length; i++) xs[i] = w*(POINTS_CNT-i+2)/POINTS_CNT;

		int[] ysCpu = new int[bufferLength+2];
		ysCpu[0] = ysCpu[1] = h;
		for (int i=2; i<ysCpu.length; i++) ysCpu[i] = (int) (h-cpuBuffer[i-2]*h);

		int[] ysMem = new int[bufferLength+2];
		ysMem[0] = ysMem[1] = h;
		for (int i=2; i<ysMem.length; i++) ysMem[i] = (int) (h-memBuffer[i-2]*h);

		gg.setColor(MEM_FILL_COLOR);
		gg.fillPolygon(xs, ysMem, bufferLength+2);
		gg.setColor(MEM_STROKE_COLOR);
		for (int i=1; i<bufferLength; i++) gg.drawLine(xs[i+1], ysMem[i+1], xs[i+2], ysMem[i+2]);

		gg.setColor(CPU_FILL_COLOR);
		gg.fillPolygon(xs, ysCpu, bufferLength+2);
		gg.setColor(CPU_STROKE_COLOR);
		for (int i=1; i<bufferLength; i++) gg.drawLine(xs[i+1], ysCpu[i+1], xs[i+2], ysCpu[i+2]);

		gg.dispose();
	}

	private void update() {
		OperatingSystemMXBean sys = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		Runtime rt = Runtime.getRuntime();

		for (int i=cpuBuffer.length-2; i>=0; i--) cpuBuffer[i+1] = cpuBuffer[i];
		for (int i=cpuBuffer.length-2; i>=0; i--) memBuffer[i+1] = memBuffer[i];
		cpuBuffer[0] = (float) sys.getSystemCpuLoad();
		memBuffer[0] = (float) (rt.totalMemory() - rt.freeMemory()) / rt.totalMemory();
		bufferLength = Math.min(POINTS_CNT, bufferLength+1);
		repaint();
	}
}
