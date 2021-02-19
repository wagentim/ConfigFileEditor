package de.etas.tef.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IImageConstants;

public class ImageController
{
	private final Map<Integer, Image> mapper;
	private final Display display;
	
	public ImageController(Display display)
	{
		this.display = display;
		mapper = new HashMap<Integer, Image>();
		initImages();
	}
	
	private void initImages()
	{
		mapper.put(IImageConstants.IMAGE_FILE, createImage("icons/file.png"));
		mapper.put(IImageConstants.IMAGE_SEARCH_CONTENT, createImage("icons/search_doc.png"));
		mapper.put(IImageConstants.IMAGE_REPLACE, createImage("icons/replace.png"));
//		mapper.put(IImageConstants.IMAGE_EXIT, createImage("icons/exit.png"));
//		mapper.put(IImageConstants.IMAGE_ABOUT, createImage("icons/about.png"));
//		mapper.put(IImageConstants.IMAGE_LOCK, createImage("icons/lock.png"));
//		mapper.put(IImageConstants.IMAGE_UNLOCK, createImage("icons/unlock.png"));
//		mapper.put(IImageConstants.IMAGE_TIME, createImage("icons/time.png"));
//		mapper.put(IImageConstants.IMAGE_LOAD, createImage("icons/load.png"));
		mapper.put(IImageConstants.IMAGE_SEARCH, createImage("icons/search_24.png"));
		mapper.put(IImageConstants.IMAGE_CANCEL, createImage("icons/cancel.png"));
		mapper.put(IImageConstants.IMAGE_ADD, createImage("icons/add.png"));
//		mapper.put(IImageConstants.IMAGE_REMOVE, createImage("icons/remove.png"));
//		mapper.put(IImageConstants.IMAGE_COPY, createImage("icons/copy.png"));
//		mapper.put(IImageConstants.IMAGE_PASTE, createImage("icons/paste.png"));
//		mapper.put(IImageConstants.IMAGE_RECORD, createImage("icons/record.png"));
//		mapper.put(IImageConstants.IMAGE_ROOT, createImage("icons/root.png"));
//		mapper.put(IImageConstants.IMAGE_EDITABLE_OUTLINE, createImage("icons/editable_outline.png"));
//		mapper.put(IImageConstants.IMAGE_EDITABLE_COLOR, createImage("icons/editable_color.png"));
//		mapper.put(IImageConstants.IMAGE_HOME, createImage("icons/home.png"));
//		mapper.put(IImageConstants.IMAGE_OPEN, createImage("icons/open.png"));
//		mapper.put(IImageConstants.IMAGE_SETTING, createImage("icons/setting.png"));
		mapper.put(IImageConstants.IMAGE_FOLDER, createImage("icons/folder.png"));
//		mapper.put(IImageConstants.IMAGE_ETAS, createImage("icons/etas.png"));
		mapper.put(IImageConstants.IMAGE_TITLE, createImage("icons/title.png"));
//		mapper.put(IImageConstants.IMAGE_PIN, createImage("icons/pin.png"));
//		mapper.put(IImageConstants.IMAGE_CONNECT, createImage("icons/connect.png"));
		mapper.put(IImageConstants.IMAGE_PROGRESSBAR_16, createImage("icons/progressbar16.png"));
		mapper.put(IImageConstants.IMAGE_CALENDAR_16, createImage("icons/calendar16.png"));
		mapper.put(IImageConstants.IMAGE_RUN, createImage("icons/run.png"));
		mapper.put(IImageConstants.IMAGE_ARROW_DOWN, createImage("icons/arrow_down.png"));
		mapper.put(IImageConstants.IMAGE_REFRESH, createImage("icons/refresh.png"));
		mapper.put(IImageConstants.IMAGE_DELETE, createImage("icons/delete.png"));
		mapper.put(IImageConstants.IMAGE_DELETE_ITEM, createImage("icons/delete_item.png"));
	}
	
	private Image createImage(String path)
	{
		return new Image(display, ImageController.class.getClassLoader().getResourceAsStream(path));
	}

	public Image getImage(int key)
	{
		return mapper.get(key);
	}
}
