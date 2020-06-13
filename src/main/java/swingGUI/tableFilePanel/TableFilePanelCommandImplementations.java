package swingGUI.tableFilePanel;

import control.Constants;
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

public class TableFilePanelCommandImplementations  {
    private static Logger logger = LogManager.getLogger(TableFilePanelCommandImplementations.class);
    private GUI guiInstance;

    private TableFilePanel tableFilePanel;
    private JTable tableFilePanelTable;

    private FolderContent folderContent;
    private String searchTerm;

    public TableFilePanelCommandImplementations(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.tableFilePanel = guiInstance.getTableFilePanel();
        this.tableFilePanelTable = guiInstance.getTableFilePanel().getTableFilePanelTable();
        this.folderContent= guiInstance.getTableFilePanel().getFolderContent();
        System.out.println(tableFilePanelTable.getSelectedRow());
    }

    public void handleCommand(String command) {
        guiInstance.getKeyInfoPanel().displayCommand(command);
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed keys");

        if (guiInstance.getAndrasCommanderInstance().getMode().equals(Constants.SEARCH_MODE)) {
            searchTerm = command;
            // This is for redrawing the panel and highlight the new search results
//            guiInstance.getFilePanel().drawFilePanel(guiInstance.getFilePanel().getHighlightedListItemIndex());
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
//                goUpToParentFolder();
                break;
            case "go back":
//                goBackInHistory();
                break;
            case "refresh panel":
//                refreshView();
                break;
            case "enter search mode":
//                enterSearchMode();
                break;
            case "execute search":
//                executeSearch();
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

    public void moveCursor(String direction) {
        int nextRow=0;
        switch (direction) {
            case "down":
                nextRow = tableFilePanelTable.getSelectedRow() + 1 >= tableFilePanelTable.getRowCount() ? 0 : tableFilePanelTable.getSelectedRow() + 1;
                break;
            case "up":
                nextRow = tableFilePanelTable.getSelectedRow() - 1 <0  ? tableFilePanelTable.getRowCount()-1 : tableFilePanelTable.getSelectedRow() - 1;
                break;
            case "top":
                nextRow=0;
                break;
            case "bottom":
                nextRow=tableFilePanelTable.getRowCount()-1;
                break;
        }
        tableFilePanelTable.setRowSelectionInterval(nextRow, nextRow);
        tableFilePanel.scrollToHighlightedItem();

        // Set the highlighted file

        // Display the new highlighted file
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

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

    public void changeFolder(String folderPath) {
        String originalPath = folderContent.getFolderPath();
        guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(originalPath); // save history

        folderContent.setFolderPath(folderPath);
        folderContent.loadFiles(folderPath);

        guiInstance.getTableFilePanel().drawTableFilePanel(0);

        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }
}
