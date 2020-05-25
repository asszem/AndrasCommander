package gui;

import data.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FilePanel {
    private static Logger logger = LogManager.getLogger(FilePanel.class);

    //DATA fields
    private FileList fileList;
    private File highlightedFile;
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
        fileListPanel = new JPanel();

        displayPanel();
        System.out.println("fileJListObject = " + fileJList.getClass());

        return fileListPanel;
    }

    private void displayPanel() {
        // Remove previous content from the panel
        fileListPanel.removeAll();

        // Create a and populate new Jlist object
        fileJList = new JList();
        fileJList = populateFileJList();
        fileJList.addKeyListener(guiInstance.getKeyListener());
        fileJList.addListSelectionListener(guiInstance.getKeyListener()); // to handle cursor and HOME and END keys as well
        fileJList.setSelectedIndex(0);
        fileJList.setVisibleRowCount(20);

        // Set the initial highlighted file
        highlightedFile = fileList.getFoldersFirstThenFiles(folderPath).get(fileJList.getSelectedIndex());

        // Remap the cursor keys for fileJlist
        RemapCursorNavigation.remapCursors(fileJList);

        // Create SCROLLPANE for JLIST
        fileListScrollPane = new JScrollPane(fileJList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setBorder(BorderFactory.createTitledBorder(folderPath));
        Dimension scrollPaneSize = new Dimension(700, 100);
        fileListScrollPane.setMinimumSize(scrollPaneSize);

        // Add SCROLLPANE to PANEL
        fileListPanel.add(fileListScrollPane);
    }

    int counter;
    private JList populateFileJList() {
        ArrayList<String> jListItemListStrings = new ArrayList<>();
        String toDisableJListJumpToTypedCharInStringLists = "\u0000";
//        String toDisableJListJumpToTypedCharInStringLists = "";

        counter=0;
        fileList.getFoldersFirstThenFiles(folderPath).forEach(file -> {
            String displayedItem;
            if (file.isDirectory()) {
                // The first item in the list is always the parent folder if exists, or itself, if no parent
                if (counter==0){
                    displayedItem="..";
                } else {
                    displayedItem = toDisableJListJumpToTypedCharInStringLists + "[" + file.getName() + "]";
                }
            } else {
                displayedItem = toDisableJListJumpToTypedCharInStringLists + file.getName();
            }
            jListItemListStrings.add(displayedItem);
            counter++;
        });
//        jListItemListStrings.forEach(item -> System.out.println(item));
        JList result = new JList(jListItemListStrings.toArray());
//        logger.debug("fileList size = " + fileList.getFoldersFirstThenFiles(folderPath).size());
//        logger.debug("Jlist size = " + result.getModel().getSize());
        result.setSelectedIndex(0);
        return result;
    }

    public void moveCursor(String direction) {
        logger.debug("move cursor order received = " + direction);
        int currentIndex = fileJList.getSelectedIndex();
        int maxIndex = fileJList.getModel().getSize() - 1;
//        logger.debug("current index = " + currentIndex);
//        logger.debug("maxindex = " + maxIndex);
        switch (direction) {
            case "down":
                if (currentIndex == maxIndex) {
                    fileJList.setSelectedIndex(0);
                    currentIndex = 0;
                } else {
                    fileJList.setSelectedIndex(currentIndex + 1);
                }
                break;
            case "up":
                if (currentIndex == 0) {
                    fileJList.setSelectedIndex(maxIndex);
                    currentIndex = maxIndex;
                } else {
                    fileJList.setSelectedIndex(currentIndex - 1);
                }
                break;
            case "top":
                fileJList.setSelectedIndex(0);
                break;
            case "bottom":
                fileJList.setSelectedIndex(maxIndex);
                break;
        }
        fileJList.ensureIndexIsVisible(fileJList.getSelectedIndex());
        highlightedFile = fileList.getFoldersFirstThenFiles(folderPath).get(fileJList.getSelectedIndex());
    }

    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public File getHighlightedFile() {
        return highlightedFile;
    }

    public int getHighlightedFileIndex() {
        return fileJList.getSelectedIndex();
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        fileList.loadFiles(folderPath); // to make sure the fileList is repopulated
        displayPanel();
        fileJList.grabFocus();
    }

    public String getFolderPath() {
        return folderPath;
    }
}

