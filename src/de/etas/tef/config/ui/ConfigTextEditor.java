package de.etas.tef.config.ui;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;

public class ConfigTextEditor extends AbstractComposite
{

	private Table table;
	private TableEditor editor;

	public ConfigTextEditor(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	protected void initComposite()
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		GridData gd = new GridData(GridData.FILL_BOTH);
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());

		table = new Table(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		gd = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gd);
		TableColumn tc1 = new TableColumn(table, SWT.CENTER);
		TableColumn tc2 = new TableColumn(table, SWT.CENTER);
		TableColumn tc3 = new TableColumn(table, SWT.CENTER);
		tc1.setText("Key");
		tc3.setText("Value");
		table.setHeaderVisible(true);
		table.addControlListener(new ControlAdapter()
		{

			@Override
			public void controlResized(ControlEvent event)
			{
				adjustColumnSize();
			}

		});

		table.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseDoubleClick(MouseEvent event)
			{
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getSelectionIndex();
				boolean visible = false;
				final TableItem item = table.getItem(index);

				if (item == null)
				{
					return;
				}

				for (int i = 0; i < table.getColumnCount(); i++)
				{
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt))
					{
						final int column = i;
						if (column == 0)
						{
							showKeyEditor();
						} else if (column == 2)
						{
//							showValueEditor();
						}
					}
				}

//		              final Text text = new Text(table, SWT.NONE);
//		              Listener textListener = new Listener() {
//		                public void handleEvent(final Event e) {
//		                  switch (e.type) {
//		                  case SWT.FocusOut:
//		                    item.setText(column, text.getText());
//		                    text.dispose();
//		                    break;
//		                  case SWT.Traverse:
//		                    switch (e.detail) {
//		                    case SWT.TRAVERSE_RETURN:
//		                      item
//		                          .setText(column, text
//		                              .getText());
//		                    //FALL THROUGH
//		                    case SWT.TRAVERSE_ESCAPE:
//		                      text.dispose();
//		                      e.doit = false;
//		                    }
//		                    break;
//		                  }
//		                }
//		              };
//		              text.addListener(SWT.FocusOut, textListener);
//		              text.addListener(SWT.Traverse, textListener);
//		              editor.setEditor(text, item, i);
//		              text.setText(item.getText(i));
//		              text.selectAll();
//		              text.setFocus();
//		              return;
//		            }
//		            if (!visible && rect.intersects(clientArea)) {
//		              visible = true;
//		            }
//		          }
			}
		});

	}

	protected void showKeyEditor()
	{
		final Combo keys = new Combo(table, SWT.NONE);
//        Listener textListener = new Listener() {
//          public void handleEvent(final Event e) {
//            switch (e.type) {
//            case SWT.FocusOut:
//              item.setText(column, text.getText());
//              text.dispose();
//              break;
//            case SWT.Traverse:
//              switch (e.detail) {
//              case SWT.TRAVERSE_RETURN:
//                item
//                    .setText(column, text
//                        .getText());
//              //FALL THROUGH
//              case SWT.TRAVERSE_ESCAPE:
//                text.dispose();
//                e.doit = false;
//              }
//              break;
//            }
//          }
//        };
	}

	private void adjustColumnSize()
	{
		Rectangle rect = table.getClientArea();
		table.getColumn(0).setWidth((int) (rect.width * 0.4));
		table.getColumn(1).setWidth((int) (rect.width * 0.1));
		table.getColumn(2).setWidth((int) (rect.width * 0.5));
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if (type == IConstants.ACTION_SET_CONFIG_BLOCK)
		{
			setConfigBlock((ConfigBlock) content);
		} 
		else if (type == IConstants.ACTION_SET_CONFIG_FILE)
		{
			setConfigFile((ConfigFile) content);
		}
		else if(type == IConstants.ACTION_UPDATE_FILE_LIST)
		{
			table.removeAll();
		}
	}

	public void setConfigFile(ConfigFile cf)
	{
//		List<ConfigBlock> configBlocks = cf.getConfigBlocks();
//		if(configBlocks == null || configBlocks.size() == 0)
//		{
//			text.setText(IConstants.EMPTY_STRING);
//			return;
//		}
//		
//		Iterator<ConfigBlock> itb = cf.getConfigBlocks().iterator();
//		StringBuilder sb = new StringBuilder();
//
//		while(itb.hasNext())
//		{
//			ConfigBlock cb = itb.next();
//			Iterator<KeyValuePair> it = cb.getAllParameters().iterator();
//			sb.append(IConstants.SYMBOL_LEFT_BRACKET);
//			sb.append(cb.getBlockName());
//			sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
//			sb.append(IConstants.SYMBOL_NEW_LINE);
//			while(it.hasNext())
//			{
//				addKeyValue(sb, it.next());
//			}
//			sb.append(IConstants.SYMBOL_NEW_LINE);
//		}
//		
//		text.setText(sb.toString());
	}

	public void setConfigBlock(ConfigBlock cb)
	{
		if (cb == null)
		{
			return;
		}

		Iterator<KeyValuePair> it = cb.getAllParameters().iterator();
		table.removeAll();
		while (it.hasNext())
		{
			KeyValuePair kvp = it.next();
			System.out.println(kvp.toString());
			addKeyValue(kvp);
		}
	}

	public void addKeyValue(KeyValuePair kvp)
	{
		int type = kvp.getType();
		String key = kvp.getKey();
		String value = kvp.getValue();

		if (type == 1 && key.isEmpty())
		{
			return;
		}

		TableItem ti = new TableItem(table, SWT.NONE);
		ti.setText(0, kvp.getKey());
		if (type == 0)
		{
			ti.setText(1, IConstants.SYMBOL_EQUAL);
			ti.setText(2, kvp.getValue());
		}
	}
}
