package swingGUI.basicFilePanel;

import control.Constants;
import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;
import swingGUI.keyListener.RemapCursorNavigation;
import utility.PropertyReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FilePanel {
    private static Logger logger = LogManager.getLogger(FilePanel.class);

    //DATA fields
    private FolderContent folderContent;
    private CommandImplementations commandImplementations;
    private boolean displaySearchResultMatches;
    private ArrayList<Integer> searchMathcedItemIndexes;

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
            startfolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        }

        folderContent = new FolderContent(startfolder);
        fileListPanel = new JPanel();

        drawFilePanel(0);

        commandImplementations = new CommandImplementations(guiInstance);
        return fileListPanel;
    }

    public void drawFilePanel(int highlightedIndex) {
        System.out.println("drawfilepanel clalled");
        // Remove previous content from the panel
        fileListPanel.removeAll();

        // Create a and populate new Jlist object
        fileListDisplayedItems = new JList();
        fileListDisplayedItems = populateJList();
        fileListDisplayedItems.addKeyListener(guiInstance.getKeyListener());
        fileListDisplayedItems.addListSelectionListener(guiInstance.getKeyListener()); // to handle cursor and HOME and END keys as well
        fileListDisplayedItems.setSelectedIndex(highlightedIndex);
        fileListDisplayedItems.setVisibleRowCount(20);

        // Remap the cursor keys for fileJlist
        RemapCursorNavigation.remapCursors(fileListDisplayedItems);

        // Create SCROLLPANE for JLIST
        fileListScrollPane = new JScrollPane(fileListDisplayedItems, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setBorder(BorderFactory.createTitledBorder(folderContent.getFolderPath()));
        Dimension scrollPaneSize = new Dimension(700, 300);
        fileListScrollPane.setPreferredSize(scrollPaneSize);

        // Add SCROLLPANE to PANEL
        fileListPanel.add(fileListScrollPane);

        // To make sure focus is on this item when it was redrawn
        fileListDisplayedItems.grabFocus();
    }

    private JList populateJList() {
        ArrayList<String> jListItemListStrings = new ArrayList<>();
        String toDisableJListJumpToTypedCharInStringLists = "\u0000";

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

            if (guiInstance.getAndrasCommanderInstance().getMode().equals(Constants.SEARCH_MODE)){
                String currentSearchTerm = getCommandImplementations().getSearchTerm();
                System.out.println("current serach term " + currentSearchTerm);
                if (displayedItem.contains(currentSearchTerm)){
                    displayedItem="| " + displayedItem;
                }
            }
            // Handle search result highlighting
            if (displaySearchResultMatches) {
                // This is to display files selected for search


                // This is to display files that are marked as search matc
                if (fileItem.getSearchMatched()) {
                    displayedItem = "-->" + displayedItem;
                    System.out.println("Adding matched item ");
                    if (searchMathcedItemIndexes == null) {
                        searchMathcedItemIndexes = new ArrayList<>();
                    }
                }
            }

            fileItem.setDisplayedTitle(displayedItem);
            jListItemListStrings.add(displayedItem);
        });

        JList result = new JList(jListItemListStrings.toArray());
        return result;
    }

    //Getters and setters
    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public JList getFileListDisplayedItems() {
        if (fileListDisplayedItems == null) {
            drawFilePanel(0);
        }
        return fileListDisplayedItems;
    }

    public void setFileListDisplayedItems(JList fileListDisplayedItems) {
        this.fileListDisplayedItems = fileListDisplayedItems;
    }

    public int getHighlightedFileIndex() {
        return fileListDisplayedItems.getSelectedIndex();
    }

    public FilePanel setHighlightedFileIndex(int index) {
        this.fileListDisplayedItems.setSelectedIndex(index);
        return this;
    }

    public FolderContent getFolderContent() {
        return folderContent;
    }

    public CommandImplementations getCommandImplementations() {
        return commandImplementations;
    }

    public FilePanel setDisplaySearchResultMatches(boolean displaySearchResultMatches) {
        this.displaySearchResultMatches = displaySearchResultMatches;
        return this;
    }

    public boolean getDisplaySearchResultMatches() {
        return this.displaySearchResultMatches;
    }

    public ArrayList<Integer> getSearchMathcedItemIndexes() {
        System.out.println("inside getSearchMatchedItemIndexes");
        return this.searchMathcedItemIndexes;
    }

    public FilePanel resetSearchMathcedItemIndexes() {
        this.searchMathcedItemIndexes.clear();
        return this;
    }
}

