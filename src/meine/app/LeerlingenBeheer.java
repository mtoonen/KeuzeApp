/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LeerlingenBeheer.java
 *
 * Created on 5-mei-2010, 21:38:14
 */
package meine.app;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import meine.models.Gebruiker;
import meine.models.Groep;
import meine.models.Leerling;
import meine.util.MyDb;
import meine.models.Rol;
import meine.models.Rollen;
import meine.util.Encrypter;
import meine.util.LeerlingTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Meine Toonen
 */
public class LeerlingenBeheer extends javax.swing.JFrame {

    private Leerling current = null;
    private static final Log log = LogFactory.getLog(LeerlingenBeheer.class);
    private List<Leerling> leerlingen;

    /** Creates new form LeerlingenBeheer */
    public LeerlingenBeheer() {
        initComponents();
        fillTable();
        //   addWindowListener(Opstart.getWindowManager());
        fillAndSelectGroepenList(null);
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    private void fillTable() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("FROM Leerling l");
            leerlingen = (List<Leerling>) q.getResultList();
            LeerlingTableModel katb = new LeerlingTableModel(leerlingen);
            leerlingenTable.setModel(katb);
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

    private void populateForm(Leerling leerling) {
        leerlingnaam.setEnabled(true);
        opslaan.setEnabled(true);
        groepen.setEnabled(true);
        schooljaar.setEnabled(true);
        opmerkingen.setEnabled(true);

        if (leerling != null) {
            leerlingnaam.setText(leerling.getNaam());
            schooljaar.setText(leerling.getGebruiker().getSchooljaar());
            opmerkingen.setText(leerling.getOpmerkingen());

            fillAndSelectGroepenList(leerling.getGebruiker().getGroep());
        } else {
            fillAndSelectGroepenList(null);
            schooljaar.setText("");
            opmerkingen.setText("");
        }
        status.setText("");
    }

    private Leerling populateObject() {

        Leerling leerling = current;
        String naam = leerlingnaam.getText();

        Groep groep = (Groep) groepen.getSelectedItem();
        if (current == null) {
            leerling = new Leerling();
            Gebruiker geb = new Gebruiker();
            leerling.setGebruiker(geb);

            Set<Rol> rol = new HashSet<Rol>();

            Rollen rollen = Rollen.LEERLING;
            Rol r = new Rol();
            r.setRol(rollen);
            rol.add(r);

            geb.setRollen(rol);

            try {
                String salt = Encrypter.generateHexSalt();
                String passString = naam;
                String encryptedPass = Encrypter.getHexSha1(salt, passString);

                geb.setSalt(salt);
                geb.setWachtwoord(encryptedPass);

            } catch (NoSuchAlgorithmException e) {
                log.error("", e);
            } catch (UnsupportedEncodingException e) {
                log.error("", e);
            }

        }
        Gebruiker geb = leerling.getGebruiker();
        leerling.setNaam(naam);
        leerling.setOpmerkingen(opmerkingen.getText());
        geb.setGroep(groep);
        geb.setSchooljaar(schooljaar.getText());
        geb.setNaam(naam);


        return leerling;
    }

    private void fillAndSelectGroepenList(Groep groep) {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        List<Groep> groepenList;
        try {
            transaction.begin();

            Query q = em.createQuery("FROM Groep g");
            groepenList = (List<Groep>) q.getResultList();
            groepen.removeAllItems();

            groepen.addItem(new Groep());
            for (Iterator<Groep> it = groepenList.iterator(); it.hasNext();) {
                Groep groep1 = it.next();
                groepen.addItem(groep1);
            }
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

        if (groep != null) {
            groepen.setSelectedItem(groep);
        }

    }

    private void disableForm() {
        leerlingnaam.setEnabled(false);
        opslaan.setEnabled(false);
        groepen.setEnabled(false);
        schooljaar.setEnabled(false);
        opmerkingen.setEnabled(false);

        leerlingnaam.setText("");
        schooljaar.setText("");
        opmerkingen.setText("");
        fillAndSelectGroepenList(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        leerlingenTable = new javax.swing.JTable();
        nieuw = new javax.swing.JButton();
        wijzig = new javax.swing.JButton();
        verwijder = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        leerlingnaam = new javax.swing.JTextField();
        opslaan = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        groepen = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        schooljaar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        opmerkingen = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Leerlingenbeheer");

        leerlingenTable.setAutoCreateRowSorter(true);
        leerlingenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        leerlingenTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leerlingenTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(leerlingenTable);

        nieuw.setText("Nieuw");
        nieuw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nieuwActionPerformed(evt);
            }
        });

        wijzig.setText("Wijzigen");
        wijzig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wijzigActionPerformed(evt);
            }
        });

        verwijder.setText("Verwijderen");
        verwijder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verwijderActionPerformed(evt);
            }
        });

        jLabel2.setText("Naam");

        leerlingnaam.setEnabled(false);

        opslaan.setText("Opslaan");
        opslaan.setEnabled(false);
        opslaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opslaanActionPerformed(evt);
            }
        });

        groepen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        groepen.setEnabled(false);
        groepen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groepenMouseClicked(evt);
            }
        });

        jLabel5.setText("Groep");

        jLabel6.setText("Schooljaar");

        schooljaar.setEnabled(false);

        opmerkingen.setColumns(10);
        opmerkingen.setRows(2);
        opmerkingen.setEnabled(false);
        jScrollPane2.setViewportView(opmerkingen);

        jLabel3.setText("Opmerkingen");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nieuw)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(wijzig)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(verwijder))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(schooljaar, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(groepen, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(leerlingnaam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(opslaan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(status)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nieuw)
                    .addComponent(wijzig)
                    .addComponent(verwijder))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(leerlingnaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(groepen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(schooljaar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opslaan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status)
                .addContainerGap(73, Short.MAX_VALUE))
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

    private void nieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nieuwActionPerformed
        current = null;
        populateForm(current);
}//GEN-LAST:event_nieuwActionPerformed

    private void wijzigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wijzigActionPerformed
        int row = leerlingenTable.getSelectedRow();

        if (row != -1) {
            Leerling ll = (Leerling) leerlingenTable.getModel().getValueAt(row, 0);
            current = ll;
            populateForm(ll);
        }
}//GEN-LAST:event_wijzigActionPerformed

    private void verwijderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verwijderActionPerformed
        current = null;
        int row = leerlingenTable.getSelectedRow();
        // TODO: zorg dat de gebruiker (+rollen) verwijderd worden

        if (row != -1) {
            Leerling ll = (Leerling) leerlingenTable.getModel().getValueAt(row, 0);
            EntityManager em = MyDb.getThreadEntityManager();
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();

                em.remove(ll);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                status.setText("Verwijderen mislukt. Niks verwijderd. Rapporteer fout.");
            } finally {
                try {
                    if (transaction.isActive()) {
                        transaction.commit();
                    }
                } catch (Exception e) {
                    log.error(e.getStackTrace());
                    transaction.rollback();
                    status.setText("Verwijderen mislukt. Niks verwijderd. Rapporteer fout.");
                }

            }

            fillTable();
            disableForm();
        }
}//GEN-LAST:event_verwijderActionPerformed

    private void opslaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opslaanActionPerformed

        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Leerling ll = populateObject();
            if (current == null) {
                Set<Rol> rol = ll.getGebruiker().getRollen();
                em.persist(ll.getGebruiker());
                em.refresh(ll.getGebruiker());

                for (Iterator<Rol> it = rol.iterator(); it.hasNext();) {
                    Rol selectedRol = it.next();
                    selectedRol.setGebruiker_id(ll.getGebruiker().getId());
                    em.persist(selectedRol);
                }

                em.flush();
                em.refresh(ll.getGebruiker());

                em.persist(ll);
                em.flush();

                em.refresh(ll);
            } else {

                em.merge(ll.getGebruiker());
                em.merge(ll);
                em.flush();
            }
        } catch (Exception e) {
            if (e.getCause().getClass() == ConstraintViolationException.class) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                status.setText("Er is geprobeerd een gebruiker aan te maken met een niet-unieke naam. Er is niks opgeslagen.");
            } else {
                log.error("", e);
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                status.setText("Opslaan mislukt. Niks opgeslagen. Rapporteer fout.");
            }
        } finally {
            if (transaction.isActive()) {
                transaction.commit();
            }

        }

        current = null;
        fillTable();
        disableForm();
}//GEN-LAST:event_opslaanActionPerformed

    private void groepenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groepenMouseClicked
        if (evt.getClickCount() == 2) {
            NieuwItem ni = new NieuwItem(this, true);
            ni.setVisible(true);
            if (ni.getStatus()) {
                String nieuwItem = ni.getItem();

                Groep g = new Groep();
                g.setGroepnaam(nieuwItem);

                EntityManager em = MyDb.getThreadEntityManager();
                EntityTransaction transaction = em.getTransaction();
                try {
                    transaction.begin();

                    em.persist(g);
                    em.flush();
                } catch (Exception e) {
                    log.error("", e);
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                    status.setText("Opslaan mislukt. Niks opgeslagen. Rapporteer fout.");
                } finally {
                    if (transaction.isActive()) {
                        transaction.commit();
                    }

                }

            }
            fillAndSelectGroepenList(current.getGebruiker().getGroep());
        }
}//GEN-LAST:event_groepenMouseClicked

    private void leerlingenTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leerlingenTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = leerlingenTable.getSelectedRow();

            if (row != -1) {
                Leerling ll = (Leerling) leerlingenTable.getModel().getValueAt(row, 0);
                current = ll;
                populateForm(ll);
            }
        }
    }//GEN-LAST:event_leerlingenTableMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox groepen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable leerlingenTable;
    private javax.swing.JTextField leerlingnaam;
    private javax.swing.JButton nieuw;
    private javax.swing.JTextArea opmerkingen;
    private javax.swing.JButton opslaan;
    private javax.swing.JTextField schooljaar;
    private javax.swing.JLabel status;
    private javax.swing.JButton verwijder;
    private javax.swing.JButton wijzig;
    // End of variables declaration//GEN-END:variables
}
