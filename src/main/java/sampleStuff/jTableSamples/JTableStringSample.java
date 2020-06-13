package sampleStuff.jTableSamples;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class JTableStringSample {

    private JFrame frame = new JFrame("Simple Table Sample");
    private JPanel jPanel;
    private JScrollPane tableScrollPane;
    private JTable jTable;
    private TableModel tableModel;

    private Vector<Vector> rowData = new Vector<>();

    private void populateRowData() {
        Vector<String> currentRow;
        for (int i = 0; i < 10; i++) {
            currentRow = new Vector<>();
            for (int k = 1; k < 5; k++) {
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

        // Set the table modell
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnTitles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        jTable = new JTable(tableModel);
        sampleStuff.jTableSamples.CustomTableCellRenderer customTableCellRenderer = new sampleStuff.jTableSamples.CustomTableCellRenderer();
        jTable.setDefaultRenderer(String.class, customTableCellRenderer);
        tableScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setPreferredSize(new Dimension(400, 100));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("tabbleScrollPane Border"));
        jTable.getColumnModel().getColumn(0).setCellRenderer(customTableCellRenderer);
        jTable.setFillsViewportHeight(true);
        //Select the row (start row, end row - to select a single row, use the same value)
        jTable.setRowSelectionInterval(7, 7);
        scrollToSelectedItem();
        jTable.updateUI();


//        jTable.getModel().addTableModelListener(new TableModelListener() {
//
//            public void tableChanged(TableModelEvent e) {
//                System.out.println("something changed");
//                System.out.println("column = " + e.getColumn());
//                System.out.println("row = " + e.getFirstRow());
//                System.out.println("class = " + e.getSource().getClass().getSimpleName());
//            }
//        });

        jTable.addKeyListener(new sampleStuff.jTableSamples.KeyListenerForTable(jTable));

        jPanel.add(tableScrollPane);
        return jPanel;
    }

    public void scrollToSelectedItem() {
        int row = jTable.getSelectedRow();
        Rectangle cellRect = jTable.getCellRect(row, 0, true);
        jTable.scrollRectToVisible(cellRect);
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
        JTableStringSample instance = new JTableStringSample();
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

