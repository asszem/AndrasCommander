package sampleStuff.jTableSamples;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class JTableStringSample {

    private JFrame frame = new JFrame("String Table Sample");
    private JPanel jPanel;
    private JScrollPane tableScrollPane;
    private JTable jTable;
    private TableModel tableModel;

    private Vector<Vector> rowData = new Vector<>();



    public JPanel initStringTable() {
        jPanel = new JPanel();

        // Set the table modell
        StringTableModel stringTableModel = new StringTableModel();

        // Create the table with the custom string table model
        jTable = new JTable(stringTableModel);
        jTable.setOpaque(true);

        // Assign the renderer to the table
        StringTableCellRenderer stringTableCellRenderer = new StringTableCellRenderer();
        jTable.setDefaultRenderer(String.class, stringTableCellRenderer);
        jTable.getColumnModel().getColumn(0).setCellRenderer(stringTableCellRenderer);


        // Create a scrollpane for the table
        tableScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setPreferredSize(new Dimension(400, 100));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("tableScrollPane Border"));
        jPanel.add(tableScrollPane);

        // Sets the selected row and sets it visible
        jTable.setFillsViewportHeight(true);
        jTable.setRowSelectionInterval(7, 7);
        scrollToSelectedItem();
        jTable.updateUI();

        // Assign a custom Key Listener
        jTable.addKeyListener(new TableKeyListener(jTable));

        return jPanel;
    }

    public void scrollToSelectedItem() {
        int row = jTable.getSelectedRow();
        Rectangle cellRect = jTable.getCellRect(row, 0, true);
        jTable.scrollRectToVisible(cellRect);
    }


    public void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.NORTH, initStringTable());
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
}

