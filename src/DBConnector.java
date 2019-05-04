import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;

public class DBConnector 
{	
	static Connection conn = null;
	static ResultSet result = null;
	static Model model = null;
	public static Connection getConnection() {
		try 
		{
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/server~/OOPProjectDB", "sa", "");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{	
			e.printStackTrace();
		}
		return conn;
	}
	public static Model getAllModel(String entity) 
	{		
		String sql = "select * from " + entity;
		
		conn = getConnection();
		try 
		{
			PreparedStatement state = conn.prepareStatement(sql);
			state = conn.prepareStatement(sql);

			result = state.executeQuery();
			model = new Model(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	public void refreshTable(String entity, JTable sqlTable)
	{
		sqlTable.setModel(DBConnector.getAllModel(entity));
		hideIDFromModel(sqlTable);
	}
	
	public void refreshNameTable(String entity, JTable sqlTable, String tabName)
	{
		sqlTable.setModel(DBConnector.getByTabNameModel(entity, tabName));
		hideIDFromModel(sqlTable);
	}
	
	public void refreshQueryTable(String entity,JTable sqlTable, String tabName,String sql)
	{
		sqlTable.setModel(DBConnector.getBySearchQueryModel(entity, tabName, sql));
		hideIDFromModel(sqlTable);
	}
	
	public static Model getByTabNameModel(String entity, String tabName)
	{
		String sql = "select * from " + entity;
		
		if(!tabName.isBlank())
		{
			sql = "select "+ tabName  +" from " + entity;
		}
		conn = getConnection();
		try 
		{
			PreparedStatement state = conn.prepareStatement(sql);
			state = conn.prepareStatement(sql);

			result = state.executeQuery();
			model = new Model(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public static Model getBySearchQueryModel(String entity, String tabName, String sql)
	{
		conn = getConnection();
		try 
		{
			PreparedStatement state = conn.prepareStatement(sql);
			state = conn.prepareStatement(sql);
			state.setString(1,"Name");
			
			result = state.executeQuery();
			model = new Model(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	/*
	
public static Model getAllExceptIDModel(String entity) // NOT USED (ORIGINAL PLAN TO HIDE ID)
	{
		String sql = "select ";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(sql);
		sql = "select * from " + entity;
		conn = getConnection();
		try {
			ResultSetMetaData resultSet = null;
			PreparedStatement state = conn.prepareStatement(sql);
			
			resultSet = state.getMetaData();
			int columns = resultSet.getColumnCount();
			for(int i = 2;i<=columns;i++)
			{
				stringBuilder.append(state.getMetaData().getColumnName(i) + " , ");
				if(i == columns)
				{
					stringBuilder.deleteCharAt(stringBuilder.length() - 2);
					stringBuilder.append(" from " + entity);
				}
					
			}
			sql = stringBuilder.toString();
			state = conn.prepareStatement(sql);
			
			
			model = new Model(result);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
}
*/
	private void hideIDFromModel(JTable sqlTable)
	{
		for(int i = 0;i < sqlTable.getColumnCount();i++)
		{
			if(sqlTable.getColumnName(i).contains("ID"))
			{
				sqlTable.getColumnModel().getColumn(i).setMinWidth(0);
				sqlTable.getColumnModel().getColumn(i).setMaxWidth(0); // Hiding all IDs from the Index Table
			}
		}
	}
	public Model getSearchModel(String entity, String tabName, String findText) // STILL UNDER WORK
	{
		String sql = null;
		
		if(!tabName.isBlank() || !findText.isBlank())
			sql = "Select " + tabName + " from " + entity + " where " + tabName + " = " + findText;
		else
			sql = "Select * from " + entity;
		
		conn = getConnection();
		try 
		{
			PreparedStatement state = conn.prepareStatement(sql);
			state = conn.prepareStatement(sql);

			result = state.executeQuery();
			model = new Model(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
