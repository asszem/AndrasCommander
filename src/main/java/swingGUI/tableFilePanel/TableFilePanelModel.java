package swingGUI.tableFilePanel;

import data.FileItem;
import swingGUI.GUI;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TableFilePanelModel extends AbstractTableModel {

    GUI guiInstance;
    Object[] columnNames;
    Object[][] rowData; //[row][col]

    public TableFilePanelModel(GUI guiInstance){
        this.guiInstance=guiInstance;
    }

    public void populateTable(){
        System.out.println("populate Table called");
        ArrayList<FileItem> fileItems = guiInstance.getTableFilePanel().getFolderContent().sortFileItemsByName();
        columnNames = new Object[3];
        columnNames[0] = "File name";
        columnNames[1] = "Size";
        columnNames[2] = "Modified";
        rowData = new Object[fileItems.size()][columnNames.length];
        for (int i=0;i<rowData.length;i++){
            rowData[i][0]=fileItems.get(i).getFile().getName();
            rowData[i][1]=fileItems.get(i).getFile().length();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rowData[i][2]=sdf.format(fileItems.get(i).getFile().lastModified());
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return rowData[row][col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}
