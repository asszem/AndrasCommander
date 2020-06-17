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

    private JPanel searchTermPanel;
    private JLabel searchTermLabel;

    public KeyInfoPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JPanel initPanel(String panelTitle) {
        keyInfoPanel = new JPanel();
        keyInfoPanel.setBorder(BorderFactory.createTitledBorder(panelTitle));
//        Dimension keyInfoPanelSize = new Dimension(800, 100);
//        keyInfoPanel.setPreferredSize(keyInfoPanelSize);


        //Highlighted File Panel
        highlightedFilePanel = new JPanel();
        highlightedFilePanel.setLayout(new GridBagLayout());
        highlightedFilePanel.setBorder(BorderFactory.createTitledBorder("Highlighted file"));
        displayHighlightedFile();

        // Search Term Panel
        searchTermPanel = new JPanel();
        searchTermPanel.setLayout(new GridBagLayout());
        searchTermPanel.setBorder(BorderFactory.createTitledBorder("Search term"));
        searchTermLabel = new JLabel("<empty>");
        searchTermPanel.add(searchTermLabel);

        //Pressed Key Panel
        pressedKeyPanel = new JPanel();
        pressedKeyPanel.setLayout(new GridBagLayout());
        pressedKeyPanel.setBorder(BorderFactory.createTitledBorder("Last Key (Code)"));
        pressedKeyLabel = new JLabel("Nothing pressed");
        pressedKeyPanel.add(pressedKeyLabel);

        //Pressed Keys List Panel
        pressedKeysListPanel = new JPanel();
        pressedKeysListPanel.setLayout(new GridBagLayout());
        setPressedKeysListTitle("Pressed Keys List");
        pressedKeysListLabel = new JLabel("<empty>");
        pressedKeysListPanel.add(pressedKeysListLabel);

        // Command Panel
        commandPanel = new JPanel();
        commandPanel.setLayout(new GridBagLayout());
        commandPanel.setBorder(BorderFactory.createTitledBorder("Last command"));
        commandLabel = new JLabel("Last command is empty");
        commandPanel.add(commandLabel);



        Dimension topRowDimension= new Dimension(400,50);
//        highlightedFilePanel.setMinimumSize(topRowDimension);
        highlightedFilePanel.setPreferredSize(topRowDimension);
//        highlightedFilePanel.setMaximumSize(topRowDimension);
//        searchTermPanel.setMinimumSize(topRowDimension);
//        searchTermPanel.setPreferredSize(topRowDimension);
//        searchTermPanel.setMaximumSize(topRowDimension);
        Dimension bottomRowDimension=new Dimension(200,30);
//        pressedKeyPanel.setMinimumSize(bottomRowDimension);
//        pressedKeyPanel.setPreferredSize(bottomRowDimension);
//        pressedKeyPanel.setMaximumSize(bottomRowDimension);
//        pressedKeysListPanel.setMinimumSize(bottomRowDimension);
//        pressedKeysListPanel.setPreferredSize(bottomRowDimension);
//        pressedKeysListPanel.setMaximumSize(bottomRowDimension);


//        commandPanel.setMinimumSize(bottomRowDimension);
//        commandPanel.setPreferredSize(bottomRowDimension);
//        commandPanel.setMaximumSize(bottomRowDimension);
//        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));


        keyInfoPanel.setLayout(new GridLayout(0,3));
        keyInfoPanel.add(pressedKeyPanel);
        keyInfoPanel.add(pressedKeysListPanel);
        keyInfoPanel.add(commandPanel);
        keyInfoPanel.add(highlightedFilePanel);
        keyInfoPanel.add(searchTermPanel);

        return keyInfoPanel;
    }

    public void displayCommand(String command) {
        commandLabel.setText(command);
    }

    public void displayHighlightedFile() {
        highlightedFilePanel.removeAll();
        highlightedFileLabel = new JLabel(guiInstance.getTableFilePanel().getHighlightedFileItem().getFile().getName() + " (" + guiInstance.getTableFilePanel().getHighlightedRowIndex() + ")");
        highlightedFilePanel.add(highlightedFileLabel);
        highlightedFilePanel.repaint();
        highlightedFilePanel.revalidate();
    }

    public void displayPressedKey(KeyEvent pressedKey) {
        pressedKeyPanel.removeAll();
        if (pressedKey != null) {
            pressedKeyLabel = new JLabel(Character.toString(pressedKey.getKeyChar()) + " (" + pressedKey.getKeyCode() + ")");
        } else {
            pressedKeyLabel = new JLabel("No keypress event passed");
        }
        pressedKeyPanel.add(pressedKeyLabel);
        pressedKeyPanel.repaint();
        pressedKeyPanel.revalidate();
    }

    public void displayAllPressedKeys(ArrayList<String> pressedKeysList) {
        pressedKeysListPanel.removeAll();
        StringBuilder listOfPressedKeys = new StringBuilder();
        pressedKeysList.forEach(key -> {
            listOfPressedKeys.append(key);
        });
        pressedKeysListLabel = new JLabel(listOfPressedKeys.toString());
        pressedKeysListPanel.add(pressedKeysListLabel);
//        pressedKeyPanel.repaint();
//        pressedKeyLabel.revalidate();
    }

    public void displayAllPressedKeys(String pressedKeysList) {
        pressedKeysListPanel.removeAll();
        pressedKeysListLabel = new JLabel(pressedKeysList);
        pressedKeysListPanel.add(pressedKeysListLabel);
//        pressedKeyPanel.repaint();
//        pressedKeyLabel.revalidate();
    }

    public void setPressedKeysListTitle(String title) {
        pressedKeysListPanel.setBorder(BorderFactory.createTitledBorder(title + " - " + guiInstance.getAndrasCommanderInstance().getMode()+" mode"));
    }

    public void displaySearchTerm(String searchTerm){
        searchTermPanel.removeAll();
        searchTermLabel = new JLabel(searchTerm);
        searchTermPanel.add(searchTermLabel);
        searchTermPanel.repaint();
        searchTermPanel.revalidate();
    }

}
