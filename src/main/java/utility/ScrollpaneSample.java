package utility;

import javax.swing.*;
import java.awt.*;

public class ScrollpaneSample {



    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Scrollpane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700,500));
        frame.setLocation(900,0);
        frame.setBackground(Color.darkGray);
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
