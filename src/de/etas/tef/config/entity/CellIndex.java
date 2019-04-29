package de.etas.tef.config.entity;

public class CellIndex
{
	private int row = -1;
	private int column = -1;
	
	public CellIndex(int row, int column)
	{
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow()
	{
		return row;
	}

	public int getColumn()
	{
		return column;
	}

	@Override
	public String toString()
	{
		return " [row=" + row + ", column=" + column + "]";
	}
}
