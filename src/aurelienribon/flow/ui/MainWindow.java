package aurelienribon.flow.ui;

import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceExecutionException;
import aurelienribon.flow.services.ServiceProvider;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MainWindow extends javax.swing.JFrame {
	private final ServiceProvider services = new ServiceProvider(this);
	private final Theme theme = new Theme();

    public MainWindow() {
        initComponents();
		modelsPanel.setup(services);

		services.addListener(serviceProviderEventListener);
		services.launchSync(ServiceProvider.SETUP_APP, null);
		services.launchSync(ServiceProvider.SHOW_WELCOME, null);

		addWindowListener(new WindowAdapter() {
			@Override public void windowOpened(WindowEvent e) {
				services.launchAsync(ServiceProvider.SETUP_GRAPHLAB, null, null);
			}
		});
    }

	private final ServiceProvider.EventListener serviceProviderEventListener = new ServiceProvider.EventListener() {
		@Override public void serviceCall(String serviceName, Service service, String input) {}
		@Override public void serviceComplete(String serviceName, Service service) {}
		@Override public void serviceProgressUpdate(String serviceName, Service service, float progress, String description) {}
		@Override public void serviceLog(String serviceName, Service service, String msg) {}
		@Override public void serviceError(String serviceName, Service service, ServiceExecutionException ex) {}
		@Override public void serviceShow(String serviceName, Service service, String title, JPanel panel) {
			applyTheme(panel);
			tabbedPanel.addTab(title, panel);
		}
	};

	// -------------------------------------------------------------------------
	// Theme
	// -------------------------------------------------------------------------

	private void applyTheme(JPanel panel) {
		panel.setBackground(theme.SERVICE_BACKGROUND_COLOR);
		panel.setForeground(theme.SERVICE_FOREGROUND_COLOR);
		panel.setOpaque(true);

		for (Component child : panel.getComponents()) applyThemeLoop(child);
	}

	private void applyThemeLoop(Component cmp) {
		if (cmp instanceof JPanel) {
			JPanel c = (JPanel) cmp;
			c.setForeground(theme.SERVICE_FOREGROUND_COLOR);
			c.setOpaque(false);
		}

		if (cmp instanceof JLabel) {
			JLabel c = (JLabel) cmp;
			c.setForeground(theme.SERVICE_FOREGROUND_COLOR);
		}

		if (cmp instanceof JButton) {
			JButton c = (JButton) cmp;
			c.setOpaque(false);
		}

		if (cmp instanceof Container) {
			Container c = (Container) cmp;
			for (Component child : c.getComponents()) applyThemeLoop(child);
		}
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        modelsPanel = new aurelienribon.flow.ui.ModelsPanel();
        jPanel3 = new javax.swing.JPanel();
        tabbedPanel = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Flow.Synthesis");

        jPanel2.setBackground(theme.MAIN_BACKGROUND_COLOR);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modelsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modelsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(theme.MAIN_BACKGROUND_COLOR);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(tabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private aurelienribon.flow.ui.ModelsPanel modelsPanel;
    private javax.swing.JTabbedPane tabbedPanel;
    // End of variables declaration//GEN-END:variables
}
