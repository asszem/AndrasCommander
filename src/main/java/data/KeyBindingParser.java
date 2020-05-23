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

    public KeyBindingParser(GUI guiInstance) {
        this.guiInstance = guiInstance;
        pressedKeysList = new ArrayList<>();
    }

    public KeyBindingParser setLastPressedKey(String key) {
        lastKeyPressed = key;
        return this;
    }

    public String parseKeys() {
        String matchedCommand = null;
//        logger.debug("last pressed key = " + lastKeyPressed);

        // Check for special keystrokes first
        switch (lastKeyPressed) {
            case "<ESC>":
                logger.debug("ESC key press passed");
                matchedCommand = "ESC";
                pressedKeysList.clear();
                return matchedCommand;
        }

        pressedKeysList.add(lastKeyPressed);
        String listString = String.join("", pressedKeysList); // Convert the ArrayList to a concatenated list of strings
        switch (listString) {
            case "j":
                logger.debug("j matched");
                matchedCommand = "down";
                break;
            case "k":
                logger.debug("k matched");
                matchedCommand = "up";
                break;
            case "G":
                logger.debug("G matched");
                matchedCommand = "bottom";
                break;
            case "gg":
                logger.debug("gg matched");
                matchedCommand = "top";
                break;
            case "quit": // TODO implement :Q<ENTER>
                logger.debug("quit matched");
                exit(0);
                break;
        }
        // if match was found, erase the pressedKeysList
        if (matchedCommand!=null){
            pressedKeysList.clear();
        }
        return matchedCommand;
    }

    public ArrayList<String> getPressedKeysList() {
        return pressedKeysList;
    }
}
