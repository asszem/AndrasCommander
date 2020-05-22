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

    // UI classes
    private UIActionListener actionListener;
    // TODO implement multiple panels and store every filePanel in a list
    private FilePanel filePanel;

    // UI related fields
    private static Color selectedColor = Color.cyan;
    private static Color unselectedColor = Color.gray;

    // This frame is to hold the entire UI
    JFrame frame = new JFrame();

    // Constructor to pass the CFReportEditor instance
    public GUI(AndrasCommander andrasCommanderInstance) {
        this.andrasCommanderInstance = andrasCommanderInstance;
        this.actionListener = new UIActionListener(this); // passing UI to action so the FilePanel classes can be reached
    }

    // This is called by AndrasCommander to initialize the GUI on the event dispatch thread
    public void initGUI() {

        // instantiate the UI classes
        filePanel = new FilePanel(this);

        // call the init method when adding UI elements to the contentPane
        frame.getContentPane().add(BorderLayout.NORTH, filePanel.initPanel("File Panel"));

        windowTitle = new WindowTitle();
        setWindowTitle();

        frame.setSize(1600, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Setting the frame visible
        frame.setVisible(true);
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
