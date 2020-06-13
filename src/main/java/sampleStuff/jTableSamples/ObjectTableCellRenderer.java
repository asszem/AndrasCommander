package sampleStuff.jTableSamples;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ObjectTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public ObjectTableCellRenderer() {
        System.out.println("ObjectTableCellRenderer constructor called");
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {

        TableData tableData = (TableData) table.getModel().getValueAt(row, col);

        String data1 = tableData.getDataItem();
        String data2 = tableData.getAnotherData();
        int number = tableData.getDataInt();
        System.out.println("data 1 " + data1);
        System.out.println("data 2 " + data2);

        String s = "<html><font color=red><span style='background:yellow;'>" + data1 + "</font></span>";
        String s2 = "<font color=green><span style='background:blue;'>" + data2 + "</font></span> | " + number;

        setText(s.concat(s2));

        if (isSelected) {
            setBackground(Color.ORANGE);
        } else {
            setBackground(Color.WHITE);
        }

        return this;
    }
}
