package swingGUI;

import control.AndrasCommander;
import swingGUI.basicFilePanel.FilePanel;
import swingGUI.keyListener.KeyInfoPanel;
import swingGUI.keyListener.KeyListener;
import swingGUI.keyListener.UIActionListener;
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
    private KeyInfoPanel keyInfoPanel;

    // This frame is to hold the entire UI
    private JFrame frame = new JFrame();

    // Constructor to pass the CFReportEditor instance
    public GUI(AndrasCommander andrasCommanderInstance) {
        this.andrasCommanderInstance = andrasCommanderInstance;

        // passing GUI instance to action listeners so they can get every GUI element
//        this.actionListener = new UIActionListener(this);
        this.keyListener = new KeyListener(this);
    }

    // This is called by AndrasCommander to initialize the GUI on the event dispatch thread
    public void initGUI() {

        // instantiate the UI classes
        filePanel = new FilePanel(this);
        keyInfoPanel = new KeyInfoPanel(this);

        // call the init method when adding UI elements to the contentPane
        frame.getContentPane().add(BorderLayout.NORTH, filePanel.initPanel("File Panel"));
        frame.getContentPane().add(BorderLayout.SOUTH, keyInfoPanel.initPanel("Key Info Panel"));
//        filePanel.getFileListPanel().addKeyListener(keyListener);
//        frame.addKeyListener(keyListener);

        windowTitle = new WindowTitle();
        setWindowTitle();

        frame.setSize(1000, 900);
//        frame.setLocationRelativeTo(null);
        frame.setLocation(900, 0);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.pack(); //sizes the frame so that all its contents are at or above their preferred sizes
        frame.setVisible(true); // Setting the frame visible
    }

    public FilePanel getFilePanel() {
        return filePanel;
    }

    public KeyInfoPanel getKeyInfoPanel() {
        return keyInfoPanel;
    }

    public KeyListener getKeyListener() {
        return keyListener;
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
