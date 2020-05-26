package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HistoryWriter {
    private static Logger logger = LogManager.getLogger(HistoryWriter.class);
    private String path = System.getProperty("user.home");
    private String historyFileName = path + "\\.ac_history";

    public void createHistoryFileIfNotExist() {
        try {
            File historyFile = new File(historyFileName);
            if (historyFile.createNewFile()) {
                logger.debug("History file created = " + historyFile.getName());
            }
        } catch (IOException e) {
            logger.debug("Error while creating history file");
            e.printStackTrace();
        }
    }

    public void appendToHistory(String text) {
        try {
            FileWriter myWriter = new FileWriter(historyFileName, true);
            myWriter.write(text + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readEntireHistory() {
        ArrayList<String> result = new ArrayList<>();
        try {
            File historyFile = new File(historyFileName);
            Scanner myReader = new Scanner(historyFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getLastHistoryItem() {
        ArrayList<String> list = readEntireHistory();
        return list.get(list.size() - 1);
    }

    public String getLastHistoryAtIndex(int index) {
        ArrayList<String> list = readEntireHistory();
        return list.get(index);
    }

    // For testing purposes
    public static void main(String[] args) {
        HistoryWriter historyWriter = new HistoryWriter();
        historyWriter.createHistoryFileIfNotExist();
        Random rnd = new Random();
        for (int i = 0; i < rnd.nextInt(10); i++) {
            historyWriter.appendToHistory("Random addition line " + i);
        }
        historyWriter.readEntireHistory().forEach(line -> System.out.println(line));
        System.out.println("The last item is = " + historyWriter.getLastHistoryItem());
        System.out.println("the ifirst item is  = " + historyWriter.getLastHistoryAtIndex(0));
    }
}
