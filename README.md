# AndrasCommander

Simple File Manager with VIM like motions. 
This is a learning project, nothing serious :)

## MVP Feature Plans

[**DONE**] Version 0.1 - Basic File Display and Navigation
- [x] Display files from a directory 
    - [x] Highlight the first file
- [x] Implement VIM like keystroke detection (normal mode)
    - [x] Display last pressed key and pressed keys list
    - [x] Handle single and multiple keystrokes
    - [x] VIM like: be able to move the cursor with "j"/"k" 
    - [x] Handle parsing special keys (ESC, Shift, Enter, etc)
    - [x] VIM like: be able to move the cursor to the top/bottom with "gg"/"G"
- [x] Quit with ":q" ENTER
- [x] Execute the highlighted file when "Enter" key pressed
- [x] Scrollpane viewport follows cursor
- [x] Remap cursor keys toj j/k

[**DONE**] Version 0.2 - Basic Folder Navigation
- [x] Display folders first, then files
- [x] Pressing Enter on a folder opens the folder
- [x] Display .. for parent folder
- [x] Pressing Enter on .. goes up one folder
- [x] VIM like: "gu" for go up one folder 
- [x] VIM like: "gb" for go back to prev folder
- [x] VIM like: ":e" for refreshing the panel
- [x] Start AC from last opened folder

### Version 0.3 - Basic Search
- [x] Implement a basic search invoked with space key
    - [x] toggle search mode with space key
    - [x] search term completed with enter key 
    - [x] first matching item selected
- [x] VIM like: "n"/"N" keys selects next / prev matching item
- [x] Highlight other matching items (after Enter)
- [ ] VIM like: :noh (no highlight) to clear search results
    
### Version 0.4 - Basic File Actions - Rename
- [ ] VIM like: rename highlighted file with "r"

### Version 0.5 - Basic Info panels
- [ ] Display History Panel
- [ ] Display Available Keys Panel

## MVP Reached
At this point it should be an actually usable product.

### Version 1.01 - Improved display - File Details
 - [ ] Display file details (size in human readable format, modified date)
 - [ ] VIM like (netrw): "s" for sort by (name, name-reversed, date, date-reversed, etc)
 - [ ] Automatically size column width to fit data (file name, date modified, etc)
 - [ ] Display line numbers for files
 - [ ] VIM like: jump to line with #linenumber"g"
 - [ ] File thumbnails

### Version 1.02 - Improved search types
- [ ] Implement different search types  (starts with, contains, equals, wildcards, search by Date, size, etc)
- [ ] Display search type in FilePanel 
- [ ] Toggle search type with Ctrl-S while in Search mode
- [ ] Trigger search mode in s specific search type with a VIM like binding (/ or ?)

### Version 1.1 - Improved display - Basic Multiple Panes
- [ ] Get default panel position, width and height from config file
- [ ] VIM like: "sp" and "vs" to add a new horizontal or vertical viewing pane
- [ ] VIM like: jump between panes with Ctrl-W and "h"/"j"/"k"/"l"
- [ ] VIM like: :on (only) maximize current pane (but remember previous panes)
- [ ] :res (reset) - reset the previous displayed pane(s)

### Version 1.2 - Nerdtree like Bookmarks panel and Vim like marks
- [ ] - VIM like: "m" for adding local marks and "'"<char> for jumping to mark
- [ ] - VIM like: "M" for adding global marks and "'"<char> for jumping to mark
- [ ] - Toggle bookmark pane
- [ ] - Add files/folders to bookmarks
- [ ] - Open bookmarks
- [ ] - Name and rename bookmarks

### Version 1.22 - Actionable History Panel
- [ ] - History panel can be navigated to, and items can be opened by selecting them with the cursor
- [ ] - Add keyboard shortcut Quickkey-s to open history items by pressing Ctrl-# of history item 

### Version 1.3 - Improved File Actions - select, copy, move, delete
- [ ] NC like: select multiple files under cursor with "Ins"
- [ ] Select/deselect all files
- [ ] Select files by wildcards
- [ ] Select files by specific commands (last changed, above size, etc)
- [ ] Copy / move / delete selected file(s)

### Version 1.4 - Preview Panels
 - [ ] File preview panel - open and close with a command
 - [ ] Folder preview panel - display5 last changed file in highlighted folder
 - [ ] Display scrollable content of highlighted file
 
### Version 2.x - QoL improvements
- [x] Matched file names are highlighted during typing of search term
- [ ] VIM like: "gf" - going forward (and not goto file!) in history
- [ ] Remap Home, End, PageUp, PageDown
- [ ] Toggle keybindings list
    - [ ] Static info of available commands
    - [ ] Dinamic info with random reminders, least used commands, etc
- [ ] Open system file explorer in current directory
- [ ] yank (copy) absolute path to current file / directory
- [ ] open shell (cmd, bash, conemu) at current location
- [ ] Save and load panel layout
- [x] Platform independence (Linux / Windows)

## Future Feature Plans
- Aliases. Set full word aliases for actions (if typed quickly before the keypress is parsed)
- Basic Theming (background, foreground color, cursor color, file color)
- File coloring based on properties (last changed, type, etc)
- Remap keys (with conflict warnings)
- VIM like command mode invoked with ":" 
- Improved Multiple panes
    - Resize panes with VIM like Ctrl-W "+" and "-" and ">" and "<" keys
    - Rename panes 
    - Set panel colors (focused, background, foreground)
- Tabs (add, switch)
- Ranger like behaviour when entering a folder (new pane opens to the right)
- History Panel (searchable, actionable, bookmarkable, panel specific and Global history)
- Compressed file handling - opening zip, rar, tar.gz, 7z, etc files

## Forbidden features
- Mouse support
