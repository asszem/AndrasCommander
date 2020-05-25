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

### Version 0.2 - Basic Folder Navigation
- [x] Display folders first, then files
- [x] Pressing Enter on a folder goes to the folder
- [ ] Display .. for parent folder
- [ ] Pressing Enter on .. goes up one folder
- [ ] VIM like: "gu" for go up one folder 
- [ ] VIM like: "gb" for go back to prev folder
- [ ] VIM like: ":e" for refreshing the panel
- [ ] Start AC from last opened folder

### Version 0.3 - Basic Search
- [ ] Implement a basic search invoked with space key
    - [ ] search term completed with enter key 
    - [ ] first matching item selected
    - [ ] VIM like: "n"/"N" keys selects next / prev matching item
    - [ ] highlight other matching items
    - [ ] VIM like: :noh (no highlight) to clear search results
    
### Version 0.4 - Basic File Actions - Rename
- [ ] VIM like: rename highlighted file with "r"

## MVP Reached
At this point it should be an actually usable product.

### Version 1.0 - Improved display - File Details
 - [ ] Display file details (size in human readable format, modified date)
 - [ ] VIM like (netrw): "s" for sort by (name, name-reversed, date, date-reversed, etc)
 - [ ] Display line numbers for files
 - [ ] VIM like: jump to line with #linenumber"g"
 - [ ] Automatically size column width to fit data (file name, date modified, etc)

### Version 1.1 - Improved search - Auto Jump
 - [ ] "insert" mode: disable VIM bindings ant enable Auto Jump
 - [ ] In insert mode jump automatically to the first matching result

### Version 1.2 - Improved display - Basic Multiple Panes
- [ ] VIM like: "sp" and "vs" to add a new viewing pane
- [ ] VIM like: jump between panes with Ctrl-W and "h"/"j"/"k"/"l"
- [ ] VIM like: :on (only) maximize current pane (but remember previous panes)
- [ ] :res (reset) - reset the previous displayed pane(s)

### Version 1.3 - Improved File Actions - select, copy, move, delete
- [ ] NC like: select multiple files under cursor with "Ins"
- [ ] Copy / move / delete selected file(s)

### Version 1.4 - Preview Panels
 - [ ] File preview panel - open and close with a command
 - [ ] Folder preview panel - display5 last changed file in highlighted folder
 - [ ] Display scrollable content of highlighted file
 
### Version 1.5 - Improved QoL
- [ ] Remap Home, End, PageUp, PageDown
- [ ] Toggle keybindings list
    - [ ] Static info of available commands
    - [ ] Dinamic info with random reminders, least used commands, etc

## Future Feature Plans
- Aliases. Set full word aliases for actions (if typed quickly before the keypress is parsed)
- Basic Theming (background, foreground color, cursor color, file color)
- Bookmarks (VIM like marks, named bookmarks and a NerdTree like Bookmark panel)
- File coloring based on properties (last changed, type, etc)
- Remap keys (with conflict warnings)
- VIM like command mode invoked with ":" 
- Improved Multiple panes
    - Resize panes with VIM like Ctrl-W "+" and "-" and ">" and "<" keys
    - Rename panes 
- Tabs 
- Ranger like behaviour when entering a folder (new pane opens to the right)

## Forbidden features
- Mouse support
