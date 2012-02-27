package aurelienribon.flow.services.welcome;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class UpdateEntryView extends javax.swing.JPanel {
    public UpdateEntryView() {
        initComponents();
    }

	public void setup(String date, String author, String title, String content) {
		title = title.replaceAll("\\<[^>]*>","");
		title = title.replaceAll("\\([^\\)]*\\)\\s*", "");

		content = content.replaceAll("</?span[^>]*>", "");
		content = content.replaceAll("</?a[^>]*>", "");

		Matcher m = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}:\\d{2}:\\d{2})Z").matcher(date);
		if (m.find()) date = m.group(3) + "/" + m.group(2) + "/" + m.group(1) + " @ " + m.group(4);

		dateLabel.setText(date);
		titleLabel.setText(title);
		contentLabel.setText("<html>" + content);
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        contentLabel = new javax.swing.JLabel();

        setOpaque(false);

        titleLabel.setText("Title...");

        dateLabel.setText("01/01/2012 @ 00:00");

        contentLabel.setForeground(new java.awt.Color(102, 102, 102));
        contentLabel.setText("Content");
        contentLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLabel))
                    .addComponent(contentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(titleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contentLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
