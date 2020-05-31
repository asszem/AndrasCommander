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


    //The result of parse should be returned always to the CommandImplementations of the FilePanel from GUI
    //This will do not do any impleementation on keys, just send a string back
    public void parseKeys() {

        // Check if special key was pressed and if yes, break execution and return result accordingly
        String specialKeyCheckResult = checkSpecialKeys();
        if (!specialKeyCheckResult.equals("No Special Key Pressed")) {
            guiInstance.getFilePanel().getCommandImplementations().handleCommand(specialKeyCheckResult);
            return; // do not continue with key parsing.
        }

        // Toggle uppercase if shift mode is on
        if (wasShiftPressed) {
            lastKeyPressed = lastKeyPressed.toUpperCase();
            wasShiftPressed = false;
        }

        // Check if in Search Mode
        String matchedCommand = null;
        pressedKeysList.add(lastKeyPressed); // so it is displayed in KeyInfoPanel
        if (inSearchMode) {
            if (searchTerm==null){
                searchTerm = new StringBuilder();
            }
            searchTerm.append(lastKeyPressed);
        }
        // Only parse the pressed key when not in search mode
        else {
//            matchedCommand = matchKeyToCommand();
        }
        matchedCommand = matchKeyToCommand();


        System.out.println("Matched command sending to be handled = " + matchedCommand);
        // It can not be null
        guiInstance.getFilePanel().getCommandImplementations().handleCommand(matchedCommand);
    }

    private String checkSpecialKeys() {
        String specialKeyCheckResult = "No Special Key Pressed";
        // Check for special keystrokes first
        switch (lastKeyPressed) {
            case "<ESC>":
//                logger.debug("ESC key press passed");
                specialKeyCheckResult = "ESC";
                pressedKeysList.clear();
                searchTerm = null;
                inSearchMode = false;
//                return specialKeyCheckResult;
                break;
            case "<SHIFT>":
                guiInstance.getKeyInfoPanel().displayCommand("SHIFT pressed");
                wasShiftPressed = true;
                break;
//                return lastKeyPressed;
            case "<ENTER>":
                // When ENTER was pressed while pressed Keys list was empty - open file OR go to Directory
                if (pressedKeysList.isEmpty()) {
                    specialKeyCheckResult="open";
                }
                // When ENTER was pressed while user was in Search Mode - do the search
                if (inSearchMode) {
                    // TODO Implement this: Execute Search
                    if (searchTerm.toString() != null) {
                        logger.debug("Search term = " + searchTerm.toString());
                    }
                    searchTerm = null;
                    inSearchMode = false;
//                    guiInstance.getKeyInfoPanel().displayCommand(specialKeyCheckResult);
//                    guiInstance.getKeyInfoPanel().setPressedKeysListTitle("Pressed Keys List");
                    pressedKeysList.clear();
                    specialKeyCheckResult = "execute search";
                }
                break;
        }
        guiInstance.getFilePanel().getCommandImplementations().displayCommand(specialKeyCheckResult);
        return specialKeyCheckResult; //if this is reached, return instruction was not changed
    }


    //NOTE this is where the hardcoded keys will be replaced when custom keymappings will be implemented
    private String matchKeyToCommand() {
        String pressedKeysListAsString = String.join("", pressedKeysList); // Convert the ArrayList to a concatenated list of strings
        String matchedCommand = pressedKeysListAsString;
        switch (pressedKeysListAsString) {
            case "<SHIFT>:":
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
                matchedCommand = "enter search mode";
                System.out.println("entering search mode");
                inSearchMode = true;
                break;
            case ":q<ENTER>":
            case "quit":
                // Saving the last folder to history
                guiInstance.getAndrasCommanderInstance().getHistoryWriter().appendToHistory(guiInstance.getFilePanel().getFolderPath());
                exit(0);
                break;
        }
        // If a match was found, the mathched command will not equal the pressedKeysListAsString, so the pressedKeysList can be cleared
        if (!matchedCommand.equals(pressedKeysListAsString)) {
            pressedKeysList.clear();
        }
        System.out.println("returning matched command " + matchedCommand);
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
