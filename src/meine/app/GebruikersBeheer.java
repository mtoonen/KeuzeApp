/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GebruikersBeheer.java
 *
 * Created on 2-mei-2010, 17:12:28
 */
package meine.app;

import meine.util.AuthenticatorRealm;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import meine.models.Gebruiker;
import meine.models.Groep;
import meine.util.MyDb;
import meine.models.Rol;
import meine.models.Rollen;
import meine.util.GebruikersTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import meine.util.Encrypter;

/**
 *
 * @author Meine Toonen
 */
public class GebruikersBeheer extends javax.swing.JFrame {

    private static final Log log = LogFactory.getLog(GebruikersBeheer.class);
    private List<Gebruiker> gebruikers;
    private Gebruiker current = null;
    private Set<Rol> rol = null;
    private Set<Rollen> allowedRoles = null;
    private AuthenticatorRealm realm;

    /** Creates new form GebruikersBeheer */
    public GebruikersBeheer() {
        realm = AuthenticatorRealm.getInstance();

        // addWindowListener(Opstart.getWindowManager());
        allowedRoles = new HashSet<Rollen>();
        Rollen beheerder = Rollen.BEHEERDER;
        allowedRoles.add(beheerder);

        initComponents();
        fillTable();
        fillAndSelectRollenList(null);
        fillAndSelectGroepenList(null);
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    private void fillTable() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("FROM Gebruiker g");
            gebruikers = (List<Gebruiker>) q.getResultList();
            GebruikersTableModel katb = new GebruikersTableModel(gebruikers);
            gebruikersTable.setModel(katb);
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

    private void fillAndSelectRollenList(Set<Rol> selectedItems) {

        List<Integer> selectedIndices = new ArrayList<Integer>();
        DefaultListModel dlm = new DefaultListModel();
        Rollen[] rollenArray = Rollen.values();
        for (int i = 0; i < rollenArray.length; i++) {
            Rollen enumRol = rollenArray[i];
            dlm.addElement(enumRol);

            if (selectedItems != null) {
                for (Iterator<Rol> it = selectedItems.iterator(); it.hasNext();) {
                    Rol rol = it.next();
                    if (rol.getRol().equals(enumRol)) {
                        selectedIndices.add(i);
                        break;
                    }
                }
            }
        }

        rollen.setModel(dlm);
        int[] si = new int[selectedIndices.size()];
        for (int i = 0; i < selectedIndices.size(); i++) {
            si[i] = selectedIndices.get(i);
        }
        rollen.setSelectedIndices(si);
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
        gebruikersnaam.setEnabled(false);
        pass.setEnabled(false);
        pass2.setEnabled(false);
        opslaan.setEnabled(false);
        rollen.setEnabled(false);
        groepen.setEnabled(false);
        schooljaar.setEnabled(false);

        gebruikersnaam.setText("");
        pass.setText("");
        schooljaar.setText("");
        pass2.setText("");
        rollen.removeAll();
        fillAndSelectGroepenList(null);
        fillAndSelectRollenList(null);


    }

    private void populateForm(Gebruiker gebruiker) {
        gebruikersnaam.setEnabled(true);
        pass.setEnabled(true);
        pass2.setEnabled(true);
        opslaan.setEnabled(true);
        rollen.setEnabled(true);
        groepen.setEnabled(true);
        schooljaar.setEnabled(true);

        if (gebruiker != null) {
            gebruikersnaam.setText(gebruiker.getNaam());
            schooljaar.setText(gebruiker.getSchooljaar());

            fillAndSelectRollenList(gebruiker.getRollen());
            fillAndSelectGroepenList(gebruiker.getGroep());
        } else {
            fillAndSelectRollenList(null);
            fillAndSelectGroepenList(null);
            schooljaar.setText("");
        }
        status.setText("");
    }

    private boolean isLastAdmin() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("FROM Rol r WHERE rol = :rol").setParameter("rol", Rollen.BEHEERDER);
            List obj = q.getResultList();
            return obj.size() == 1;
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
        return true;
    }

