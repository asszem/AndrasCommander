package control;

import swingGUI.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.HistoryWriter;
import utility.PropertyReader;

import javax.swing.*;

public class AndrasCommander {
    private static Logger logger = LogManager.getLogger(AndrasCommander.class);
    private static PropertyReader propertyReader;
    private static HistoryWriter historyWriter;

    public static void main(String args[]) {
        logger.info("____________________________________________________________________________________");
        logger.info("                        (¯`·._.·(¯`·._.· START ·._.·´¯)·._.·´¯)                     ");
        logger.info("                                  Andras Commander Started                          ");
        logger.info("____________________________________________________________________________________");
        // Initial thread to run the GUI. All GUI code that creates or interacts with Swing components must run on the event dispatch thread.
        AndrasCommander andrasCommanderInstance = new AndrasCommander();
        propertyReader = new PropertyReader();
        historyWriter = new HistoryWriter();
        historyWriter.createHistoryFileIfNotExist();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingGUI.GUI gui = new GUI(andrasCommanderInstance);
                gui.initGUI();
            }
        });
    }

    public PropertyReader getPropertyReader() {
        return propertyReader;
    }

    public static HistoryWriter getHistoryWriter() {
        return historyWriter;
    }
}
