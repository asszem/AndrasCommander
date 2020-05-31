package data;

import java.util.ArrayList;

public interface CommandsInterface {

    public void displayCommand(String command);
    public void moveCursor(String direction);
    public void goUpToParentFolder();
    public void goBackInHistory();
    public void changeFolder(String folderPath);
    public void refreshView();
    public void highlightSearchResult(ArrayList matchedIndexes);
}
