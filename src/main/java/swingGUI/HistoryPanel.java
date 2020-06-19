package swingGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class HistoryPanel {
    private static Logger logger = LogManager.getLogger(HistoryPanel.class);
    private GUI guiInstance;

    private int maxNumberOfHistoryItems = 10;
    int historyItemFrontCharactersNumber = 15;
    int truncateHistoryItemOverCharacter = 30;
    private JPanel historyPanel;
    private JScrollPane historyScrollPane;
    private String historyItemLastFolderBackground="grey";
    private String historyItemLastFolderForeground="orange";

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
        historyScrollPane = new JScrollPane(historyPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        displayHistoryItems();

        return historyScrollPane;
    }

    int counter;

    JLabel historyItemLabel;

    public void displayHistoryItems() {
        historyPanel.removeAll();
        ArrayList<String> historyItems = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastNHistoryItems(maxNumberOfHistoryItems);
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(new File(".")); // get the current dir
        historyItems.forEach(historyItem -> logger.debug(historyItem));
        counter = 1;
        historyItems.forEach(historyItem -> {
            historyItemLabel = new JLabel(highlightLastFolder(counter + ". " + truncatePath(historyItem)));
            historyItemLabel.setBorder(new EmptyBorder(5,0,5,0));
            historyItemLabel.setIcon(icon);
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
        return front + " ... " + end;
    }
     private String highlightLastFolder(String input){
        // TODO handle if last folder length is greater than displayed and / is not found or found in before ...
        int indexOfLastPart=0;
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            indexOfLastPart = input.lastIndexOf("\\");
        } else {
            indexOfLastPart = input.lastIndexOf("/");
        }
        String firstPart=input.substring(0, indexOfLastPart);
        String lastPart=input.substring(indexOfLastPart, input.length());
        String result = "<html><nobr>"+firstPart+"<font color=" + historyItemLastFolderForeground + "><span style='background:" + historyItemLastFolderBackground + ";'>" + lastPart + "</font></span></html>";
         System.out.println(result);
        return result;
     }

}
