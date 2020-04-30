package de.etas.tef.config.controller;

public interface IConstants
{
	// Constants Text
	public static final String EMPTY_STRING = "";
	public static final String TXT_INI_FILE_DEFAULT = "DEFAULT";
	public static final String TXT_REPOSITORY_FILES = "Repository Files";
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
	
	// internal data for GUI Composite
	public static final String DATA_PATH = "data_path";
	public static final String DATA_TYPE = "data_type";
	
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

}
