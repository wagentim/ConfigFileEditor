package de.etas.tef.config.entity;

import de.etas.tef.config.constant.IConstants;

public class MissingKeyProblem implements Problem
{
	private final String block;
	private final String key;
	
	public MissingKeyProblem(final String block, final String key)
	{
		this.block = block;
		this.key = key;
	}

	@Override
	public int getProblemType()
	{
		return IConstants.PROBLEM_MISSING_KEY;
	}

	@Override
	public String getProblemDescription()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Missing Key: ");
		sb.append(IConstants.SYMBOL_LEFT_BRACKET);
		sb.append(block);
		sb.append(" -> ");
		sb.append(key);
		sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
		
		return sb.toString();
	}

	@Override
	public Object getProblem()
	{
		return key;
	}

}
