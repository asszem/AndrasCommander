package data;

import java.util.ArrayList;

public interface CommandsInterface {

    public void handleCommand(String command);
    public void openHighlighted();
    public void moveCursor(String direction);
    public void goUpToParentFolder();
    public void goBackInHistory();
    public void changeFolder(String folderPath, boolean preserveHistory);
    public void refreshView();
    public void enterSearchMode();
    public void executeSearch();
    public void exitSearchMode();
    public void jumpToSearchResult(String direction);
    public void setHighlightSearchResults(boolean highlightSearchResults);
    public void toggleSortOrder();
    public void toggleSortBy();
}
