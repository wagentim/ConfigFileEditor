package de.etas.tef.config.controller;

public interface IConstants
{
	// Constants Text
	public static final String EMPTY_STRING = "";
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
	
	// internal data for GUI Composite
	public static final String DATA_PATH = "data_path";
	public static final String DATA_TYPE = "data_type";
	public static final String DATA_VALUE = "data_value";
	
	public static final int DATA_TYPE_DIR = 0x00;
	public static final int DATA_TYPE_FILE = 0x01;

	// Constants Numer
	public static final int HEIGHT_HINT = 150;
	
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
	
	public static final int EMPTY_INT = -1;
	public static final String[] EMPTY_STRING_ARRAY = {};
	public static final String SYMBOL_INIT_FILE_COMMENT_DASH = "--";
	public static final String SYMBOL_INIT_FILE_COMMENT_SEMICOLON = ";";
	public static final String SYMBOL_NEW_LINE = "\n";
	public static final String SYMBOL_LEFT_BRACKET = "[";
	public static final String SYMBOL_RIGHT_BRACKET = "]";
	public static final String SYMBOL_EQUAL = "=";
	public static final String SYMBOL_SPACE = " ";
	public static final String SYMBOL_LEFT_PARENTHESES_ = "("; 
	public static final String SYMBOL_RIGHT_PARENTHESES_ = ")";

}
