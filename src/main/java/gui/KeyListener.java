package gui;

import data.KeyBindingParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.KeyEvent;

import static java.lang.System.exit;

public class KeyListener implements java.awt.event.KeyListener, ListSelectionListener {
    private static Logger logger = LogManager.getLogger(KeyListener.class);

    private GUI guiInstance;
    private KeyBindingParser keyBindingParser;

    public KeyListener(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.keyBindingParser = new KeyBindingParser(guiInstance);
    }


    @Override
    public void keyPressed(KeyEvent e) {
//      logger.debug("Key pressed event " + e.getKeyCode());
        guiInstance.getKeyInfoPanel().displayPressedKey(e);

        // Convert special keys (ESC, Shift, Enter) from key code to string
        String lastKeyPressedConverted = convertKeyEventToString(e);

        // Check if pressed key(s) can be matched against any command
        String matchedCommandReturned = keyBindingParser.setLastPressedKey(lastKeyPressedConverted).parseKeys();
        if (matchedCommandReturned != null) { // Send the GUI the action to be performed
            sendCommandToGui(matchedCommandReturned);
        }
        guiInstance.getKeyInfoPanel().displayAllPressedKeys(keyBindingParser.getPressedKeysList());
        guiInstance.getKeyInfoPanel().displayHighlightedFile();
    }

    // later not only the moveCursor command will be invoked
    private void sendCommandToGui(String command) {
//        logger.debug("command send to GUI = " + command);
        switch (command) {
            case "down":
                guiInstance.getFilePanel().moveCursor("down");
                break;
            case "up":
                guiInstance.getFilePanel().moveCursor("up");
                break;
            case "top":
                guiInstance.getFilePanel().moveCursor("top");
                break;
            case "bottom":
                guiInstance.getFilePanel().moveCursor("bottom");
                break;
            case "pageDown":
                //TODO implement pageDown move action
                break;
            case "pageUp":
                //todo implement pageUp move action
                break;
            case "ESC":
                //todo implement pageUp move action
                break;
            case "Enter":
                //todo implement pageUp move action
                break;
            case "quit":
                exit(0);
                break;
        }
    }

    private String convertKeyEventToString(KeyEvent e) {
        String result = "";
        // ESC pressed
        if (e.getKeyCode() == 27) {
            result = "<ESC>";
        }
        // SHIFT pressed (left or right)
        else if (e.getKeyCode() == 16) {
            result = "<SHIFT>";
        }
        // ENTER pressed
        else if (e.getKeyCode() == 10) {
            result = "<ENTER>";
        }
        // CURSOR Down
        else if (e.getKeyCode() == 40) {
            result = "j"; // TODO remove hardcoded keybinding
        }
        // CURSOR UP
        else if (e.getKeyCode() == 38) {
            result = "k";
        } else {
            result = Character.toString((e.getKeyChar()));
        }
        return result;
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        logger.debug("Key typed event " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        logger.debug("Key released event " + e.getKeyCode());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // nothing implemented here yet
    }
}
