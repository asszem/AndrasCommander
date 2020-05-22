package gui;

import andrasCommander.AndrasCommander;
import data.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FilePanel {

    //DATA fields
    private static Logger logger = LogManager.getLogger(FilePanel.class);
    private AndrasCommander andrasCommander;
    private FileList fileList;
    private File highlightedFile;
    private int highlightedFileIndex;
    private String folderPath;

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
        folderPath=startfolder;
        fileList = new FileList();
        highlightedFile = fileList.getFilesAndFolders(startfolder).get(0);
        highlightedFileIndex=0;

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
        displayFolderContent();

        // Add the fileListScrollPane to the mainFilePanel
        mainFilePanel.add(fileListScrollPane);

        return mainFilePanel;
    }

    private void displayFolderContent() {
        logger.debug("--> inside DisplayFiles");
        fileListPanel.removeAll();
        fileList.getFilesAndFolders(folderPath).forEach(file -> {
            JLabel label = new JLabel();
            if (file.equals(highlightedFile)) {
                // TODO find out why label background doesn't work
                label.setBackground(Color.GREEN);
                label.setForeground(Color.RED);
                label.setText("[" + file.getName() + "]");
            } else {
                label.setText(file.getName());
            }

            fileListPanel.add(label);
        });
//        frame.repaint();
//        frame.revalidate();
//        frame.setVisible(true);
    }

    public void stepHighlightDown(){
       logger.debug("stephighlightdown called");
       highlightedFile=fileList.getFilesAndFolders(folderPath).get(++highlightedFileIndex);
       displayFolderContent();
       frame.repaint();
       frame.setVisible(true);
    }

    public JPanel getMainFilePanel(){
        return fileListPanel;
    }
}

