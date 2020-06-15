package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FolderContent {
    private static Logger logger = LogManager.getLogger(FolderContent.class);
    private ArrayList<FileItem> fileItems; // does NOT contain the parent folder or the highlighted file item
    private ArrayList<FileItem> searchResults;
    private FileItem parentFolder;
    private String folderPath;

    public FolderContent(String folderPath) {
        this.folderPath = folderPath;
        loadFiles(folderPath); // so parentFolder and fileItems are always instantiazed
    }

    public FolderContent loadFiles(String folderPath) {
        logger.debug("Loading files and folders from folder = " + folderPath);

        // Set the parent folder
        File currentDir = new File(folderPath);
        if (currentDir.getParent() != null) {
            parentFolder = new FileItem(currentDir.getParentFile(), false);
        } else {
            parentFolder = new FileItem(currentDir, false);
        }

        // Get the files in the folder
        File[] fileList = new File(folderPath).listFiles();
        fileItems = new ArrayList<>();
        FileItem currentFileItem;

        for (File file : fileList) {
            currentFileItem = new FileItem(file, false);
            fileItems.add(currentFileItem);
        }

        return this; // so it can be chained
    }

    // Directories first
    public ArrayList<FileItem> sortFileItemsByName() {

        List<FileItem> sortedFileItemList = fileItems;
        sortedFileItemList.sort((fileName1, fileName2) -> fileName1.getFile().getName().compareTo(fileName2.getFile().getName()));

        List<FileItem> foldersOnly = sortedFileItemList.stream().filter(fileItem -> fileItem.getFile().isDirectory()).collect(Collectors.toList());
        foldersOnly.sort((fileName1, fileName2) -> fileName1.getFile().getName().compareTo(fileName2.getFile().getName()));

        List<FileItem> filesOnly = sortedFileItemList.stream().filter(fileItem -> !fileItem.getFile().isDirectory()).collect(Collectors.toList());
        filesOnly.sort((fileName1, fileName2) -> fileName1.getFile().getName().compareTo(fileName2.getFile().getName()));

        List<FileItem> sortedFilesAndFolders = new ArrayList<>();
        sortedFilesAndFolders.add(parentFolder);
        sortedFilesAndFolders.addAll(foldersOnly);
        sortedFilesAndFolders.addAll(filesOnly);

        return (ArrayList<FileItem>) sortedFilesAndFolders;
    }

    // Consider moving all search realted methods to the SearchFileList class or delete the class if not needed
    public ArrayList<FileItem> getSearchResults() {
        return this.searchResults;
    }

    public FolderContent setEveryFileItemSearchMatchedToFalse() {
        if (searchResults != null) {
            searchResults.forEach(fileItem -> fileItem.setSearchMatched(false));
        }
        return this;
    }

    //TODO Create Unit test for this Method
    //TODO add settings for different search method = first match, contents, exact match
    public FolderContent executeSearch(String searchTerm) {
        searchResults = new ArrayList<>();
        logger.debug("In executeSearch for = " + searchTerm);

        searchResults = (ArrayList<FileItem>) fileItems.stream().filter(fileItem -> fileItem.getFile().getName().startsWith(searchTerm)).collect(Collectors.toList());

        searchResults.forEach(fileItem -> {
//            logger.debug("matched file item title = [" + fileItem.getFile().getName() + "]");
            fileItem.setSearchMatched(true);
        });
        return this;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public FolderContent setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        return this;
    }

    public ArrayList<FileItem> getFileItems() {
        return fileItems;
    }

    public FileItem getParentFolder() {
        return parentFolder;
    }
}
