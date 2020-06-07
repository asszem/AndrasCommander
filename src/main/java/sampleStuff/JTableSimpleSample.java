package sampleStuff;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class JTableSimpleSample {

    private JFrame frame = new JFrame("Simple Table Sample");
    private JPanel jPanel;
    private JScrollPane tableScrollPane;
    private JTable jTable;
    private TableModel tableModel;

    private Vector<Vector> rowData=new Vector<>();

    private void populateRowData() {
        Vector<String> currentRow ;
        for (int i = 0; i < 10; i++) {
            currentRow = new Vector<>();
            for (int k=1;k<5;k++){
                currentRow.add("Row " + i + " col " + k);
            }
            rowData.add(currentRow);
        }
    }


    public JPanel initSimpleTable() {
        jPanel = new JPanel();
//        jPanel.setBorder(BorderFactory.createTitledBorder("Table Panel"));

        String[] columtTitlesArray = {"Column 1", "Column 2", "Column 3", "Column 4"};
        Vector<String> columnTitles = new Vector<String>(Arrays.asList(columtTitlesArray));
        populateRowData();
        TableModel model = new DefaultTableModel(rowData, columnTitles);
        jTable = new JTable(model);
        tableScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setPreferredSize(new Dimension(400, 100));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("tabbleScrollPane Border"));

        jTable.setFillsViewportHeight(true);
        jTable.setRowSelectionInterval(7,7);
        Rectangle cellRect = jTable.getCellRect(7, 0, true);
        jTable.scrollRectToVisible(cellRect);

        jPanel.add(tableScrollPane);
        return jPanel;
    }


    public void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.NORTH, initSimpleTable());
//        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocation(150, 150);
        frame.setPreferredSize(new Dimension(500, 200));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JTableSimpleSample instance = new JTableSimpleSample();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                instance.createAndShowGUI();
            }
        });
    }

    private class customTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }
    }
}

class Data {
    private String dataItem;
    private String anotherData;
    private int dataInt;

    public String getDataItem() {
        return dataItem;
    }

    public void setDataItem(String dataItem) {
        this.dataItem = dataItem;
    }

    public String getAnotherData() {
        return anotherData;
    }

    public void setAnotherData(String anotherData) {
        this.anotherData = anotherData;
    }

    public int getDataInt() {
        return dataInt;
    }

    public void setDataInt(int dataInt) {
        this.dataInt = dataInt;
    }
}
