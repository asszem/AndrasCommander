package gui;

import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;

public class CommandImplementations {
    private static Logger logger = LogManager.getLogger(CommandImplementations.class);
    private GUI guiInstance;
    private FilePanel filePanel;

    public CommandImplementations(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.filePanel=guiInstance.getFilePanel();
    }

    public void moveCursor(String direction) {
//        logger.debug("move cursor order received = " + direction);
        int currentIndex = filePanel.getFileJList().getSelectedIndex();
        int maxIndex = filePanel.getFileJList().getModel().getSize() - 1;
//        logger.debug("current fileJlist getSelectedIndex = " + currentIndex);
//        logger.debug("maxindex - fileJList size -1 = " + maxIndex);
        switch (direction) {
            case "down":
                if (currentIndex == maxIndex) {
                    filePanel.getFileJList().setSelectedIndex(0);
                    currentIndex = 0;
                } else {
                    filePanel.getFileJList().setSelectedIndex(currentIndex + 1);
                }
                break;
            case "up":
                if (currentIndex == 0) {
                    filePanel.getFileJList().setSelectedIndex(maxIndex);
                    currentIndex = maxIndex;
                } else {
                    filePanel.getFileJList().setSelectedIndex(currentIndex - 1);
                }
                break;
            case "top":
                filePanel.getFileJList().setSelectedIndex(0);
                break;
            case "bottom":
                filePanel.getFileJList().setSelectedIndex(maxIndex);
                break;
        }
        // TODO This part is ugly. Refactor when possible
        guiInstance.getFilePanel().getFileJList().ensureIndexIsVisible(filePanel.getFileJList().getSelectedIndex());
        String folderPath = guiInstance.getFilePanel().getFolderPath();
        int newIndex = filePanel.getFileJList().getSelectedIndex();
        File newHighlightedFile = guiInstance.getFilePanel().getFolderContent().getFoldersFirstThenFiles(folderPath).get(newIndex);
        guiInstance.getFilePanel().setHighlightedFile(newHighlightedFile);
    }

    public void goUpToParentFolder() {
//        logger.debug("Go Up To parent folder command received" );
        File parentFolder = new File(guiInstance.getFilePanel().getFolderPath()).getParentFile();
        if (parentFolder!=null) {
            guiInstance.getFilePanel().setFolderPath(parentFolder.getAbsolutePath());
        }
    }

    public void goBackInHistory(){
        logger.debug("Go back in History command received" );
        String prevFolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        logger.debug("Last History Item = " + prevFolder);
        // Validation
        File prevFolderCheck = new File(prevFolder);
        if (prevFolderCheck.exists() && prevFolderCheck.isDirectory()){
//            guiInstance.getFilePanel().setFolderPath(prevFolder);
            guiInstance.getFilePanel().getFolderContent().loadFiles(prevFolder);
            guiInstance.getFilePanel().displayPanel();
            guiInstance.getFilePanel().getFileJList().grabFocus();
        } else{
           logger.debug("prev Folder is not a valid Directory");
        }
        // set the actual folder to the previous folder
    }

}
