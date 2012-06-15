package meine.app;

/**
 *
 * @author Meine Toonen
 */
public class Leerkrachten extends javax.swing.JFrame {

    public Leerkrachten() {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo( this.getParent());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        leerlingen = new javax.swing.JButton();
        overzichten = new javax.swing.JButton();
        aanmakenTest = new javax.swing.JButton();
        activeerTest = new javax.swing.JButton();

        setTitle("Overzicht testen");

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Leerkrachten");

        leerlingen.setText("Leerlingen");
        leerlingen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leerlingenActionPerformed(evt);
            }
        });

        overzichten.setText("Overzichten");
        overzichten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overzichtenActionPerformed(evt);
            }
        });

        aanmakenTest.setText("Testen");
        aanmakenTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aanmakenTestActionPerformed(evt);
            }
        });

        activeerTest.setText("Activeer test");
        activeerTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeerTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(80, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(aanmakenTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(overzichten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(leerlingen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(activeerTest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)))
                .addGap(138, 138, 138))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(leerlingen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(overzichten)
                .addGap(18, 18, 18)
                .addComponent(aanmakenTest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(activeerTest)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aanmakenTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aanmakenTestActionPerformed
        TestOverzicht tm = new TestOverzicht();
    }//GEN-LAST:event_aanmakenTestActionPerformed

    private void leerlingenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leerlingenActionPerformed
        LeerlingenBeheer l = new LeerlingenBeheer();
    }//GEN-LAST:event_leerlingenActionPerformed

    private void activeerTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeerTestActionPerformed
        ActiveerTest at = new ActiveerTest();
    }//GEN-LAST:event_activeerTestActionPerformed

    private void overzichtenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overzichtenActionPerformed
        VoltooideTesten vt = new VoltooideTesten();
    }//GEN-LAST:event_overzichtenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aanmakenTest;
    private javax.swing.JButton activeerTest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton leerlingen;
    private javax.swing.JButton overzichten;
    // End of variables declaration//GEN-END:variables

}
