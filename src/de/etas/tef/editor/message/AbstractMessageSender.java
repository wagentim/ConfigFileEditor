package de.etas.tef.editor.message;

import de.etas.tef.config.listener.IMessageListener;

public abstract class AbstractMessageSender implements IMessageListener
{
	public AbstractMessageSender()
	{
		MessageManager.INSTANCE.addMessageListener(this);
	}
}
