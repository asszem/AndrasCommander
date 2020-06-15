package swingGUI.tableFilePanel;

import data.FileItem;
import sampleStuff.jTableSamples.TableData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;

public class TableFilePanelCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private String searchTerm="";
    private Font font = getFont();
    final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
    String text;

    public TableFilePanelCellRenderer() {
        setOpaque(true);
    }

    public void setSearchTerm(String searchTerm){
        this.searchTerm=searchTerm;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {


        setIcon(null);

        text = value.toString();

        if (table.getModel().getValueAt(row, col) instanceof FileItem) {
            FileItem fileItem = (FileItem) table.getModel().getValueAt(row, col);
            if (fileItem.getFile().isDirectory()) {
                text = "[" + fileItem.getFile().getName() + "]";
                setForeground(Color.BLUE);
                font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD));
            } else {
                text = fileItem.getFile().getName();
                setForeground(Color.BLACK);
                font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD));
            }
            // TODO Find a solution that displays thumbnails correctly under Linux
            Icon icon = fc.getUI().getFileView(fc).getIcon(fileItem.getFile());
            setIcon(icon);
            setFont(font);
        }

        if (row == 0) {
            if (col == 0) {
                text = "..";
            } else {
                text = "";
            }
        }

//        text = "<html><font color=black><span style='background:yellow;'>" + matchingPart + "</font></span>" + rest;
        if (!searchTerm.isEmpty() && text.startsWith(searchTerm)){
            String matchingPart=text.substring(0,searchTerm.length());
            String rest=text.substring(searchTerm.length(),text.length());
            text = "<html><font color=red><span style='background:yellow;'>" + matchingPart + "</font></span>" + rest;
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
