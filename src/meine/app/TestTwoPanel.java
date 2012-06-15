/*
 * TestTwoPanel.java
 *
 * Created on 10-sep-2010, 20:51:28
 */
package meine.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import meine.util.TestTaker;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager; 
import javax.persistence.EntityTransaction;
import javax.swing.JFrame;
import javax.swing.Timer;
import meine.models.Foto;
import meine.models.KeuzeMoment;
import meine.models.KeuzeMomentFoto;
import meine.models.LopendeTest;
import meine.util.MyDb;
import meine.util.Plaats;
import meine.util.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class TestTwoPanel extends javax.swing.JFrame implements TestTaker, ActionListener {

    private static final Log log = LogFactory.getLog(TestTwoPanel.class);
    private KeuzeMoment[] keuzemomentenArray;
    private int plek = 0;
    private KeuzeMoment current = null;
    private LopendeTest test;
    private Date startTest;
    private Date startMoment;
    private Timer timer;
    private boolean timerFinished;

    /** Creates new form TestTwoPanel */
    public TestTwoPanel(LopendeTest test) {
        this.test = test;
        initComponents();
        foto1.setPlaats(Plaats.LINKS);
        foto2.setPlaats(Plaats.RECHTS);

        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
        jPanel1.setSize(this.getWidth(), this.getHeight());
        leerlingnaam.setText(test.getLeerling().getNaam());
        timer = new Timer(test.getTijdlimiet() * 60 * 1000, this);
        timer.setRepeats(false);
    }

    public void runTest() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            test.setStatus(Status.BEZIG);
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
        foto1.setTestTaker(this);
        foto2.setTestTaker(this);
        Set<KeuzeMoment> keuzemomenten = test.getKeuzemoment();

        keuzemomentenArray = new KeuzeMoment[keuzemomenten.size()];
        keuzemomenten.toArray(keuzemomentenArray);
        startTest = new Date();
        volgende();
        // createTest(lt);
    }

    private void volgende() {

        double percentage = plek / test.getKeuzemomenten().doubleValue() * 100;
        voortgang.setValue((int) percentage);
        if (plek < keuzemomentenArray.length && !timerFinished) {
            startMoment = new Date();
            KeuzeMoment keuzeMoment = keuzemomentenArray[plek];
            Set<KeuzeMomentFoto> kmfs = keuzeMoment.getFotos();
            KeuzeMomentFoto[] kmfA = new KeuzeMomentFoto[2];
            kmfs.toArray(kmfA);
            KeuzeMomentFoto een = kmfA[0];
            KeuzeMomentFoto twee = kmfA[1];

            positionFoto(een);
            positionFoto(twee);

            current = keuzeMoment;
            plek++;
        } else {
            EntityManager em = MyDb.getThreadEntityManager();
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                test.setStatus(Status.AFGEROND);
                test.setEinddatum(new Date());
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
            status.setText("Test klaar. Roep de docent.");
        }
    }

    private void positionFoto(KeuzeMomentFoto kmf) {
        String fotoPlek = kmf.getPositie().toString();
        if (fotoPlek.equals(Plaats.LINKS.toString())) {
            foto1.setFoto(kmf);
        } else if (fotoPlek.equals(Plaats.RECHTS.toString())) {
            foto2.setFoto(kmf);
        }
    }

    private void createTest(LopendeTest lt) {

        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Set<Foto> fotos = lt.getTest().getFoto();
            Foto[] fotoArray = fotos.toArray(new Foto[fotos.size()]);
            Random rand = new Random(new Date().getTime());

            for (int i = 0; i < lt.getKeuzemomenten(); i++) {
                KeuzeMoment km = new KeuzeMoment();
                km.setLopendetest(lt);
                km.setPlek(i);

                em.persist(km);
                em.refresh(km);

                int f1 = rand.nextInt(fotoArray.length);
                Foto fo1 = fotoArray[f1];
                int f2 = rand.nextInt(fotoArray.length);
                Foto fo2 = fotoArray[f2];

                Plaats pos1 = null;
                Plaats pos2 = null;
                int plaats = rand.nextInt(2);
                if (plaats == 0) {
                    pos1 = Plaats.LINKS;
                    pos2 = Plaats.RECHTS;
                } else {
                    pos1 = Plaats.RECHTS;
                    pos2 = Plaats.LINKS;
                }
                KeuzeMomentFoto kmf1 = new KeuzeMomentFoto();
                kmf1.setFoto(fo1);
                kmf1.setPositie(pos1);
                kmf1.setKeuzemoment(km);
                em.persist(kmf1);

                KeuzeMomentFoto kmf2 = new KeuzeMomentFoto();
                kmf2.setFoto(fo2);
                kmf2.setPositie(pos2);
                kmf2.setKeuzemoment(km);
                em.persist(kmf2);
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

    }

    public void setGekozenFoto(KeuzeMomentFoto f, Plaats plaats) {
        Date eindeMoment = new Date();
        long diff = eindeMoment.getTime() - startMoment.getTime();
        long sec = (diff + 500) / 1000;
        Double tijdInSeconde = Math.ceil(sec);
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            current.setKeuze(f);
            current.setLopendetest(test);
            current.setTijd(tijdInSeconde);

            em.persist(f);
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

        volgende();
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
        foto1 = new meine.app.TestImagePanel();
        welkom = new javax.swing.JLabel();
        leerlingnaam = new javax.swing.JLabel();
        foto2 = new meine.app.TestImagePanel();
        startButton = new javax.swing.JButton();
        voortgang = new javax.swing.JProgressBar();
        status = new javax.swing.JLabel();

        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        foto1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        foto1.setPreferredSize(new java.awt.Dimension(308, 264));
        foto1.setRequestFocusEnabled(false);

        welkom.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        welkom.setForeground(new java.awt.Color(51, 170, 110));
        welkom.setText("Welkom");

        leerlingnaam.setFont(new java.awt.Font("Tahoma", 1, 24));
        leerlingnaam.setForeground(new java.awt.Color(51, 170, 110));

        javax.swing.GroupLayout foto1Layout = new javax.swing.GroupLayout(foto1);
        foto1.setLayout(foto1Layout);
        foto1Layout.setHorizontalGroup(
            foto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foto1Layout.createSequentialGroup()
                .addComponent(welkom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leerlingnaam)
                .addContainerGap(203, Short.MAX_VALUE))
        );
        foto1Layout.setVerticalGroup(
            foto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foto1Layout.createSequentialGroup()
                .addGroup(foto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(welkom)
                    .addComponent(leerlingnaam))
                .addContainerGap(389, Short.MAX_VALUE))
        );

        foto2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout foto2Layout = new javax.swing.GroupLayout(foto2);
        foto2.setLayout(foto2Layout);
        foto2Layout.setHorizontalGroup(
            foto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );
        foto2Layout.setVerticalGroup(
            foto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        startButton.setText("Start test");
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        status.setFont(new java.awt.Font("Tahoma", 1, 24));
        status.setForeground(new java.awt.Color(255, 0, 0));
        status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(foto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(foto2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(voortgang, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(foto2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(foto1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(voortgang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        startButton.setVisible(false);
        leerlingnaam.setVisible(false);
        welkom.setVisible(false);
       // if(test.getTijdlimiet() > 0){
            runTest();
       // }
    }//GEN-LAST:event_startButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private meine.app.TestImagePanel foto1;
    private meine.app.TestImagePanel foto2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel leerlingnaam;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel status;
    private javax.swing.JProgressBar voortgang;
    private javax.swing.JLabel welkom;
    // End of variables declaration//GEN-END:variables

    public void actionPerformed(ActionEvent e) {
        timerFinished = true;
    }
}
