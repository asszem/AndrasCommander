package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

public class FolderContent {
    private static Logger logger = LogManager.getLogger(FolderContent.class);
    private ArrayList<FileItem> fileItems;
    private String folderPath;

    public FolderContent(String folderPath) {
        this.folderPath = folderPath;
        loadFiles(folderPath);
    }

    public FolderContent loadFiles(String folderPath) {
        logger.debug("Loading files and folders from folder = " + folderPath);

        File[] fileList = new File(folderPath).listFiles();
        fileItems = new ArrayList<>();

        // Add the parent folder (if exists) to the list
        File currentDir = new File(folderPath);
        File parentDir;
        if (currentDir.getParent() != null) {
            parentDir = currentDir.getParentFile();
        } else {
            parentDir = currentDir;
        }
        FileItem currentFileItem = new FileItem(currentDir, true, false, true);
        fileItems.add((currentFileItem));


        for (File file : fileList) {
            currentFileItem = new FileItem(file, false, false, false);
            fileItems.add(currentFileItem);
        }

        return this; // so it can be chained
    }

    // TODO handle case when fileItems is empty and folderPath is not known
    public ArrayList<FileItem> getFileItems() {
        return fileItems;
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
