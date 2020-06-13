package sampleStuff.jTableSamples;

import javax.swing.*;
import java.awt.*;

public class JTableObjectSample {

    private JFrame frame = new JFrame("Object Table Sample");
    private JPanel jPanel;
    private JScrollPane tableScrollPane;
    private JTable jTable;


    public JPanel initObjectTable() {
        jPanel = new JPanel();

        // Set the table modell
        ObjectTableModel objectTableModel = new ObjectTableModel();

        // Create the table with the custom string table model
        jTable = new JTable(objectTableModel);

        // Assign the renderer to the table
        ObjectTableCellRenderer objectTableCellRenderer = new ObjectTableCellRenderer();
        jTable.setDefaultRenderer(String.class, objectTableCellRenderer);
        jTable.getColumnModel().getColumn(0).setCellRenderer(objectTableCellRenderer);
        jTable.getColumnModel().getColumn(1).setCellRenderer(objectTableCellRenderer);
        jTable.getColumnModel().getColumn(2).setCellRenderer(objectTableCellRenderer);


        // Create a scrollpane for the table
        tableScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setPreferredSize(new Dimension(400, 100));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("tableScrollPane Border"));
        jPanel.add(tableScrollPane);

        // Sets the selected row and sets it visible
        jTable.setFillsViewportHeight(true);
//        jTable.setRowSelectionInterval(7, 7);
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
        frame.getContentPane().add(BorderLayout.NORTH, initObjectTable());
        frame.setLocation(150, 150);
        frame.setPreferredSize(new Dimension(500, 200));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JTableObjectSample instance = new JTableObjectSample();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                instance.createAndShowGUI();
            }
        });
    }
}



