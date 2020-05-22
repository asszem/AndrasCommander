package gui;

import andrasCommander.AndrasCommander;
import data.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class FilePanel {

    //DATA fields
    private static Logger logger = LogManager.getLogger(FilePanel.class);
    private AndrasCommander andrasCommander;
    private FileList fileList;

    //GUI Fields
    private GUI guiInstance;
    private JFrame frame;
    private JPanel mainFilePanel;
    private JPanel fileListPanel;
    private JScrollPane fileListScrollPane;

    //Constructor
    public FilePanel(GUI guiInstance) {
        logger.debug("FilePanel Constructor");
        this.guiInstance = guiInstance;
        this.frame = guiInstance.getFrame();
        this.andrasCommander = guiInstance.getAndrasCommanderInstance();
    }

    public JPanel initPanel(String panelTitle) {
        logger.debug("--> Inside initPanel");
        String startfolder = andrasCommander.getPropertyReader().readProperty("STARTFOLDER");
        fileList = new FileList();

        // Create the mainFilePanel that will hold the fileListPanel which is the viewport of the fileListScrollPane
        mainFilePanel = new JPanel();
        mainFilePanel.setBorder(BorderFactory.createTitledBorder("File Panel"));

        // Create the fileListPanel
        fileListPanel = new JPanel();
        fileListPanel.setLayout(new BoxLayout(fileListPanel, BoxLayout.Y_AXIS));

        // Create the scroll pane for fileListPanel
        fileListScrollPane = new JScrollPane(fileListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileListScrollPane.setBorder(BorderFactory.createTitledBorder(startfolder));
        fileListScrollPane.setPreferredSize(new Dimension(900, 700));

        // Populate the fileListPanel with files
        displayFolderContent(startfolder);

        // Add the fileListScrollPane to the mainFilePanel
        mainFilePanel.add(fileListScrollPane);


//        displayFiles(startfolder);

        return mainFilePanel;
    }

    private void displayFolderContent(String folderPath) {
        logger.debug("--> inside DisplayFiles");
        fileList.getFilesAndFolders(folderPath).forEach(file -> {
            JLabel label = new JLabel();
            label.setText(file.getName());
            fileListPanel.add(label);
        });
//        frame.repaint();
//        frame.revalidate();
//        frame.setVisible(true);
    }
}

