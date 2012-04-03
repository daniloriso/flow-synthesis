package aurelienribon.flow.services.graphplot;

import aurelienribon.ui.components.PaintedPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.SwingUtilities;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PlotGraphPanel extends PaintedPanel {
	private static final Image nodeImg = Res.getImage("gfx/graph_node.png").getImage();
	private double offsetX = 0, offsetY = 0, scale = 1;

	public PlotGraphPanel() {
		addMouseListener(zoomPanHandler);
		addMouseMotionListener(zoomPanHandler);
		addMouseWheelListener(zoomPanHandler);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g.create();
		gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		gg.translate(offsetX, offsetY);
		gg.scale(scale, scale);

		gg.drawImage(nodeImg, null, null);

		gg.dispose();
	}

	private final MouseAdapter zoomPanHandler = new MouseAdapter() {
		private int lastX, lastY;
		private boolean isDragged = false;

		@Override
		public void mousePressed(MouseEvent e) {
			isDragged = SwingUtilities.isMiddleMouseButton(e);
			lastX = e.getX();
			lastY = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (isDragged) {
				offsetX += e.getX() - lastX;
				offsetY += e.getY() - lastY;
				lastX = e.getX();
				lastY = e.getY();
				repaint();
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			scale *= e.getWheelRotation() > 0 ? 1/1.2 : 1.2;
			scale = Math.min(scale, 1);
			repaint();
		}
	};
}
