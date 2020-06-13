package swingGUI.tableFilePanel;

import data.FileItem;
import sampleStuff.jTableSamples.TableData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableFilePanelCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public TableFilePanelCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {


//        String s = "<html><font color=red><span style='background:yellow;'>" + data1 + "</font></span>";
//        String s2 = "<font color=green><span style='background:blue;'>" + data2 + "</font></span> | " + number;

        String text = value.toString();
        if (table.getModel().getValueAt(row, col) instanceof FileItem){
            FileItem fileItem = (FileItem) table.getModel().getValueAt(row, col);
            if (fileItem.getFile().isDirectory()){
                text = "[" + fileItem.getFile().getName() + "]";
            } else
            {
                text = fileItem.getFile().getName();
            }
        }

        if (row==0 ){
            if (col==0){
                text ="..";
            }
            else
            {
                text="";
            }
        }

        setText(text);

        if (isSelected) {
            setBackground(Color.ORANGE);
        } else {
            setBackground(Color.WHITE);
        }

        return this;
    }
}
