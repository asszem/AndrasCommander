package swingGUI.keyListener;

import swingGUI.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIActionListener implements ActionListener {
    private static Logger logger = LogManager.getLogger(UIActionListener.class);
    private GUI guiInstance;

    public UIActionListener(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.debug("-> Inside actionPerformed. Action event = " + e.getActionCommand());
        logger.debug("<- Return from actionPerformed. Action event = " + e.getActionCommand());
    }
}
