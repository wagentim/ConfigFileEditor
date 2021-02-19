package de.etas.tef.config.entity;

import de.etas.tef.config.constant.IConstants;

public class DuplicatedSectionProblem implements Problem 
{
	
	private final String cb;
	
	public DuplicatedSectionProblem(String cb)
	{
		this.cb = cb;
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
		sb.append("Duplicate Section: ");
		sb.append(IConstants.SYMBOL_LEFT_BRACKET);
		sb.append(cb);
		sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
		
		return sb.toString();
	}

	@Override
	public Object getProblem() 
	{
		return cb;
	}

}
