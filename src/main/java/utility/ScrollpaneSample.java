package utility;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class ScrollpaneSample {

    private static JPanel panelA;
    private static JPanel panelAa;
    private static JScrollPane scrollPaneA;
    private static JList jListA;
    private static ListSelectionListener listSelectionListenerA;

    private static JPanel initJpanelA() {
        panelA = new JPanel();
        panelA.setBackground(Color.YELLOW);
        Dimension panelAsize = new Dimension(400, 200);
        panelA.setMinimumSize(panelAsize);
        panelA.setPreferredSize(panelAsize);
        panelA.setMaximumSize(panelAsize);
        panelA.setSize(panelAsize);
        String info = panelA.getSize().toString();
        panelA.setBorder(BorderFactory.createTitledBorder("Panel A - " + info));

        scrollPaneA = new JScrollPane();


        ArrayList<String> listContent = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            listContent.add("This is a line item number " + i);
        }

        jListA = new JList(listContent.toArray());
        jListA.setSelectedIndex(3);
//        jListA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListA.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListA.setSelectedIndex(0);

        listSelectionListenerA = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("selection event happened " + e.toString());
            }
        };
        jListA.addListSelectionListener(listSelectionListenerA);
        disableDownCursor();
        remapUpCursorToK();
        panelA.add(jListA);

        scrollPaneA = new JScrollPane(jListA);
        scrollPaneA.setMinimumSize(panelAsize);
        panelA.add(scrollPaneA);

        return panelA;
    }


    static void disableDownCursor() {
        KeyStroke removeDown = KeyStroke.getKeyStroke("DOWN");
        InputMap im = jListA.getInputMap();
        im.put(removeDown, "none");
        InputMap im2 = scrollPaneA.getInputMap();
        im2.put(removeDown, "none");
    }

    static void remapUpCursorToK() {
        KeyStroke remapUP = KeyStroke.getKeyStroke("UP");
        InputMap im = jListA.getInputMap();
        im.put(remapUP, "k");

    }


    private static JPanel panelB;

    private static JPanel initJpanelB() {
        panelB = new JPanel();
        panelB.setBackground(Color.cyan);
        Dimension panelBsize = new Dimension(400, 200);
        panelB.setMinimumSize(panelBsize);
        panelB.setPreferredSize(panelBsize);
        panelB.setMaximumSize(panelBsize);
        panelB.setSize(panelBsize);
        String info = panelB.getSize().toString();
        panelB.setBorder(BorderFactory.createTitledBorder("Panel B - " + info));
        return panelB;
    }

    private static JFrame frame;

    private static void createAndShowGUI() {
        frame = new JFrame("Scrollpane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 500));
        frame.setLocation(900, 0);
        frame.setBackground(Color.darkGray);

        frame.getContentPane().add(BorderLayout.WEST, initJpanelA());
        frame.getContentPane().add(BorderLayout.EAST, initJpanelB());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
