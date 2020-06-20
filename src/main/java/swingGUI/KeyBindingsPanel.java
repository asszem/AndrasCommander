package swingGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KeyBindingsPanel {
    private static Logger logger = LogManager.getLogger(KeyBindingsPanel.class);
    private GUI guiInstance;
    JPanel keyBindingsPanel;
    private LinkedHashMap<String, String> keyBindingsMap;
    private JScrollPane keyBindingsScrollPane;

    public KeyBindingsPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    private void populateKeyBindings() {
        keyBindingsMap = new LinkedHashMap<>();
        keyBindingsMap.put("headerNav", "────Navigation────");
        keyBindingsMap.put("j/k", "down/up");
        keyBindingsMap.put("gg/G", "top/bottom");
        keyBindingsMap.put("gu", "go up to parent folder");
        keyBindingsMap.put("gb", "go back to prev folder");
        keyBindingsMap.put("separator1", "");
        keyBindingsMap.put("headerSearch", "────Search──── ");
        keyBindingsMap.put("space", "enter search mode");
        keyBindingsMap.put("n", "next search match");
        keyBindingsMap.put("N", "prev search match");
        keyBindingsMap.put(":noh", "no highlight search results");
        keyBindingsMap.put(":hl", "highlight search results");
        keyBindingsMap.put(":ic", "toggle case sensitive search");
        keyBindingsMap.put("ESC", "quit search mode, clear results");
        keyBindingsMap.put("Enter", "quit search mode, keep results ");
        keyBindingsMap.put("separator2", "");
        keyBindingsMap.put("headerOther", "────Other────");
        keyBindingsMap.put("Enter ", "Execute file / enter folder");
        keyBindingsMap.put(":q", "Quit");
    }

    public void toggleKeyBindingsPanel() {

    }

    private void displayKeyBindings() {
        keyBindingsMap.forEach((k, v) -> {
            JLabel label = new JLabel();
            int keyCharWidth = 6;


            if (k.startsWith("header")) {
                String header = setTextAttribute(v, "Orange", "none", 800);
                label.setText("<html>" + header + "</html>");
            } else if (k.startsWith("separator")) {
                label.setText(" ");
            } else {
//                String padding= String.format("%-" + 8 + "s", k);
                //a____(4) total 5, size 1
                //aa___(3)
                String padding = "&nbsp " + k;
                for (int i = 0; i < keyCharWidth - k.length(); i++) {
                    padding += "&nbsp";
                }
                String key = setTextAttribute(padding, "blue", "grey", 800);
                String value = setTextAttribute(v, "black", "none", 200);
                label.setText("<html>" + key + value + "</html>");
            }

            Font currFont = label.getFont();
            label.setFont(new Font("monospaced", currFont.getStyle(), currFont.getSize()));
            keyBindingsPanel.add(label);
        });
    }

    private String setTextAttribute(String text, String fg, String bg, int weight) {
        String result = "<font color=" + fg + "><span style='background:" + bg + "; font-weight:" + weight + "'>" + text + "</span></font>";
        return result;
    }

    public JScrollPane initPanel(String panelTitle) {
        keyBindingsPanel = new JPanel();
        keyBindingsPanel.setLayout(new BoxLayout(keyBindingsPanel, BoxLayout.Y_AXIS));

        keyBindingsPanel.setBorder(BorderFactory.createTitledBorder(panelTitle));
        keyBindingsScrollPane = new JScrollPane(keyBindingsPanel);

        populateKeyBindings();
        displayKeyBindings();

        return keyBindingsScrollPane;
    }

    public JScrollPane getKeyBindingsScrollPane() {
        return keyBindingsScrollPane;
    }
}
