/*
 * KeuzeMomentTwoPanel.java
 *
 * Created on 28-aug-2011, 15:24:02
 */
package meine.app;

import java.util.Set;
import meine.models.KeuzeMoment;
import meine.models.KeuzeMomentFoto;
import meine.util.Plaats;

/**
 *
 * @author Meine Toonen
 */
public class KeuzeMomentTwoPanel extends javax.swing.JPanel {

    private KeuzeMoment keuzemoment = null;

    /** Creates new form KeuzeMomentTwoPanel */
    public KeuzeMomentTwoPanel() {
        initComponents();
    }

    public void setKeuzemoment(KeuzeMoment km) {
        this.keuzemoment = km;
        populateForm();
    }

    private void populateForm() {
        nummer.setText("" + (keuzemoment.getPlek() +1 ));
        tijd.setText(""+keuzemoment.getTijd());
        Set<KeuzeMomentFoto> fotos = keuzemoment.getFotos();
        KeuzeMomentFoto[] kmfs = fotos.toArray(new KeuzeMomentFoto[0]);
        if (kmfs[0].getPositie() == Plaats.LINKS) {
            links.setFoto(kmfs[0]);
            rechts.setFoto(kmfs[1]);
            if (kmfs[0].getKeuzemoment().getKeuze() == kmfs[0]) {
                links.setHatch(true);
            }else{
                rechts.setHatch(true);
            }

        } else {
            links.setFoto(kmfs[1]);
            rechts.setFoto(kmfs[0]);
            if (kmfs[0].getKeuzemoment().getKeuze() == kmfs[0]) {
                rechts.setHatch(true);
            }else{
                links.setHatch(true);
            }
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

        nummer = new javax.swing.JLabel();
        links = new meine.app.KeuzeMomentFotoPanel();
        rechts = new meine.app.KeuzeMomentFotoPanel();
        jLabel1 = new javax.swing.JLabel();
        tijd = new javax.swing.JLabel();

        setBackground(new java.awt.Color(252, 220, 147));

        links.setPreferredSize(new java.awt.Dimension(185, 214));

        javax.swing.GroupLayout linksLayout = new javax.swing.GroupLayout(links);
        links.setLayout(linksLayout);
        linksLayout.setHorizontalGroup(
            linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 195, Short.MAX_VALUE)
        );
        linksLayout.setVerticalGroup(
            linksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        rechts.setPreferredSize(new java.awt.Dimension(190, 214));

        javax.swing.GroupLayout rechtsLayout = new javax.swing.GroupLayout(rechts);
        rechts.setLayout(rechtsLayout);
        rechtsLayout.setHorizontalGroup(
            rechtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        rechtsLayout.setVerticalGroup(
            rechtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        jLabel1.setText("Tijd:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nummer))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(links, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addGap(39, 39, 39)
                        .addComponent(rechts, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(tijd)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nummer)
                    .addComponent(links, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rechts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tijd))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private meine.app.KeuzeMomentFotoPanel links;
    private javax.swing.JLabel nummer;
    private meine.app.KeuzeMomentFotoPanel rechts;
    private javax.swing.JLabel tijd;
    // End of variables declaration//GEN-END:variables
}
