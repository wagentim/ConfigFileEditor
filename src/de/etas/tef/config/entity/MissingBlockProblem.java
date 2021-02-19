package de.etas.tef.config.entity;

import de.etas.tef.config.constant.IConstants;

public class MissingBlockProblem implements Problem
{
	private final String block;
	
	public MissingBlockProblem(final String block)
	{
		this.block = block;
	}

	@Override
	public int getProblemType()
	{
		return IConstants.PROBLEM_MISSING_BLOCK;
	}

	@Override
	public String getProblemDescription()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Missing Block: ");
		sb.append(IConstants.SYMBOL_LEFT_BRACKET);
		sb.append(block);
		sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
		
		return sb.toString();
	}

	@Override
	public Object getProblem()
	{
		return block;
	}

}
