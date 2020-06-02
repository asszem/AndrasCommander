package swingGUI.basicFilePanel;

import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;
import swingGUI.keyListener.RemapCursorNavigation;
import utility.PropertyReader;

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
    private JList fileListDisplayedItems;

    //Constructor
    public FilePanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initPanel(String panelTitle) {
//        logger.debug("--> Inside initPanel");
        PropertyReader propertyReader = guiInstance.getAndrasCommanderInstance().getPropertyReader();
        String startfolder;
        startfolder = propertyReader.readProperty("STARTFOLDER");
        if (propertyReader.readProperty("START_IN").equalsIgnoreCase("Last folder")) {
            logger.debug("Last folder from history used as start folder");
            startfolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        }

        folderPath = startfolder;
        folderContent = new FolderContent(folderPath);
        fileListPanel = new JPanel();

        updateFilePanelDisplay();

        commandImplementations = new CommandImplementations(guiInstance);
        return fileListPanel;
    }

    public void updateFilePanelDisplay() {
        // Remove previous content from the panel
        fileListPanel.removeAll();

        // Create a and populate new Jlist object
        fileListDisplayedItems = new JList();
        fileListDisplayedItems = populateFileJList();
        fileListDisplayedItems.addKeyListener(guiInstance.getKeyListener());
        fileListDisplayedItems.addListSelectionListener(guiInstance.getKeyListener()); // to handle cursor and HOME and END keys as well
        fileListDisplayedItems.setSelectedIndex(0);
        fileListDisplayedItems.setVisibleRowCount(20);

        // Set the initial highlighted file to the Parent Folder (so cursor is always at the .. on the top of the file list
        highlightedFile = folderContent.getParentFolder();

        // Remap the cursor keys for fileJlist
        RemapCursorNavigation.remapCursors(fileListDisplayedItems);

        // Create SCROLLPANE for JLIST
        fileListScrollPane = new JScrollPane(fileListDisplayedItems, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setBorder(BorderFactory.createTitledBorder(folderPath));
        Dimension scrollPaneSize = new Dimension(700, 100);
//        fileListScrollPane.setMinimumSize(scrollPaneSize);
        fileListScrollPane.setPreferredSize(scrollPaneSize);

        // Add SCROLLPANE to PANEL
        fileListPanel.add(fileListScrollPane);
    }

    private JList populateFileJList() {


        ArrayList<String> jListItemListStrings = new ArrayList<>();
        String toDisableJListJumpToTypedCharInStringLists = "\u0000";
//        String toDisableJListJumpToTypedCharInStringLists = "";

        // First add the parent folder dots to the list
        jListItemListStrings.add("..");
        // Create the String list
        folderContent.sortFileItemsByName().forEach(fileItem -> {
            String displayedItem;
            if (fileItem.getFile().isDirectory()) {
                // The first item in the list is always the parent folder if exists, or itself, if no parent
                displayedItem = toDisableJListJumpToTypedCharInStringLists + "[" + fileItem.getFile().getName() + "]";
            } else {
                displayedItem = toDisableJListJumpToTypedCharInStringLists + fileItem.getFile().getName();
            }
            jListItemListStrings.add(displayedItem);
        });

        // Order the list


//        jListItemListStrings.forEach(item -> System.out.println(item));
        JList result = new JList(jListItemListStrings.toArray());
//        logger.debug("folderContent size = " + folderContent.getFoldersFirstThenFiles(folderPath).size());
//        logger.debug("Jlist size = " + result.getModel().getSize());
        result.setSelectedIndex(0);
        return result;
    }

    //Getters and setters
    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public JList getFileListDisplayedItems() {
        if (fileListDisplayedItems == null) {
            updateFilePanelDisplay();
        }
        return fileListDisplayedItems;
    }

    public void setFileListDisplayedItems(JList fileListDisplayedItems) {
        this.fileListDisplayedItems = fileListDisplayedItems;
    }

    public File getHighlightedFile() {
        return highlightedFile;
    }

    public void setHighlightedFile(File highlightedFile) {
        this.highlightedFile = highlightedFile;
    }

    public int getHighlightedFileIndex() {
        return fileListDisplayedItems.getSelectedIndex();
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public FolderContent getFolderContent() {
        return folderContent;
    }

    public CommandImplementations getCommandImplementations() {
        return commandImplementations;
    }
}

