package de.etas.tef.config.db;

public class AbstractDBController implements IDBController
{
	protected final IDBInfo dbInfo;
	
	public AbstractDBController(final IDBInfo dbInfo)
	{
		this.dbInfo = dbInfo;
	}
}
