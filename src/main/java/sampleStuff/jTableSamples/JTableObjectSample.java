package sampleStuff.jTableSamples;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class JTableObjectSample {

    private JFrame frame = new JFrame("Object Table Sample");
    private JPanel jPanel;
    private JScrollPane tableScrollPane;
    private JTable jTable;
    private TableModel tableModel;

    private Vector<Vector> rowData = new Vector<>();

    private void populateRowData() {
    }


    public JPanel initSimpleTable() {
        jPanel = new JPanel();

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
        ObjectTableCellRenderer objectTableCellRenderer = new ObjectTableCellRenderer();
        jTable.setDefaultRenderer(String.class, objectTableCellRenderer);
        tableScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setPreferredSize(new Dimension(400, 100));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("tableScrollPane Border"));
        jTable.getColumnModel().getColumn(0).setCellRenderer(objectTableCellRenderer);
        jTable.setFillsViewportHeight(true);
        //Select the row (start row, end row - to select a single row, use the same value)
        jTable.setRowSelectionInterval(7, 7);
        scrollToSelectedItem();
        jTable.updateUI();


        jTable.addKeyListener(new TableKeyListener(jTable));

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
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                instance.createAndShowGUI();
            }
        });
    }
}



