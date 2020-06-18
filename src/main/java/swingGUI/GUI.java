package swingGUI;

import control.AndrasCommander;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.keyListener.KeyInfoPanel;
import swingGUI.tableFilePanel.TableFilePanel;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    // Basic fields
    private static Logger logger = LogManager.getLogger(GUI.class);
    private WindowTitle windowTitle;
    private AndrasCommander andrasCommanderInstance;

    // UI classes
    // TODO implement multiple panels and store every filePanel in a list
    private TableFilePanel tableFilePanel;
    private KeyInfoPanel keyInfoPanel;
    private KeyBindingsPanel keyBindingsPanel;
    private boolean isKeyBindingsPanelVisible;
    private HistoryPanel historyPanel;

    // This frame is to hold the entire UI
    private JFrame frame;

    // Constructor to pass the CFReportEditor instance
    public GUI(AndrasCommander andrasCommanderInstance) {
        this.andrasCommanderInstance = andrasCommanderInstance;
    }

    // This is called by AndrasCommander to initialize the GUI on the event dispatch thread
    public void initGUI() {

        frame = new JFrame();
        // instantiate the UI classes
        tableFilePanel = new TableFilePanel(this);
        keyInfoPanel = new KeyInfoPanel(this);
        historyPanel = new HistoryPanel(this);
        keyBindingsPanel = new KeyBindingsPanel(this);
        isKeyBindingsPanelVisible = true;

        // call the init method when adding UI elements to the contentPane
        frame.getContentPane().add(BorderLayout.WEST, historyPanel.initPanel("History"));
        frame.getContentPane().add(BorderLayout.CENTER, tableFilePanel.initTableFilePanel("Table File Panel"));
        frame.getContentPane().add(BorderLayout.EAST, keyBindingsPanel.initPanel("Key Bindings"));
        frame.getContentPane().add(BorderLayout.SOUTH, keyInfoPanel.initPanel("Key Info Panel"));

        windowTitle = new WindowTitle();
        setWindowTitle();

        Dimension frameSize = new Dimension(1000, 500);
//        frame.setPreferredSize(frameSize);
        frame.setLocation(600, 0);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.pack(); //sizes the frame so that all its contents are at or above their preferred sizes
        frame.setVisible(true); // Setting the frame visible
    }

    public TableFilePanel getTableFilePanel() {
        return this.tableFilePanel;
    }

    public KeyInfoPanel getKeyInfoPanel() {
        return keyInfoPanel;
    }

    public void toggleKeyBindingsPanel() {
        if (isKeyBindingsPanelVisible) {
            frame.getContentPane().remove(keyBindingsPanel.getKeyBindingsScrollPane());
            frame.repaint();
            isKeyBindingsPanelVisible = false;
        } else {
            frame.getContentPane().add(BorderLayout.EAST, keyBindingsPanel.initPanel("Key Bindings"));
            frame.repaint();
            isKeyBindingsPanelVisible = true;
        }
    }

    public WindowTitle getWindowTitle() {
        return windowTitle;
    }

    private void setWindowTitle() {
        frame.setTitle(windowTitle.getApplicationName() + " version " + windowTitle.getVersion());
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

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }
}
