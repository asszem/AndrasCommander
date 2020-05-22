package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    private static Logger logger = LogManager.getLogger(KeyListener.class);

    private GUI guiInstance;

    public KeyListener(GUI guiInstance) {
        this.guiInstance = guiInstance;
        logger.debug("calling KeyListerner constructor");
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        logger.debug("Key typed event " + e.getKeyCode());

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        logger.debug("Key pressed event " + e.getKeyCode());
        // j is key 74
        if (e.getKeyCode()==74){
            logger.debug("j pressed.");
            guiInstance.getFilePanel().stepHighlightDown();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        logger.debug("Key released event " + e.getKeyCode());
    }
}
