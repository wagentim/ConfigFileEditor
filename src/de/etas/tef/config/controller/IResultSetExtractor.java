package de.etas.tef.config.controller;

import java.sql.ResultSet;

public interface IResultSetExtractor<T>
{
	public abstract T extractData(ResultSet rs);
}
