package swingGUI.basicFilePanel;

import control.Constants;
import data.CommandsInterface;
import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandImplementations implements CommandsInterface {
    private static Logger logger = LogManager.getLogger(CommandImplementations.class);
    private GUI guiInstance;
    private FilePanel filePanel;
    private FolderContent folderContent;
    private String searchTerm;

    public CommandImplementations(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.filePanel = guiInstance.getFilePanel();
        this.folderContent = guiInstance.getFilePanel().getFolderContent();
        this.searchTerm = "";
    }

    // this is being called from the KeyBindingParser with the actual command sent as a String
    public void handleCommand(String command) {
        guiInstance.getKeyInfoPanel().displayCommand(command);
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed keys");

        if (guiInstance.getAndrasCommanderInstance().getMode().equals(Constants.SEARCH_MODE)) {
            searchTerm = command;
            // This is for redrawing the panel and highlight the new search results
            guiInstance.getFilePanel().drawFilePanel(guiInstance.getFilePanel().getHighlightedListItemIndex());
        }

        switch (command) {
            case "down":
                moveCursor("down");
                break;
            case "up":
                moveCursor("up");
                break;
            case "top":
                moveCursor("top");
                break;
            case "bottom":
                moveCursor("bottom");
                break;
            case "go up":
                goUpToParentFolder();
                break;
            case "go back":
                goBackInHistory();
                break;
            case "refresh panel":
                refreshView();
                break;
            case "enter search mode":
                enterSearchMode();
                break;
            case "execute search":
                executeSearch();
                break;
            case "next search result":
                setNextSearchResultHighlighted("next");
                break;
            case "prev search result":
                setNextSearchResultHighlighted("prev");
                break;
            case "set no highlight search results":
                setNoHighlightSearchResults();
                break;
            case "ESC":
                guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed Keys List");
                guiInstance.getKeyInfoPanel().displayAllPressedKeys("Pressed Keys list cleared.");
                break;
            case "open":
                openHighlighted();
                break;
            case "pageDown":
                //TODO implement pageDown move action
                break;
            case "pageUp":
                //todo implement pageUp move action
                break;
            case "Enter":
                //todo implement pageUp move action
                break;
            default: // if no match was found, then display the commands as the pressed keys list
                guiInstance.getKeyInfoPanel().displayAllPressedKeys(command);
                break;
        }
    }

    public void openHighlighted() {
//        File fileToBeExecuted = guiInstance.getFilePanel().getFolderContent().getHighlightedFile();
//        if (fileToBeExecuted.isDirectory()) {
//            guiInstance.getKeyInfoPanel().displayCommand("ENTER to go to Folder " + fileToBeExecuted.getName());
//            changeFolder(fileToBeExecuted.getAbsolutePath());
//        } else {
//            guiInstance.getKeyInfoPanel().displayCommand("ENTER to execute File " + fileToBeExecuted.getName());
//            logger.debug("Executing file = " + fileToBeExecuted.getAbsolutePath());
//            Desktop desktop = Desktop.getDesktop();
//            try {
//                desktop.open(fileToBeExecuted);
//            } catch (IOException e) {
//                logger.debug("ERROR in File Execution");
//                e.printStackTrace();
//            }
//        }
    }

    public void moveCursor(String direction) {
        int currentIndex = filePanel.getFileListDisplayedItems().getSelectedIndex();
        int maxIndex = filePanel.getFileListDisplayedItems().getModel().getSize() - 1;
        switch (direction) {
            case "down":
                if (currentIndex == maxIndex) {
                    filePanel.getFileListDisplayedItems().setSelectedIndex(0);
                    currentIndex = 0;
                } else {
                    filePanel.getFileListDisplayedItems().setSelectedIndex(currentIndex + 1);
                }
                break;
            case "up":
                if (currentIndex == 0) {
                    filePanel.getFileListDisplayedItems().setSelectedIndex(maxIndex);
                    currentIndex = maxIndex;
                } else {
                    filePanel.getFileListDisplayedItems().setSelectedIndex(currentIndex - 1);
                }
                break;
            case "top":
                filePanel.getFileListDisplayedItems().setSelectedIndex(0);
                break;
            case "bottom":
                filePanel.getFileListDisplayedItems().setSelectedIndex(maxIndex);
                break;
        }
        // Set the newly highlighted item visible in scrollpane
        int newIndex = filePanel.getFileListDisplayedItems().getSelectedIndex();
        guiInstance.getFilePanel().getFileListDisplayedItems().ensureIndexIsVisible(newIndex);

        // Set the highlighted file
        String highlightedItemTitle = filePanel.getFileListDisplayedItems().getModel().getElementAt(newIndex).toString();
//        folderContent.setHighlightedFileByDisplayedTitle(highlightedItemTitle);

        // Display the new highlighted file
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }


    public void changeFolder(String folderPath) {
        String originalPath = folderContent.getFolderPath();
        guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(originalPath); // save history

        folderContent.setFolderPath(folderPath);
        folderContent.loadFiles(folderPath);

        guiInstance.getFilePanel().drawFilePanel(0);
        guiInstance.getFilePanel().getFileListDisplayedItems().grabFocus();

        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

    public void goUpToParentFolder() {
//        File parentFolder = new File(folderContent.getParentFolder().getAbsolutePath());
//        if (parentFolder != null) {
//            changeFolder(parentFolder.getAbsolutePath());
//        }
    }

    public void goBackInHistory() {
        logger.debug("Go back in History command received");
        String prevFolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        logger.debug("Last History Item = " + prevFolder);
        // Validation
        File prevFolderCheck = new File(prevFolder);
        if (prevFolderCheck.exists() && prevFolderCheck.isDirectory()) {
            changeFolder(prevFolder);
        } else {
            logger.debug("prev Folder is not a valid Directory");
        }
    }

    public void refreshView() {
        changeFolder(folderContent.getFolderPath());
    }

//    @Override
    public void highlightSearchResult(ArrayList matchedIndexes) {

    }

    public void pressedESC() {

    }

    public void displayCommand(String command) {
        guiInstance.getKeyInfoPanel().displayCommand(command);
    }

    public void enterSearchMode() {
        searchTerm = ""; // To remove any previous search terms
        guiInstance.getFilePanel().setDisplaySearchResultMatches(true);
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Search Term");
        guiInstance.getKeyInfoPanel().displayAllPressedKeys("<type search term>");
    }

    public void executeSearch() {

        // Reset previous search results, update every FileItem object that was matched true
//        guiInstance.getFilePanel().resetSearchMathcedItemIndexes();
        guiInstance.getFilePanel().getFolderContent().setEveryFileItemSearchMatchedToFalse();

        //Execute search - search term must be set before this is called
        guiInstance.getFilePanel().getFolderContent().executeSearch(searchTerm.toString());

        // Handle if there was no result
        boolean noFileItemsMatched = guiInstance.getFilePanel().getFolderContent().getSearchResults().size() == 0;
        boolean noJlistItemHighlighted = guiInstance.getFilePanel().getSearchMathcedItemIndexes().size() == 0;
        if (noFileItemsMatched || noJlistItemHighlighted) {
            logger.debug("No search result was found, returning from executeSearch()");
            return;
        }

        // Turn on search result display mode in FilePanel
        guiInstance.getFilePanel().setDisplaySearchResultMatches(true);

        int currentHighlightedIndex = guiInstance.getFilePanel().getSearchMathcedItemIndexes().get(0);
        guiInstance.getFilePanel().setSearchMatchedItemIndexesPointer(0);
        guiInstance.getFilePanel().drawFilePanel(currentHighlightedIndex); // Set the current highlighted index

        // Update KeyInfoPanel content
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed Keys list");
        guiInstance.getKeyInfoPanel().displayAllPressedKeys("<empty>");
        guiInstance.getKeyInfoPanel().displayCommand("Search executed for term " + searchTerm);
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

    @Override
    public void exitSearchMode() {

    }


    public void setNextSearchResultHighlighted(String direction) {
        int searchResultSize = guiInstance.getFilePanel().getSearchMathcedItemIndexes().size();
        int currentPointer = guiInstance.getFilePanel().getSearchMatchedItemIndexesPointer();
        if (searchResultSize == 0) {
            guiInstance.getKeyInfoPanel().displayCommand("No result for " + searchTerm);
            return;
        }

        if (direction.equals("next")) {
            if (currentPointer + 1 >= searchResultSize) {
                guiInstance.getFilePanel().setSearchMatchedItemIndexesPointer(0); // Points to the FIRST index
            } else {
                guiInstance.getFilePanel().setSearchMatchedItemIndexesPointer(currentPointer + 1); // Points to the NEXT index
            }
        } else {
            if (currentPointer - 1 < 0) {
                guiInstance.getFilePanel().setSearchMatchedItemIndexesPointer(searchResultSize - 1); // Points to the LAST index
            } else {
                guiInstance.getFilePanel().setSearchMatchedItemIndexesPointer(currentPointer - 1); // Points to the PREV index
            }
        }

        int currentHighlightedIndex = guiInstance.getFilePanel().getSearchMathcedItemIndexes().get(guiInstance.getFilePanel().getSearchMatchedItemIndexesPointer());
        guiInstance.getFilePanel().drawFilePanel(currentHighlightedIndex);

        String displayedTitle = guiInstance.getFilePanel().getFileListDisplayedItems().getModel().getElementAt(currentHighlightedIndex).toString();
//        guiInstance.getFilePanel().getFolderContent().setHighlightedFileByDisplayedTitle(displayedTitle);
        guiInstance.getKeyInfoPanel().displayCommand(direction + " search result for " + searchTerm);
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

    public void setNoHighlightSearchResults(){
        guiInstance.getKeyInfoPanel().displayCommand("Set search result highlight OFF");
        guiInstance.getFilePanel().setDisplaySearchResultMatches(false);
        guiInstance.getFilePanel().drawFilePanel(guiInstance.getFilePanel().getHighlightedListItemIndex());
    }

    @Override
    public void setHighlightSearchResults(boolean highlightSearchResults) {

    }

    public String getSearchTerm() {
        return this.searchTerm;
    }
}
