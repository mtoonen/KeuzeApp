package meine.app;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import meine.models.LopendeTest;
import meine.util.MyDb;
import meine.util.Status;
import meine.util.VoltooideTestTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class VoltooideTesten extends javax.swing.JFrame {

    private List<LopendeTest> lopendeTests;
    private LopendeTest current = null;
    private static final Log log = LogFactory.getLog(VoltooideTesten.class);

    /** Creates new form VoltooideTesten */
    public VoltooideTesten() {
        initComponents();

        fillTable();
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    private void fillTable() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("FROM LopendeTest l WHERE status = :status ORDER BY einddatum DESC").setParameter("status", Status.AFGEROND);
            lopendeTests = (List<LopendeTest>) q.getResultList();
            VoltooideTestTableModel vttm = new VoltooideTestTableModel(lopendeTests);
            voltooideTestenTable.setModel(vttm);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        voltooideTestenTable = new javax.swing.JTable();
        bekijk = new javax.swing.JButton();

        setTitle("Overzicht voltooide testen");

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Voltooide testen");

        voltooideTestenTable.setAutoCreateRowSorter(true);
        voltooideTestenTable.setModel(new javax.swing.table.DefaultTableModel(
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
        voltooideTestenTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voltooideTestenTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(voltooideTestenTable);

        bekijk.setText("Bekijk resultaten");
        bekijk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bekijkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(bekijk))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bekijk)
                .addContainerGap(288, Short.MAX_VALUE))
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

    private void voltooideTestenTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voltooideTestenTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = voltooideTestenTable.getSelectedRow();

            if (row != -1) {
                LopendeTest lt = (LopendeTest) voltooideTestenTable.getModel().getValueAt(row, 4);
                current = lt;
                openTest();
            }
        }
    }//GEN-LAST:event_voltooideTestenTableMouseClicked

    private void bekijkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bekijkActionPerformed
        int row = voltooideTestenTable.getSelectedRow();

        if (row != -1) {
            LopendeTest lt = (LopendeTest) voltooideTestenTable.getModel().getValueAt(row, 4);
            current = lt;
            openTest();
        }
    }//GEN-LAST:event_bekijkActionPerformed

    private void openTest() {
        TestResultaat tr = new TestResultaat(current);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bekijk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable voltooideTestenTable;
    // End of variables declaration//GEN-END:variables
}
