package swingGUI.keyListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInfoPanel {
    private static Logger logger = LogManager.getLogger(KeyInfoPanel.class);
    private GUI guiInstance;

    private JPanel keyInfoPanel;

    private JPanel pressedKeyPanel;
    private JLabel pressedKeyLabel;

    private JPanel pressedKeysListPanel;
    private JLabel pressedKeysListLabel;

    private JPanel highlightedFilePanel;
    private JLabel highlightedFileLabel;

    private JPanel commandPanel;
    private JLabel commandLabel;

    public KeyInfoPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initPanel(String panelTitle) {
        keyInfoPanel = new JPanel();
        keyInfoPanel.setBorder(BorderFactory.createTitledBorder(panelTitle));

        //Pressed Key Panel
        pressedKeyPanel = new JPanel();
        pressedKeyPanel.setBorder(BorderFactory.createTitledBorder("Last Key (Code)"));
        pressedKeyPanel.setPreferredSize(new Dimension(130, 50));

        pressedKeyLabel = new JLabel("Nothing pressed");
        pressedKeyPanel.add(pressedKeyLabel);

        //Pressed Keys List Panel
        pressedKeysListPanel = new JPanel();
        setPressedKeysListTitle("Pressed Keys List");
        pressedKeysListPanel.setPreferredSize(new Dimension(200, 50));

        pressedKeysListLabel = new JLabel("<empty>");
        pressedKeysListPanel.add(pressedKeysListLabel);

        highlightedFilePanel = new JPanel();
        highlightedFilePanel.setBorder(BorderFactory.createTitledBorder("Highlighted file"));
        highlightedFilePanel.setPreferredSize(new Dimension(200, 50));
        displayHighlightedFile();

        commandPanel = new JPanel();
        commandPanel.setBorder(BorderFactory.createTitledBorder("Last command"));
        commandLabel = new JLabel("Last command is empty");
        commandPanel.add(commandLabel);

        BorderLayout keyInfoLayout = new BorderLayout();
        keyInfoPanel.setLayout(keyInfoLayout);
        Dimension keyInfoPanelSize = new Dimension(800,130);
        keyInfoPanel.setPreferredSize(keyInfoPanelSize);
        keyInfoPanel.add(highlightedFilePanel, BorderLayout.NORTH);
        keyInfoPanel.add(pressedKeyPanel, BorderLayout.WEST);
        keyInfoPanel.add(pressedKeysListPanel, BorderLayout.EAST);
        keyInfoPanel.add(commandPanel, BorderLayout.CENTER);

        return keyInfoPanel;
    }

    public void displayCommand(String command) {
        commandLabel.setText(command);
        guiInstance.getFrame().repaint();
        guiInstance.getFrame().setVisible(true);
    }

    public void displayHighlightedFile() {
        highlightedFilePanel.removeAll();
        highlightedFileLabel = new JLabel(guiInstance.getFilePanel().getFolderContent().getHighlightedFile().getName() + " (" + guiInstance.getFilePanel().getHighlightedFileIndex() + ")");

//        logger.debug("Highlighted file index = " + guiInstance.getFilePanel().getHighlightedFileIndex() );
        highlightedFilePanel.add(highlightedFileLabel);
//        logger.debug("Highlighted file abs path = " + guiInstance.getFilePanel().getHighlightedFileItem().getAbsolutePath());
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

    public void displayAllPressedKeys(String pressedKeysList) {
        pressedKeysListPanel.removeAll();
        pressedKeysListLabel = new JLabel(pressedKeysList);
        pressedKeysListPanel.add(pressedKeysListLabel);
        guiInstance.getFrame().repaint();
        guiInstance.getFrame().setVisible(true);
    }

    public void setPressedKeysListTitle(String title){
        pressedKeysListPanel.setBorder(BorderFactory.createTitledBorder(title));
    }

}
