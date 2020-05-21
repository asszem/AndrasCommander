package andrasCommander;

import gui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class AndrasCommander {
    private static Logger logger = LogManager.getLogger(AndrasCommander.class);

    public static void main(String args[]) {
        logger.info("____________________________________________________________________________________");
        logger.info("                        (¯`·._.·(¯`·._.· START ·._.·´¯)·._.·´¯)                     ");
        logger.info("                                  Andras Commander Started                          ");
        logger.info("____________________________________________________________________________________");
        // Initial thread to run the GUI. All GUI code that creates or interacts with Swing components must run on the event dispatch thread.
        AndrasCommander andrasCommanderInstance = new AndrasCommander();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.GUI gui = new GUI(andrasCommanderInstance);
                gui.initGUI();
            }
        });
    }
}
