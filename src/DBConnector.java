import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class DBConnector 
{	
	static Connection conn = null;
	static ResultSet result = null;
	static Model model = null;
	static PreparedStatement state = null;
	
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
	
	public void refreshForeignKeyTable(String entity, String property, String[] foreignEntities, String[] foreignReferences, JTable sqlTable)
	{
		sqlTable.setModel(DBConnector.getForeignKeyModel(entity, property,foreignEntities, foreignReferences));
		hideIDFromModel(sqlTable);
	}
	
	public static Model getByTabNameModel(String entity, String tabName)
	{
		String sql = "select * from " + entity;
		
		if(!tabName.isEmpty() && !tabName.equals(" "))
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
			
			result = state.executeQuery();
			model = new Model(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
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
	public Model getSearchModel(String entity, String tabName, String findText) 
	{
		String sql = null;
		
		if(!tabName.isEmpty() || !findText.isEmpty() || !findText.equals(" "))
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
	
	public static Model getForeignKeyModel(String entity, String property, String[] foreignEntities, String[] foreignReferences) 
	{
		StringBuilder sb = new StringBuilder();
		String sql = "Select " + entity +"." + property + ", ";
		
		sb.append(sql);
		for(int i = 0;i<foreignEntities.length;i++)
		{
			sb.append(foreignEntities[i] + "." + property + " AS " + foreignEntities[i]+ ", ");
			
			if(i == foreignEntities.length - 1)
				sb.deleteCharAt(sb.length() - 2);
		}
		sb.append(" from " + entity);
		
		for(int i = 0;i<foreignEntities.length;i++)
		{
			sb.append(" join " + foreignEntities[i]);
		}
		sb.append(" where ");
		for(int i = 0;i<foreignEntities.length;i++)
		{
			sb.append(entity + "." + foreignReferences[i] + "_ID"  + " = " + foreignEntities[i] + ".ID" + " AND ");
			
			if(i == foreignEntities.length - 1)
				sb.delete(sb.length() - 4, sb.length());
		}
		sql = sb.toString();
		
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
		System.out.println(sql);
		return model;
	}
	
	public static Object[] getDataFromProperty(String entity, String property,JTable sqlTable)
	{
		String sql = "Select " + property + " from " + entity;
		
		ArrayList<String> namesString = new ArrayList<String>();
		Object[] names = null;
		int rows = sqlTable.getRowCount();
		
		conn = DBConnector.getConnection();
		try 
		{
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			// DBhelper.refreshQueryTable(referenceText, sqlTable, property, sql);
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		try {
			while(result.next())
			{
				try {
					namesString.add(result.getString(property));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try {
				state.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		names = namesString.toArray();
		namesString.clear();
		return names;
	}
	public static String getDataFromEntity(String entity, String selectProperty, String property, String data)
	{
		String sql = "select " + selectProperty + " from " +entity + " where " + property + " = " +"'" + data + "'";
		String output = "0";
		
		conn = DBConnector.getConnection();
		try 
		{
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			
			while(result.next())
			{
				output = result.getString(selectProperty);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				state.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(output);
		System.out.println(sql);
		
		return output.toString();
	}
	
	public void setAdditionalColumns(String entity, String property, String[] foreignEntities, String[] foreignReferences, JTable sqlTable)
	{
		String sql = "Select * from " + entity;
		
		TableColumn companyColumn = new TableColumn(1);
		TableColumn categoryColumn = new TableColumn(1);	
		
		model = getForeignKeyModel(entity, property, foreignEntities, foreignReferences);
		
		for(int i = 0;i<model.getColumnCount();i++)
		{
			sqlTable.setValueAt(model.getValueAt(i, 2) , i, 3);
			sqlTable.setValueAt(model.getValueAt(i,3), i, 4);
		}
		companyColumn.setHeaderValue("COMPANY");
		categoryColumn.setHeaderValue("CATEGORY");
		
		sqlTable.addColumn(companyColumn);
		sqlTable.addColumn(categoryColumn);
		
	}
	
}
