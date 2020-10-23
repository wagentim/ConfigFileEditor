package de.etas.tef.config.helper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageFactory
{
	private final Map<Integer, Image> mapper;
	private final Display display;
	
	public ImageFactory(Display display)
	{
		this.display = display;
		mapper = new HashMap<Integer, Image>();
		initImages();
	}
	
	private void initImages()
	{
//		mapper.put(IImageConstants.IMAGE_PASSWORD_TITLE, createImage("icons/title.png"));
//		mapper.put(IImageConstants.IMAGE_LOAD_OUTLINE, createImage("icons/load_outline.png"));
//		mapper.put(IImageConstants.IMAGE_LOAD_COLOR, createImage("icons/load_color.png"));
//		mapper.put(IImageConstants.IMAGE_EXIT, createImage("icons/exit.png"));
//		mapper.put(IImageConstants.IMAGE_ABOUT, createImage("icons/about.png"));
//		mapper.put(IImageConstants.IMAGE_LOCK, createImage("icons/lock.png"));
//		mapper.put(IImageConstants.IMAGE_UNLOCK, createImage("icons/unlock.png"));
//		mapper.put(IImageConstants.IMAGE_TIME, createImage("icons/time.png"));
//		mapper.put(IImageConstants.IMAGE_LOAD, createImage("icons/load.png"));
//		mapper.put(IImageConstants.IMAGE_SEARCH, createImage("icons/search.png"));
//		mapper.put(IImageConstants.IMAGE_CANCEL, createImage("icons/cancel.png"));
//		mapper.put(IImageConstants.IMAGE_ADD, createImage("icons/add.png"));
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
//		mapper.put(IImageConstants.IMAGE_FOLDER, createImage("icons/folder.png"));
//		mapper.put(IImageConstants.IMAGE_ETAS, createImage("icons/etas.png"));
//		mapper.put(IImageConstants.IMAGE_TITLE, createImage("icons/file_24.png"));
//		mapper.put(IImageConstants.IMAGE_PIN, createImage("icons/pin.png"));
//		mapper.put(IImageConstants.IMAGE_CONNECT, createImage("icons/connect.png"));
//		mapper.put(IImageConstants.IMAGE_ETAS, createImage("icons/disconnect.png"));
//		mapper.put(IImageConstants.IMAGE_HISTORY, createImage("icons/history.png"));
		mapper.put(IImageConstants.IMAGE_PLAY, createImage("icons/run.png"));
	}
	
	private Image createImage(String path)
	{
		return new Image(display, ImageFactory.class.getClassLoader().getResourceAsStream(path));
	}

	public Image getImage(int key)
	{
		return mapper.get(key);
	}
}
