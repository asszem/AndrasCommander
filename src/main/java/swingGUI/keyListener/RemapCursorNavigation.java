package swingGUI.keyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RemapCursorNavigation {

    public static <T extends JComponent> void remapCursors(T component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "replace with j");
        component.getActionMap().put("replace with j", setActionToSendKey(74));
//
        component.getInputMap().put(KeyStroke.getKeyStroke("UP"), "replace with k");
        component.getActionMap().put("replace with k", setActionToSendKey(75));

//        component.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "disable Enter");
//        component.getActionMap().put("disable Enter", null);

        component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        component.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
//                System.out.println("Enter disabled");
            }
        });
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
