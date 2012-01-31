package aurelienribon.flow.services.welcome;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.*;
import org.apache.commons.io.IOUtils;
import org.ini4j.Wini;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class WelcomePanel extends JPanel {
    public WelcomePanel() {
        initComponents();

		try {
			Wini ini = new Wini(new File("config.ini"));
			String feed = ini.get("welcome", "feed");
			feedField.setText(feed != null ? feed : "");
		} catch (IOException ex) {
		}

		updatesListScrollPane.getViewport().setOpaque(false);
		updatesList.setModel(new DefaultListModel<UpdateEntry>());
		updatesList.setCellRenderer(new UpdatesListCellRenderer());
		reloadBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {loadEntries();}});

		loadEntries();
    }

	private void loadEntries() {
		new Thread(new Runnable() {
			@Override public void run() {
				final CardLayout cl = (CardLayout) cardPanel.getLayout();
				SwingUtilities.invokeLater(new Runnable() {@Override public void run() {cl.show(cardPanel, "loadingCard");}});

				try {
					URLConnection connection = new URL("http://code.google.com/feeds/p/flow-synthesis/updates/basic").openConnection();
					connection.setConnectTimeout(3000);
					String feedContent = IOUtils.toString(connection.getInputStream());

					DefaultListModel<UpdateEntry> model = new DefaultListModel<UpdateEntry>();
					updatesList.setModel(model);
					SwingUtilities.invokeLater(new Runnable() {@Override public void run() {cl.show(cardPanel, "listCard");}});

				} catch (MalformedURLException ex) {
				} catch (IOException ex) {
					SwingUtilities.invokeLater(new Runnable() {@Override public void run() {cl.show(cardPanel, "msgCard");}});
				}
			}
		}).start();
	}

	private class UpdateEntry {
		public final String date;
		public final String author;
		public final String title;
		public final String content;

		public UpdateEntry(String date, String author, String title, String content) {
			this.date = date;
			this.author = author;
			this.title = title;
			this.content = content;
		}
	}

	private class UpdatesListCellRenderer implements ListCellRenderer<UpdateEntry> {
		@Override
		public Component getListCellRendererComponent(JList<? extends UpdateEntry> list, UpdateEntry value, int index, boolean isSelected, boolean cellHasFocus) {
			UpdateEntryView view = new UpdateEntryView();
			view.setup(value.date, value.author, value.title, value.content);
			return view;
		}
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        feedField = new javax.swing.JTextField();
        reloadBtn = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        updatesListScrollPane = new javax.swing.JScrollPane();
        updatesList = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);

        jLabel1.setText("<html>\nWelcome to the <font color=\"#228822\">Flow.Synthesis</font> environment.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(399, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Latest updates");

        reloadBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/ic_reload.png"))); // NOI18N
        reloadBtn.setMargin(new java.awt.Insets(2, 3, 2, 3));

        cardPanel.setLayout(new java.awt.CardLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/ic32_loading.gif"))); // NOI18N
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        cardPanel.add(jLabel5, "loadingCard");

        updatesListScrollPane.setBorder(null);
        updatesListScrollPane.setOpaque(false);

        updatesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        updatesList.setOpaque(false);
        updatesListScrollPane.setViewportView(updatesList);

        cardPanel.add(updatesListScrollPane, "listCard");

        jLabel3.setText("<html>Feed not available.<br/>Please check the url or your internet connection.");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/ic_warning.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cardPanel.add(jPanel4, "msgCard");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(feedField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reloadBtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reloadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(feedField))
                .addGap(18, 18, 18)
                .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPanel;
    private javax.swing.JTextField feedField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton reloadBtn;
    private javax.swing.JList updatesList;
    private javax.swing.JScrollPane updatesListScrollPane;
    // End of variables declaration//GEN-END:variables
}
