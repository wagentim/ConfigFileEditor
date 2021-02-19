package de.etas.tef.config.ui;

import java.nio.file.Paths;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.constant.IImageConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.controller.TemplateController;
import de.etas.tef.config.listener.CheckEnableSelectionListener;
import de.etas.tef.config.worker.search.SearchInfo;
import de.etas.tef.editor.message.MessageManager;

/**
 * Selection components are for the definition of working directory
 * 
 * @author UIH9FE
 *
 */
public class SelectSearchComposite extends AbstractComposite
{

	private Label labelTemplate;
	private Combo comboTemplate;
	
	private Composite searchSelectComposite;
	private Composite searchComposite;

	private Text txtFileSelect;
	private Button btnFileSelect;
	
	private Button checkSearchFileName;
	private Text txtSearchFileName;
	
	private Button checkSearchSectionName;
	private Text txtSearchSectionName;
	
	private Button checkSearchKeyName;
	private Text txtSearchKeyName;
	
	private Button checkSearchValueName;
	private Text txtSearchValueName;
	
	private TabFolder tabFolder;
	
	private static final int TEMPLATE_SELECED = 0x00;
	private static final int TEMPLATE_NOT_SELECED = 0x01;
	private static final int DIR_SELECED = 0x02;
	private static final int DIR_NOT_SELECED = 0x03;

//	private Button btnSearch;
	private ToolItem search;

	protected SelectSearchComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	protected void initComposite()
	{
		GridLayout layout = new GridLayout(4, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.setBackground(controller.getColorFactory().getColorBackground());

		initTemplate();
		initSelection();
		initSearchComposite();
	}

	private void initSearchComposite() 
	{
		searchSelectComposite = new Composite(this, SWT.NONE);
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 0;
		searchSelectComposite.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		searchSelectComposite.setLayoutData(gd);
		
		tabFolder = new TabFolder(searchSelectComposite, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);
		
		searchComposite = new Composite(tabFolder, SWT.BORDER);
		layout = new GridLayout(2, false);
		layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 2;
		searchComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		searchComposite.setLayoutData(gd);
		
		ToolBar tb = new ToolBar(searchComposite, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		tb.setLayoutData(gd);
		search = new ToolItem(tb, SWT.PUSH);
		Image searchImage = controller.getImageFactory().getImage(IImageConstants.IMAGE_SEARCH);
		search.setImage(searchImage);
		search.addSelectionListener(new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				SearchInfo si = getSearchInfo();
				controller.search(si);
			}
		});
		
		checkSearchFileName = new Button(searchComposite, SWT.CHECK);
		checkSearchFileName.setText(IConstants.TXT_FILE_NAME);
		txtSearchFileName = new Text(searchComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtSearchFileName.setLayoutData(gd);
		txtSearchFileName.setMessage("Search File Name");
		txtSearchFileName.setEnabled(false);
		checkSearchFileName.addSelectionListener(new CheckEnableSelectionListener(txtSearchFileName));
		
//		btnSearch = new Button(searchComposite, SWT.PUSH);
//		gd = new GridData(24, 24);
//		btnSearch.setLayoutData(gd);
//		btnSearch.setImage(searchImage);
//		btnSearch.addPaintListener(new PaintListener() {
//			
//			@Override
//			public void paintControl(PaintEvent event) {
//				event.gc.fillRectangle(event.x, event.y, event.width, event.height);
//				event.gc.drawImage(searchImage, 0, 0);
//				event.gc.setBackground(controller.getColorFactory().getColorBackground());
//			}
//		});
//		

//		btnSearch.setVisible(false);
		
		checkSearchSectionName = new Button(searchComposite, SWT.CHECK);
		checkSearchSectionName.setText(IConstants.TXT_SECTION);
		txtSearchSectionName = new Text(searchComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtSearchSectionName.setLayoutData(gd);
		txtSearchSectionName.setMessage("Search Section Name");
		txtSearchSectionName.setEnabled(false);
		checkSearchSectionName.addSelectionListener(new CheckEnableSelectionListener(txtSearchSectionName));
		
		checkSearchKeyName = new Button(searchComposite, SWT.CHECK);
		checkSearchKeyName.setText(IConstants.TXT_KEY);
		txtSearchKeyName = new Text(searchComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtSearchKeyName.setLayoutData(gd);
		txtSearchKeyName.setMessage("Search Key Name");
		txtSearchKeyName.setEnabled(false);
		checkSearchKeyName.addSelectionListener(new CheckEnableSelectionListener(txtSearchKeyName));
		
		checkSearchValueName = new Button(searchComposite, SWT.CHECK);
		checkSearchValueName.setText(IConstants.TXT_VALUE);
		txtSearchValueName = new Text(searchComposite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtSearchValueName.setLayoutData(gd);
		txtSearchValueName.setMessage("Search Value Name");
		txtSearchValueName.setEnabled(false);
		checkSearchValueName.addSelectionListener(new CheckEnableSelectionListener(txtSearchValueName));
		
		setSearchStatus(false);
		TabItem loggerItem = new TabItem(tabFolder, SWT.NULL);
		loggerItem.setText("Search");
		loggerItem.setControl(searchComposite);
		
		TabItem validationItem = new TabItem(tabFolder, SWT.NULL);
		validationItem.setText("Checker");
		validationItem.setControl(new ValidationComposite(tabFolder, SWT.BORDER, controller));
		
		TabItem replaceItem = new TabItem(tabFolder, SWT.NULL);
		replaceItem.setText("Operation");
		replaceItem.setControl(new OperationComposite(tabFolder, SWT.BORDER, controller));
		
	}

	protected SearchInfo getSearchInfo()
	{
		SearchInfo si = new SearchInfo();
		
		si.setStartPath(Paths.get(txtFileSelect.getText()));
		
		if(checkSearchFileName.getSelection())
		{
			si.setFileName(txtSearchFileName.getText());
		}
		
		if(checkSearchSectionName.getSelection())
		{
			si.setSectionName(txtSearchSectionName.getText());
		}
		
		if(checkSearchKeyName.getSelection())
		{
			si.setKeyName(txtSearchKeyName.getText());
		}
		
		if(checkSearchValueName.getSelection())
		{
			si.setValueName(txtSearchValueName.getText());
		}
		
		return si;
	}

	private void initTemplate()
	{
		labelTemplate = new Label(this, SWT.NONE);
		labelTemplate.setBackground(controller.getColorFactory().getColorBackground());
		labelTemplate.setText(IConstants.TXT_TEMPLATE);
		
		comboTemplate = new Combo(this, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		comboTemplate.setLayoutData(gd);
		comboTemplate.add(IConstants.EMPTY_STRING);
		Iterator<String> it = controller.getTemplateNames().iterator();
		while(it.hasNext())
		{
			comboTemplate.add(it.next());
		}
		
		comboTemplate.addSelectionListener(new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent even)
			{
				String text = comboTemplate.getText();
				
				if(!text.isEmpty())
				{
					updateComponentStatus(TEMPLATE_SELECED);
					if(text.equalsIgnoreCase("MCD Template"))
					{
						controller.setTemplate(TemplateController.TEMPLATE_MCD);
						MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SET_TEMPLATE, null);
					}
					else
					{
						controller.setTemplate(1000);
					}
					
					
				}
				else
				{
					updateComponentStatus(TEMPLATE_NOT_SELECED);
				}
				
			}
		});
	}

	private void initSelection()
	{

		txtFileSelect = new Text(this, SWT.SINGLE | SWT.BORDER);
		GridData txtGD = new GridData(GridData.FILL_HORIZONTAL);
		txtGD.horizontalSpan = 3;
		txtFileSelect.setLayoutData(txtGD);
		txtFileSelect.setEditable(false);
		txtFileSelect.setMessage("Select Directory");
		txtFileSelect.setBackground(controller.getColorFactory().getColorBackground());

		btnFileSelect = new Button(this, SWT.PUSH);
		GridData gd = new GridData(24, 26);
		btnFileSelect.setLayoutData(gd);
		btnFileSelect.addPaintListener(new PaintListener()
		{

			@Override
			public void paintControl(PaintEvent event)
			{
				event.gc.setBackground(controller.getColorFactory().getColorBackground());
				event.gc.fillRectangle(event.x, event.y, event.width, event.height);
				Image image = controller.getImageFactory().getImage(IImageConstants.IMAGE_FOLDER);
				event.gc.drawImage(image, 0, 0);
			}
		});

		btnFileSelect.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				setCurrFilePath(fileSelector(getShell()));
			}

		});
		
