package swingGUI.keyListener;

import data.KeyBindingParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.KeyEvent;

import static java.lang.System.exit;

// This class should server every GUI Panel instances that are made in SWING
/* 1. Detects if a key is pressed
 * 2. Checks if it's a special key and converts it to a string or sends the pressed key's string to KeyBindingParser
 * 3. KeyBindingParser checks whether the string matches any commands
 * 4. If yes, KeyBindingParser returns it as a command string, if not, it adds to the pressedKeysList
 * 5. KeyListener sends back the command string to the GUI
 *
 *Pressed KeyCode -> Key String -> Command String if matched -> Command String back to GUI
 *
 * */
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
//        guiInstance.getKeyInfoPanel().displayPressedKey(e);

        // Convert special keys (ESC, Shift, Enter) from key code to string
//        String lastKeyPressedConverted = convertKeyEventToString(e);

        // Check if pressed key(s) can be matched against any command
//        keyBindingParser.setLastPressedKey(lastKeyPressedConverted).parseKeys();

        // This ends here. Everything else is handled from the keybindingparser

//        if (matchedCommandReturned != null) { // Send the GUI the action to be performed
//            sendCommandToGui(matchedCommandReturned);
//        }
//        guiInstance.getKeyInfoPanel().displayAllPressedKeys(keyBindingParser.getPressedKeysList());
//        guiInstance.getKeyInfoPanel().displayHighlightedFile();
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
        else if (e.getKeyCode() == 40) { // when down key is pressed, do not send a j because RemapCursorNavigation already sent a j
//            result = "j";
        }
        // CURSOR UP
        else if (e.getKeyCode() == 38) {
//            result = "k";              // when up key is pressed, do not send a k because RemapCursorNavigation already sent a k
        }
        // SPACE
        else if (e.getKeyCode() == 32) {
            result = "<SPACE>";
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
