package de.etas.tef.config.ui;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.constant.IImageConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Utils;
import de.etas.tef.config.worker.operation.TextAddWorker;
import de.etas.tef.config.worker.operation.TextDeleteWorker;
import de.etas.tef.config.worker.operation.TextReplaceWorker;

public class OperationComposite extends AbstractComposite
{
	
	private Combo cbSection;
	private Label section;
	
	private Combo cbKey;
	private Label key;
	
	private Text txtValue;
	private Label value;
	
	private ToolItem tiReplace;
	private ToolItem tiAdd;
	private ToolItem tiDelete;

	public OperationComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 0;
		layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 2;
		GridData gd = new GridData(GridData.FILL_BOTH);
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());
		
		ToolBar tb = new ToolBar(this, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		tb.setLayoutData(gd);
		tb.setBackground(controller.getColorFactory().getColorBackground());
		
		tiAdd = new ToolItem(tb, SWT.PUSH);
		Image addImage = controller.getImageFactory().getImage(IImageConstants.IMAGE_ADD);
		tiAdd.setImage(addImage);
		tiAdd.setToolTipText("Add New Section or Parameter");
		tiAdd.addSelectionListener(new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				int index = cbSection.getSelectionIndex();
				
				if(index < 0)
				{
					return;
				}
				
				String section = cbSection.getItem(index);
				String key = cbKey.getItem(cbKey.getSelectionIndex());
				String value = txtValue.getText();
				
				if(Utils.isStringEmpty(section) || Utils.isStringEmpty(key) || Utils.isStringEmpty(value))
				{
					return;
				}
				
				Thread t = new Thread(new TextAddWorker(new String[] {section, key, value},  controller.getCurrentFileList(), tiReplace.getDisplay(), controller));
				t.start();
				
			}
			
		});
		
		tiReplace = new ToolItem(tb, SWT.PUSH);
		Image searchImage = controller.getImageFactory().getImage(IImageConstants.IMAGE_REPLACE);
		tiReplace.setImage(searchImage);
		tiReplace.setToolTipText("Replace the parameter value");
		tiReplace.addSelectionListener(new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				int index = cbSection.getSelectionIndex();
				
				if(index < 0)
				{
					return;
				}
				
				String section = cbSection.getItem(cbSection.getSelectionIndex());
				String key = cbKey.getItem(cbKey.getSelectionIndex());
				String value = txtValue.getText();
				
				if(Utils.isStringEmpty(section) || Utils.isStringEmpty(key) || Utils.isStringEmpty(value))
				{
					return;
				}
				
				Thread t = new Thread(new TextReplaceWorker(new String[] {section, key, value},  controller.getCurrentFileList(), tiReplace.getDisplay(), controller));
				t.start();
				
			}
			
		});
		
		tiDelete = new ToolItem(tb, SWT.PUSH);
		Image deleteImage = controller.getImageFactory().getImage(IImageConstants.IMAGE_DELETE_ITEM);
		tiDelete.setImage(deleteImage);
		tiDelete.setToolTipText("Delete Parameter");
		tiDelete.addSelectionListener(new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				int index = cbSection.getSelectionIndex();
				
				if(index < 0)
				{
					return;
				}
				
				String section = cbSection.getItem(cbSection.getSelectionIndex());
				String key = cbKey.getItem(cbKey.getSelectionIndex());
				String value = txtValue.getText();
				
				if(Utils.isStringEmpty(section) || Utils.isStringEmpty(key) || Utils.isStringEmpty(value))
				{
					return;
				}
				
				Thread t = new Thread(new TextDeleteWorker(new String[] {section, key, value},  controller.getCurrentFileList(), tiReplace.getDisplay(), controller));
				t.start();
				
			}
			
		});
		
		section = new Label(this, SWT.NONE);
		section.setText("Section");
		section.setBackground(controller.getColorFactory().getColorBackground());
		cbSection = new Combo(this, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		cbSection.setLayoutData(gd);
		cbSection.addSelectionListener(new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				int index = cbSection.getSelectionIndex();
				String sel = cbSection.getItem(index);
				if(sel == null || sel.isEmpty())
				{
					return;
				}
				
				ConfigFilePath cfp = controller.getCurrentTemplate();
				
				if(cfp == null)
				{
					return;
				}
				
				ConfigBlock cb = cfp.getConfigFile().findConfigBlock(sel).get(0);
				if(cb == null)
				{
					return;
				}
				
				updateKeyList(cb);
				
			}
		});
		
		key = new Label(this, SWT.NONE);
		key.setText("Key");
		key.setBackground(controller.getColorFactory().getColorBackground());
		cbKey = new Combo(this, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		cbKey.setLayoutData(gd);
		
		value = new Label(this, SWT.NONE);
		value.setText("Value");
		value.setBackground(controller.getColorFactory().getColorBackground());
		txtValue = new Text(this, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		txtValue.setLayoutData(gd);
	}

	protected void updateKeyList(ConfigBlock cb)
	{
		cbKey.removeAll();
		
		List<KeyValuePair> paras = cb.getAllParameters();
		
		if(paras == null || paras.isEmpty())
		{
			return;
		}
		
		Iterator<KeyValuePair> it = paras.iterator();
		while(it.hasNext())
		{
			KeyValuePair kvp = it.next();
			
			if(kvp.getType() == KeyValuePair.TYPE_PARA && !kvp.getKey().isEmpty())
			{
				cbKey.add(kvp.getKey());
			}
		}
	}

	private void updateSections()
	{
		ConfigFilePath template = controller.getCurrentTemplate();
		cbSection.removeAll();
		
		if(template == null)
		{
			return;
		}
		
		List<ConfigBlock> blocks = template.getConfigFile().getConfigBlocks();
		Iterator<ConfigBlock> it = blocks.iterator();
		
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
			cbSection.add(cb.getBlockName());
		}
		
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_SET_TEMPLATE)
		{
			updateSections();
		}
	}


}
