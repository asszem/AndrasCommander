package gui;

import andrasCommander.AndrasCommander;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    // Basic fields
    private static Logger logger = LogManager.getLogger(GUI.class);
    private WindowTitle windowTitle;
    private AndrasCommander andrasCommanderInstance;

    // Action Listener classes
    private UIActionListener actionListener;
    private KeyListener keyListener;

    // UI classes
    // TODO implement multiple panels and store every filePanel in a list
    private FilePanel filePanel;
    private KeyPressedPanel keyPressedPanel;

    // UI related fields
    private static Color selectedColor = Color.cyan;
    private static Color unselectedColor = Color.gray;

    // This frame is to hold the entire UI
    private JFrame frame = new JFrame();

    // Constructor to pass the CFReportEditor instance
    public GUI(AndrasCommander andrasCommanderInstance) {
        this.andrasCommanderInstance = andrasCommanderInstance;

        // passing GUI instance to action listeners so they can get every GUI element
        this.actionListener = new UIActionListener(this);
        this.keyListener = new KeyListener(this);
    }

    // This is called by AndrasCommander to initialize the GUI on the event dispatch thread
    public void initGUI() {

        // instantiate the UI classes
        filePanel = new FilePanel(this);
        keyPressedPanel = new KeyPressedPanel(this);

        // call the init method when adding UI elements to the contentPane
        frame.getContentPane().add(BorderLayout.NORTH, filePanel.initPanel("File Panel"));
        frame.getContentPane().add(BorderLayout.SOUTH, keyPressedPanel.initPanel("KeyPressed Panel"));
//        filePanel.getMainFilePanel().addKeyListener(keyListener);
        frame.addKeyListener(keyListener);

        windowTitle = new WindowTitle();
        setWindowTitle();

        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Setting the frame visible
        frame.setVisible(true);
    }

    public FilePanel getFilePanel() {
        return filePanel;
    }
    public KeyPressedPanel getKeyPressedPanel(){
        return keyPressedPanel;
    }

    public WindowTitle getWindowTitle() {
        return windowTitle;
    }

    private void setWindowTitle() {
        frame.setTitle(windowTitle.getApplicationName() + " version " + windowTitle.getVersion());
    }

    public UIActionListener getActionListener() {
        return actionListener;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public Color getUnselectedColor() {
        return unselectedColor;
    }

    public AndrasCommander getAndrasCommanderInstance() {
        return andrasCommanderInstance;
    }

    public class WindowTitle {

        private String applicationName;
        private String version;


        public WindowTitle() {
            applicationName = "Andras Commander";
            version = andrasCommanderInstance.getPropertyReader().readProperty("VERSION");
        }

        public String getApplicationName() {
            return applicationName;
        }

        public WindowTitle setApplicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public String getVersion() {
            return version;
        }

    }
}
