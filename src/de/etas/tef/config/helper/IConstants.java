package de.etas.tef.config.helper;

public interface IConstants
{
	public static final int EMPTY_INT = -1;
	public static final String[] EMPTY_STRING_ARRAY = {};
	public static final String EMPTY_STRING = "";
	public static final String SYMBOL_INIT_FILE_COMMENT_DASH = "--";
	public static final String SYMBOL_INIT_FILE_COMMENT_SEMICOLON = ";";
	public static final String SYMBOL_NEW_LINE = "\n";
	public static final String SYMBOL_LEFT_BRACKET = "[";
	public static final String SYMBOL_RIGHT_BRACKET = "]";
	public static final String SYMBOL_EQUAL = "=";
	public static final String SYMBOL_SPACE = " ";
	public static final String SYMBOL_LEFT_PARENTHESES_ = "("; 
	public static final String SYMBOL_RIGHT_PARENTHESES_ = ")"; 
	
	public static final int MAIN_SCREEN_WIDTH = 800;
	public static final int MAIN_SCREEN_HEIGHT = 700;
	
	public static final int BTN_DEFAULT_WIDTH = 80;
	public static final int LABEL_DEFAULT_WIDTH = 45;
	public static final int HEIGHT_HINT = 150;
	public static final int SEARCH_TEXT_HEIGHT = 24;
	
	public static final String[] CONFIG_FILE_EXTENSION = {"*.ini", "*.*"};
	public static final String[] CONFIG_FILE_NAME = {"Config File (*.ini)", "All Files (*.*)"};
	
	public static final String TXT_APP_TITLE = "Config File Tool";
	public static final String TXT_LABEL_FILE = "File";
	public static final String TXT_LABEL_TARGET = "Target";
	public static final String TXT_LABEL_BLOCK_LIST = "Blocks:";
	public static final String TXT_LABEL_EXEC_DIRECTORY = "Directory: ";
	public static final String TXT_BTN_SELECT = "Select";
	public static final String TXT_BTN_DIRECTORY = "Dir";
	public static final String TXT_BTN_RUN = "Run";
	public static final String TXT_BTN_ADD = "Add";
	public static final String TXT_BTN_DELETE = "Delete";
	public static final String TXT_BTN_SAVE = "Output";
	public static final String TXT_PM_NUMBER = "PM_NUMBER";
	public static final String TXT_BTN_CONNECT = "Connect";
	public static final String TXT_BTN_ACCEPT_SOURCE = "Accept";
	public static final String TXT_BTN_LEFT = "Left";
	public static final String TXT_BTN_RIGHT = "Right";
	public static final String TXT_GPIB_DEVICE_NAME = "DEVICE_NAME";
	public static final String TXT_GPIB_PM_NUMBER = "PM_NUMBER";
	public static final String TXT_GPIB_ADRESS = "GPIB_ADRESS";
	public static final String TXT_CONFIG_FILE = "Config File";
	public static final String TXT_LOCK_EDITING = "Edit Lock";
	public static final String TXT_TEMP = "TEMP";
	public static final String[] TABLE_TITLES = {"Name", "Value"};
	public static final String TXT_COPY = "Copy";
	public static final String TXT_PASTE = "Paste";
	public static final String TXT_INI_FILE_DEFAULT = "DEFAULT";
	public static final String TXT_REPOSITORY_FILES = "Repository Files";
	public static final String TXT_CONFIG_FILES = "INI Files";
	public static final String TXT_MENU_COPY = "Copy";
	public static final String TXT_MENU_PASTE = "Paste";
	public static final String TXT_MENU_ADD_DIR = "Add Directory";
	public static final String TXT_MENU_ADD_FILE = "Add File";
	public static final String TXT_MENU_DELETE = "Delete";
	public static final String TXT_REPOSITORY_REMOTE = "Repository Remote: ";
	public static final String TXT_REPOSITORY_LOCAL = "Repository Local: ";
	public static final String TXT_BROWSE = "Browse...";
	public static final String TXT_OK = "OK";
	public static final String TXT_CANCEL = "Cancel";
	public static final String TXT_DEFAULT_DIR = "DEFAULT";
	public static final String TXT_DEFAULT_INI = "default.ini";
	public static final String TXT_DEFAULT_FILE = "default.txt";
	public static final String TXT_REPOSITORY_README_FILE = "README.md";
	public static final String TXT_FILE_HISTORY = "File History"; 
	public static final String TXT_COMMITS = "Commits"; 
	public static final String TXT_TOOLBAR_ADD_FILE = "Add File";
	public static final String TXT_TOOLBAR_ADD_DIR = "Add Directory";
	public static final String TXT_TOOLBAR_FILE_HISTORY = "File History";
	public static final String TXT_TOOLBAR_COMMIT_HISTORY = "Commit History";
	public static final String[] ARRAY_TABLE_HISTORY_HEADER = {"Time", "Comments", "User"};
	public static final String[] ARRAY_TABLE_COMMITS_HEADER = {"Time", "Comments", "User"};
	public static final String TXT_SEARCH_FILE_NAME = "Search File Name";
	public static final String TXT_SEARCH_FILE_CONTENT = "Search File Content";
	
	
	public static final int ACTION_NEW_FILE_SELECTED = 0x00;
	public static final int ACTION_LOG_WRITE_INFO = 0x01;
	public static final int ACTION_LOG_WRITE_ERROR = 0x02;
	public static final int ACTION_SET_SHOW_CONFIG_BLOCKS = 0x03;
	public static final int ACTION_SOURCE_BLOCK_UPDATED = 0x06;
	public static final int ACTION_PARAMETER_UPDATE = 0x08;
	public static final int ACTION_CONNECT_SELECTED = 0x10;
	public static final int ACTION_PARAMETER_UPDATED = 0x0A;
	public static final int ACTION_TAKE_SOURCE_PARAMETERS_START = 0x0B;
	public static final int ACTION_LEFT_SELECTED = 0x0D;
	public static final int ACTION_GPIB_SOURCE_FINISHED = 0x0F;
	public static final int ACTION_SOURCE_PARAMETER_SELECTED = 0x11;
	public static final int ACTION_RIGHT_SELECTED = 0X12;
	public static final int ACTION_COMPOSITE_CHANGED = 0x13;
	public static final int ACTION_SOURCE_SAVE_FILE_FINISHED = 0x14;
	public static final int ACTION_BLOCK_SELECTED = 0x15;
	public static final int ACTION_LOCK_SELECTION_CHANGED = 0x16;
	public static final int ACTION_DROP_NEW_FILE_SELECTED = 0x18;
	public static final int ACTION_ADD_NEW_BLOCK = 0x04;
	public static final int ACTION_DELETE_BLOCK = 0x05;
	public static final int ACTION_LOG_WRITE_WARNING = 0x07;
	public static final int ACTION_MENU_ADD = 0x09;
	public static final int ACTION_MENU_DELETE = 0x0C;
	public static final int ACTION_COPY_BLOCK = 0x0E;
	public static final int ACTION_PASTE_BLOCK = 0x17;
	public static final int ACTION_COPY_PARAMETER = 0x19;
	public static final int ACTION_PASTE_PARAMETER = 0x1A;
	public static final int ACTION_COMMENT_SAVED = 0x1B;
	public static final int ACTION_PARAMETER_SELECTED = 0x1C;
	public static final int ACTION_GET_SELECTED_PATH = 0x1D;
	public static final int ACTION_SELECTED_SEARCH_PATH = 0x1E;
	public static final int ACTION_FILE_SEARCH_FINISHED = 0x1F;
	public static final int ACTION_PROGRESS_BAR_DISPLAY = 0x20;
	public static final int ACTION_PROGRESS_BAR_UPDATE = 0x21;
	public static final int ACTION_OPEN_INI_FILE = 0x22;
	public static final int ACTION_PARSER_INI_FINISH = 0x23;
	public static final int ACTION_SEARCH_TYPE_CHANGED = 0x24;
	public static final int ACTION_SET_FILE = 0x25;
	
