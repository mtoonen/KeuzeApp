package meine.app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import meine.util.ChartGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import meine.models.KeuzeMoment;
import meine.models.Leerling;
import meine.models.LopendeTest;
import meine.models.Test;
import meine.util.MyDb;
import meine.util.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Meine Toonen
 */
public class TestResultaat extends javax.swing.JFrame {

    private LopendeTest test;
    private Map<String, Map<String, Integer>> grafiekData;
    private static final Log log = LogFactory.getLog(TestResultaat.class);

    public TestResultaat(LopendeTest test) {
        this.test = test;
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        /* this.setExtendedState(JFrame.MAXIMIZED_VERT);

       Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        int height = 979;
        if(height > dim.getHeight()){
            height = (int) dim.getHeight();
        }
        this.setSize(816, height);*/
        populateForm();
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    private void populateForm() {
        testNaam.setText(test.getTest().getNaam());
        leerlingNaam.setText(test.getLeerling().getNaam());
        aantalKeuzemomenten.setText(test.getKeuzemomenten().toString());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String datum = df.format(test.getEinddatum());
        datumAfronding.setText(datum);
        Set<KeuzeMoment> kms = test.getKeuzemoment();

        grafiekData = new HashMap<String, Map<String, Integer>>();

        GroupLayout resultatenLayout = new GroupLayout(resultaten);
        resultatenLayout.setAutoCreateGaps(true);
        resultatenLayout.setAutoCreateContainerGaps(true);

        resultaten.setLayout(resultatenLayout);
        ParallelGroup horizontalGroup = resultatenLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        SequentialGroup verticalGroup = resultatenLayout.createSequentialGroup();

        String serie = datum;

        Map<String, Integer> eenSerie = new HashMap<String, Integer>();
        int numKeuzemoment = 0;
        for (KeuzeMoment keuzeMoment : kms) {

            if (keuzeMoment.getKeuze() == null) {
                break;
            }

            numKeuzemoment++;
            JPanel kmtp = null;
            if (test.getFotosperkeuzemoment() == 2) {
                kmtp = new KeuzeMomentTwoPanel();
                ((KeuzeMomentTwoPanel) kmtp).setKeuzemoment(keuzeMoment);

            } else if (test.getFotosperkeuzemoment() == 4) {

                kmtp = new KeuzeMomentFourPanel();
                ((KeuzeMomentFourPanel) kmtp).setKeuzemoment(keuzeMoment);

            } else {
                log.error("Aantal foto's per keuzemoment niet ondersteund bij test: " + test.getTest().getNaam() + " van " + test.getLeerling().getNaam());
            }
            horizontalGroup.addComponent(kmtp);
            verticalGroup.addComponent(kmtp);
            JSeparator sep = new JSeparator();
            verticalGroup.addComponent(sep);
            horizontalGroup.addComponent(sep);

            String categorie = keuzeMoment.getKeuze().getFoto().getCategorie();

            if (eenSerie.containsKey(categorie)) {
                eenSerie.put(categorie, eenSerie.get(categorie) + 1);
            } else {
                eenSerie.put(categorie, 1);
            }
        }

        grafiekData.put(serie, eenSerie);
        resultatenLayout.setHorizontalGroup(resultatenLayout.createSequentialGroup().addGroup(horizontalGroup));
        resultatenLayout.setVerticalGroup(verticalGroup);

        Map<String, Integer> total = new HashMap<String, Integer>();
        total.put(datum, numKeuzemoment);
        ChartGenerator bcd = new ChartGenerator("Uitslag test " + test.getTest().getNaam(), grafiekData, total);
        grafiek.setFoto(bcd.getImage(grafiek.getWidth(), grafiek.getHeight()));


    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        testNaam = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultaten = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        leerlingNaam = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        aantalKeuzemomenten = new javax.swing.JTextField();
        grafiek = new meine.app.ImagePanel();
        jLabel6 = new javax.swing.JLabel();
        datumAfronding = new javax.swing.JTextField();
        vergelijk = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Testresultaat");

        jLabel2.setText("Naam test");

        testNaam.setEditable(false);

        jScrollPane1.setBackground(new java.awt.Color(204, 255, 204));

        resultaten.setBackground(new java.awt.Color(252, 220, 147));

        javax.swing.GroupLayout resultatenLayout = new javax.swing.GroupLayout(resultaten);
        resultaten.setLayout(resultatenLayout);
        resultatenLayout.setHorizontalGroup(
            resultatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );
        resultatenLayout.setVerticalGroup(
            resultatenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(resultaten);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 170, 110));
        jLabel3.setText("Keuzes");

        jLabel4.setText("Naam leerling");

        leerlingNaam.setEditable(false);

        jLabel5.setText("Aantal keuzemomenten");

        aantalKeuzemomenten.setEditable(false);

        grafiek.setBackground(new java.awt.Color(0, 255, 204));

        javax.swing.GroupLayout grafiekLayout = new javax.swing.GroupLayout(grafiek);
        grafiek.setLayout(grafiekLayout);
        grafiekLayout.setHorizontalGroup(
            grafiekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );
        grafiekLayout.setVerticalGroup(
            grafiekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel6.setText("Datum afronding");

        datumAfronding.setEditable(false);

        vergelijk.setText("Vergelijk resultaten");
        vergelijk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vergelijkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(datumAfronding)
                                    .addComponent(testNaam)
                                    .addComponent(leerlingNaam)
                                    .addComponent(aantalKeuzemomenten)
                                    .addComponent(vergelijk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(46, 46, 46))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(99, 99, 99)
                                        .addComponent(jLabel1))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(51, 51, 51)
                        .addComponent(grafiek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(testNaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(leerlingNaam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(aantalKeuzemomenten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(datumAfronding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vergelijk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(grafiek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void vergelijkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vergelijkActionPerformed
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Leerling ll = test.getLeerling();
            Test t = test.getTest();

            Query q = em.createQuery("FROM LopendeTest l WHERE leerling = :leerling AND test = :test AND status = :status ORDER BY l.einddatum ASC").setParameter("test", t).setParameter("leerling", ll).setParameter("status", Status.AFGEROND);
            List<LopendeTest> lopendeTests = (List<LopendeTest>) q.getResultList();
            grafiekData = new HashMap<String, Map<String, Integer>>();
            Map<String, Integer> serieTotals = new HashMap<String, Integer>();

            for (LopendeTest lopendeTest : lopendeTests) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String datum = df.format(lopendeTest.getEinddatum());
                String probeerNaam = datum;
                int num = 1;
                while (grafiekData.containsKey(probeerNaam)) {
                    num++;
                    probeerNaam = datum + "(" + num + ")";
                }

                grafiekData.put(probeerNaam, new HashMap<String, Integer>());
                Map<String, Integer> data = grafiekData.get(probeerNaam);
                Set<KeuzeMoment> kms = lopendeTest.getKeuzemoment();
                int numKeuzemomenten = 0;
                for (KeuzeMoment keuzeMoment : kms) {

                    if (keuzeMoment.getKeuze() == null) {
                        break;
                    }
                    numKeuzemomenten++;
                    String categorie = keuzeMoment.getKeuze().getFoto().getCategorie();
                    if (data.containsKey(categorie)) {
                        data.put(categorie, data.get(categorie) + 1);
                    } else {
                        data.put(categorie, 1);
                    }
                }
                serieTotals.put(probeerNaam, numKeuzemomenten);
            }
            ChartGenerator bcd = new ChartGenerator("Uitslag test " + test.getTest().getNaam(), grafiekData, serieTotals);
            JFreeChart c = bcd.getChart();
            // cp = new ChartPanel(c, 400, 400, 50, 50, 400, 400, false, false, false, false, false, false, true);

            ChartFrame cf = new ChartFrame("Vergelijken testresultaten", c);
            //  cf.setPreferredSize(new Dimension(400,400));
            cf.pack();
            cf.setVisible(true);
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
    }//GEN-LAST:event_vergelijkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aantalKeuzemomenten;
    private javax.swing.JTextField datumAfronding;
    private meine.app.ImagePanel grafiek;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField leerlingNaam;
    private javax.swing.JPanel resultaten;
    private javax.swing.JTextField testNaam;
    private javax.swing.JButton vergelijk;
    // End of variables declaration//GEN-END:variables
}
