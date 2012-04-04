package aurelienribon.flow.ui;

import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceExecutionException;
import aurelienribon.flow.ServiceProvider;
import aurelienribon.ui.components.AruiStyle;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.swing.SwingStyle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;
import res.Res;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MainWindow extends javax.swing.JFrame {
	private final Style style;
	private final ServiceProvider services;

    public MainWindow() {
        initComponents();

		ServiceProvider sp = null;

		try {
			sp = new ServiceProvider(this);
		} catch (IOException ex) {
			String msg = "Something wrong happened during initialization of services.\n\n"
				+ ex.getClass().getSimpleName() + " - " + ex.getMessage();
			JOptionPane.showMessageDialog(getContentPane(), msg);
			System.exit(-1);
		}

		services = sp;
		services.addListener(serviceProviderEventListener);
		services.launchSync(ServiceProvider.SETUP_APP, null);
		services.launchSync(ServiceProvider.SHOW_WELCOME, null);
		services.launchAsync(ServiceProvider.SETUP_GRAPHLAB, null, null);

		modelsPanel.setup(services);

		SwingStyle.init();
		AruiStyle.init();
		Style.registerCssClasses(rootPanel, "#rootPanel");
		Style.registerCssClasses(modelsPanel, "#modelsPanel");
		Style.registerCssClasses(perfMonPanel, "#perfMonPanel");
		style = new Style(Res.getUrl("css/style.css"));
		Style.apply(getContentPane(), style);

		addWindowListener(new WindowAdapter() {
			@Override public void windowOpened(WindowEvent e) {
				tabPanel.getModel().setSelection(0);
			}
		});
    }

	private final ServiceProvider.EventListener serviceProviderEventListener = new ServiceProvider.EventListener() {
		@Override public void serviceCall(String serviceName, Service service, Object input) {}
		@Override public void serviceComplete(String serviceName, Service service) {}
		@Override public void serviceProgressUpdate(String serviceName, Service service, float progress, String description) {}
		@Override public void serviceLog(String serviceName, Service service, String msg) {}
		@Override public void serviceError(String serviceName, Service service, ServiceExecutionException ex) {}
		@Override public void serviceShow(String serviceName, Service service, String title, JPanel panel, Icon icon) {
			Style.registerCssClasses(panel, ".servicePanel");
			Style.apply(panel, style);
			tabPanel.getModel().add(panel, title, (ImageIcon) icon);
			tabPanel.getModel().setSelection(panel);
		}
	};

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new aurelienribon.ui.components.PaintedPanel();
        sidePanel = new javax.swing.JPanel();
        modelsPanel = new aurelienribon.flow.ui.modelstree.ModelsPanel();
        perfMonPanel = new aurelienribon.flow.ui.perfmon.PerfMonPanel();
        centerPanel = new javax.swing.JPanel();
        tabPanel = new aurelienribon.ui.components.TabPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Flow.Synthesis");

        rootPanel.setLayout(new java.awt.BorderLayout());

        sidePanel.setOpaque(false);

        javax.swing.GroupLayout perfMonPanelLayout = new javax.swing.GroupLayout(perfMonPanel);
        perfMonPanel.setLayout(perfMonPanelLayout);
        perfMonPanelLayout.setHorizontalGroup(
            perfMonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        perfMonPanelLayout.setVerticalGroup(
            perfMonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(perfMonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modelsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(perfMonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        rootPanel.add(sidePanel, java.awt.BorderLayout.LINE_START);

        centerPanel.setOpaque(false);

        javax.swing.GroupLayout centerPanelLayout = new javax.swing.GroupLayout(centerPanel);
        centerPanel.setLayout(centerPanelLayout);
        centerPanelLayout.setHorizontalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                .addComponent(tabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );
        centerPanelLayout.setVerticalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );

        rootPanel.add(centerPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(rootPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centerPanel;
    private aurelienribon.flow.ui.modelstree.ModelsPanel modelsPanel;
    private aurelienribon.flow.ui.perfmon.PerfMonPanel perfMonPanel;
    private aurelienribon.ui.components.PaintedPanel rootPanel;
    private javax.swing.JPanel sidePanel;
    private aurelienribon.ui.components.TabPanel tabPanel;
    // End of variables declaration//GEN-END:variables
}
