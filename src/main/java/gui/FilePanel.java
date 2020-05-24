package gui;

import data.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

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

        // Create JPANEL
        fileListPanel = new JPanel();
//        fileListPanel.setLayout(new BoxLayout(fileListPanel, BoxLayout.Y_AXIS));

        // Create JLIST
        fileJList = new JList();
        fileJList.setListData(fileList.getFoldersFirstThenFiles(folderPath));
        fileJList.addKeyListener(guiInstance.getKeyListener());
        fileJList.addListSelectionListener(guiInstance.getKeyListener()); // to handle cursor and HOME and END keys as well
        fileJList.setSelectedIndex(0);
        fileJList.setVisibleRowCount(20);

        // Set the initial highlighted file
        highlightedFileIndex = fileJList.getSelectedIndex();
        highlightedFile = fileList.getFoldersFirstThenFiles(folderPath).get(highlightedFileIndex);

        // Remap the cursor keys for fileJlist
        RemapCursorNavigation.remapCursors(fileJList);

        // Create SCROLLPANE for JLIST
        fileListScrollPane = new JScrollPane(fileJList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setBorder(BorderFactory.createTitledBorder(startfolder));
        Dimension scrollPaneSize = new Dimension(700, 100);
        fileListScrollPane.setMinimumSize(scrollPaneSize);

        // Add SCROLLPANE to PANEL
        fileListPanel.add(fileListScrollPane);

        return fileListPanel;
    }


    private JList getFileListWithFoldersFirst() {
        JList result = new JList();
        result.setListData(fileList.getFoldersOnly(folderPath));

        highlightedFileIndex = 0;
        return result;
    }

    public void moveCursor(String direction) {
//        logger.debug("move cursor order received = " + direction);
        int currentIndex = fileJList.getSelectedIndex();
        int maxIndex = fileList.getFilesAndFolders(folderPath).size() - 1;
        switch (direction) {
            case "down":
                if (currentIndex == maxIndex) {
                    fileJList.setSelectedIndex(0);
                    currentIndex = 0;
                } else {
                    fileJList.setSelectedIndex(currentIndex + 1);
                }
                highlightedFileIndex = fileJList.getSelectedIndex();
                break;
            case "up":
                if (currentIndex == 0) {
                    fileJList.setSelectedIndex(maxIndex);
                    currentIndex = maxIndex;
                } else {
                    fileJList.setSelectedIndex(currentIndex - 1);
                }
                highlightedFileIndex = fileJList.getSelectedIndex();
                break;
            case "top":
                fileJList.setSelectedIndex(0);
                highlightedFileIndex = 0;
                break;
            case "bottom":
                fileJList.setSelectedIndex(maxIndex);
                highlightedFileIndex = fileList.getFoldersFirstThenFiles(folderPath).size() - 1;
                break;
        }
        fileJList.ensureIndexIsVisible(fileJList.getSelectedIndex());
        highlightedFile = fileList.getFoldersFirstThenFiles(folderPath).get(highlightedFileIndex);
//        logger.debug("highlighted File is " + highlightedFile.getAbsolutePath());
    }

    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public File getHighlightedFile() {
        return highlightedFile;
    }
}

