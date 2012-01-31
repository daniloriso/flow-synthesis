package aurelienribon.flow.services.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class EditView extends javax.swing.JPanel {
	private File file;

    public EditView() {
        initComponents();

		saveBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				try {
					FileUtils.writeStringToFile(file, editArea.getText());
				} catch (IOException ex) {
					String msg = "The file cannot be saved.";
					JOptionPane.showMessageDialog(EditView.this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
    }

	public void setup(File file) {
		this.file = file;

		try {
			filenameLabel.setText(file.getCanonicalPath());
			editArea.setText(FileUtils.readFileToString(file));
		} catch (IOException ex) {
			filenameLabel.setText(file.getAbsolutePath());
			String msg = "The file cannot be read.";
			JOptionPane.showMessageDialog(EditView.this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        saveBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        editArea = new javax.swing.JTextArea();
        filenameLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/ic_save.png"))); // NOI18N
        saveBtn.setText("Save");
        saveBtn.setFocusable(false);
        jToolBar1.add(saveBtn);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        editArea.setColumns(20);
        editArea.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        editArea.setRows(5);
        jScrollPane1.setViewportView(editArea);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        filenameLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/ic_file.png"))); // NOI18N
        filenameLabel.setText("jLabel1");
        jPanel1.add(filenameLabel, java.awt.BorderLayout.PAGE_START);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea editArea;
    private javax.swing.JLabel filenameLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
