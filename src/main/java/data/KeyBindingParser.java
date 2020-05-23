package data;

import gui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.lang.System.exit;

public class KeyBindingParser {
    private static Logger logger = LogManager.getLogger(KeyBindingParser.class);
    private GUI guiInstance;

    // TODO get the list of pressed keys and match it against existing bindings
    // If match is found, return the command and reset the list
    public KeyBindingParser(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public void parseKeysList(ArrayList<KeyEvent> pressedKeysList) {

        StringBuilder listOfPressedKeys = new StringBuilder();
        pressedKeysList.forEach(keyEvent -> {
            listOfPressedKeys.append(keyEvent.getKeyChar());
        });
        switch (listOfPressedKeys.toString()) {
            case "j":
                logger.debug("j pressed.");
                guiInstance.getFilePanel().moveHighlightedFile("down");
                guiInstance.getKeyListener().resetPressedKeysList();
                break;
            case "k":
                logger.debug("k pressed.");
                guiInstance.getFilePanel().moveHighlightedFile("up");
                guiInstance.getKeyListener().resetPressedKeysList();
                break;
            case "G":
                logger.debug("G pressed.");
                guiInstance.getFilePanel().moveHighlightedFile("bottom");
                guiInstance.getKeyListener().resetPressedKeysList();
                break;
            case "gg":
                logger.debug("gg pressed.");
                guiInstance.getFilePanel().moveHighlightedFile("top");
                guiInstance.getKeyListener().resetPressedKeysList();
                break;
            case ":q": // TODO make only enter to exit
                logger.debug(":q pressed.");
                exit(0);
                break;

        }
    }
}
