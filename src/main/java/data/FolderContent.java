package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FolderContent {
    private static Logger logger = LogManager.getLogger(FolderContent.class);
    private ArrayList<FileItem> fileItems; // does NOT contain the parent folder!
    private File parentFolder;
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
            parentFolder = currentDir.getParentFile();
        } else {
            parentFolder = currentDir;
        }

        // Get the files in the folder
        File[] fileList = new File(folderPath).listFiles();
        fileItems = new ArrayList<>();
        FileItem currentFileItem;
        for (File file : fileList) {
            currentFileItem = new FileItem(file, false, false);
            fileItems.add(currentFileItem);
        }

        return this; // so it can be chained
    }

    // TODO handle case when fileItems is empty and folderPath is not known
    public ArrayList<FileItem> getFileItems() {
        return fileItems;
    }
    public File getParentFolder(){
        return parentFolder;
    }

    // Directories first
    public ArrayList<FileItem> sortFileItemsByName(){
        List<FileItem> sortedFileItemList = fileItems;
//        System.out.println("\n sorted by name order");
        sortedFileItemList.sort((fileName1, fileName2) -> fileName1.getFile().getName().compareTo(fileName2.getFile().getName()));
//        sortedFileItemList.forEach(fileItem -> System.out.println(fileItem.getFile().getName()));

//        System.out.println("\n get directories only");
        List<FileItem> foldersOnly = sortedFileItemList.stream().filter(fileItem -> fileItem.getFile().isDirectory()).collect(Collectors.toList());
        foldersOnly.sort((fileName1, fileName2) -> fileName1.getFile().getName().compareTo(fileName2.getFile().getName()));
//        foldersOnly.forEach(fileItem -> System.out.println(fileItem.getFile().getName()));

//        System.out.println("\n get files only");
        List<FileItem> filesOnly = sortedFileItemList.stream().filter(fileItem -> !fileItem.getFile().isDirectory()).collect(Collectors.toList());
        filesOnly.sort((fileName1, fileName2) -> fileName1.getFile().getName().compareTo(fileName2.getFile().getName()));
//        filesOnly.forEach(fileItem -> System.out.println(fileItem.getFile().getName()));

//        System.out.println("\n Sorted Files and Folder, folders first");
        List<FileItem> sortedFilesAndFolders = new ArrayList<>();
        sortedFilesAndFolders.addAll(foldersOnly);
        sortedFilesAndFolders.addAll(filesOnly);
        sortedFilesAndFolders.forEach(fileItem -> logger.debug(fileItem.getFile().getName()));

        return (ArrayList<FileItem>) sortedFilesAndFolders;
    }

    // Consider moving this to the SearchFileList class or delete the class if not needed
    //TODO Create Unit test for this Method
    public ArrayList<Integer> getSearchResultsIndex(String searchTerm) {
        ArrayList<Integer> searchResults = new ArrayList<Integer>();
        logger.debug("Search term to be looking for = " + searchTerm);

//        List<String> resultList = Arrays.stream(presets)
//                .filter(x -> x.startsWith("C"))
//                .collect(Collectors.toList());

//        searchResults.forEach(searchResult -> System.out.println("Index matched = " + searchResult + " file = " + foldersFirstThenFiles.get(searchResult)));
        return searchResults;
    }

    public FileItem getHighlightedFile() {
        return fileItems.stream().filter(fileItem -> fileItem.isHighlighted()).findFirst().orElse(null);
    }

    public FolderContent setHighlightedFile(String fileName) {
        //Set the file highlighted based on file name
        // reset previous item
        fileItems.stream().filter(fileItem -> fileItem.isHighlighted()).findFirst().get().setHighlighted(false);
        fileItems.stream().filter(fileItem -> fileItem.getFile().getName().equals(fileName)).findFirst().get().setHighlighted(true);
        System.out.println("highlighted file = " + getHighlightedFile().getFile().getName());
        return this;
    }

    public FolderContent setHighlightedFile(int index) {
        fileItems.stream().filter(fileItem -> fileItem.isHighlighted()).findFirst().get().setHighlighted(false);
        fileItems.get(index).setHighlighted(true);
        System.out.println("highlighted file = " + getHighlightedFile().getFile().getName());
        return this;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public FolderContent setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        return this;
    }
}
