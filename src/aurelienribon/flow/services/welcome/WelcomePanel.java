package aurelienribon.flow.services.welcome;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.ini4j.Wini;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class WelcomePanel extends JPanel {
    public WelcomePanel() {
        initComponents();

		arLabel.addMouseListener(new MouseAdapter() {
			@Override public void mouseClicked(MouseEvent e) {
				if (!Desktop.isDesktopSupported()) return;
				try {Desktop.getDesktop().browse(new URI("http://www.aurelienribon.com"));}
				catch (URISyntaxException ex) {}
				catch (IOException ex) {}
			}

			@Override public void mouseEntered(MouseEvent e) {setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
			@Override public void mouseExited(MouseEvent e) {setCursor(Cursor.getDefaultCursor());}
		});

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
					DefaultListModel<UpdateEntry> model = new DefaultListModel<UpdateEntry>();
					
					HttpURLConnection connection = (HttpURLConnection) new URL("http://code.google.com/feeds/p/flow-synthesis/updates/basic").openConnection();
					connection.setConnectTimeout(1000);
					
					Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());
					connection.disconnect();
					
					NodeList elems = doc.getElementsByTagName("entry");
					for (int i=0; i<elems.getLength(); i++) {
						Element elem = (Element) elems.item(i);
						String date = getString(elem, "updated");
						String author = getString(elem, "author");
						String title = getString(elem, "title");
						String content = getString(elem, "content");
						UpdateEntry entry = new UpdateEntry(date, author, title, content);
						model.addElement(entry);
					}
					
					updatesList.setModel(model);
					SwingUtilities.invokeLater(new Runnable() {@Override public void run() {cl.show(cardPanel, "listCard");}});

				} catch (SAXException ex) {
				} catch (ParserConfigurationException ex) {
				} catch (MalformedURLException ex) {
				} catch (IOException ex) {
					SwingUtilities.invokeLater(new Runnable() {@Override public void run() {cl.show(cardPanel, "msgCard");}});
				}
			}
		}).start();
	}
	
	private String getString(Element entry, String child) {
		NodeList elems = entry.getElementsByTagName(child);
		if (elems.getLength() >= 1) return elems.item(0).getTextContent();
		return "";
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

	private class UpdatesListCellRenderer extends UpdateEntryView implements ListCellRenderer<UpdateEntry> {
		@Override
		public Component getListCellRendererComponent(JList<? extends UpdateEntry> list, UpdateEntry value, int index, boolean isSelected, boolean cellHasFocus) {
			setup(value.date, value.author, value.title, value.content);
			return this;
		}
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        arLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
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

        jLabel1.setText("<html>\nWelcome to the Flow.Synthesis environment.");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/logo.png"))); // NOI18N

        jLabel7.setText("<html>Based on the GraphLab high-level synthesis engine.");

        arLabel.setText("<html>\nAurelien Ribon - 2012<br/>\n<a href=\"\">www.aurelienribon.com</a>");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(arLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(arLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addGap(0, 303, Short.MAX_VALUE))
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
                .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arLabel;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JTextField feedField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton reloadBtn;
    private javax.swing.JList updatesList;
    private javax.swing.JScrollPane updatesListScrollPane;
    // End of variables declaration//GEN-END:variables
}