	public static final int FOCUS_NONE = 0x00;
	public static final int FOCUS_BLOCK = 0x01;
	public static final int FOCUS_PARAMETER = 0x02;
	
	// internal data for GUI Composite
	public static final String DATA_PATH = "data_path";
	public static final String DATA_TYPE = "data_type";
	public static final String DATA_VALUE = "data_value";
	
	public static final int DATA_TYPE_DIR = 0x00;
	public static final int DATA_TYPE_FILE = 0x01;

	// Setting File
	public static final String SETTING_FILE = "settings.json";
	
	// Operation Status
	public static final int OPERATION_FAILED = 0x00;
	public static final int OPERATION_SUCCESS = 0x01;
	public static final int OPERATION_INPUT_ERROR = 0x02;
	
	// supported file type
	public static final String[] SUPPORT_FILE_TYPE = {".ini", ".txt"};
	
	// checker
	public static final String CHECKER_FILE_NAME = "CheckerFileName";
	public static final int SEARCH_CONTENT = 0x00;
	public static final int SEARCH_FILE_NAME = 0x01;
	public static final int ACTION_GET_FILE_HISTORY = 0x26;
	public static final int ACTION_SEARCH_CONTENT = 0x27;
	public static final int ACTION_TOOLBAR_ITEM = 0x28;
	public static final int ACTION_UPDATE_FILE_LIST_NUM = 0x29;
	public static final int ACTION_START_SEARCH = 0x30;
	public static final int ACTION_SEARCH_NEXT = 0x31;
	public static final int ACTION_GET_SEARCH_TEXT = 0x32;
	public static final int ACTION_GET_REPLACE_TEXT = 0x33;
	public static final int ACTION_SET_SEARCH_TEXT = 0x34;
	public static final int ACTION_SET_REPLACE_TEXT = 0x35;
	public static final int ACTION_REPLACE_TEXT = 0x36;
	
}
