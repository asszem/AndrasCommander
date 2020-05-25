package gui;

import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;

public class CommandImplementations {
    private static Logger logger = LogManager.getLogger(CommandImplementations.class);
    private GUI guiInstance;
    private JList fileJList;
    private File highlightedFile;
    private String folderPath;
    private FolderContent folderContent;

    public CommandImplementations(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.fileJList = guiInstance.getFilePanel().getFileJList();
        this.highlightedFile = guiInstance.getFilePanel().getHighlightedFile();
        this.folderPath = guiInstance.getFilePanel().getFolderPath();
        this.folderContent = guiInstance.getFilePanel().getFolderContent();
        logger.debug("highlighted file = " + highlightedFile);
        logger.debug("fileJlist index = " + fileJList.getSelectedIndex());
    }

    public void moveCursor(String direction) {
        logger.debug("move cursor order received = " + direction);
        int currentIndex = fileJList.getSelectedIndex();
        int maxIndex = fileJList.getModel().getSize() - 1;
        logger.debug("current fileJlist getSelectedIndex = " + currentIndex);
        logger.debug("maxindex - fileJList size -1 = " + maxIndex);
        switch (direction) {
            case "down":
                if (currentIndex == maxIndex) {
                    fileJList.setSelectedIndex(0);
                    currentIndex = 0;
                } else {
                    fileJList.setSelectedIndex(currentIndex + 1);
                }
                break;
            case "up":
                if (currentIndex == 0) {
                    fileJList.setSelectedIndex(maxIndex);
                    currentIndex = maxIndex;
                } else {
                    fileJList.setSelectedIndex(currentIndex - 1);
                }
                break;
            case "top":
                fileJList.setSelectedIndex(0);
                break;
            case "bottom":
                fileJList.setSelectedIndex(maxIndex);
                break;
        }
        guiInstance.getFilePanel().getFileJList().ensureIndexIsVisible(fileJList.getSelectedIndex());
        logger.debug("getSelectedIndex after move command = " + fileJList.getSelectedIndex());
        guiInstance.getFilePanel().setHighlightedFile( folderContent.getFoldersFirstThenFiles(folderPath).get(fileJList.getSelectedIndex()));
        logger.debug("Highlighted file is set to = " + highlightedFile.getName());
    }

    public void goUpToParentFolder() {
        logger.debug("Go Up To parent folder command received" );

    }


}
