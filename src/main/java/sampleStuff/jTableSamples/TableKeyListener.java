package sampleStuff.jTableSamples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TableKeyListener implements KeyListener {

    private JTable jTable;

    public TableKeyListener(JTable jTable) {
        this.jTable = jTable;
    }

    public void scrollToSelectedItem() {
        int row = jTable.getSelectedRow();
        Rectangle cellRect = jTable.getCellRect(row, 0, true);
        jTable.scrollRectToVisible(cellRect);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 74) {
            int nextRow = jTable.getSelectedRow() + 1 >= jTable.getRowCount() ? 0 : jTable.getSelectedRow() + 1;
            jTable.setRowSelectionInterval(nextRow, nextRow);
            scrollToSelectedItem();
        }
        if (e.getKeyCode() == 75) {
            int nextRow = jTable.getSelectedRow() - 1 <= 0 ? jTable.getRowCount() - 1 : jTable.getSelectedRow() - 1;
            jTable.setRowSelectionInterval(nextRow, nextRow);
            scrollToSelectedItem();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
