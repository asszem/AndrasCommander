package data;

import java.io.File;

public class FileItem {
    private File file;
    private boolean isHighlighted;
    private boolean isSelected;
    private boolean isFolderParent;

    public FileItem(File file, boolean isHighlighted, boolean isSelected, boolean isFolderParent) {
        this.file = file;
        this.isHighlighted = isHighlighted;
        this.isSelected = isSelected;
        this.isFolderParent= isFolderParent;
    }

    public File getFile() {
        return file;
    }

    public FileItem setFile(File file) {
        this.file = file;
        return this;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public FileItem setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public FileItem setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public boolean isFolderParent(){
        return isFolderParent;
    }

    public FileItem setFolderParent(boolean isFolderParent){
        this.isFolderParent=isFolderParent;
        return this;
    }
}
