package meine.app;

import meine.util.TestTaker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.Math;
import javax.imageio.ImageIO;
import meine.models.KeuzeMomentFoto;
import meine.util.Plaats;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class TestImagePanel extends javax.swing.JPanel {

    private static final Log log = LogFactory.getLog(LogoPanel.class);
    private KeuzeMomentFoto foto = null;
    private BufferedImage image = null;
    private TestTaker testTaker = null;
    private Plaats plaats;

    /** Creates new form TestImagePanel */
    public TestImagePanel() {
        initComponents();
    }

    public void setPlaats(Plaats plaats) {
        this.plaats = plaats;
    }

    public void setFoto(KeuzeMomentFoto f) {
        this.foto = f;
        loadImage();
    }

    private void loadImage() {
        try {
            byte[] bytes = foto.getFoto().getFoto();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            if (image != null) {
                image = null;
            }
            image = ImageIO.read(bais);
            this.repaint(); // misschien this.repaint() aanroepen als image niet (op tijd) geladen wordt
        } catch (IOException ex) {
            log.error(ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        // g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        g.setColor(this.getBackground());
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        g.fillRect(0, 0, panelWidth, panelHeight);
        if (image != null) {
            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();

            int minWidth = Math.min(imageWidth, panelWidth);
            int minHeight = Math.min(imageHeight, panelHeight);
            g.drawImage(image, 0, 0, minWidth, minHeight, this.getBackground(), null); // see javadoc for more info on the parameters
        } else {
            g.drawImage(image, 0, 0, this.getBackground(), null);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(252, 220, 147));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        testTaker.setGekozenFoto(foto, plaats);
    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public TestTaker getTestTaker() {
        return testTaker;
    }

    public void setTestTaker(TestTaker testTaker) {
        this.testTaker = testTaker;
    }
}
