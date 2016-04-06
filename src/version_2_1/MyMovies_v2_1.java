/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package version_2_1;

import _MainPackage.Main;
import support.tools.Exist;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.w3c.dom.NodeList;

/**
 *
 * @author christos
 */
public class MyMovies_v2_1 extends javax.swing.JFrame {

    Properties prop = new Properties();
    //
    public static int movieID = 1;
    public static String MoviesDataXML;
    public static String MoviesListTXT;
    public static String ServerResponseTXT;
    public static String MoviesPosterDIR;
    // Movies Table
    TableModel model;
    TableRowSorter<TableModel> sorter;
    int selectedRadioButtonIndex;
    int selectedItemID;

    /**
     * Creates new form MyMovies_v2_1
     */
    public MyMovies_v2_1() {
        initFileChooser();

        initProperties();

        initComponents();

        initMoviesTableView();
//        jPanel_West.setVisible(false);

        jTable_Movies.setRowSelectionInterval(movieID - 1, movieID - 1);

        displayMovie(movieID);

    }

    void initFileChooser() {

        JFileChooser_SelectMoviesFolder jfc = new JFileChooser_SelectMoviesFolder();
        jfc.showDialog(this, "Select");

        File selectedFile = jfc.getSelectedFile();


        try {
            prop.setProperty("MoviesDirectory", selectedFile.getAbsolutePath());
            prop.setProperty("MoviesDataXML", "./src/chris/results/MoviesData.xml");
            prop.setProperty("MoviesListTXT", "./src/chris/results/MoviesList.txt");
            prop.setProperty("ServerResponseTXT", "./src/chris/results/ServerResponse.txt");
            prop.setProperty("MoviesPosterDIR", "./src/chris/results/POSTERS/");

            prop.store(new FileOutputStream(Main.propertiesFile), null);
            System.out.println(selectedFile.getAbsolutePath());

            // Get data!
            Main.createTittleYearLists();
            Main.getMoviesImages();

        } catch (IOException ex) {
            Logger.getLogger(MyMovies_v2_1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void initProperties() {

        try {
            prop.load(new FileInputStream(Main.propertiesFile));

            MoviesDataXML = prop.getProperty("MoviesDataXML");
            MoviesListTXT = prop.getProperty("MoviesListTXT");
            ServerResponseTXT = prop.getProperty("ServerResponseTXT");
            MoviesPosterDIR = prop.getProperty("MoviesPosterDIR");
        } catch (IOException ex) {
            Logger.getLogger(MyMovies_v2_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void displayMovie(int id) {
        jPanel_MainFrame_Center.removeAll();

        jPanel_MoviesImage Img = new jPanel_MoviesImage(id);
        jPanel_MoviesInfo info = new jPanel_MoviesInfo(id);

//        jPanel_MainFrame_Center.add(Img, BorderLayout.WEST);
//        jPanel_MainFrame_Center.add(info, BorderLayout.CENTER);
        jPanel_MainFrame_Center.add(Img);
        jPanel_MainFrame_Center.add(info);

        jPanel_MainFrame_Center.revalidate();
        jPanel_MainFrame_Center.repaint();
    }

    // === == == == == == == == == == = == = = = === = == == == = = = === = == == 
    // === == == == == == == == == == = == = = = === = == == == = = = === = == == 
    // === == == == == == == == == == = == = = = === = == == == = = = === = == == 
    void initMoviesTableView() {

        fillTable();

        // Enable sorting
        jTable_Movies.setAutoCreateRowSorter(true);
        jTable_Movies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create sorter for the search
        model = jTable_Movies.getModel();
        sorter = new TableRowSorter<TableModel>(model);
        jTable_Movies.setRowSorter(sorter);

        radioButtonSelected();

        //Whenever filterText changes, invoke newFilter.
        jTextField_Search.getDocument().addDocumentListener(
                new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                radioButtonSelected();
                newFilter(selectedRadioButtonIndex);
            }

            public void insertUpdate(DocumentEvent e) {
                radioButtonSelected();
                newFilter(selectedRadioButtonIndex);
            }

            public void removeUpdate(DocumentEvent e) {
                radioButtonSelected();
                newFilter(selectedRadioButtonIndex);
            }
        });
    }

    /*
     * Get index of selected radio button
     */
    private void radioButtonSelected() {

        if (jRadioButton_Year.isSelected()) {
            selectedRadioButtonIndex = 1;
        } else {
            selectedRadioButtonIndex = 2;
        }
    }

    /**
     * Update the row filter regular expression from the expression in the text
     * box.
     */
    private void newFilter(int selectedType) {
//        System.out.println("test");
        RowFilter<TableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            // The int at the end is the column id
            rf = RowFilter.regexFilter(jTextField_Search.getText(), selectedType);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }

        sorter.setRowFilter(rf);
    }

    void fillTable() {

        DefaultTableModel model = (DefaultTableModel) jTable_Movies.getModel();

        int movieCounter = new Integer(Exist.executeGetQuery("//moviesCounter", MoviesDataXML).item(0).getTextContent().trim());

        for (int i = 1; i <= movieCounter; i++) {

            String movieXpath = "//movie[" + i + "]/title";
            NodeList movieTitle = Exist.executeGetQuery(movieXpath, MoviesDataXML);
            String title = movieTitle.item(0).getTextContent().trim();

            String yearXpath = "//movie[" + i + "]/year";
            NodeList movieYear = Exist.executeGetQuery(yearXpath, MoviesDataXML);
            String year = movieYear.item(0).getTextContent().trim();


            // Create object with data
//            Object[] row = new Object[]{i, name, surname, address, code, restamount, uniqueID};
            Object[] row = new Object[]{i, year, title};

            // Instert row in the table
            model.addRow(row);
        }
    }

    // === == == == == == == == == == = == = = = === = == == == = = = === = == == 
    // === == == == == == == == == == = == = = = === = == == == = = = === = == == 
    // === == == == == == == == == == = == = = = === = == == == = = = === = == == 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel_Navigation = new javax.swing.JPanel();
        jPanel_Navigation_HideTable = new javax.swing.JPanel();
        jToggleButton_HideTable = new javax.swing.JToggleButton();
        jPanel_Navigation_Main = new javax.swing.JPanel();
        jPanel_MainFrame_Center = new javax.swing.JPanel();
        jPanel_West = new javax.swing.JPanel();
        jPanel_Search = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField_Search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton_Year = new javax.swing.JRadioButton();
        jRadioButton_Title = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Movies = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyMovies");
        setMinimumSize(new java.awt.Dimension(950, 600));
        setPreferredSize(new java.awt.Dimension(950, 44));
        setResizable(false);

        jPanel_Navigation.setLayout(new java.awt.BorderLayout());

        jPanel_Navigation_HideTable.setLayout(new java.awt.BorderLayout());

        jToggleButton_HideTable.setText("Hide");
        jToggleButton_HideTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_HideTableActionPerformed(evt);
            }
        });
        jPanel_Navigation_HideTable.add(jToggleButton_HideTable, java.awt.BorderLayout.CENTER);

