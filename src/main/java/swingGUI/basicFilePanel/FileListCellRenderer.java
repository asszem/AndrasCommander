package swingGUI.basicFilePanel;

import data.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class FileListCellRenderer extends JLabel implements ListCellRenderer<FileItem> {
    private static Logger logger = LogManager.getLogger(FileListCellRenderer.class);

    //Constructor
    public FileListCellRenderer() {
        setOpaque(true); // This is required to actually set the background color
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends FileItem> list, FileItem fileItem, int index, boolean isSelected, boolean cellHasFocus) {
        setText(fileItem.getFile().getName());

        if (isSelected) {
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
        } else {
            setBackground(null);
            setForeground(null);
        }
        return this;
    }
}
