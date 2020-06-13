package sampleStuff.jTableSamples;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StringTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public StringTableCellRenderer() {
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
