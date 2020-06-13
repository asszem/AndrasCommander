package sampleStuff.jTableSamples;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.Vector;

public class StringTableModel extends AbstractTableModel {
    Object[] columnNames;
    Object[][] rowData;

    //Constructor
    public StringTableModel() {
        populateTableData(10, 5);
    }

    private void populateTableData(int row, int col) {
        this.rowData = new Object[row][col];
        this.columnNames = new Object[col];

        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                columnNames[k]="Column " + k;
                rowData[i][k] = "Row " + i + " Col " + k;
            }
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return rowData[row][col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        rowData[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}
