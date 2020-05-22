package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class KeyPressedPanel {
    private static Logger logger = LogManager.getLogger(FilePanel.class);
    private GUI guiInstance;
    private KeyEvent pressedKey;

    private JPanel keyPressedPanel;
    private JLabel pressedKeyLabel;

    public KeyPressedPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initPanel(String panelTitle) {
        keyPressedPanel = new JPanel();
        keyPressedPanel.setBorder(BorderFactory.createTitledBorder(panelTitle));
        pressedKeyLabel = new JLabel("Nothing pressed");
        keyPressedPanel.add(pressedKeyLabel);
        return keyPressedPanel;
    }

    public void displayPressedKey(KeyEvent pressedKey) {
        keyPressedPanel.removeAll();
        if (pressedKey != null) {
            pressedKeyLabel = new JLabel(Character.toString(pressedKey.getKeyChar()));
        } else {
            pressedKeyLabel = new JLabel("No keypress event passed");
        }
        keyPressedPanel.add(pressedKeyLabel);
        guiInstance.getFrame().repaint();
    }
}
