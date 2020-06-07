package data;

import java.io.File;

public class FileItem {
    private File file;
    private boolean isSelected;
    private boolean isSearchMatched;
    private String displayedTitle; // This is what is displayed in JPanel. Based on this, the search can work

    public FileItem(File file, boolean isSelected) {
        this.file = file;
        this.isSelected = isSelected;
    }

    public File getFile() {
        return file;
    }

    public FileItem setFile(File file) {
        this.file = file;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public FileItem setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public String getDisplayedTitle() {
        return displayedTitle;
    }

    public FileItem setDisplayedTitle(String displayedTitle) {
        this.displayedTitle = displayedTitle;
        return this;
    }

    public boolean isSearchMatched() {
        return isSearchMatched;
    }

    public FileItem setSearchMatched(boolean isMatched) {
        this.isSearchMatched = isMatched;
        return this;
    }
}
