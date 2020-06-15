package data;

import control.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;

import java.util.ArrayList;


// This class must be independent of anything GUI related (no Swing/AWT stuff)
public class KeyBindingParser {
    private static Logger logger = LogManager.getLogger(KeyBindingParser.class);
    private GUI guiInstance;
    private ArrayList<String> pressedKeysList;
    private String lastKeyPressed;
    private boolean wasShiftPressed;
    private boolean inSearchMode; //if true, every keypress should be added to searchTerm string, instead of parsing until Enter or ESC
//    private StringBuilder searchTerm;

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
            guiInstance.getTableFilePanel().getTableFilePanelCommandImplementations().handleCommand(specialKeyCheckResult);
            return; // do not continue with key parsing.
        }

        // Toggle uppercase if shift mode is on
        if (wasShiftPressed) {
            lastKeyPressed = lastKeyPressed.toUpperCase();
            wasShiftPressed = false;
        }

        // Display the list of pressed keys before parsing to command string
        pressedKeysList.add(lastKeyPressed); // so it is displayed in KeyInfoPanel
        guiInstance.getKeyInfoPanel().displayAllPressedKeys(pressedKeysList);

        // Get the matched command by parsing and send it back to commandImplementation
        String matchedCommand = null;

        if (inSearchMode) {
            matchedCommand = String.join("", pressedKeysList); // Convert to String without any delimiter
        } else {
            matchedCommand = matchKeyToCommand();
        }

        guiInstance.getTableFilePanel().getTableFilePanelCommandImplementations().handleCommand(matchedCommand);
    }

    private String checkSpecialKeys() {
        String specialKeyCheckResult = "No Special Key Pressed";
        // Check for special keystrokes first
        switch (lastKeyPressed) {
            case "<ESC>":
//                logger.debug("ESC key press passed");
                specialKeyCheckResult = "ESC";
                pressedKeysList.clear();
                inSearchMode = false;
                guiInstance.getAndrasCommanderInstance().setMode(Constants.NORMAL_MODE);
                break;
            case "<SHIFT>":
                specialKeyCheckResult = "SHIFT pressed ";
                wasShiftPressed = true;
                break;
            case "<ENTER>":
                // When ENTER was pressed while pressed Keys list was empty - open file OR go to Directory
                if (pressedKeysList.isEmpty()) {
                    specialKeyCheckResult = "open";
                }
                // When ENTER was pressed while user was in Search Mode - do the search
                if (inSearchMode) {
                    specialKeyCheckResult = "execute search";
                    inSearchMode = false;
                    pressedKeysList.clear();
                    guiInstance.getAndrasCommanderInstance().setMode(Constants.NORMAL_MODE);
                }
                break;
            case "<SPACE>":
                // Handle when a space key is pressed in Search mode - do not set specialKeyResult, but replace
                // lastKeyPressed from <SPACE> to " " so a literal space will be added to the command
                if (inSearchMode) {
                    lastKeyPressed = " ";
                }
                break;
            case "<BACKSPACE>":
                // Handle when backspace is pressed in Search mode - set specialkeyresult as the updated pressedKeysList table
                // Which has removed the last item
                if (inSearchMode) {
                    if (pressedKeysList.size() > 0) {
                        pressedKeysList.remove(pressedKeysList.size() - 1);
                        specialKeyCheckResult = String.join("", pressedKeysList);
                    } else {
                        specialKeyCheckResult = "";
                    }
                }
                break;
        }
        return specialKeyCheckResult; //if this is reached, return instruction was not changed
    }


    //NOTE this is where the hardcoded keys will be replaced when custom keymappings will be implemented
    private String matchKeyToCommand() {
        String pressedKeysListAsString = String.join("", pressedKeysList); // Convert the ArrayList to a concatenated list of strings
        String matchedCommand = pressedKeysListAsString;
        switch (pressedKeysListAsString) {
            case ":":
//                guiInstance.getAndrasCommanderInstance().setMode(Constants.COMMAND_MODE);
                break;
            case ":noh":
                matchedCommand = "set no highlight search results";
                break;
            case ":hl":
                matchedCommand="set highlight search results";
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
            case "n":
                matchedCommand = "next search result";
                break;
            case "N":
                matchedCommand = "prev search result";
                break;
            case ":e<ENTER>":
                matchedCommand = "refresh panel";
                break;
            case "<SPACE>": // Enter search mode only when space is pressed in an empty pressedKeyList
                matchedCommand = "enter search mode";
                inSearchMode = true;
                // Note - the command implementation class should handle the mode switch
//                guiInstance.getAndrasCommanderInstance().setMode(Constants.SEARCH_MODE);
                break;
            case ":q<ENTER>":
            case "quit":
                matchedCommand = "quit";
                break;
        }
        // If a match was found, the mathched command will not equal the pressedKeysListAsString, so the pressedKeysList can be cleared
        if (!matchedCommand.equals(pressedKeysListAsString)) {
            pressedKeysList.clear();
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
