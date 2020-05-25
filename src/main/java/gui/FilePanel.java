package gui;

import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FilePanel {
    private static Logger logger = LogManager.getLogger(FilePanel.class);

    //DATA fields
    private FolderContent folderContent;
    private File highlightedFile;
    private String folderPath;
    private CommandImplementations commandImplementations;

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
        folderContent = new FolderContent();
        fileListPanel = new JPanel();

        displayPanel();

        commandImplementations = new CommandImplementations(guiInstance);
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
        highlightedFile = folderContent.getFoldersFirstThenFiles(folderPath).get(fileJList.getSelectedIndex());

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

        counter = 0;
        folderContent.getFoldersFirstThenFiles(folderPath).forEach(file -> {
            String displayedItem;
            if (file.isDirectory()) {
                // The first item in the list is always the parent folder if exists, or itself, if no parent
                if (counter == 0) {
                    displayedItem = "..";
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
//        logger.debug("folderContent size = " + folderContent.getFoldersFirstThenFiles(folderPath).size());
//        logger.debug("Jlist size = " + result.getModel().getSize());
        result.setSelectedIndex(0);
        return result;
    }


    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public File getHighlightedFile() {
        return highlightedFile;
    }
    public void setHighlightedFile(File highlightedFile){
        this.highlightedFile=highlightedFile;
    }

    public int getHighlightedFileIndex() {
        return fileJList.getSelectedIndex();
    }

    public void setFolderPath(String folderPath) {
        // TODO Save current folderpath to History file
        this.folderPath = folderPath;
        folderContent.loadFiles(folderPath); // to make sure the folderContent is repopulated
        displayPanel();
        fileJList.grabFocus();
    }

    public String getFolderPath() {
        return folderPath;
    }

    public JList getFileJList() {
        if (fileJList == null) {
            displayPanel();
        }
        return fileJList;
    }
    public void setFileJList(JList fileJList){
        this.fileJList=fileJList;
    }

    public FolderContent getFolderContent() {
        return folderContent;
    }

    public CommandImplementations getCommandImplementations() {
        return commandImplementations;
    }
}

