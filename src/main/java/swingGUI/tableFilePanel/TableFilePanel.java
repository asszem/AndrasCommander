package swingGUI.tableFilePanel;

import control.Constants;
import data.FolderContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swingGUI.GUI;
import swingGUI.keyListener.KeyListener;
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
    }

    public JPanel initTableFilePanel(String panelTitle) {

        // File related tasks

        // Get the start folder path
        PropertyReader propertyReader = guiInstance.getAndrasCommanderInstance().getPropertyReader();
        String startfolder;
        startfolder = propertyReader.readProperty("STARTFOLDER");
        if (propertyReader.readProperty("START_IN").equalsIgnoreCase("Last folder")) {
            startfolder = guiInstance.getAndrasCommanderInstance().getHistoryWriter().getLastHistoryItem();
        }
        // Create a new FolderContent
        folderContent = new FolderContent(startfolder);

        // Table Related tasks

        // Create the Panel that will hold the Table
        tableFilePanelPanel = new JPanel();

        // Create a new table model
        tableFilePanelModel = new TableFilePanelModel(guiInstance);

        // Crate a new cell renderer
        tableFilePanelCellRenderer = new TableFilePanelCellRenderer();

        // Create a new key listener
        keyListener = new KeyListener(guiInstance);


        // Search related stuff
        searchMathcedItemIndexes = new ArrayList<>();
        searchType = Constants.SEARCH_MODE_STARTSWITH;
        searchMatchedItemIndexesPointer = 0;

        drawTableFilePanel(10);
        scrollToHighlightedItem();

        //Create a new Command Implementation (call this after drawTableFilePanel so the JTable is instantiazed)
        tableFilePanelCommandImplementations = new TableFilePanelCommandImplementations(guiInstance);

        return tableFilePanelPanel;
    }

    public void scrollToHighlightedItem() {
        int row = tableFilePanelTable.getSelectedRow();
        Rectangle cellRect = tableFilePanelTable.getCellRect(row, 0, true);
        tableFilePanelTable.scrollRectToVisible(cellRect);
    }

    public void drawTableFilePanel(int highlightedRowIndex) {

        // Remove previous content from the panel
        tableFilePanelPanel.removeAll();

        // Update the table model
        tableFilePanelModel.populateTable();

        // Create a new table
        tableFilePanelTable = new JTable(tableFilePanelModel);
        tableFilePanelTable.getColumnModel().getColumn(0).setCellRenderer(tableFilePanelCellRenderer);

        tableFilePanelTable.addKeyListener(keyListener);
//        RemapCursorNavigation.remapCursors(tableFilePanelTable);


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
        tableFilePanelTable.setFillsViewportHeight(true);
        tableFilePanelTable.setRowSelectionInterval(highlightedRowIndex, highlightedRowIndex);
        scrollToHighlightedItem();
        tableFilePanelTable.updateUI();
    }


    //Getters and setters
    public int getHighlightedRowIndex() {
        return tableFilePanelTable.getSelectedRow();
    }

    public JPanel getTableFilePanelPanel() {
        return tableFilePanelPanel;
    }

    public JTable getTableFilePanelTable() {
        return this.tableFilePanelTable;
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
