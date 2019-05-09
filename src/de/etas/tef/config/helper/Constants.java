package de.etas.tef.config.helper;

public final class Constants
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
	public static final int MAIN_SCREEN_HEIGHT = 450;
	
	public static final int BTN_DEFAULT_WIDTH = 80;
	public static final int LABEL_DEFAULT_WIDTH = 45;
	public static final int HEIGHT_HINT = 150;
	public static final int SEARCH_TEXT_HEIGHT = 24;
	
	public static final String[] CONFIG_FILE_EXTENSION = {"*.ini", "*.*"};
	public static final String[] CONFIG_FILE_NAME = {"Config File (*.ini)", "All Files (*.*)"};
	
	public static final String TXT_APP_TITLE = "Config File Editor";
	public static final String TXT_LABEL_FILE = "File";
	public static final String TXT_LABEL_TARGET = "Target";
	public static final String TXT_LABEL_BLOCK_LIST = "Blocks:";
	public static final String TXT_LABEL_EXEC_DIRECTORY = "Directory: ";
	public static final String TXT_BTN_SELECT = "Select";
	public static final String TXT_BTN_RUN = "Run";
	public static final String TXT_BTN_ADD = "Add";
	public static final String TXT_BTN_DELETE = "Delete";
	public static final String TXT_BTN_SAVE = "Save";
	public static final String TXT_PM_NUMBER = "PM_NUMBER";
	public static final String TXT_BTN_CONNECT = "Connect";
	public static final String TXT_BTN_ACCEPT_SOURCE = "Accept";
	public static final String TXT_BTN_LEFT = "Left";
	public static final String TXT_BTN_RIGHT = "Right";
	public static final String TXT_GPIB_DEVICE_NAME = "DEVICE_NAME";
	public static final String TXT_GPIB_PM_NUMBER = "PM_NUMBER";
	public static final String TXT_GPIB_ADRESS = "GPIB_ADRESS";
	public static final String TXT_CONFIG_FILE = "Config File";
	public static final String[] TABLE_TITLES = {"Name", "Value"};
	
	
	public static final int ACTION_NEW_FILE_SELECTED = 0x00;
	public static final int ACTION_LOG_WRITE_INFO = 0x01;
	public static final int ACTION_LOG_WRITE_ERROR = 0x02;
//	public static final int ACTION_TARGET_NEW_FILE_SELECTED = 0x03;
	public static final int ACTION_SOURCE_BLOCK_UPDATED = 0x06;
//	public static final int ACTION_TARGET_BLOCK_UPDATED = 0x07;
	public static final int ACTION_PARAMETER_UPDATE = 0x08;
//	public static final int ACTION_TARGET_PARAMETER_UPDATE = 0x09;
	public static final int ACTION_CONNECT_SELECTED = 0x10;
	public static final int ACTION_PARAMETER_UPDATED = 0x0A;
	public static final int ACTION_TAKE_SOURCE_PARAMETERS_START = 0x0B;
	public static final int ACTION_TAKE_SOURCE_PARAMETERS_FINISHED = 0x0C;
	public static final int ACTION_LEFT_SELECTED = 0x0D;
	public static final int ACTION_GPIB_SOURCE_FINISHED = 0x0F;
	public static final int ACTION_SOURCE_PARAMETER_SELECTED = 0x11;
	public static final int ACTION_RIGHT_SELECTED = 0X12;
	public static final int ACTION_COMPOSITE_CHANGED = 0x13;
	public static final int ACTION_SOURCE_SAVE_FILE_FINISHED = 0x14;
	public static final int ACTION_BLOCK_SELECTED = 0x15;
	public static final int ACTION_LOCK_SELECTION_CHANGED = 0x16;
//	public static final int ACTION_DROP_TARGET_NEW_FILE_SELECTED = 0x17;
	public static final int ACTION_DROP_NEW_FILE_SELECTED = 0x18;
}
