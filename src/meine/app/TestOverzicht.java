package meine.app;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import meine.models.Foto;
import meine.util.MyDb;
import meine.models.Test;
import meine.util.ImageFilter;
import meine.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class TestOverzicht extends javax.swing.JFrame {

    private static final Log log = LogFactory.getLog(TestOverzicht.class);
    private File currentDir = null;
    private List<Test> tests;
    private Test current = null;
    private List<Foto> fotos = new ArrayList<Foto>();

    /** Creates new form TestOverzicht */
    public TestOverzicht() {
        initComponents();
        fillTable();
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
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
        openMap = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        map = new javax.swing.JTextField();
        logoPanel1 = new meine.app.LogoPanel();
        jLabel3 = new javax.swing.JLabel();
        naamTest = new javax.swing.JTextField();
        opslaan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        testOverzicht = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        verwijder = new javax.swing.JButton();
        wijzig = new javax.swing.JButton();
        nieuw = new javax.swing.JButton();
        status = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(252, 220, 147));

        openMap.setText("...");
        openMap.setEnabled(false);
        openMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMapActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(51, 170, 110));
        jLabel1.setText("Maak test");

        jLabel2.setText("Kies hoofdmap");

        map.setEditable(false);

        javax.swing.GroupLayout logoPanel1Layout = new javax.swing.GroupLayout(logoPanel1);
        logoPanel1.setLayout(logoPanel1Layout);
        logoPanel1Layout.setHorizontalGroup(
            logoPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );
        logoPanel1Layout.setVerticalGroup(
            logoPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );

        jLabel3.setText("Naam test");

        naamTest.setEnabled(false);

        opslaan.setText("Opslaan");
        opslaan.setEnabled(false);
        opslaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opslaanActionPerformed(evt);
            }
        });

        testOverzicht.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        testOverzicht.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(testOverzicht);

        jLabel4.setText("Testen");

        verwijder.setText("Verwijderen");
        verwijder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verwijderActionPerformed(evt);
            }
        });

        wijzig.setText("Wijzigen");
        wijzig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wijzigActionPerformed(evt);
            }
        });

        nieuw.setText("Nieuw");
        nieuw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nieuwActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(nieuw)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(wijzig)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verwijder))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opslaan))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(naamTest, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(map, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(openMap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(status))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52)
                .addComponent(logoPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(logoPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nieuw)
                    .addComponent(wijzig)
                    .addComponent(verwijder))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(naamTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openMap))
                .addGap(18, 18, 18)
                .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(opslaan)
                .addGap(13, 13, 13))
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

    private void openMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMapActionPerformed

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fc.showOpenDialog(this);
        File dir = fc.getSelectedFile();
        currentDir = dir;
        map.setText(currentDir.getPath());

    }//GEN-LAST:event_openMapActionPerformed

    private void opslaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opslaanActionPerformed
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        try {
            Test t = populateObject();
            if (current == null) {
                em.persist(t);
                em.flush();


                for (Iterator<Foto> it = fotos.iterator(); it.hasNext();) {
                    Foto foto = it.next();
                    foto.setTest(t);
                    em.persist(foto);
                    t.addFotos(foto);
                }
            } else {
                if (current != null && currentDir != null) {
                    Set<Foto> fotoSet = current.getFoto();

                    for (Iterator<Foto> it = fotoSet.iterator(); it.hasNext();) {
                        Foto foto = it.next();
                        em.remove(foto);
                    }

                    for (Iterator<Foto> it = fotos.iterator(); it.hasNext();) {
                        Foto foto = it.next();
                        foto.setTest(t);
                        em.persist(foto);
                    }
                }
                em.merge(t);
            }

            em.flush();

        } catch (Exception e) {
            log.error("", e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            //status.setText("Opslaan mislukt. Niks opgeslagen. Rapporteer fout.");
        } finally {
            if (transaction.isActive()) {
                transaction.commit();
            }
            
            disableForm();
        }

    }//GEN-LAST:event_opslaanActionPerformed

    private void verwijderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verwijderActionPerformed
        current = null;
        Test test = (Test) testOverzicht.getSelectedValue();

        if (test != null) {
            EntityManager em = MyDb.getThreadEntityManager();
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                Set<Foto> fotoSet = test.getFoto();
                for (Iterator<Foto> it = fotoSet.iterator(); it.hasNext();) {
                    Foto foto = it.next();
                    em.remove(foto);
                }
                em.flush();

                em.remove(test);
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

    private void wijzigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wijzigActionPerformed
        Test selected = (Test) testOverzicht.getSelectedValue();

        if (selected != null) {
            current = selected;
            populateForm(selected);
        }
}//GEN-LAST:event_wijzigActionPerformed

    private void nieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nieuwActionPerformed
        current = null;
        populateForm(current);
}//GEN-LAST:event_nieuwActionPerformed

    private void saveFile(File f) {

        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                saveFile(file);
            }
        } else {

            String ext = Utils.getExtension(f);

            if (ImageFilter.isImage(f)) {

                try {
                    BufferedImage bi = ImageIO.read(f);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi, ext, baos);

                    byte[] ba = baos.toByteArray();
                    Foto fo = new Foto();
                    fo.setFoto(ba);
                    fo.setMime(ext);
                    String parent = f.getParent();
                    parent = parent.substring(parent.lastIndexOf("\\") + 1);
                    fo.setCategorie(parent);

                    fotos.add(fo);

                } catch (IOException e) {
                    log.error("", e);
                }



            }
        }
    }

    private void fillTable() {
        EntityManager em = MyDb.getThreadEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query q = em.createQuery("FROM Test t");
            tests = (List<Test>) q.getResultList();

            DefaultListModel dlm = new DefaultListModel();
            for (Iterator<Test> it = tests.iterator(); it.hasNext();) {
                Test test = it.next();
                dlm.addElement(test);
            }
            testOverzicht.setModel(dlm);
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

    private Test populateObject() {
        Test test = current;
        if (test == null) {
            test = new Test();
        }

        test.setNaam(naamTest.getText());
        if (currentDir != null) {

            if (currentDir.isDirectory()) {
                saveFile(currentDir);
            }
        }

        return test;
    }

    private void populateForm(Test test) {
        naamTest.setEnabled(true);
        map.setEnabled(true);
        openMap.setEnabled(true);
        opslaan.setEnabled(true);

        if (test == null) {
            naamTest.setText("");
        } else {
            naamTest.setText(test.getNaam());
        }
    }

    private void disableForm() {
        naamTest.setEnabled(false);
        map.setEnabled(false);
        openMap.setEnabled(false);
        opslaan.setEnabled(false);

        naamTest.setText("");
        map.setText("");
        currentDir = null;
        fotos = new ArrayList<Foto>();
        fillTable();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private meine.app.LogoPanel logoPanel1;
    private javax.swing.JTextField map;
    private javax.swing.JTextField naamTest;
    private javax.swing.JButton nieuw;
    private javax.swing.JButton openMap;
    private javax.swing.JButton opslaan;
    private javax.swing.JLabel status;
    private javax.swing.JList testOverzicht;
    private javax.swing.JButton verwijder;
    private javax.swing.JButton wijzig;
    // End of variables declaration//GEN-END:variables
}
