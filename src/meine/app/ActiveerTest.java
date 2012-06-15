/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ActiveerTest.java
 *
 * Created on 7-sep-2010, 22:46:56
 */
package meine.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import meine.models.Foto;
import meine.models.KeuzeMoment;
import meine.models.KeuzeMomentFoto;
import meine.models.Leerling;
import meine.models.LopendeTest;
import meine.util.MyDb;
import meine.models.Test;
import meine.util.LopendeTestTableModel;
import meine.util.Plaats;
import meine.util.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class ActiveerTest extends javax.swing.JFrame {

    private static final Log log = LogFactory.getLog(ActiveerTest.class);
    private List<LopendeTest> lopendeTests;
    private List<Leerling> leerlingList;
    private List<Test> tests;
    private LopendeTest current = null;

    /** Creates new form ActiveerTest */
    public ActiveerTest() {
        initComponents();
        //  addWindowListener(Opstart.getWindowManager());
        fillTable();
        this.setVisible(true);
    }

    private void fillTable() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query lopendeTestQuery = em.createQuery("FROM LopendeTest t WHERE status <> :afgerond").setParameter("afgerond", Status.AFGEROND);
            lopendeTests = (List<LopendeTest>) lopendeTestQuery.getResultList();

            LopendeTestTableModel lttm = new LopendeTestTableModel(lopendeTests);
            lopendetesttable.setModel(lttm);

            Query leerlingQuery = em.createQuery("From Leerling l");
            leerlingList = leerlingQuery.getResultList();

            leerlingen.removeAllItems();

            for (Iterator<Leerling> it = leerlingList.iterator(); it.hasNext();) {
                Leerling leerling = it.next();
                leerlingen.addItem(leerling);
            }
            Query testQuery = em.createQuery("From Test t");
            tests = testQuery.getResultList();

            testen.removeAllItems();

            for (Iterator<Test> it = tests.iterator(); it.hasNext();) {
                Test test = it.next();
                testen.addItem(test);
            }
            status.setText("");

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

    private void disableForm() {
        leerlingen.setEnabled(false);
        testen.setEnabled(false);
        numKeuzemomenten.setEnabled(false);
        numFotosPerMoment.setEnabled(false);
        tijdslimiet.setEnabled(false);
        leerlingUitslag.setEnabled(false);
        opslaan.setEnabled(false);
        annuleer.setEnabled(false);

        leerlingen.setSelectedIndex(0);
        testen.setSelectedIndex(0);
        numKeuzemomenten.setText("10");
        numFotosPerMoment.setSelectedIndex(0);
        tijdslimiet.setValue(0);
        leerlingUitslag.setSelectedIndex(0);
    }

    private LopendeTest populateObject() throws NumberFormatException {
        LopendeTest lt = current;

        if (current == null) {
            lt = new LopendeTest();
        }

        lt.setLeerling((Leerling) leerlingen.getSelectedItem());
        lt.setTest((Test) testen.getSelectedItem());
        lt.setKeuzemomenten(Integer.valueOf(numKeuzemomenten.getText()));

        lt.setFotosperkeuzemoment(Integer.valueOf(numFotosPerMoment.getSelectedItem().toString()));
        lt.setTijdlimiet((Integer) tijdslimiet.getValue());
        Boolean uitslag = leerlingUitslag.getSelectedItem().toString().equalsIgnoreCase("Ja") ? true : false;
        lt.setLeerlinguitslag(uitslag);

        lt.setStatus(Status.GEACTIVEERD);

        return lt;
    }

    private void populateForm(LopendeTest lt) {
        testNumFotosPerMoment((Test) testen.getSelectedItem());
        leerlingen.setEnabled(true);
        testen.setEnabled(true);
        numKeuzemomenten.setEnabled(true);
        numFotosPerMoment.setEnabled(true);
        tijdslimiet.setEnabled(true);
        leerlingUitslag.setEnabled(true);
        opslaan.setEnabled(true);
        annuleer.setEnabled(true);

        if (lt != null) {
            leerlingen.setSelectedItem(lt.getLeerling());
            testen.setSelectedItem(lt.getTest());
            numKeuzemomenten.setText("" + lt.getKeuzemomenten());
            numFotosPerMoment.setSelectedItem("" + lt.getFotosperkeuzemoment());
            tijdslimiet.setValue(lt.getTijdlimiet());
            String uitslag = lt.getLeerlinguitslag() ? "Ja" : "Nee";
            leerlingUitslag.setSelectedItem(uitslag);
        }
    }

    private void testNumFotosPerMoment(Test test) {
        if (test == null) {
            return;
        }
        if (!has4Categories(test)) {
            if (numFotosPerMoment.getItemCount() == 2) {
                numFotosPerMoment.removeItem("4");
            }
        } else {
            if (numFotosPerMoment.getItemCount() == 1) {
                numFotosPerMoment.addItem("4");
            }
        }
    }

    private Set<String> getCategories(Test t) {
        Set<Foto> fotos = t.getFoto();
        Set<String> categorien = new HashSet<String>();
        for (Foto foto : fotos) {
            categorien.add(foto.getCategorie());
        }
        return categorien;
    }

    private boolean has4Categories(Test t) {
        Set<String> categorien = getCategories(t);
        return categorien.size() >= 4;
    }

    private void createTestTwoPanel(KeuzeMoment km, Foto[] fotoArray, EntityManager em) {
        Set<String> categorien = getCategories(km.getLopendetest().getTest());
        List<String> cats = new ArrayList<String>(categorien);
        Map<String, List<Foto>> fotoMap  = getCategoryFotos(fotoArray);

        Random rand = new Random(new Date().getTime());
        int f1 = rand.nextInt(cats.size());
        Foto fo1 = getRandomFoto(cats.remove(f1), fotoMap);
        int f2 = rand.nextInt(cats.size());
        Foto fo2 = getRandomFoto(cats.remove(f2), fotoMap);
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

        km.getFotos().add(kmf1);
        km.getFotos().add(kmf2);
    }

    private Map<String, List<Foto>> getCategoryFotos(Foto[] fotoArray) {
        Map<String, List<Foto>> fotoMap = new HashMap<String, List<Foto>>();

        for (int i = 0; i < fotoArray.length; i++) {
            Foto foto = fotoArray[i];
            if (!fotoMap.containsKey(foto.getCategorie())) {
                fotoMap.put(foto.getCategorie(), new ArrayList<Foto>());
            }
            fotoMap.get(foto.getCategorie()).add(foto);
        }
        return fotoMap;
    }

    private Foto getRandomFoto(String categorie,Map<String, List<Foto>> fotoMap){
        List<Foto> fotos = fotoMap.get(categorie);
        Random rand = new Random(new Date().getTime());
        int index = rand.nextInt(fotos.size());
        return fotos.get(index);
    }

    private void createTestFourPanel(KeuzeMoment km, Foto[] fotoArray, EntityManager em) {
        Set<String> categorien = getCategories(km.getLopendetest().getTest());
        List<String> cats = new ArrayList<String>(categorien);
        Map<String, List<Foto>> fotoMap  = getCategoryFotos(fotoArray);

        Random rand = new Random(new Date().getTime());
        int f1 = rand.nextInt(cats.size());
        Foto fo1 =getRandomFoto(cats.remove(f1), fotoMap); // fotoArray[f1];
        int f2 = rand.nextInt(cats.size());
        Foto fo2 = getRandomFoto(cats.remove(f2), fotoMap);;

        int f3 = rand.nextInt(cats.size());
        Foto fo3 = getRandomFoto(cats.remove(f3), fotoMap);;

        int f4 = rand.nextInt(cats.size());
        Foto fo4 = getRandomFoto(cats.remove(f4), fotoMap);;

        Plaats pos1 = Plaats.LINKSBOVEN;
        Plaats pos2 = Plaats.RECHTSBOVEN;
        Plaats pos3 = Plaats.LINKSONDER;
        Plaats pos4 = Plaats.RECHTSONDER;

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

        KeuzeMomentFoto kmf3 = new KeuzeMomentFoto();
        kmf3.setFoto(fo3);
        kmf3.setPositie(pos3);
        kmf3.setKeuzemoment(km);
        em.persist(kmf3);

        KeuzeMomentFoto kmf4 = new KeuzeMomentFoto();
        kmf4.setFoto(fo4);
        kmf4.setPositie(pos4);
        kmf4.setKeuzemoment(km);
        em.persist(kmf4);

        km.getFotos().add(kmf1);
        km.getFotos().add(kmf2);
        km.getFotos().add(kmf3);
        km.getFotos().add(kmf4);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lopendetesttable = new javax.swing.JTable();
        wijzig = new javax.swing.JButton();
        verwijder = new javax.swing.JButton();
        nieuw = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        leerlingen = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        testen = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        numKeuzemomenten = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        numFotosPerMoment = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        tijdslimiet = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        leerlingUitslag = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        opslaan = new javax.swing.JButton();
        annuleer = new javax.swing.JButton();
        status = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 626));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Activeer test");

        lopendetesttable.setModel(new javax.swing.table.DefaultTableModel(
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
        lopendetesttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lopendetesttableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lopendetesttable);

        wijzig.setText("Wijzigen");

        verwijder.setText("Verwijderen");
        verwijder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verwijderActionPerformed(evt);
            }
        });

        nieuw.setText("Nieuw");
        nieuw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nieuwActionPerformed(evt);
            }
        });

        jLabel2.setText("Leerling");

        leerlingen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        leerlingen.setEnabled(false);

        jLabel3.setText("Test");

        testen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        testen.setEnabled(false);
        testen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testenActionPerformed(evt);
            }
        });

        jLabel4.setText("Aantal keuzemomenten");

        numKeuzemomenten.setText("10");
        numKeuzemomenten.setEnabled(false);

        jLabel5.setText("Foto's per keuzemoment");

        numFotosPerMoment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "4" }));
        numFotosPerMoment.setEnabled(false);

        jLabel6.setText("Tijdslimiet ");

        tijdslimiet.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        tijdslimiet.setEnabled(false);

        jLabel7.setText("Leerling uitslag");

        leerlingUitslag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ja", "Nee" }));
        leerlingUitslag.setEnabled(false);

        jLabel8.setText("(minuten, 0 is geen limiet)");

        opslaan.setText("Opslaan");
        opslaan.setEnabled(false);
        opslaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opslaanActionPerformed(evt);
            }
        });

        annuleer.setText("Annuleer");
        annuleer.setEnabled(false);
        annuleer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annuleerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(nieuw)
                                .addGap(40, 40, 40)
                                .addComponent(wijzig)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(verwijder))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(leerlingen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(testen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(numKeuzemomenten))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(tijdslimiet, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(numFotosPerMoment, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(leerlingUitslag, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)))))
                        .addGap(1288, 1288, 1288))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(opslaan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(annuleer))
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
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nieuw)
                    .addComponent(wijzig)
                    .addComponent(verwijder))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(leerlingen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(testen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numKeuzemomenten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(numFotosPerMoment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tijdslimiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(leerlingUitslag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(status)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opslaan)
                    .addComponent(annuleer))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opslaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opslaanActionPerformed
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            LopendeTest lt = populateObject();
            if (current == null) {
                em.persist(lt);
            } else {
                em.merge(lt);
            }
            em.refresh(lt);
            Set<Foto> fotos = lt.getTest().getFoto();
            Foto[] fotoArray = fotos.toArray(new Foto[fotos.size()]);
            for (int i = 0; i < lt.getKeuzemomenten(); i++) {
                KeuzeMoment km = new KeuzeMoment();
                km.setLopendetest(lt);
                km.setPlek(i);

                em.persist(km);
                em.refresh(km);

                if (lt.getFotosperkeuzemoment() == 2) {
                    createTestTwoPanel(km, fotoArray, em);

                } else if (lt.getFotosperkeuzemoment() == 4) {
                    createTestFourPanel(km, fotoArray, em);
                }
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
    private void lopendetesttableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lopendetesttableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = lopendetesttable.getSelectedRow();

            if (row != -1) {
                LopendeTest lt = (LopendeTest) lopendetesttable.getModel().getValueAt(row, 5);
                current = lt;
                populateForm(lt);

            }
        }
    }//GEN-LAST:event_lopendetesttableMouseClicked

    private void annuleerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annuleerActionPerformed
        disableForm();
        current = null;
    }//GEN-LAST:event_annuleerActionPerformed

    private void nieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nieuwActionPerformed
        current = null;
        populateForm(
                current);
    }//GEN-LAST:event_nieuwActionPerformed
    private void verwijderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verwijderActionPerformed
        current = null;

        int row = lopendetesttable.getSelectedRow();

        if (row != -1) {
            LopendeTest lt = (LopendeTest) lopendetesttable.getModel().getValueAt(row, 5);
            EntityManager em = MyDb.getThreadEntityManager();
            EntityTransaction transaction = em.getTransaction();

            try {
                transaction.begin();
                em.remove(lt);

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

    private void testenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testenActionPerformed
        testNumFotosPerMoment((Test) testen.getSelectedItem());
    }//GEN-LAST:event_testenActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annuleer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox leerlingUitslag;
    private javax.swing.JComboBox leerlingen;
    private javax.swing.JTable lopendetesttable;
    private javax.swing.JButton nieuw;
    private javax.swing.JComboBox numFotosPerMoment;
    private javax.swing.JTextField numKeuzemomenten;
    private javax.swing.JButton opslaan;
    private javax.swing.JLabel status;
    private javax.swing.JComboBox testen;
    private javax.swing.JSpinner tijdslimiet;
    private javax.swing.JButton verwijder;
    private javax.swing.JButton wijzig;
    // End of variables declaration//GEN-END:variables
}
