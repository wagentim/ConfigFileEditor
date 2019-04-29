package de.etas.tef.config.main;

public abstract class AbstractWorker implements IWorker
{
//	protected Map<String, ConfigBlock> configs = null;
//	protected ConfigBlock currentConfigBlock = null;
//	
//	public AbstractWorker()
//	{
//		if( null == configs )
//		{
//			configs = new HashMap<String, ConfigBlock>();
//		}
//		
//		if ( null == receivers )
//		{
//			receivers = new ArrayList<IReceiver>();
//		}
//	}
//	
//	public void addReceiver(IReceiver receiver)
//	{
//		if( null != receiver && !receivers.contains(receiver) )
//		{
//			receivers.add(receiver);
//		}
//	}
//	
//	public void removeReceiver(IReceiver receiver)
//	{
//		if( null != receivers && null != receiver )
//		{
//			receivers.remove(receiver);
//		}
//	}
//	
	
//	
//	public void updateBlockList()
//	{
//		String[] result = Constants.EMPTY_STRING_ARRAY;
//		
//		if( null != configs )
//		{
//			Set<String> keys = configs.keySet();
//			result = new String[keys.size()];
//			int index = 0;
//			Iterator<String> itKey = keys.iterator();
//			
//			while(itKey.hasNext())
//			{
//				result[index] = itKey.next();
//				index++;
//			}
//		}
//		
//		for(int i = 0; i < receivers.size(); i++)
//		{
//			receivers.get(i).updateConfigBlockList(result);
//		}
//	}
//	
//	@Override
//	public void blockSelected(String text, int index)
//	{
//		ConfigBlock block = configs.get(text);
//		List<KeyValuePair> values = null;
//		
//		if(null != block)
//		{
//			values = block.getAllParameters();
//		}
//		
//		for(int i = 0; i < receivers.size(); i++)
//		{
//			receivers.get(i).updateSelectedParameters(values, index);
//		}
//	}
}
