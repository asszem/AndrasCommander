package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Vector;

public class FileList {
    private static Logger logger = LogManager.getLogger(FileList.class);
    private Vector<File> filesAndFolders;
    private Vector<File> filesOnly;
    private Vector<File> foldersOnly;
    private Vector<File> foldersFirstThenFiles;

    public Vector<File> getFoldersFirstThenFiles(String folderpath){
        if (foldersOnly==null || filesOnly == null){
            loadFiles(folderpath);
        }
        foldersFirstThenFiles = new Vector<>();
        foldersFirstThenFiles.addAll(foldersOnly);
        foldersFirstThenFiles.addAll(filesOnly);
        return foldersFirstThenFiles;
    }

    public Vector<File> getFoldersOnly (String folderPath){
       if (foldersOnly== null) {
           loadFiles(folderPath);
       }
       return foldersOnly;
    }

    public Vector<File> getFilesOnly(String folderPath) {
        if (filesOnly == null) {
            loadFiles(folderPath);
        }
        return filesOnly;
    }

    public Vector<File> getFilesAndFolders(String folderPath) {
        if (filesAndFolders == null) {
            loadFiles(folderPath);
        }
        return filesAndFolders;
    }

    public FileList loadFiles(String folderPath) {
        logger.debug("Loading files and folders from folder = " + folderPath);
        File[] fileList = new File(folderPath).listFiles();
        //create a new list every time this method is called
        filesOnly = new Vector<File>();
        foldersOnly = new Vector<File>();
        filesAndFolders = new Vector<File>();

        // Add the parent directory as the first element (index 0) of the foldersOnly list
        File currentDir = new File(folderPath);
        if (currentDir.getParent()!=null){
            foldersOnly.add(currentDir.getParentFile());
        } else {
            foldersOnly.add(currentDir); // Return itself if there is now upper folder in file hierarchy
        }


        for (File file : fileList) {
            filesAndFolders.add(file);
            if (file.isFile()) {
                filesOnly.add(file);
            } else {
               foldersOnly.add(file);
            }
        }
        return this; // so it can be chained
    }
}
