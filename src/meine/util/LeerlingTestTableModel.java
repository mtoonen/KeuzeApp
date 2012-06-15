/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package meine.util;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import meine.models.LopendeTest;

/**
 *
 * @author Meine Toonen
 */
public class LeerlingTestTableModel extends AbstractTableModel {

    String[] columnNames;
    Object[][] data;

     public LeerlingTestTableModel() {
        columnNames = new String[2];
        columnNames[0] = "Naam test";
        columnNames[1] = "Voortgang";

        data = new Object[1][3];
        data[0][0] = "";
        data[0][1] = "";
        data[0][2] = "";
    }

    public LeerlingTestTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public LeerlingTestTableModel(List<LopendeTest> data) {
        if (!data.isEmpty()) {
            this.data = new Object[data.size()][3];
            for (int i = 0; i < data.size(); i++) {
                LopendeTest lt = data.get(i);
                this.data[i][0] = lt.getTest().getNaam();
                this.data[i][1] = lt.getStatus().toString();
                this.data[i][2] = lt;
            }
        } else {
            this.data = new Object[1][3];
            this.data[0][0] = "";
            this.data[0][1] = "";
            this.data[0][2] = "";
        }

        columnNames = new String[2];
        columnNames[0] = "Naam test";
        columnNames[1] = "Voortgang";
    }

      public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        boolean DEBUG = false;
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
