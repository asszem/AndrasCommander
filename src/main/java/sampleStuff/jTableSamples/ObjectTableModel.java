package sampleStuff.jTableSamples;

import javax.swing.table.AbstractTableModel;

public class ObjectTableModel extends AbstractTableModel {
    Object[] columnNames;
    Object[][] rowData;

    //Constructor
    public ObjectTableModel() {
        populateTableData(3, 3);
    }

    private void populateTableData(int row, int col) {
        this.columnNames = new Object[col];
        this.rowData = new Object[row][col];

        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                columnNames[k]="Column " + k;
                TableData tableData = new TableData();
                tableData.setDataItem("R " + i + " C " + k);
                tableData.setAnotherData(columnNames[k].toString());
                tableData.setDataInt(i);
                rowData[i][k] = tableData;
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
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        rowData[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}