    private Gebruiker populateObject() {

        Gebruiker gebruiker = current;
        if (current == null) {
            gebruiker = new Gebruiker();
        }

        String gebnaam = gebruikersnaam.getText();
        gebruiker.setNaam(gebnaam);
        if (pass.getPassword().length != 0) {
            char[] een = pass.getPassword();
            char[] twee = pass2.getPassword();

            if (Arrays.equals(een, twee)) {
                String passString = new String(een);

                try {
                    String salt = Encrypter.generateHexSalt();
                    String encryptedPass = Encrypter.getHexSha1(salt, passString);

                    gebruiker.setSalt(salt);
                    gebruiker.setWachtwoord(encryptedPass);
                } catch (NoSuchAlgorithmException e) {
                    log.error("", e);
                } catch (UnsupportedEncodingException e) {
                    log.error("", e);
                }
            } else {

                status.setText("Wachtwoorden komen niet overeen.");
                gebruiker = null;
                disableForm();
            }
        } else {
            if (current == null) {
                status.setText("Wachtwoord mag niet leeg zijn");
                gebruiker = null;
            }
        }

        Object[] selectedRollen = rollen.getSelectedValues();
        Set<Rol> rol = new HashSet<Rol>();
        for (int i = 0; i < selectedRollen.length; i++) {
            Rollen rollen = (Rollen) selectedRollen[i];
            Rol r = new Rol();
            r.setRol(rollen);
            rol.add(r);
        }
        this.rol = rol;

        Groep groep = (Groep) groepen.getSelectedItem();
        gebruiker.setGroep(groep);

        gebruiker.setSchooljaar(schooljaar.getText());

        return gebruiker;
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
        gebruikersTable = new javax.swing.JTable();
        nieuw = new javax.swing.JButton();
        wijzig = new javax.swing.JButton();
        verwijder = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        gebruikersnaam = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        pass2Label = new javax.swing.JLabel();
        pass2 = new javax.swing.JPasswordField();
        opslaan = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rollen = new javax.swing.JList();
        groepen = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        schooljaar = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Gebruikersbeheer");

        gebruikersTable.setAutoCreateRowSorter(true);
        gebruikersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        gebruikersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gebruikersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(gebruikersTable);

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

        jLabel2.setText("Gebruikersnaam");

        jLabel3.setText("Rollen");

        gebruikersnaam.setEnabled(false);

        jLabel4.setText("Wachtwoord");

        pass.setEnabled(false);

        pass2Label.setText("Nogmaals");

        pass2.setEnabled(false);

        opslaan.setText("Opslaan");
        opslaan.setEnabled(false);
        opslaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opslaanActionPerformed(evt);
            }
        });

        rollen.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        rollen.setEnabled(false);
        jScrollPane2.setViewportView(rollen);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(opslaan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nieuw)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(wijzig)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(verwijder))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(schooljaar, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pass, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                    .addComponent(gebruikersnaam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(groepen, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pass2Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pass2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(status))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel1)))
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
                    .addComponent(gebruikersnaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pass2Label)
                    .addComponent(pass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(groepen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 3, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(opslaan))
                    .addComponent(schooljaar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
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

    private void wijzigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wijzigActionPerformed
        int row = gebruikersTable.getSelectedRow();

        if (row != -1) {
            Gebruiker geb = (Gebruiker) gebruikersTable.getModel().getValueAt(row, 0);
            current = geb;
            populateForm(geb);
        }
    }//GEN-LAST:event_wijzigActionPerformed

    private void opslaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opslaanActionPerformed

        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Gebruiker geb = populateObject();
            if (current == null) {
                em.persist(geb);
                em.flush();

                for (Iterator<Rol> it = rol.iterator(); it.hasNext();) {
                    Rol selectedRol = it.next();
                    selectedRol.setGebruiker_id(geb.getId());
                    em.persist(selectedRol);
                }

                em.flush();

                em.refresh(geb);
            } else {
                // Verwijder alle oude rollen

                Set<Rol> oudeRollen = geb.getRollen();
                for (Iterator<Rol> it = oudeRollen.iterator(); it.hasNext();) {
                    Rol rol = it.next();
                    em.remove(rol);
                }
                em.flush();

                geb.setRollen(null);
                em.merge(geb);
                em.flush();

                for (Iterator<Rol> it = rol.iterator(); it.hasNext();) {
                    Rol selectedRol = it.next();
                    selectedRol.setGebruiker_id(geb.getId());
                    em.persist(selectedRol);
                }

                em.flush();
                em.refresh(geb);
            }
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

        current = null;
        fillTable();
        disableForm();
    }//GEN-LAST:event_opslaanActionPerformed

    private void nieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nieuwActionPerformed
        current = null;
        populateForm(current);
    }//GEN-LAST:event_nieuwActionPerformed

    private void verwijderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verwijderActionPerformed
        current = null;
        int row = gebruikersTable.getSelectedRow();

        if (row != -1) {
            Gebruiker geb = (Gebruiker) gebruikersTable.getModel().getValueAt(row, 0);
            if (geb.isInRole(Rollen.BEHEERDER) && isLastAdmin()) {
                status.setText("Kan laatste admin niet verwijderen.");
            } else {
                EntityManager em = MyDb.getThreadEntityManager();
                EntityTransaction transaction = em.getTransaction();
                try {
                    transaction.begin();
                    Set<Rol> rollen = geb.getRollen();
                    for (Iterator<Rol> it = rollen.iterator(); it.hasNext();) {
                        Rol rol = it.next();
                        em.remove(rol);
                    }
                    em.flush();

                    em.remove(geb);
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
        }
    }//GEN-LAST:event_verwijderActionPerformed

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
            if (current == null) {
                fillAndSelectGroepenList(null);
            } else {
                fillAndSelectGroepenList(current.getGroep());
            }
        }
    }//GEN-LAST:event_groepenMouseClicked

    private void gebruikersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gebruikersTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = gebruikersTable.getSelectedRow();

            if (row != -1) {
                Gebruiker geb = (Gebruiker) gebruikersTable.getModel().getValueAt(row, 0);
                current = geb;
                populateForm(geb);
            }
        }
    }//GEN-LAST:event_gebruikersTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable gebruikersTable;
    private javax.swing.JTextField gebruikersnaam;
    private javax.swing.JComboBox groepen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton nieuw;
    private javax.swing.JButton opslaan;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField pass2;
    private javax.swing.JLabel pass2Label;
    private javax.swing.JList rollen;
    private javax.swing.JTextField schooljaar;
    private javax.swing.JLabel status;
    private javax.swing.JButton verwijder;
    private javax.swing.JButton wijzig;
    // End of variables declaration//GEN-END:variables
}
