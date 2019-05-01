package de.etas.tef.config.helper;

public final class Constants
{
	public static final String EMPTY_STRING = "";
	public static final int EMPTY_INT = -1;
	public static final String SYMBOL_INIT_FILE_COMMENT_DASH = "--";
	public static final String SYMBOL_INIT_FILE_COMMENT_SEMICOLON = ";";
	public static final String SYMBOL_NEW_LINE = "\n";
	public static final String SYMBOL_LEFT_BRACKET = "[";
	public static final String SYMBOL_RIGHT_BRACKET = "]";
	public static final String SYMBOL_EQUAL = "=";
	
	public static final int MAIN_SCREEN_WIDTH = 800;
	public static final int MAIN_SCREEN_HEIGHT = 450;
	
	public static final int BTN_DEFAULT_WIDTH = 80;
	public static final int LABEL_DEFAULT_WIDTH = 45;
	
	public static final String[] CONFIG_FILE_EXTENSION = {"*.ini", "*.*"};
	public static final String[] CONFIG_FILE_NAME = {"Config File", "All Files"};
	
	public static final String TXT_APP_TITLE = "Config File Editor";
	public static final String TXT_LABEL_SOURCE = "Source";
	public static final String TXT_LABEL_TARGET = "Target";
	public static final String TXT_LABEL_BLOCK_LIST = "Blocks:";
	public static final String TXT_LABEL_EXEC_DIRECTORY = "Directory: ";
	public static final String TXT_BTN_SELECT = "Select";
	public static final String TXT_BTN_RUN = "Run";
	public static final String[] EMPTY_STRING_ARRAY = {};
	
	public static final String[] TABLE_TITLES = {"Name", "Value"};
	public static final String TXT_BTN_ADD = "Add";
	public static final String TXT_BTN_DELETE = "Delete";
	public static final String TXT_BTN_SAVE = "Save";
	public static final String PM_NUMBER = "PM_NUMBER";
	public static final String TXT_BTN_CONNECT = "Connect";
	public static final String TXT_BTN_ACCEPT_SOURCE = "Accept";
	public static final String TXT_BTN_GPIB = "GPIB";
	
	public static final int ACTION_SOURCE_NEW_FILE_SELECTED = 0x00;
	public static final int ACTION_LOG_WRITE_INFO = 0x01;
	public static final int ACTION_LOG_WRITE_ERROR = 0x02;
	public static final int ACTION_TARGET_NEW_FILE_SELECTED = 0x03;
	public static final int ACTION_SOURCE_BLOCK_UPDATED = 0x06;
	public static final int ACTION_TARGET_BLOCK_UPDATED = 0x07;
	public static final int ACTION_SOURCE_PARAMETER_UPDATE = 0x08;
	public static final int ACTION_TARGET_PARAMETER_UPDATE = 0x09;
	public static final int ACTION_CONNECT_SELECTED = 0x10;
	public static final int ACTION_PARAMETER_UPDATED = 0x0A;
	public static final int ACTION_TAKE_SOURCE_PARAMETERS_START = 0x0B;
	public static final int ACTION_TAKE_SOURCE_PARAMETERS_FINISHED = 0x0C;
	public static final int ACTION_GPIB_SELECTED = 0x0D;
	public static final int ACTION_GPIB_SOURCE_FINISHED = 0x0F;
	public static final int ACTION_SOURCE_PARAMETER_SELECTED = 0x11;
	
	public static final String TXT_GPIB_DEVICE_NAME = "DEVICE_NAME";
	public static final String TXT_GPIB_PM_NUMBER = "PM_NUMBER";
	public static final String TXT_GPIB_ADRESS = "GPIB_ADRESS";
	
	public static final String TXT_CONFIG_FILE = "Config File";
}
