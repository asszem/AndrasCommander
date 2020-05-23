package gui;

import data.KeyBindingParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyListener implements java.awt.event.KeyListener {
    private static Logger logger = LogManager.getLogger(KeyListener.class);

    private GUI guiInstance;

    private ArrayList<KeyEvent> pressedKeysList;
    private KeyBindingParser keyBindingParser;

    public KeyListener(GUI guiInstance) {
        this.guiInstance = guiInstance;
        this.pressedKeysList = new ArrayList<>();
        this.keyBindingParser = new KeyBindingParser(guiInstance);
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
        // TODO this is actually parsing, so this validation should go to parser

        // TODO TO BE CONTINUED FROM HERE
       // Key pressed
       // Parser called
       // Special key validation (Enter, ESC, Shift, etc)
       // Single keystroke validation
       // Multiple keystroke validation
       // If no match found, add to pressedKeysList
       // If match found, reset pressed keys list







        //Esc is pressed
        if (e.getKeyCode()==27){
           resetPressedKeysList();
        } else {
            keyBindingParser.parseKeysList(pressedKeysList);
        }
        guiInstance.getKeyInfoPanel().displayAllPressedKeys(pressedKeysList);

    }

    @Override
    public void keyReleased(KeyEvent e) {
//        logger.debug("Key released event " + e.getKeyCode());
    }

    public ArrayList<KeyEvent> getPressedKeysList() {
        return pressedKeysList;
    }
    public void resetPressedKeysList(){
        logger.debug("pressed keys list reset called");
        pressedKeysList=new ArrayList<KeyEvent>();
        guiInstance.getKeyInfoPanel().displayAllPressedKeys(pressedKeysList);
    }
}
