package swingGUI.basicFilePanel;

import data.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class FileListCellRenderer extends JLabel implements ListCellRenderer<FileItem> {
    private static Logger logger = LogManager.getLogger(FileListCellRenderer.class);
    private double bytes = 0;
    private double kilobytes = (bytes / 1024);
    private double megabytes = (kilobytes / 1024);
    private double gigabytes = (megabytes / 1024);
    private double terabytes = (gigabytes / 1024);
    private double petabytes = (terabytes / 1024);
    private double exabytes = (petabytes / 1024);
    private double zettabytes = (exabytes / 1024);
    private double yottabytes = (zettabytes / 1024);

    //Constructor
    public FileListCellRenderer() {
        setOpaque(true); // This is required to actually set the background color
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends FileItem> list, FileItem fileItem, int index, boolean isSelected, boolean cellHasFocus) {
        bytes = fileItem.getFile().length();
        if (fileItem.getFile().isDirectory()) {
            setText("[" + fileItem.getFile().getName() + "]");
        } else {
            String fileName= fileItem.getFile().getName();
            String text= "<html><font color=red><span style='background:yellow;'>"+fileName.substring(0,4)+"</font></span> - <font color=navy><backgrouund-color=red>"+fileName.substring(4,7)+"</font></html>";
            setText(text);
        }

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
