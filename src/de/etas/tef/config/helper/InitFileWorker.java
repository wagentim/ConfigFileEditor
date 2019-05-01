package de.etas.tef.config.helper;

public class InitFileWorker extends AbstractWorker
{

	@Override
	public void blockSelected(String text, int index)
	{
		// TODO Auto-generated method stub
		
	}
//	public InitFileWorker()
//	{
//		super();
//		
//		if( null == configs )
//		{
//			configs = new HashMap<String, ConfigBlock>();
//		}
//		
//	}
//	public void printAllBlockNames()
//	{
//		if( null != configs )
//		{
//			Collection<ConfigBlock> values = configs.values();
//			Iterator<ConfigBlock> it = values.iterator();
//			while(it.hasNext())
//			{
//				System.out.println(it.next().getBlockName());
//			}
//		}
//	}
//	
//	public void printAllBlocks()
//	{
//		if( null != configs )
//		{
//			Set<String> keys = configs.keySet();
//			Iterator<String> itKey = keys.iterator();
//			while(itKey.hasNext())
//			{
//				String key = itKey.next();
//				System.out.println(key);
//				
//				printBlock(key);
//				
//				System.out.println("---------------------");
//			}
//		}
//	}
//
//	@Override
//	public void printBlock(String key)
//	{
//		ConfigBlock block = configs.get(key);
//		if( null != block )
//		{
//			List<KeyValuePair> paras = block.getAllParameters();
//			
//			if( !paras.isEmpty() )
//			{
//				for(int i = 0; i < paras.size(); i++)
//				{
//					System.out.println(paras.get(i).toString());
//				}
//			}
//		}
//	}
//
//	public void blockSelected(String text, int index)
//	{
//		// TODO Auto-generated method stub
//		
//	}

}
