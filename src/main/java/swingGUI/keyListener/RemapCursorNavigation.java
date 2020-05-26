package swingGUI.keyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RemapCursorNavigation {

    public static <T extends JComponent> void remapCursors(T component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "replace with j");
        component.getActionMap().put("replace with j", setActionToSendKey(74));

        component.getInputMap().put(KeyStroke.getKeyStroke("UP"), "replace with k");
        component.getActionMap().put("replace with k", setActionToSendKey(75));
    }

    private static AbstractAction setActionToSendKey(int keyCode) {
        final AbstractAction action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Robot r = new Robot();
                    r.keyPress(keyCode); //actually press the key
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
            }
        };
        return action;
    }
}