		setSelectionStatus(false);
	}

	protected void setCurrFilePath(String currFilePath)
	{

		if (null == currFilePath)
		{
			currFilePath = IConstants.EMPTY_STRING;
		}

		txtFileSelect.setText(currFilePath);
		
		checkDir();
	}
	
	private void checkDir()
	{
		if(txtFileSelect.getText().equalsIgnoreCase(IConstants.EMPTY_STRING))
		{
			updateComponentStatus(DIR_NOT_SELECED);
		}
		else
		{
			updateComponentStatus(DIR_SELECED);
		}
	}

	protected String fileSelector(Shell shell)
	{
		DirectoryDialog fd = new DirectoryDialog(shell, SWT.APPLICATION_MODAL | SWT.OPEN);
		return fd.open();
	}
	
	private void updateComponentStatus(int status)
	{
		switch(status)
		{
		case TEMPLATE_SELECED:
			setSelectionStatus(true);
			checkDir();
			break;
		case TEMPLATE_NOT_SELECED:
			setSelectionStatus(false);
			setSearchStatus(false);
			break;
		case DIR_NOT_SELECED:
			setSearchStatus(false);
			break;
		case DIR_SELECED:
			setSearchStatus(true);
			break;
		}
	}
	
	private void setSelectionStatus(boolean enable)
	{
		txtFileSelect.setEnabled(enable);
		btnFileSelect.setEnabled(enable);
	}
	
	private void setSearchStatus(boolean enable)
	{
		searchComposite.setEnabled(enable);
		checkSearchFileName.setEnabled(enable);
		checkSearchKeyName.setEnabled(enable);
		checkSearchValueName.setEnabled(enable);
		checkSearchSectionName.setEnabled(enable);
		search.setEnabled(enable);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}
}
