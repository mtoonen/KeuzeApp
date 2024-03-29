package meine.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class ImagePanel extends javax.swing.JPanel {

    /** Creates new form ImagePanel */
    public ImagePanel() {
        initComponents();
    }
    private static final Log log = LogFactory.getLog(LogoPanel.class);
    private BufferedImage image = null;

    public void setFoto(BufferedImage image) {
        this.image = image;
        repaint();
        //  loadImage();
    }

    /* private void loadImage() {
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
    }*/
    @Override
    public void paintComponent(Graphics g) {

        g.setColor(this.getBackground());
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        g.fillRect(0, 0, panelWidth, panelHeight);
        if (image != null) {
            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();
            double ratio = (double) imageWidth / (double) imageHeight;
            int minWidth = 0;
            int minHeight = 0;

            if (imageWidth > panelWidth || imageHeight > panelHeight) {
                minWidth = panelWidth;
                minHeight = (int) Math.ceil( (double)imageWidth/ratio);
            }else{
                minWidth = imageWidth;
                minHeight = imageHeight;
            }
            g.drawImage(image, 0, 0, minWidth, minHeight, this.getBackground(), null); // see javadoc for more info on the parameters
        } else {
            g.drawImage(image, 0, 0, this.getBackground(), null);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
