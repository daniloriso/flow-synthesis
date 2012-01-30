package aurelienribon.flow.ui;

import aurelienribon.flow.services.Service;
import aurelienribon.flow.services.ServiceExecutionException;
import aurelienribon.flow.services.ServiceProvider;
import aurelienribon.flow.services.ServiceUi;
import aurelienribon.flow.services.loadgraphlab.LaunchingGraphlabDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MainWindow extends javax.swing.JFrame {
    public MainWindow() {
        initComponents();

		ServiceProvider.addListener(serviceProviderEventListener);
		ServiceProvider.launchSync(ServiceProvider.SHOW_WELCOME, null);

		addWindowListener(new WindowAdapter() {
			@Override public void windowOpened(WindowEvent e) {load();}
		});
    }

	private void load() {
		final LaunchingGraphlabDialog dialog = new LaunchingGraphlabDialog(this, true);
		dialog.setLocationRelativeTo(this);

		ServiceProvider.launchAsync(ServiceProvider.LOAD_GRAPHLAB, null, new ServiceProvider.Callback() {
			@Override public void begin() {dialog.setVisible(true);}
			@Override public void complete() {dialog.setVisible(false);}
		});
	}

	private final ServiceProvider.EventListener serviceProviderEventListener = new ServiceProvider.EventListener() {
		@Override public void serviceCall(String serviceName, Service service, String input) {
		}

		@Override public void serviceComplete(String serviceName, Service service) {
		}

		@Override public void serviceProgressUpdate(String serviceName, Service service, float progress, String description) {
		}

		@Override public void serviceLog(String serviceName, Service service, String msg) {
		}

		@Override public void serviceError(String serviceName, Service service, ServiceExecutionException ex) {
		}

		@Override public void serviceShow(String serviceName, Service service, String title, ServiceUi panel) {
			tabbedPanel.addTab(title, panel);
		}
	};

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        modelsTree = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        tabbedPanel = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Flow.Synthesis");

        jScrollPane1.setViewportView(modelsTree);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree modelsTree;
    private javax.swing.JTabbedPane tabbedPanel;
    // End of variables declaration//GEN-END:variables
}
