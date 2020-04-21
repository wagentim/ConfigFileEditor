package de.etas.tef.editor.message;

import java.util.ArrayList;
import java.util.List;

import de.etas.tef.config.listener.IMessageListener;

/**
 * Event Dispatcher. {@link IMessageListener} must register itself here for Event notification.
 * 
 * Singleton Pattern
 * 
 * @author UIH9FE
 *
 */
public class MessageManager
{
	public static MessageManager INSTANCE = new MessageManager();
	private List<IMessageListener> listenerList = null;
	
	public MessageManager()
	{
		listenerList = new ArrayList<IMessageListener>();
	}
	
	public void addMessageListener(IMessageListener listener)
	{
		if( !listenerList.contains(listener) )
		{
			listenerList.add(listener);
		}
	}
	
	public void removeMessageListener(IMessageListener listener)
	{
		if( !listenerList.isEmpty() )
		{
			listenerList.remove(listener);
		}
	}
	
	public void sendMessage(final int type, final Object content)
	{
		if( !listenerList.isEmpty() )
		{
			for( int i = 0; i < listenerList.size(); i++ )
			{
				listenerList.get(i).receivedAction(type, content);
			}
		}
	}
}
