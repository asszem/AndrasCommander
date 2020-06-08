package sampleStuff;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Vector;

public class JTableSimpleSample {

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
        CustomTableCellRenderer customTableCellRenderer = new CustomTableCellRenderer();
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

        jTable.addKeyListener(new KeyListenerForTable(jTable));

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

class KeyListenerForTable implements KeyListener {

    private JTable jTable;

    public KeyListenerForTable(JTable jTable) {
        this.jTable = jTable;
    }

    public void scrollToSelectedItem() {
        int row = jTable.getSelectedRow();
        Rectangle cellRect = jTable.getCellRect(row, 0, true);
        jTable.scrollRectToVisible(cellRect);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 74) {
            int nextRow = jTable.getSelectedRow() + 1 >= jTable.getRowCount() ? 0 : jTable.getSelectedRow() + 1;
            jTable.setRowSelectionInterval(nextRow, nextRow);
            scrollToSelectedItem();
        }
        if (e.getKeyCode() == 75) {
            int nextRow = jTable.getSelectedRow() - 1 <= 0 ? jTable.getRowCount() - 1 : jTable.getSelectedRow() - 1;
            jTable.setRowSelectionInterval(nextRow, nextRow);
            scrollToSelectedItem();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

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

class CustomTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public CustomTableCellRenderer() {
        System.out.println("custom table cell renderer called");
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        Object valueAt = table.getModel().getValueAt(row, col);

        c.setBackground(Color.GREEN);
        String s = "";
        if (valueAt != null) {
            s = valueAt.toString();
        }
        s = "<html><font color=red><span style='background:yellow;'>" + s.substring(0, 4) + "</font></span> - <font color=navy><backgrouund-color=red>" +
                s.substring(4, 7) + "</font></html>";

        setText(s);
//        if (s.equalsIgnoreCase("yellow")) {
//            c.setForeground(Color.YELLOW);
//            c.setBackground(Color.gray);
//        } else {
//            c.setForeground(Color.black);
//            c.setBackground(Color.WHITE);
//        }
        if (isSelected){
            c.setBackground(Color.ORANGE);
        }
        this.setOpaque(true);
        return c;
    }
}
