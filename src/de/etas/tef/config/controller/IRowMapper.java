package de.etas.tef.config.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowMapper<T>
{
	public abstract T mapRow(ResultSet rs, int index) throws SQLException;
}
