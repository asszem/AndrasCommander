package gui;

import andrasCommander.AndrasCommander;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.PropertyReader;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    // Basic fields
    private static Logger logger = LogManager.getLogger(GUI.class);
    private WindowTitle windowTitle;
    private AndrasCommander andrasCommanderInstance;
    private PropertyReader propertyReader;

    // UI classes
    private UIActionListener actionListener;
//        private BottomRowPanel bottomRowPanel;
//        private ProductSelectPanel productSelectPanel;
//        private TopMenuBarPanel topMenuBarPanel;
//        private ProductDataPanel productDataPanel;

    // UI related fields
    private static Color selectedColor = Color.cyan;
    private static Color unselectedColor = Color.gray;

    // This frame is to hold the entire UI
    JFrame frame = new JFrame();

    // Constructor to pass the CFReportEditor instance
    public GUI(AndrasCommander andrasCommanderInstance) {
        this.andrasCommanderInstance = andrasCommanderInstance;
        this.actionListener = new UIActionListener(this); // passing UI to action so the Panel classes can be reached
        this.propertyReader = new PropertyReader();
    }


    // This is called by CFReportEditor to initialize the GUI on the event dispatch thread
    public void initGUI() {
        // instantiate the UI classes
//            topMenuBarPanel = new TopMenuBarPanel(this); // pass the instance of CFReportEditorUI
//            bottomRowPanel = new BottomRowPanel(this);
//            productSelectPanel = new ProductSelectPanel(this);
//            productDataPanel = new ProductDataPanel(this);
        windowTitle = new WindowTitle();

        // call the init method when adding UI elements to the contentPane
//            frame.getContentPane().add(BorderLayout.NORTH, topMenuBarPanel.initTopMenuBar());
//            frame.getContentPane().add(BorderLayout.SOUTH, bottomRowPanel.initBottomRow());
//            frame.getContentPane().add(BorderLayout.WEST, productSelectPanel.initProductSelectPanel());
//            frame.getContentPane().add(BorderLayout.CENTER, productDataPanel.initProductDataPanel());

        setWindowTitle();
        frame.setSize(1600, 700);
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

    public class WindowTitle {

        private String applicationName;
        private String version;


        public WindowTitle() {
            applicationName = "Andras Commander";
            version = propertyReader.readProperty("VERSION");
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

        public WindowTitle setVersion(String version) {
            this.version = version;
            return this;
        }

    }
}