        jPanel_Navigation.add(jPanel_Navigation_HideTable, java.awt.BorderLayout.WEST);

        jPanel_Navigation_Main.setLayout(new java.awt.GridLayout(1, 0));
        jPanel_Navigation.add(jPanel_Navigation_Main, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel_Navigation, java.awt.BorderLayout.NORTH);

        jPanel_MainFrame_Center.setLayout(new java.awt.GridLayout(1, 2));
        getContentPane().add(jPanel_MainFrame_Center, java.awt.BorderLayout.CENTER);

        jPanel_West.setMaximumSize(new java.awt.Dimension(300, 2147483647));
        jPanel_West.setMinimumSize(new java.awt.Dimension(300, 0));
        jPanel_West.setPreferredSize(new java.awt.Dimension(300, 0));
        jPanel_West.setLayout(new java.awt.BorderLayout());

        jPanel_Search.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jTextField_Search, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Search:");
        jPanel2.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel_Search.add(jPanel2);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        buttonGroup1.add(jRadioButton_Year);
        jRadioButton_Year.setSelected(true);
        jRadioButton_Year.setText("Year");
        jPanel3.add(jRadioButton_Year);

        buttonGroup1.add(jRadioButton_Title);
        jRadioButton_Title.setText("Title");
        jPanel3.add(jRadioButton_Title);

        jPanel_Search.add(jPanel3);

        jPanel_West.add(jPanel_Search, java.awt.BorderLayout.NORTH);

        jTable_Movies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Year", "Title"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Movies.getTableHeader().setReorderingAllowed(false);
        jTable_Movies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_MoviesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Movies);
        jTable_Movies.getColumnModel().getColumn(0).setPreferredWidth(25);
        jTable_Movies.getColumnModel().getColumn(1).setPreferredWidth(25);

        jPanel_West.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel_West, java.awt.BorderLayout.WEST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton_HideTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_HideTableActionPerformed
        // TODO add your handling code here:
        if (jToggleButton_HideTable.isSelected()) {
            jPanel_West.setVisible(false);
        } else {
            jPanel_West.setVisible(true);
        }

    }//GEN-LAST:event_jToggleButton_HideTableActionPerformed

    private void jTable_MoviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_MoviesMouseClicked
        // TODO add your handling code here:
        int[] selRows = jTable_Movies.getSelectedRows();
        int j = selRows[0];
        int i = Integer.parseInt(jTable_Movies.getValueAt(j, 0).toString());
        selectedItemID = i;

        movieID = selectedItemID;
        displayMovie(movieID);
    }//GEN-LAST:event_jTable_MoviesMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyMovies_v2_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyMovies_v2_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyMovies_v2_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyMovies_v2_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyMovies_v2_1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_MainFrame_Center;
    private javax.swing.JPanel jPanel_Navigation;
    private javax.swing.JPanel jPanel_Navigation_HideTable;
    private javax.swing.JPanel jPanel_Navigation_Main;
    private javax.swing.JPanel jPanel_Search;
    private javax.swing.JPanel jPanel_West;
    private javax.swing.JRadioButton jRadioButton_Title;
    private javax.swing.JRadioButton jRadioButton_Year;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Movies;
    private javax.swing.JTextField jTextField_Search;
    private javax.swing.JToggleButton jToggleButton_HideTable;
    // End of variables declaration//GEN-END:variables
}
