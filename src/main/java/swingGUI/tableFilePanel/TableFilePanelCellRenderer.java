package swingGUI.tableFilePanel;

import data.FileItem;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;

public class TableFilePanelCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    //    private GUI guiInstance;
    private String searchTerm = "";
    private Font font = getFont();
    private String textToDisplay;
    private String originalText;
    private boolean isSearchHighlightEnabled;
    private String colorForegroundSearchMatch="red";
    private String colorBackgroundSearchMatch="yellow";
    private Color folderForeground=Color.BLUE;
    private Color folderBackground=Color.BLACK;
    private Color fileForeground=Color.BLACK;
    private Color fileBackground=Color.WHITE;

    private String setTextColor(String text){
        String result="<html><font color="+colorForegroundSearchMatch+"><span style='background:"+colorBackgroundSearchMatch+";'>" + text + "</font></span>";
        return result;
    }


    public TableFilePanelCellRenderer() {
        setOpaque(true);
        isSearchHighlightEnabled = false;
    }

    public void setSearchHighlightEnabled(boolean searchHighlightEnabled) {
        isSearchHighlightEnabled = searchHighlightEnabled;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {


        setIcon(null);

        textToDisplay = value.toString(); // This will be formatted

        if (table.getModel().getValueAt(row, col) instanceof FileItem) {
            FileItem fileItem = (FileItem) table.getModel().getValueAt(row, col);
            originalText = fileItem.getFile().getName();
            if (fileItem.getFile().isDirectory()) {
                textToDisplay = "[" + fileItem.getFile().getName() + "]";
                setForeground(folderForeground);
                font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD));
            } else {
                textToDisplay = fileItem.getFile().getName();
                setForeground(fileForeground);
                font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD));
            }
            // TODO Find a solution that displays thumbnails correctly under Linux
            Icon icon = FileSystemView.getFileSystemView().getSystemIcon(fileItem.getFile());

            setIcon(icon);
            setFont(font);
        } else {
            originalText = "";
        }


        if (row == 0) {
            if (col == 0) {
                textToDisplay = "..";
            } else {
                textToDisplay = "";
            }
        }

        if (isSearchHighlightEnabled) {
            if (!searchTerm.isEmpty() && !originalText.isEmpty() && originalText.startsWith(searchTerm)) {
                String matchingPart;
                String rest;
                // This is to handle folder names in brackets
                if (textToDisplay.startsWith("[")) {
                    matchingPart = textToDisplay.substring(1, searchTerm.length());
                    rest = textToDisplay.substring(searchTerm.length(), textToDisplay.length());
                    textToDisplay =setTextColor("["+matchingPart)+rest;
                } else {
                    matchingPart = textToDisplay.substring(0, searchTerm.length());
                    rest = textToDisplay.substring(searchTerm.length(), textToDisplay.length());
                    textToDisplay =setTextColor(matchingPart)+rest;
                }

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
