package de.etas.tef.config.listener;

public interface IMessageListener
{
	void receivedAction(int type, Object content);
}
