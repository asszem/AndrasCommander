package swingGUI.tableFilePanel;

import control.Constants;
import data.CommandsInterface;
import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;
import swingGUI.basicFilePanel.CommandImplementations;
import swingGUI.basicFilePanel.FilePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
            //Handle if <SPACE> is sent as a command
            if (command.contains("space pressed in Search mode")){
                command.replace("space pressed in Search mode", " ");
            }
            searchTerm = command;
            // This is for redrawing the panel and highlight the new search results
//            guiInstance.getFilePanel().drawFilePanel(guiInstance.getFilePanel().getHighlightedListItemIndex());
            // redraw the tablefilepanel according to the updated search command
            guiInstance.getTableFilePanel().getTableFilePanelCellRenderer().setSearchTerm(searchTerm);
            guiInstance.getKeyInfoPanel().displaySearchTerm(searchTerm);
            guiInstance.getTableFilePanel().getTableFilePanelTable().repaint();
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
            case "exit search mode":
                exitSearchMode();
                break;
            case "next search result":
//                setNextSearchResultHighlighted("next");
                break;
            case "prev search result":
//                setNextSearchResultHighlighted("prev");
                break;
            case "set no highlight search results":
//                setNoHighlightSearchResults();
                break;
            case "ESC":
                guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed Keys List");
                guiInstance.getKeyInfoPanel().displayAllPressedKeys("Pressed Keys list cleared.");
                if (searchTerm!=null && !searchTerm.equals("")){
                    exitSearchMode();
                }
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
            changeFolder(fileToBeExecuted.getAbsolutePath());
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
    public void changeFolder(String folderPath) {
        String originalPath = guiInstance.getTableFilePanel().getFolderContent().getFolderPath();
        guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(originalPath); // save history

        guiInstance.getTableFilePanel().getFolderContent().setFolderPath(folderPath);
        guiInstance.getTableFilePanel().getFolderContent().loadFiles(folderPath);

        guiInstance.getTableFilePanel().drawTableFilePanel(0);
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

    @Override
    public void goUpToParentFolder() {
        File parentFolder = new File(guiInstance.getTableFilePanel().getFolderContent().getParentFolder().getFile().getAbsolutePath());
        if (parentFolder != null) {
            changeFolder(parentFolder.getAbsolutePath());
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
            changeFolder(prevFolder);
        } else {
            logger.debug("prev Folder is not a valid Directory");
        }
    }

    @Override
    public void refreshView() {
        changeFolder(guiInstance.getTableFilePanel().getFolderContent().getFolderPath());
    }

    @Override
    public void enterSearchMode() {
        searchTerm = ""; // To remove any previous search terms
        guiInstance.getTableFilePanel().setDisplaySearchResultMatches(true);
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Search Term");
        guiInstance.getKeyInfoPanel().displayAllPressedKeys("<type search term>");
    }

    @Override
    public void executeSearch() {
        guiInstance.getTableFilePanel().getFolderContent().executeSearch(searchTerm.toString());
    }

    @Override
    public void exitSearchMode() {
        searchTerm="";
        guiInstance.getTableFilePanel().setDisplaySearchResultMatches(false);
    }


}
