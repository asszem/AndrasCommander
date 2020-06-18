package swingGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel {
    private static Logger logger = LogManager.getLogger(HistoryPanel.class);
    private GUI guiInstance;

    private int maxNumberOfHistoryItems = 10;
    int historyItemFrontCharactersNumber = 10;
    int truncateHistoryItemOverCharacter = 25;
    private JPanel historyPanel;
    private JScrollPane historyScrollPane;

    public HistoryPanel(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

    public JScrollPane initPanel(String title) {
        historyPanel = new JPanel();
        historyPanel.setBorder(BorderFactory.createTitledBorder(title));
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        Dimension historyPanelSize = new Dimension(250, 50);
        historyPanel.setPreferredSize(historyPanelSize);
        historyPanel.setMaximumSize(historyPanelSize);
        historyScrollPane = new JScrollPane(historyPanel);

        displayHistoryItems();

        return historyScrollPane;
    }

    int counter;

    JLabel historyItemLabel;

    public void displayHistoryItems() {
        historyPanel.removeAll();
        ArrayList<String> historyItems = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastNHistoryItems(maxNumberOfHistoryItems);
        historyItems.forEach(historyItem -> logger.debug(historyItem));
        counter = 1;
        historyItems.forEach(historyItem -> {
            historyItemLabel = new JLabel(counter + ". " + truncatePath(historyItem));
            historyPanel.add(historyItemLabel);
            counter++;
        });
        historyPanel.repaint();
        historyPanel.revalidate();
    }

    private String truncatePath(String input) {
        String front = "";
        String end = "";
        if (input.length() > truncateHistoryItemOverCharacter) {
            front = input.substring(0, historyItemFrontCharactersNumber);
            end = input.substring(input.length() - (truncateHistoryItemOverCharacter - historyItemFrontCharactersNumber), input.length());
        } else {
            return input;
        }
        return front + ". . ." + end;
    }

}
