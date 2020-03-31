package de.etas.tef.config.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqliteDBHandler
{
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private Logger logger = LoggerFactory.getLogger(SqliteDBHandler.class);

	public boolean openDB(String jdbc, String conn)
	{
		logger.info("Current jdbc is: {} and connection is: {}", jdbc, conn );
		
		try
		{
			Class.forName(jdbc);
			connection = DriverManager.getConnection(conn);
			return true;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public <T> T executeQuery(String sql, IResultSetExtractor<T> rse) throws SQLException, ClassNotFoundException
	{
		
		logger.info("Query Statement: {}", sql);
		
		try
		{
			resultSet = getStatement().executeQuery(sql);
			T rs = rse.extractData(resultSet);
			return rs;
		}
		finally
		{
			destroyed();
		}
	}

	public <T> List<T> executeQuery(String sql, IRowMapper<T> rm) throws SQLException, ClassNotFoundException
	{
		logger.info("Query Statement: {}", sql);
		
		List<T> rsList = new ArrayList<T>();
		try
		{
			resultSet = getStatement().executeQuery(sql);
			while (resultSet.next())
			{
				rsList.add(rm.mapRow(resultSet, resultSet.getRow()));
			}
		}
		finally
		{
			destroyed();
		}
		return rsList;
	}

	public int executeUpdate(String sql) throws SQLException, ClassNotFoundException
	{
		logger.info("Update Statement: {}", sql);
		
		try
		{
			int c = getStatement().executeUpdate(sql);
			return c;
		}
		finally
		{
			destroyed();
		}

	}

	public void executeUpdate(String... sqls) throws SQLException, ClassNotFoundException
	{
		try
		{
			for (String sql : sqls)
			{
				logger.info("Update Statement: {}", sql);
				getStatement().executeUpdate(sql);
			}
		}
		finally
		{
			destroyed();
		}
	}

	public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException
	{	
		try
		{
			for (String sql : sqls)
			{
				logger.info("Update Statement: {}", sql);
				getStatement().executeUpdate(sql);
			}
		}
		finally
		{
			destroyed();
		}
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException
	{
		return connection;
	}

	private Statement getStatement() throws SQLException, ClassNotFoundException
	{
		if (null == statement)
		{
			statement = getConnection().createStatement();
		}
		return statement;
	}

	public void destroyed()
	{
		try
		{
			if (null != connection)
			{
				connection.close();
				connection = null;
			}

			if (null != statement)
			{
				statement.close();
				statement = null;
			}

			if (null != resultSet)
			{
				resultSet.close();
				resultSet = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
