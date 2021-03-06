package swingGUI.tableFilePanel;

import control.Constants;
import data.CommandsInterface;
import data.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class TableFilePanelCommandImplementations implements CommandsInterface {
    private static Logger logger = LogManager.getLogger(TableFilePanelCommandImplementations.class);
    private GUI guiInstance;

    private String searchTerm;

    public TableFilePanelCommandImplementations(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    @Override
    public void handleCommand(String command) {
        guiInstance.getKeyInfoPanel().displayCommand(command);
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed keys");

        if (guiInstance.getAndrasCommanderInstance().getMode().equals(Constants.SEARCH_MODE)) {
            searchTerm = command;
            // redraw the tablefilepanel according to the updated search command
            guiInstance.getTableFilePanel().getTableFilePanelCellRenderer().setSearchTerm(searchTerm);
            guiInstance.getKeyInfoPanel().displaySearchTerm(searchTerm);
            guiInstance.getTableFilePanel().getTableFilePanelTable().repaint();

            // To immediately jump to the first matching item, reset previous matches before
            guiInstance.getTableFilePanel().getFolderContent().setEveryFileItemSearchMatchedToFalse();
            executeSearch();
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
//                executeSearch();
                guiInstance.getAndrasCommanderInstance().setMode(Constants.NORMAL_MODE); // set here and not in execute search
                guiInstance.getTableFilePanel().getTableFilePanelTable().repaint();
                break;
            case "exit search mode":
                exitSearchMode();
                break;
            case "next search result":
                jumpToSearchResult("next");
                break;
            case "prev search result":
                jumpToSearchResult("prev");
                break;
            case "set no highlight search results":
                setHighlightSearchResults(false);
                break;
            case "set highlight search results":
                setHighlightSearchResults(true);
                break;
            case "ESC":
                guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed Keys List");
                guiInstance.getKeyInfoPanel().displayAllPressedKeys("Pressed Keys list cleared.");
                if (searchTerm != null && !searchTerm.equals("")) {
                    exitSearchMode();
                }
                break;
            case "open":
                openHighlighted();
                break;
            case "toggle sort order":
                toggleSortOrder();
                break;
            case "toggle sort by":
                toggleSortBy();
                break;
            case "toggle Key Bindings Panel":
                guiInstance.toggleKeyBindingsPanel();
                break;
            case "toggle ignore case":
                toggleIgnoreCase();
                break;
            case "pageDown":
                //TODO implement pageDown move action
                break;
            case "pageUp":
                //todo implement pageUp move action
                break;
            case "quit":
                // Saving the last folder to history
                guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(guiInstance.getTableFilePanel().getFolderContent().getFolderPath());
                exit(0);
                break;
            default: // if no match was found, then display the commands as the pressed keys list
                guiInstance.getKeyInfoPanel().displayAllPressedKeys(command);
                break;
        }
    }

    @Override
    public void moveCursor(String direction) {
        JTable table = guiInstance.getTableFilePanel().getTableFilePanelTable();
        int nextRow = 0;
        switch (direction) {
            case "down":
                nextRow = table.getSelectedRow() + 1 >= table.getRowCount() ? 0 : table.getSelectedRow() + 1;
                break;
            case "up":
                nextRow = table.getSelectedRow() - 1 < 0 ? table.getRowCount() - 1 : table.getSelectedRow() - 1;
                break;
            case "top":
                nextRow = 0;
                break;
            case "bottom":
                nextRow = table.getRowCount() - 1;
                break;
        }
        table.setRowSelectionInterval(nextRow, nextRow);
        guiInstance.getTableFilePanel().scrollToHighlightedItem();

        // Display the new highlighted file
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

    @Override
    public void openHighlighted() {
        File fileToBeExecuted = guiInstance.getTableFilePanel().getHighlightedFileItem().getFile();
        if (fileToBeExecuted.isDirectory()) {
            guiInstance.getKeyInfoPanel().displayCommand("ENTER to go to Folder " + fileToBeExecuted.getName());
            changeFolder(fileToBeExecuted.getAbsolutePath(), true);
        } else {
            guiInstance.getKeyInfoPanel().displayCommand("ENTER to execute File " + fileToBeExecuted.getName());
            logger.debug("Executing file = " + fileToBeExecuted.getAbsolutePath());
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(fileToBeExecuted);
            } catch (IOException e) {
                logger.debug("ERROR in File Execution");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeFolder(String folderPath, boolean writeOldFolderToHistoryFile) {
        if (writeOldFolderToHistoryFile) {
            String originalPath = guiInstance.getTableFilePanel().getFolderContent().getFolderPath();
            guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(originalPath); // save history
        }

        guiInstance.getTableFilePanel().getFolderContent().setFolderPath(folderPath);
        guiInstance.getTableFilePanel().getFolderContent().loadFiles(folderPath);

        guiInstance.getTableFilePanel().drawTableFilePanel(0);
        guiInstance.getKeyInfoPanel().displayHighlightedFile();

        guiInstance.getHistoryPanel().displayHistoryItems();
        exitSearchMode(); // so n/N will not work in new directory where the search was not executed
    }

    @Override
    public void goUpToParentFolder() {
        File parentFolder = new File(guiInstance.getTableFilePanel().getFolderContent().getParentFolder().getFile().getAbsolutePath());
        if (parentFolder != null) {
            changeFolder(parentFolder.getAbsolutePath(), true);
        }
    }

    @Override
    public void goBackInHistory() {
        logger.debug("Go back in History command received");
        String prevFolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        logger.debug("Last History Item = " + prevFolder);
        // Validation
        File prevFolderCheck = new File(prevFolder);
        if (prevFolderCheck.exists() && prevFolderCheck.isDirectory()) {
            changeFolder(prevFolder, false);
        } else {
            logger.debug("prev Folder is not a valid Directory");
        }
    }

    @Override
    public void refreshView() {
        changeFolder(guiInstance.getTableFilePanel().getFolderContent().getFolderPath(), false);
    }

    @Override
    public void enterSearchMode() {
        searchTerm = ""; // To remove any previous search terms
        guiInstance.getTableFilePanel().getFolderContent().setEveryFileItemSearchMatchedToFalse();
        guiInstance.getAndrasCommanderInstance().setMode(Constants.SEARCH_MODE);
        setHighlightSearchResults(true);
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Search Term");
        guiInstance.getKeyInfoPanel().displayAllPressedKeys("<type search term>");
    }

    private int searchResultIndexPointer;
    private ArrayList<Integer> searchResultIndexes;

    @Override
    public void executeSearch() {
        guiInstance.getTableFilePanel().getFolderContent().executeSearch(searchTerm.toString(), guiInstance.getTableFilePanel().getSearchHighlightIgnoreCase());

        // Prepare the results pointers
        searchResultIndexes = new ArrayList<>();
        searchResultIndexPointer = 0;

        // Get the total number of rows in table
        int rows = guiInstance.getTableFilePanel().getTableFilePanelTable().getModel().getRowCount();

        // Get the index of matched fileitems
        for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
            FileItem fileItem = (FileItem) guiInstance.getTableFilePanel().getTableFilePanelTable().getModel().getValueAt(currentRowIndex, 0);
            if (fileItem.isSearchMatched()) {
//                logger.debug("Matched file item index = " + currentRowIndex + " file = " + fileItem.getFile().getName());
                searchResultIndexes.add(currentRowIndex);
            }
        }
        // Set the highlighted row only when there is at least one match
        if (searchResultIndexes.size() > 0) {
            guiInstance.getTableFilePanel().setHighlightedRowIndex(searchResultIndexes.get(0));
        }
    }

    @Override
    public void exitSearchMode() {
        searchTerm = "";
        searchResultIndexPointer = -1;
        searchResultIndexes = null;
        guiInstance.getKeyInfoPanel().displaySearchTerm("<none>");
        guiInstance.getTableFilePanel().setDisplaySearchResultMatches(false);
        guiInstance.getTableFilePanel().getTableFilePanelCellRenderer().setSearchTerm(""); // to avoid :hl displaying previous search term
        guiInstance.getTableFilePanel().getTableFilePanelTable().repaint(); // so cursor color will change back
    }

    @Override
    public void jumpToSearchResult(String direction) {
        if (searchResultIndexes != null && searchResultIndexes.size() > 0) {
            if (direction.equals("next")) {
                if (searchResultIndexPointer + 1 >= searchResultIndexes.size()) {
                    searchResultIndexPointer = 0;   // Set FIRST item
                } else {
                    searchResultIndexPointer++;     // Set Next item
                }
            } else if (direction.equals("prev")) {
                if (searchResultIndexPointer - 1 < 0) {
                    searchResultIndexPointer = searchResultIndexes.size() - 1; // Set LAST item
                } else {
                    searchResultIndexPointer--; // Set Prev item
                }
            }
            guiInstance.getTableFilePanel().setHighlightedRowIndex(searchResultIndexes.get(searchResultIndexPointer));
        }
    }

    @Override
    public void setHighlightSearchResults(boolean highlightSearchResults) {
        guiInstance.getTableFilePanel().getTableFilePanelCellRenderer().setSearchHighlightEnabled(highlightSearchResults); // Set the cell renderer accordingly
        guiInstance.getTableFilePanel().getTableFilePanelTable().repaint();

        guiInstance.getTableFilePanel().setDisplaySearchResultMatches(highlightSearchResults);
        guiInstance.getKeyInfoPanel().displayCommand("Set search result highlight " + highlightSearchResults);
    }

    // Sort
    private void refreshTableAfterSort(){
        FileItem highlightedFileItem = guiInstance.getTableFilePanel().getHighlightedFileItem();           // to preserve highlighted file item after sort
        guiInstance.getTableFilePanel().getTableFilePanelModel().populateTable();                          // to repopulate table with new data
        guiInstance.getTableFilePanel().getTableFilePanelModel().fireTableStructureChanged();              // to update model
        guiInstance.getTableFilePanel().setColumnWidth(guiInstance.getTableFilePanel().getColumnWidths()); // set the column widths
        guiInstance.getTableFilePanel().assignSameCellRendererToEachColumn();                              // to assign the cell renderers to the updated model
        guiInstance.getTableFilePanel().setRowSelectionToFileItem(highlightedFileItem);                    // to set the preserved fileitem as highlighted
        guiInstance.getTableFilePanel().scrollToHighlightedItem();
    }
    @Override
    public void toggleSortOrder() {
        String newOrder;
        if (guiInstance.getTableFilePanel().getSortOrder().equals(Constants.SORT_ORDER_NORMAL)) {
            newOrder = Constants.SORT_ORDER_REVERSED;
        } else {
            newOrder = Constants.SORT_ORDER_NORMAL;
        }

        // This is how update if only the header is changed, but not the content of the table itself
//        tableFilePanelTable.getColumnModel().getColumn(0).setHeaderValue(sortOrder);
//        tableFilePanelTable.getTableHeader().resizeAndRepaint();

        guiInstance.getTableFilePanel().setSortOrder(newOrder);
        refreshTableAfterSort();
    }

    @Override
    public void toggleSortBy() {
        String oldSortBy = guiInstance.getTableFilePanel().getSortBy();
        String newSortBy="";
        switch (oldSortBy) {
            case Constants.SORT_BY_NAME:
                newSortBy = Constants.SORT_BY_SIZE;
                break;
            case Constants.SORT_BY_SIZE:
                newSortBy = Constants.SORT_BY_DATE;
                break;
            case Constants.SORT_BY_DATE:
                newSortBy = Constants.SORT_BY_NAME;
                break;
        }
        guiInstance.getTableFilePanel().setSortBy(newSortBy);
        refreshTableAfterSort();
    }

    public void toggleIgnoreCase(){
        boolean currentIsTrue=guiInstance.getTableFilePanel().getSearchHighlightIgnoreCase();
        if (currentIsTrue){
            guiInstance.getTableFilePanel().setSearchHighlightIgnoreCase(false);
            guiInstance.getTableFilePanel().getTableFilePanelCellRenderer().setSearchHighlightIgnoreCase(false);
            guiInstance.getTableFilePanel().getTableFilePanelTable().repaint();
            guiInstance.getKeyInfoPanel().setIgnoreCaseInSearchTermTitle();
        } else {
            guiInstance.getTableFilePanel().setSearchHighlightIgnoreCase(true);
            guiInstance.getTableFilePanel().getTableFilePanelCellRenderer().setSearchHighlightIgnoreCase(true);
            guiInstance.getTableFilePanel().getTableFilePanelTable().repaint();
            guiInstance.getKeyInfoPanel().setIgnoreCaseInSearchTermTitle();
        }
    }
}
