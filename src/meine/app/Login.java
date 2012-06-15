/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * login.java
 *
 * Created on 27-apr-2010, 21:27:31
 */
package meine.app;

import meine.util.AuthenticatorRealm;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.JFrame;
import meine.models.Gebruiker;
import meine.util.MyDb;
import meine.models.Rollen;
import meine.util.Encrypter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class Login extends javax.swing.JFrame {

    private Rollen forward;
    private final Log log = LogFactory.getLog(this.getClass());
    private AuthenticatorRealm realm;

    public Login() {
        initComponents();

        this.setVisible(true);
       // addWindowListener(Opstart.getWindowManager());
        realm = AuthenticatorRealm.getInstance();
    }

    private void moveToForward(Rollen forward){

        switch(forward){
            case BEHEERDER:
                GebruikersBeheer gb = new GebruikersBeheer();
              //  openNext(gb);
                break;
            case LERAAR:
                Leerkrachten l = new Leerkrachten();
               // openNext(l);
                break;
            case LEERLING:
                log.error("Wrong forward supplied, not yet supported: " + forward.getDescription() + " - "  + forward );
                break;
            default:
                log.error("Wrong forward supplied: " + forward.getDescription() + " - "  + forward );
                break;
        }
    }

    private void openNext(JFrame next) {
        next.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        login = new javax.swing.JButton();
        annuleren = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        status = new javax.swing.JLabel();
        rol = new javax.swing.JLabel();

        setBackground(new java.awt.Color(252, 220, 147));

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Keuzetest");

        jLabel2.setText("Gebruikersnaam");

        login.setText("Inloggen");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        annuleren.setText("Annuleren");
        annuleren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerenActionPerformed(evt);
            }
        });

        jLabel3.setText("Wachtwoord");

        status.setText("jLabel4");

        rol.setFont(new java.awt.Font("Tahoma", 1, 14));
        rol.setForeground(new java.awt.Color(51, 170, 110));
        rol.setText("rol");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(status))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(login)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(annuleren))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(rol))))))
                .addContainerGap(271, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(rol)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(username))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login)
                    .addComponent(annuleren))
                .addGap(18, 18, 18)
                .addComponent(status)
                .addContainerGap(154, Short.MAX_VALUE))
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

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        String user = username.getText();
        char[] pass = password.getPassword();
        String passw = new String(pass);
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("FROM Gebruiker g WHERE naam = :user").setParameter("user", user);
            Gebruiker gebruiker = (Gebruiker) q.getSingleResult();
            String encryptedPass = Encrypter.getHexSha1(gebruiker.getSalt(), passw);
            if (encryptedPass.equalsIgnoreCase(gebruiker.getWachtwoord())) {
                transaction.commit();
                realm.setGebruiker(gebruiker);
                moveToForward(forward);
            } else {
                status.setText("Gebruikersnaam en/of wachtwoord komen niet overeen.");
                realm.nullifyAuthentication();
            }
        } catch (Exception e) {
            realm.nullifyAuthentication();
            log.error(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();

            }
        } finally {
            if (transaction.isActive()) {
                transaction.commit();
            }
            
        }
    }//GEN-LAST:event_loginActionPerformed

    private void annulerenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_annulerenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annuleren;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel rol;
    private javax.swing.JLabel status;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    public Rollen getForward() {
        return forward;
    }

    public void setForward(Rollen forward) {
        this.forward = forward;
        rol.setText(forward.getDescription());
    }
}
