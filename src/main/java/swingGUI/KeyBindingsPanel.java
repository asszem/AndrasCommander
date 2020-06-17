package swingGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class KeyBindingsPanel {
    private static Logger logger = LogManager.getLogger(KeyBindingsPanel.class);
    private GUI guiInstance;
    JPanel keyBindingsPanel;
    private ArrayList<String> keyBindings;
    private LinkedHashMap<String, String> keyBindingsMap;
    private JTable keyBindingsTable;

    public KeyBindingsPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    private void populateKeyBindings() {
        keyBindingsMap = new LinkedHashMap<>();
        keyBindingsMap.put("j/k", "down/up");
        keyBindingsMap.put("n", "next search match");
        keyBindingsMap.put("N", "prev search match");
    }

    public void toggleKeyBindingsPanel() {

    }

    private void displayKeyBindings() {
        keyBindingsMap.forEach((k, v) -> {
            String key = setTextAttribute(k, "blue", "WHITE", 600);
            String value = setTextAttribute(v, "RED", "WHITE", 300);
            JLabel label =new JLabel(k + ":" + v);
            label.setOpaque(true);
            keyBindingsPanel.add(label);
        });
    }

    private String setTextAttribute(String text, String fg, String bg, int weight) {
        //String result = "<html><font color=" + fg + "><span style='background:" + bg + "; font-weight:" + weight + "'>" + text + "</font></span>";
        String result = "<html><font color=" + fg + "><span style='background:" + bg + ";'>" + text + "</font></span>";
        System.out.println(result);
        return result;
    }

    public JPanel initPanel(String panelTitle) {
        keyBindingsPanel = new JPanel();
        keyBindingsPanel.setLayout(new BoxLayout(keyBindingsPanel, BoxLayout.Y_AXIS));

        keyBindingsPanel.setBorder(BorderFactory.createTitledBorder(panelTitle));
        keyBindingsPanel.setOpaque(true);

        JLabel titleLabel = new JLabel("Available commands:");
        keyBindingsPanel.add(titleLabel);

        populateKeyBindings();
        displayKeyBindings();


        return keyBindingsPanel;
    }


}
