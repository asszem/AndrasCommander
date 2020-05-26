package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;


// This class must be independent of anything GUI related (no Swing/AWT stuff)
public class KeyBindingParser {
    private static Logger logger = LogManager.getLogger(KeyBindingParser.class);
    private GUI guiInstance;
    private ArrayList<String> pressedKeysList;
    private String lastKeyPressed;
    private boolean wasShiftPressed;

    public KeyBindingParser(GUI guiInstance) {
        this.guiInstance = guiInstance;
        pressedKeysList = new ArrayList<>();
        wasShiftPressed = false;
    }

    public KeyBindingParser setLastPressedKey(String key) {
        lastKeyPressed = key;
        return this;
    }

    public String parseKeys() {
        String matchedCommand = null;

        // Check for special keystrokes first
        switch (lastKeyPressed) {
            case "<ESC>":
//                logger.debug("ESC key press passed");
                matchedCommand = "ESC";
                pressedKeysList.clear();
                guiInstance.getKeyInfoPanel().displayCommand(matchedCommand);
                return matchedCommand;
            case "<SHIFT>":
                guiInstance.getKeyInfoPanel().displayCommand("SHIFT pressed");
                wasShiftPressed = true;
                return null;
            case "<ENTER>":
                // If ENTER after empty commands - open file OR go to Directory
                // If ENTER after commands - execute bindings with an <ENTER> key
                if (pressedKeysList.isEmpty()) {
                    File fileToBeExecuted = guiInstance.getFilePanel().getHighlightedFile();
                    if (fileToBeExecuted.isDirectory()) {
                        guiInstance.getKeyInfoPanel().displayCommand("ENTER to go to Folder " + fileToBeExecuted.getName());
                        guiInstance.getFilePanel().setFolderPath(fileToBeExecuted.getAbsolutePath());
                        return null;
                    } else {
                        guiInstance.getKeyInfoPanel().displayCommand("ENTER to execute File " + fileToBeExecuted.getName());
                        logger.debug("Executing file = " + fileToBeExecuted.getAbsolutePath());
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.open(fileToBeExecuted);
                        } catch (IOException e) {
                            logger.debug("ERROR in File Execution");
                            e.printStackTrace();
                        } finally {
                            return null; //to make sure no command was interpreted and keep listening
                        }
                    }
                }
                // Execute the file under the cursor if no key(s) were pressed, otherwise add <ENTER> to the pressedKeysList
                break;
        }

        // This code can only be reached by the next keystroke after shift pressed
        if (wasShiftPressed) {
            lastKeyPressed = lastKeyPressed.toUpperCase();
            wasShiftPressed = false;
        }
        pressedKeysList.add(lastKeyPressed);
        String listString = String.join("", pressedKeysList); // Convert the ArrayList to a concatenated list of strings
        switch (listString) {
            case ":":
                logger.debug(": matched");
                break;
            case "j":
                matchedCommand = "down";
                break;
            case "k":
                matchedCommand = "up";
                break;
            case "G":
                matchedCommand = "bottom";
                break;
            case "gg":
                matchedCommand = "top";
                break;
            case "gu":
                matchedCommand = "go up";
                break;
            case "gb":
                matchedCommand = "go back";
                break;
            case ":e<ENTER>":
                matchedCommand = "refresh panel";
                break;
            case ":q<ENTER>":
            case "quit":
                exit(0);
                break;
        }
        // if match was found, erase the pressedKeysList
        if (matchedCommand != null) {
            pressedKeysList.clear();
        }
        guiInstance.getKeyInfoPanel().displayCommand(matchedCommand);
        return matchedCommand;
    }

    public ArrayList<String> getPressedKeysList() {
        return pressedKeysList;
    }
}
