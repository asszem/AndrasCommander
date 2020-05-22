package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

public class FileList {
    private static Logger logger = LogManager.getLogger(FileList.class);
    private ArrayList<File> filesAndFolders;
    private ArrayList<File> filesOnly;

    public ArrayList<File> getFilesOnly(String folderPath) {
        if (filesOnly == null) {
            loadFiles(folderPath);
        }
        return filesOnly;
    }

    public ArrayList<File> getFilesAndFolders(String folderPath) {
        if (filesAndFolders == null) {
            loadFiles(folderPath);
        }
        return filesAndFolders;
    }

    public FileList loadFiles(String folderPath) {
        logger.debug("Loading files and folders from folder = " + folderPath);
        File[] fileList = new File(folderPath).listFiles();
        //create a new list every time this method is called
        filesOnly = new ArrayList<File>();
        filesAndFolders = new ArrayList<File>();

        for (File file : fileList) {
            filesAndFolders.add(file);
            if (file.isFile()) {
                filesOnly.add(file);
            }
        }
        return this; // so it can be chained
    }
}
