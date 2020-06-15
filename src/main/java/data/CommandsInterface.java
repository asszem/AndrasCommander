package data;

import java.util.ArrayList;

public interface CommandsInterface {

    public void handleCommand(String command);
    public void openHighlighted();
    public void moveCursor(String direction);
    public void goUpToParentFolder();
    public void goBackInHistory();
    public void changeFolder(String folderPath);
    public void refreshView();
    public void enterSearchMode();
    public void executeSearch();
    public void exitSearchMode();
    public void setHighlightSearchResults(boolean highlightSearchResults);
}
