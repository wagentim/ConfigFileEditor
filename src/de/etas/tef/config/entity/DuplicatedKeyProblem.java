package de.etas.tef.config.entity;

import de.etas.tef.config.constant.IConstants;

public class DuplicatedKeyProblem implements Problem 
{
	
	private final ConfigBlock cb;
	private final String key;
	
	public DuplicatedKeyProblem(ConfigBlock cb, String key)
	{
		this.cb = cb;
		this.key = key;
	}
	
	@Override
	public int getProblemType() 
	{
		return IConstants.PROBLEM_DUPLICATED_SECTION;
	}

	@Override
	public String getProblemDescription() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Duplicate Key: ");
		sb.append(IConstants.SYMBOL_LEFT_BRACKET);
		sb.append(cb.getBlockName());
		sb.append(" -> ");
		sb.append(key);
		sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
		
		return sb.toString();
	}

	@Override
	public Object getProblem() 
	{
		return new Object[] {cb, key};
	}

}
