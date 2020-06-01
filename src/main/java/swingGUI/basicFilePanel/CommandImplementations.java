package swingGUI.basicFilePanel;

import data.CommandsInterface;
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
    private String searchTerm;

    public CommandImplementations(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.filePanel = guiInstance.getFilePanel();
    }

    // this is being called from the KeyBindingParser with the actual command sent as a String
    public void handleCommand(String command) {
        guiInstance.getKeyInfoPanel().displayCommand(command);
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
        File fileToBeExecuted = filePanel.getHighlightedFile();
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

    public void moveCursor(String direction) {
//        logger.debug("move cursor order received = " + direction);
        int currentIndex = filePanel.getFileListDisplayedItems().getSelectedIndex();
        int maxIndex = filePanel.getFileListDisplayedItems().getModel().getSize() - 1;
//        logger.debug("current fileJlist getSelectedIndex = " + currentIndex);
//        logger.debug("maxindex - fileJList size -1 = " + maxIndex);
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
        // TODO This part is ugly. Refactor when possible - use ineheritance from FilePanel
        int newIndex = filePanel.getFileListDisplayedItems().getSelectedIndex();
        guiInstance.getFilePanel().getFileListDisplayedItems().ensureIndexIsVisible(newIndex);

        String newFileName = filePanel.getFileListDisplayedItems().getModel().getElementAt(newIndex).toString();
        System.out.println("new file name based on fileJList = " + newFileName);

        System.out.println("index of new highlighted file = " + newIndex);
        // Set the highlighted file in FolderContent and based on that in FilePanel
        guiInstance.getFilePanel().getFolderContent().setHighlightedFile(newIndex);
        File newHighlightedFile = guiInstance.getFilePanel().getFolderContent().getHighlightedFile().getFile();
        guiInstance.getFilePanel().setHighlightedFile(newHighlightedFile);
    }

    public void goUpToParentFolder() {
//        logger.debug("Go Up To parent folder command received" );
        File parentFolder = new File(guiInstance.getFilePanel().getFolderPath()).getParentFile();
        if (parentFolder != null) {
            changeFolder(parentFolder.getAbsolutePath());
        }
    }

    public void changeFolder(String folderPath) {
        String originalPath = guiInstance.getFilePanel().getFolderPath();
        guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(originalPath); // save history
        guiInstance.getFilePanel().setFolderPath(folderPath);
        guiInstance.getFilePanel().getFolderContent().loadFiles(folderPath);
        guiInstance.getFilePanel().updateFilePanelDisplay();
        guiInstance.getFilePanel().getFileListDisplayedItems().grabFocus();
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
        logger.debug("Refresh view ");
        guiInstance.getFilePanel().setFolderPath(guiInstance.getFilePanel().getFolderPath());
    }

    @Override
    public void highlightSearchResult(ArrayList matchedIndexes) {

    }

    public void pressedESC() {

    }

    public void displayCommand(String command) {
        guiInstance.getKeyInfoPanel().displayCommand(command);
    }

    public void enterSearchMode() {
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Search Term");
        guiInstance.getKeyInfoPanel().displayAllPressedKeys("<type search term>");
    }

    public void executeSearch() {
        guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed Keys list");
        guiInstance.getKeyInfoPanel().displayAllPressedKeys("<empty>");
        guiInstance.getKeyInfoPanel().displayCommand("Executing search");
        ArrayList<Integer> searchResults = guiInstance.getFilePanel().getFolderContent().getSearchResultsIndex(searchTerm);
        // TODO implement search term highlight
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
