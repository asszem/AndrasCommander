package gui;

import control.AndrasCommander;
import data.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

public class FilePanel {
    private static Logger logger = LogManager.getLogger(FilePanel.class);

    //DATA fields
    private FileList fileList;
    private File highlightedFile;
    private int highlightedFileIndex;
    private String folderPath;

    //GUI Fields
    private GUI guiInstance;
    private JPanel fileListPanel;
    private JScrollPane fileListScrollPane;
    private JList fileJList;

    //Constructor
    public FilePanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initPanel(String panelTitle) {
//        logger.debug("--> Inside initPanel");
        String startfolder = guiInstance.getAndrasCommanderInstance().getPropertyReader().readProperty("STARTFOLDER");
        folderPath = startfolder;
        fileList = new FileList();
        highlightedFile = fileList.getFilesAndFolders(startfolder).get(0);
        highlightedFileIndex = 0;

        // 1. Create JPANEL
        fileListPanel = new JPanel();
//        fileListPanel.setLayout(new BoxLayout(fileListPanel, BoxLayout.Y_AXIS));

        // 2. Create JLIST
        fileJList = new JList(fileList.getFilesAndFolders(folderPath).toArray());
        fileJList.addKeyListener(guiInstance.getKeyListener());
        // 3. Create SCROLLPANE for JLIST
        fileListScrollPane = new JScrollPane(fileJList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setBorder(BorderFactory.createTitledBorder(startfolder));
        Dimension scrollPaneSize = new Dimension(700,300);
        fileListScrollPane.setMinimumSize(scrollPaneSize);

        // 4. Add SCROLLPANE to PANEL
        fileListPanel.add(fileListScrollPane);
        return fileListPanel;
    }


    private void displayFolderContent() {
//        logger.debug("--> inside DisplayFiles");
//        fileListPanel.removeAll();
        fileJList.removeAll();
        fileJList = new JList(fileList.getFilesAndFolders(folderPath).toArray());
    }

    public void moveCursor(String direction) {
        switch (direction) {
            case "down":
                if (highlightedFileIndex == fileList.getFilesAndFolders(folderPath).size() - 1) {
                    highlightedFileIndex = 0;
                } else {
                    highlightedFileIndex++;
                }
                break;
            case "up":
                if (highlightedFileIndex == 0) {
                    highlightedFileIndex = fileList.getFilesAndFolders(folderPath).size() - 1; // index starts at 0
                } else {
                    highlightedFileIndex--;
                }
                break;
            case "top":
                highlightedFileIndex = 0;
                break;
            case "bottom":
                highlightedFileIndex = fileList.getFilesAndFolders(folderPath).size() - 1;
                break;
        }
        highlightedFile = fileList.getFilesAndFolders(folderPath).get(highlightedFileIndex);
        displayFolderContent();
        guiInstance.getFrame().repaint();
        guiInstance.getFrame().setVisible(true);
    }

    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public File getHighlightedFile() {
        return highlightedFile;
    }
}

