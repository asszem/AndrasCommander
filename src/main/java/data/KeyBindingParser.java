package data;

import gui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                logger.debug("ESC key press passed");
                matchedCommand = "ESC";
                pressedKeysList.clear();
                return matchedCommand;
            case "<SHIFT>":
                wasShiftPressed = true;
                return null;
            case "<ENTER>":
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
            case ":q<ENTER>":
            case "quit":
                exit(0);
                break;
        }
        // if match was found, erase the pressedKeysList
        if (matchedCommand != null) {
            pressedKeysList.clear();
        }
        return matchedCommand;
    }

    public ArrayList<String> getPressedKeysList() {
        return pressedKeysList;
    }
}
