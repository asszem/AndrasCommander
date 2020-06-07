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
    private int searchMatchedItemIndexesPointer; // points to the current searchMathcedItemIndex. Used when navigating with n/N
    private String searchType;

    //GUI Fields
    private GUI guiInstance;
    private JPanel fileListPanel;
    private JScrollPane fileListScrollPane;
    private JList fileListDisplayedItems;

    //Constructor
    public FilePanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initFilePanel(String panelTitle) {
        PropertyReader propertyReader = guiInstance.getAndrasCommanderInstance().getPropertyReader();
        String startfolder;
        startfolder = propertyReader.readProperty("STARTFOLDER");
        if (propertyReader.readProperty("START_IN").equalsIgnoreCase("Last folder")) {
            startfolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        }

        folderContent = new FolderContent(startfolder);
        fileListPanel = new JPanel();
        searchMathcedItemIndexes = new ArrayList<>();
        commandImplementations = new CommandImplementations(guiInstance);
        searchType = Constants.SEARCH_MODE_STARTSWITH;

        drawFilePanel(0);

        return fileListPanel;
    }

    public void drawFilePanel(int highlightedIndex) {
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
        fileListDisplayedItems.ensureIndexIsVisible(fileListDisplayedItems.getSelectedIndex());
    }

    private int indexOfJlist; //to be able to determine the jlist indexOfJlist

    private JList populateJList() {
        ArrayList<String> jListItemListStrings = new ArrayList<>();
        String toDisableJListJumpToTypedCharInStringLists = "\u0000";
        searchMathcedItemIndexes.clear(); // remove every search indexes from previous draws

        // First add the parent folder dots to the list
        jListItemListStrings.add("..");

        // Create the String list that will be displayed
        indexOfJlist = 1; // This should start from 1 because at 0 indexOfJlist, the parent folder is
        folderContent.sortFileItemsByName().forEach(fileItem -> {
            String displayedItem;

            // Add [ and ] brackets to folders
            if (fileItem.getFile().isDirectory()) {
                displayedItem = toDisableJListJumpToTypedCharInStringLists + "[" + fileItem.getFile().getName() + "]";
            } else {
                displayedItem = toDisableJListJumpToTypedCharInStringLists + fileItem.getFile().getName();
            }

            // Record the indexOfJlist if the displayed file is matched against a search term (regardless whether in search mode or not)
            // TODO implement search match not only by filename, but any parameter of file
            if (searchType.equals(Constants.SEARCH_MODE_STARTSWITH)) {
                if (fileItem.getFile().getName().startsWith(getCommandImplementations().getSearchTerm())) {
                    searchMathcedItemIndexes.add(indexOfJlist);
                }
            } else if (searchType.equals(Constants.SEARCH_MODE_CONTAINS)) {
                if (fileItem.getFile().getName().contains(getCommandImplementations().getSearchTerm())) {
                    searchMathcedItemIndexes.add(indexOfJlist);
                }
            }

            // Handle search result highlighting
            if (displaySearchResultMatches) {

                // Display only if a file matches the current search term, but the search is not yet executed
                if (guiInstance.getAndrasCommanderInstance().getMode().equals(Constants.SEARCH_MODE)) {
                    String currentSearchTerm = getCommandImplementations().getSearchTerm();
                    if (displayedItem.contains(currentSearchTerm)) {
                        displayedItem = "| " + displayedItem;
                    }
                }

                // Display when the search executed is pressed
                if (fileItem.isSearchMatched()) {
                    displayedItem = "-->" + displayedItem;
                }
            }

            fileItem.setDisplayedTitle(displayedItem);
            jListItemListStrings.add(displayedItem);
            indexOfJlist++;
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
        return this.searchMathcedItemIndexes;
    }

    public FilePanel resetSearchMathcedItemIndexes() {
        this.searchMathcedItemIndexes.clear();
        return this;
    }
}

