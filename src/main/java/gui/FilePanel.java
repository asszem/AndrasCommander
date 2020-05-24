package gui;

import control.AndrasCommander;
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
        this.guiInstance = guiInstance;
        this.frame = guiInstance.getFrame();
        this.andrasCommander = guiInstance.getAndrasCommanderInstance();
    }

    public JPanel initPanel(String panelTitle) {
//        logger.debug("--> Inside initPanel");
        String startfolder = andrasCommander.getPropertyReader().readProperty("STARTFOLDER");
        folderPath = startfolder;
        fileList = new FileList();
        highlightedFile = fileList.getFilesAndFolders(startfolder).get(0);
        highlightedFileIndex = 0;

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
        Cursor cursor = new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR);
        fileListScrollPane.setCursor(cursor);


        // Populate the fileListPanel with files
        displayFolderContent();

        // Add the fileListScrollPane to the mainFilePanel
        mainFilePanel.add(fileListScrollPane);

        return mainFilePanel;
    }

    private int fileLabelsHeightSummary;
    private int currentHighlightedFileHeight;

    private void displayFolderContent() {
//        logger.debug("--> inside DisplayFiles");
        fileLabelsHeightSummary = 0;
        fileListPanel.removeAll();
        fileList.getFilesAndFolders(folderPath).forEach(file -> {
            JLabel label = new JLabel();
            label.setSize(300, 100);
            if (file.equals(highlightedFile)) {
                // TODO find out why label background doesn't work
                label.setBackground(Color.GREEN);
                label.setForeground(Color.RED);
                label.setText("[" + file.getName() + "]");
                currentHighlightedFileHeight = fileLabelsHeightSummary;
            } else {
                label.setText(file.getName());
            }

            fileListPanel.add(label);
            fileLabelsHeightSummary += label.getHeight();
        });
        if (currentHighlightedFileHeight>4100){
            logger.debug("scrolling should be happening now");
            fileListScrollPane.scrollRectToVisible(fileListPanel.getBounds());
//            fileListScrollPane.getViewport().setCursor();
        }
//        logger.debug("filePanelHeight           =" + fileListScrollPane.getPreferredSize().height);
//        logger.debug("fileLabelsHeightSummary   = " + fileLabelsHeightSummary);
//        logger.debug("highlighted file height   = " + currentHighlightedFileHeight);
    }

    public void moveCursor(String direction) {
        switch (direction) {
            case "down":
                if (highlightedFileIndex == fileList.getFilesAndFolders(folderPath).size() - 1) {
                    highlightedFileIndex = 0;
                } else {
                    highlightedFileIndex++;
                }
                break;
            case "up":
                if (highlightedFileIndex == 0) {
                    highlightedFileIndex = fileList.getFilesAndFolders(folderPath).size() - 1; // index starts at 0
                } else {
                    highlightedFileIndex--;
                }
                break;
            case "top":
                highlightedFileIndex = 0;
                break;
            case "bottom":
                highlightedFileIndex = fileList.getFilesAndFolders(folderPath).size() - 1;
                break;
        }
        highlightedFile = fileList.getFilesAndFolders(folderPath).get(highlightedFileIndex);
        displayFolderContent();
        frame.repaint();
        frame.setVisible(true);
    }

    public JPanel getFileListPanel() {
        return fileListPanel;
    }

    public File getHighlightedFile() {
        return highlightedFile;
    }
}

