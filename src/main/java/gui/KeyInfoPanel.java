package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInfoPanel {
    private static Logger logger = LogManager.getLogger(FilePanel.class);
    private GUI guiInstance;

    private JPanel keyInfoPanel;

    private JPanel pressedKeyPanel;
    private JLabel pressedKeyLabel;

    private JPanel pressedKeysListPanel;
    private JLabel pressedKeysListLabel;

    private JPanel highlightedFilePanel;
    private JLabel highlightedFileLabel;

    public KeyInfoPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initPanel(String panelTitle) {
        keyInfoPanel = new JPanel();
        keyInfoPanel.setBorder(BorderFactory.createTitledBorder(panelTitle));

        //Pressed Key Panel
        pressedKeyPanel = new JPanel();
        pressedKeyPanel.setBorder(BorderFactory.createTitledBorder("Pressed Key (Code)"));
        pressedKeyPanel.setPreferredSize(new Dimension(200, 50));

        pressedKeyLabel = new JLabel("Nothing pressed");
        pressedKeyPanel.add(pressedKeyLabel);

        //Pressed Keys List Panel
        pressedKeysListPanel = new JPanel();
        pressedKeysListPanel.setBorder(BorderFactory.createTitledBorder("Pressed Keys List"));
        pressedKeysListPanel.setPreferredSize(new Dimension(200, 50));

        pressedKeysListLabel = new JLabel("Pressed keys list is empty.");
        pressedKeysListPanel.add(pressedKeysListLabel);

        highlightedFilePanel = new JPanel();
        highlightedFilePanel.setBorder(BorderFactory.createTitledBorder("Highlighted file"));
        highlightedFilePanel.setPreferredSize(new Dimension(200, 50));
        displayHighlightedFile();


        keyInfoPanel.add(highlightedFilePanel);
        keyInfoPanel.add(pressedKeyPanel);
        keyInfoPanel.add(pressedKeysListPanel);

        return keyInfoPanel;
    }

    public void displayHighlightedFile() {
        highlightedFilePanel.removeAll();
        highlightedFileLabel = new JLabel(guiInstance.getFilePanel().getHighlightedFile().getName() + " ("+guiInstance.getFilePanel().getHighlightedFileIndex()+")" );

        logger.debug("Highlighted file index = " + guiInstance.getFilePanel().getHighlightedFileIndex() );
        highlightedFilePanel.add(highlightedFileLabel);
        logger.debug("Highlighted file abs path = " + guiInstance.getFilePanel().getHighlightedFile().getAbsolutePath());
        guiInstance.getFrame().repaint();
        guiInstance.getFrame().setVisible(true);
    }

    public void displayPressedKey(KeyEvent pressedKey) {
        pressedKeyPanel.removeAll();
        if (pressedKey != null) {
            pressedKeyLabel = new JLabel(Character.toString(pressedKey.getKeyChar()) + " (" + pressedKey.getKeyCode() + ")");
        } else {
            pressedKeyLabel = new JLabel("No keypress event passed");
        }
        pressedKeyPanel.add(pressedKeyLabel);
        guiInstance.getFrame().repaint();
        guiInstance.getFrame().setVisible(true);
    }

    public void displayAllPressedKeys(ArrayList<String> pressedKeysList) {
        pressedKeysListPanel.removeAll();
        StringBuilder listOfPressedKeys = new StringBuilder();
        pressedKeysList.forEach(key -> {
            listOfPressedKeys.append(key);
        });
        pressedKeysListLabel = new JLabel(listOfPressedKeys.toString());
        pressedKeysListPanel.add(pressedKeysListLabel);
        guiInstance.getFrame().repaint();
        guiInstance.getFrame().setVisible(true);
    }
}
