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
    private boolean inSearchMode; //if true, every keypress should be added to searchTerm string, instead of parsing until Enter or ESC
    private StringBuilder searchTerm;

    public KeyBindingParser(GUI guiInstance) {
        this.guiInstance = guiInstance;
        pressedKeysList = new ArrayList<>();
        wasShiftPressed = false;
        inSearchMode = false;
    }

    public String parseKeys() {

        // Check if special key was pressed and if yes, break execution and return result accordingly
        String specialKeyCheckResult = checkSpecialKeys();
        logger.debug("special key result = " + specialKeyCheckResult);
        if (specialKeyCheckResult == null || !specialKeyCheckResult.equalsIgnoreCase("No Special Key Pressed. Continue with parsing")) {
            return specialKeyCheckResult;
        }

        // Toggle uppercase if shift mode is on
        if (wasShiftPressed) {
            lastKeyPressed = lastKeyPressed.toUpperCase();
            wasShiftPressed = false;
        }

        pressedKeysList.add(lastKeyPressed);

        String matchedCommand = matchKeyToCommand();

        // If a match was found, the pressedKeyList must be cleared
        if (matchedCommand != null) {
            pressedKeysList.clear();
        }

        guiInstance.getKeyInfoPanel().displayCommand(matchedCommand);
        return matchedCommand;
    }

    private String checkSpecialKeys() {
        String returnInstruction = "No Special Key Pressed. Continue with parsing";
        // Check for special keystrokes first
        switch (lastKeyPressed) {
            case "<ESC>":
//                logger.debug("ESC key press passed");
                returnInstruction = "ESC";
                pressedKeysList.clear();
                searchTerm = null;
                inSearchMode = false;
                guiInstance.getKeyInfoPanel().displayCommand(returnInstruction);
                return returnInstruction;
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
                break;
        }
        return returnInstruction; //if this is reached, return instruction was not changed
    }


    private String matchKeyToCommand() {
        String matchedCommand = null;
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
            case "<SPACE>": // Enter search mode only when space is pressed in an empty pressedKeyList
                matchedCommand = "searchMode";
                break;
            case ":q<ENTER>":
            case "quit":
                // Saving the last folder to history
                guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(guiInstance.getFilePanel().getFolderPath());
                exit(0);
                break;
        }
        return matchedCommand;
    }

    public ArrayList<String> getPressedKeysList() {
        return pressedKeysList;
    }

    public KeyBindingParser setLastPressedKey(String key) {
        lastKeyPressed = key;
        return this;
    }
}
