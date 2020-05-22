package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyListener implements java.awt.event.KeyListener {
    private static Logger logger = LogManager.getLogger(KeyListener.class);

    private GUI guiInstance;

    private ArrayList<KeyEvent> pressedKeysList;

    public KeyListener(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.pressedKeysList = new ArrayList<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        logger.debug("Key typed event " + e.getKeyCode());

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        logger.debug("Key pressed event " + e.getKeyCode());
        pressedKeysList.add(e);
        guiInstance.getKeyInfoPanel().displayPressedKey(e);
        guiInstance.getKeyInfoPanel().displayAllPressedKeys(pressedKeysList);
        // j is key 74
        if (e.getKeyCode()==74){
            logger.debug("j pressed.");
            guiInstance.getFilePanel().moveHighlightedFile("down");
        }
        if (e.getKeyCode()==75){
            logger.debug("k pressed.");
            guiInstance.getFilePanel().moveHighlightedFile("up");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        logger.debug("Key released event " + e.getKeyCode());
    }

    public ArrayList<KeyEvent> getPressedKeysList() {
        return pressedKeysList;
    }
}
