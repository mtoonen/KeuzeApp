/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LeerlingTesten.java
 *
 * Created on 24-aug-2011, 15:11:08
 */
package meine.app;

import meine.util.AuthenticatorRealm;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import meine.models.Leerling;
import meine.models.LopendeTest;
import meine.util.MyDb;
import meine.util.LeerlingTestTableModel;
import meine.util.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class LeerlingTesten extends javax.swing.JFrame {

    private static final Log log = LogFactory.getLog(LeerlingTesten.class);
    private List<LopendeTest> lopendeTests;
    private AuthenticatorRealm realm = AuthenticatorRealm.getInstance();
    private LopendeTest current = null;

    /** Creates new form LeerlingTesten */
    public LeerlingTesten() {
        initComponents();
        fillTable();
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    private void openTest() {
        if (current != null) {

            if (current.getFotosperkeuzemoment() == 2) {
                TestTwoPanel ttp = new TestTwoPanel(current);
            } else if (current.getFotosperkeuzemoment() == 4) {
                TestFourPanel tfp = new TestFourPanel(current);
            } else {
                log.error("Anders dan twee en vier foto's per keuzemoment nog niet geïmplementeerd.");
            }
        }
    }

    private void fillTable() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query getLeerling = em.createQuery("FROM Leerling t where gebruiker = :gebruiker").setParameter("gebruiker", realm.getGebruiker());
            Leerling leerling = (Leerling) getLeerling.getSingleResult();
            Query lopendeTestQuery = em.createQuery("FROM LopendeTest t WHERE leerling = :leerling AND status <> :status").setParameter("leerling", leerling).setParameter("status", Status.AFGEROND);
            lopendeTests = (List<LopendeTest>) lopendeTestQuery.getResultList();

            LeerlingTestTableModel lttm = new LeerlingTestTableModel(lopendeTests);
            leerlingTestenTable.setModel(lttm);

        } catch (Exception e) {
            log.error("", e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (transaction.isActive()) {
                transaction.commit();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        leerlingTestenTable = new javax.swing.JTable();
        startTest = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Testen");

        leerlingTestenTable.setAutoCreateRowSorter(true);
        leerlingTestenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        leerlingTestenTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leerlingTestenTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(leerlingTestenTable);

        startTest.setText("Start");
        startTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(startTest)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(startTest)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void leerlingTestenTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leerlingTestenTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = leerlingTestenTable.getSelectedRow();

            if (row != -1) {
                LopendeTest lt = (LopendeTest) leerlingTestenTable.getModel().getValueAt(row, 2);
                current = lt;
                openTest();
            }
        }
    }//GEN-LAST:event_leerlingTestenTableMouseClicked

    private void startTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTestActionPerformed
        int row = leerlingTestenTable.getSelectedRow();

        if (row != -1) {
            LopendeTest lt = (LopendeTest) leerlingTestenTable.getModel().getValueAt(row, 2);
            current = lt;
            openTest();
        }
    }//GEN-LAST:event_startTestActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LeerlingTesten().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable leerlingTestenTable;
    private javax.swing.JButton startTest;
    // End of variables declaration//GEN-END:variables
}
