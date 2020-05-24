package gui;

import javax.swing.*;

public class RemapCursorNavigation {

    public static void remapCursors(JList jList) {
        KeyStroke remove = KeyStroke.getKeyStroke("DOWN");
        InputMap im = jList.getInputMap();
        im.put(remove, "j");
    }
}
