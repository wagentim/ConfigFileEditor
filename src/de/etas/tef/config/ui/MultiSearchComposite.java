package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;
import de.etas.tef.editor.message.MessageManager;

public class MultiSearchComposite extends AbstractComposite
{

	private Text searchText;
	private Label labelSearch;
	private Label labelReplace;
	private Text replaceText;

	public MultiSearchComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	protected void initComposite()
	{

		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0;
		layout.marginHeight = layout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.verticalAlignment = GridData.CENTER;
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackgroundMode(SWT.INHERIT_FORCE);
		this.setBackground(controller.getColorFactory().getColorWhite());

		labelSearch = new Label(this, SWT.NONE);
		labelSearch.setText("Search Text: ");
		labelSearch.setBackground(controller.getColorFactory().getColorWhite());
		
		searchText = new Text(this, SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = GridData.CENTER;
		gd.verticalSpan = gd.horizontalSpan = 0;
		searchText.setLayoutData(gd);
		searchText.setMessage(IConstants.TXT_SEARCH_FILE_CONTENT);

		labelReplace = new Label(this, SWT.NONE);
		labelReplace.setText("Replace Text: ");
		labelReplace.setBackground(controller.getColorFactory().getColorWhite());
		
		replaceText = new Text(this, SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = GridData.CENTER;
		gd.verticalSpan = gd.horizontalSpan = 0;
		replaceText.setLayoutData(gd);
		replaceText.setMessage("Replace Text");

	}
	
	@Override
	public void receivedAction(int type, Object content)
	{

		if (type == IConstants.ACTION_NEW_FILE_SELECTED || type == IConstants.ACTION_DROP_NEW_FILE_SELECTED)
		{
			searchText.setText(IConstants.EMPTY_STRING);
		}
		else if( type == IConstants.ACTION_SEARCH_TYPE_CHANGED)
		{
			switch((int)content)
			{
			case IConstants.SEARCH_CONTENT:
				labelSearch.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_SEARCH_CONTENT));
				searchText.setMessage(IConstants.TXT_SEARCH_FILE_CONTENT);
				break;
			default:
				labelSearch.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_SEARCH));
				searchText.setMessage(IConstants.TXT_SEARCH_FILE_NAME);
				break;				
			}
		}
		else if(type == IConstants.ACTION_START_SEARCH)
		{
			String s = searchText.getText();
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SEARCH_CONTENT, s);
		}
		else if(type == IConstants.ACTION_GET_SEARCH_TEXT)
		{
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SET_SEARCH_TEXT, searchText.getText());
		}
		else if(type == IConstants.ACTION_GET_REPLACE_TEXT)
		{
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SET_REPLACE_TEXT, replaceText.getText());
		}
	}
}
