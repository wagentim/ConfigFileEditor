package de.etas.tef.config.ui.core;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;

/**
 * Using Table composite to implement basic text editor functions
 * 
 * @author UIH9FE
 *
 */
public abstract class TextEditor extends AbstractComposite
{

	protected Menu rightClickMenu;
	protected Text text;
	
	public TextEditor(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initMainComposite();
		
	}
	
	protected void initMainComposite()
	{
		// setting for the composite
		super.initComposite();
		
		// setting for the table composite
		text = new Text(this, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP | SWT.BORDER);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = IConstants.HEIGHT_HINT;
		text.setLayoutData(gd);
		
	}

	protected void createRightMenu(Control control, SelectionListener listener)
	{
		rightClickMenu = new Menu(text);
		text.setMenu(rightClickMenu);
		
		rightClickMenu.addMenuListener(new MenuAdapter()
	    {
	        public void menuShown(MenuEvent e)
	        {
	            MenuItem[] items = rightClickMenu.getItems();
	            
	            for (int i = 0; i < items.length; i++)
	            {
	                items[i].dispose();
	            }
	            
	            if(text.getSelectionCount() <= 0)
	            {
	            	return;
	            }
	            
	            MenuItem copyItem = new MenuItem(rightClickMenu, SWT.NONE);
	            copyItem.setText(IConstants.TXT_COPY);
	            copyItem.addSelectionListener(listener);
	            
	            MenuItem pasteItem = new MenuItem(rightClickMenu, SWT.NONE);
	            pasteItem.setText(IConstants.TXT_PASTE);
	            pasteItem.addSelectionListener(listener);
	            
	            new MenuItem(rightClickMenu, SWT.SEPARATOR);
	            
	            MenuItem newItem = new MenuItem(rightClickMenu, SWT.NONE);
	            newItem.setText(IConstants.TXT_BTN_ADD);
	            newItem.addSelectionListener(listener);
	            
	            MenuItem deleteItem = new MenuItem(rightClickMenu, SWT.NONE);
	            deleteItem.setText(IConstants.TXT_BTN_DELETE);
	            deleteItem.addSelectionListener(listener);
	        }
	    });
	}

	protected String fileSave(Shell shell)
	{
		FileDialog fd = new FileDialog(shell, SWT.APPLICATION_MODAL | SWT.SAVE);
		fd.setFilterExtensions(IConstants.CONFIG_FILE_EXTENSION);
		fd.setFilterNames(IConstants.CONFIG_FILE_NAME);
		String result = fd.open();
		if( null != result )
		{
			Path file = Paths.get(result);
	        if (Files.exists(file))
	        {
	          MessageBox mb = new MessageBox(fd.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

	          mb.setMessage(result + " already exists. Do you want to replace it?");

	          boolean done = mb.open() == SWT.YES;
	          
	          if( !done )
	          {
	        	return null;  
	          }
	        }
		}
		
		return result;
	}
	
	protected void toSave() 
	{
		String fileSavePath = fileSave(this.getShell());

		if (null == fileSavePath)
		{
			return;
		}

		saveAction(fileSavePath);
	}
	
	protected void saveAction(String targetFilePath)
	{
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		
	}
}
