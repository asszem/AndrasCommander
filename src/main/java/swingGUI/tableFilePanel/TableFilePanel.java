package swingGUI.tableFilePanel;

import control.Constants;
import data.FileItem;
import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;
import swingGUI.basicFilePanel.CommandImplementations;
import swingGUI.keyListener.KeyListener;
import swingGUI.keyListener.RemapCursorNavigation;
import utility.PropertyReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TableFilePanel {
    private static Logger logger = LogManager.getLogger(TableFilePanel.class);

    //File related fields
    private FolderContent folderContent;

    // SEARCH related fields
    private boolean displaySearchResultMatches;
    private ArrayList<Integer> searchMathcedItemIndexes;
    private int searchMatchedItemIndexesPointer; // points to the current searchMathcedItemIndex. Used when navigating with n/N
    private String searchType;

    //GUI Fields
    private GUI guiInstance;
    private JPanel tableFilePanelPanel;
    private JTable tableFilePanelTable;
    private JScrollPane tableFilePanelScrollPane;
    private TableFilePanelCellRenderer tableFilePanelCellRenderer;
    private TableFilePanelModel tableFilePanelModel;
    private TableFilePanelCommandImplementations tableFilePanelCommandImplementations;
    private KeyListener keyListener;

    //Constructor
    public TableFilePanel(GUI guiInstance) {
        this.guiInstance = guiInstance;

        // TODO decide if these can be done in the construcor, no need to recreate
        // Create a new table model
        tableFilePanelModel = new TableFilePanelModel();
        // Crate a new cell renderer
        tableFilePanelCellRenderer = new TableFilePanelCellRenderer();
        // Create a new key listener
        keyListener = new KeyListener(guiInstance);
        logger.debug("TableFilePanel Constructor calledj.");
    }

    public JPanel initTableFilePanel(String panelTitle) {
        PropertyReader propertyReader = guiInstance.getAndrasCommanderInstance().getPropertyReader();
        String startfolder;
        startfolder = propertyReader.readProperty("STARTFOLDER");
        if (propertyReader.readProperty("START_IN").equalsIgnoreCase("Last folder")) {
            startfolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        }

        folderContent = new FolderContent(startfolder);

        tableFilePanelCommandImplementations = new TableFilePanelCommandImplementations(guiInstance);
        tableFilePanelPanel = new JPanel();
        tableFilePanelCellRenderer = new TableFilePanelCellRenderer();

        searchMathcedItemIndexes = new ArrayList<>();
        searchType = Constants.SEARCH_MODE_STARTSWITH;
        searchMatchedItemIndexesPointer=0;

        drawFilePanel(0);

        return tableFilePanelPanel;
    }

    public void drawFilePanel(int highlightedIndex) {
        // Remove previous content from the panel
        tableFilePanelPanel.removeAll();


        // Create a new table
        tableFilePanelTable = new JTable(tableFilePanelModel);
        tableFilePanelTable.getColumnModel().getColumn(0).setCellRenderer(tableFilePanelCellRenderer);

        RemapCursorNavigation.remapCursors(tableFilePanelTable);

        // Create SCROLLPANE
        tableFilePanelScrollPane = new JScrollPane(tableFilePanelTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableFilePanelScrollPane.setBorder(BorderFactory.createTitledBorder(folderContent.getFolderPath()));
        Dimension scrollPaneSize = new Dimension(700, 300);
        tableFilePanelScrollPane.setPreferredSize(scrollPaneSize);

        // Add SCROLLPANE to PANEL
        tableFilePanelPanel.add(tableFilePanelScrollPane);

        // To make sure focus is on this item when it was redrawn
        tableFilePanelTable.grabFocus();

        // Scroll to selected item
        //TODO implement this
    }


    //Getters and setters
    public JPanel getTableFilePanelPanel() {
        return tableFilePanelPanel;
    }


    public FolderContent getFolderContent() {
        return folderContent;
    }

    public TableFilePanelCommandImplementations getTableFilePanelCommandImplementations() {
        return tableFilePanelCommandImplementations;
    }

    public TableFilePanel setDisplaySearchResultMatches(boolean displaySearchResultMatches) {
        this.displaySearchResultMatches = displaySearchResultMatches;
        return this;
    }

    public boolean getDisplaySearchResultMatches() {
        return this.displaySearchResultMatches;
    }

    public ArrayList<Integer> getSearchMathcedItemIndexes() {
        return this.searchMathcedItemIndexes;
    }

    public TableFilePanel resetSearchMathcedItemIndexes() {
        this.searchMathcedItemIndexes.clear();
        return this;
    }

    public int getSearchMatchedItemIndexesPointer() {
        return searchMatchedItemIndexesPointer;
    }

    public void setSearchMatchedItemIndexesPointer(int searchMatchedItemIndexesPointer) {
        this.searchMatchedItemIndexesPointer = searchMatchedItemIndexesPointer;
    }
}
