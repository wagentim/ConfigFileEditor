package de.etas.tef.config.controller;

public interface IConstants
{
	// Constants Text
	public static final String EMPTY_STRING = "";
	public static final String TXT_INI_FILE_DEFAULT = "DEFAULT";
	public static final String TXT_FILES = "Files";
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
	public static final String TXT_DEFAULT = "DEFAULT";
	
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
	
	

}
