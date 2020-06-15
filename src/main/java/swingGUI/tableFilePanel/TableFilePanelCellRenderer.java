package swingGUI.tableFilePanel;

import data.FileItem;

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
    String textToDisplay;
    String originalText;


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

        textToDisplay = value.toString(); // This will be formatted

        if (table.getModel().getValueAt(row, col) instanceof FileItem) {
            FileItem fileItem = (FileItem) table.getModel().getValueAt(row, col);
            originalText=fileItem.getFile().getName();
            if (fileItem.getFile().isDirectory()) {
                textToDisplay = "[" + fileItem.getFile().getName() + "]";
                setForeground(Color.BLUE);
                font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD));
            } else {
                textToDisplay = fileItem.getFile().getName();
                setForeground(Color.BLACK);
                font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD));
            }
            // TODO Find a solution that displays thumbnails correctly under Linux
            Icon icon = fc.getUI().getFileView(fc).getIcon(fileItem.getFile());
            setIcon(icon);
            setFont(font);
        } else {
            originalText="";
        }


        if (row == 0) {
            if (col == 0) {
                textToDisplay = "..";
            } else {
                textToDisplay = "";
            }
        }

//        textToDisplay = "<html><font color=black><span style='background:yellow;'>" + matchingPart + "</font></span>" + rest;
        if (!searchTerm.isEmpty() && !originalText.isEmpty() && originalText.startsWith(searchTerm)){
            // This is to handle folder names in brackets
            String matchingPart;
            String rest;
            if (textToDisplay.startsWith("[")){
                matchingPart= textToDisplay.substring(1,searchTerm.length());
                rest= textToDisplay.substring(searchTerm.length(), textToDisplay.length());
                textToDisplay = "<html><font color=red><span style='background:yellow;'>[" + matchingPart + "</font></span>" + rest;

            }
            else {
                matchingPart= textToDisplay.substring(0,searchTerm.length());
                rest= textToDisplay.substring(searchTerm.length(), textToDisplay.length());
                textToDisplay = "<html><font color=red><span style='background:yellow;'>" + matchingPart + "</font></span>" + rest;

            }

         }

        setText(textToDisplay);

        if (isSelected) {
            setBackground(Color.ORANGE);
        } else {
            setBackground(Color.WHITE);
        }

        return this;
    }
}
