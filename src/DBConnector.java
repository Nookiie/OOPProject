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
	public void refreshByEntityTable(String entity, JTable sqlTable)
	{
		sqlTable.setModel(DBConnector.getAllModel(entity));
		hideIDFromModel(sqlTable);
	}
	
	public void refreshNameTable(String entity, JTable sqlTable, String tabName,ArrayList<String> tabList)
	{
		sqlTable.setModel(DBConnector.getByTabNameModel(entity, tabName, tabList));
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
		 renameForeignColumns(foreignReferences, sqlTable);
		 hideIDFromModel(sqlTable);
	}
	
	public void refreshAllTable(JTable sqlTable)
	{
		sqlTable.setModel(DBConnector.getAllPossibleModel());
		hideIDFromModel(sqlTable);
	}
	
	public void renameForeignColumns(String[] foreignReferences, JTable sqlTable)
	{
		int columns = sqlTable.getColumnCount();
		
		for(int i = 1;i<=foreignReferences.length;i++)
		{
			sqlTable.removeColumn(sqlTable.getColumnModel().getColumn(columns - i));
		}
		for(int i = 0;i<foreignReferences.length;i++)
		{
			TableColumn column = new TableColumn(i + columns - foreignReferences.length);
			column.setHeaderValue(foreignReferences[i]);
			
			sqlTable.addColumn(column);
		}
	}
	
	public static Model getByTabNameModel(String entity, String tabName, ArrayList<String> tabList)
	{//Do not touch, works for videogames foreign key filters
		// String sql = "select from " + entity;
		
		String sql = "select ";
		
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		if(!tabName.isEmpty() && !tabName.equals(" "))
		{
			//if(tabName.equals("COMPANY_NAME")) {
				//sql = "select game_name, game_description, company_name, category_name " +  "from videogames join companies on videogames.company_id = companies.id join categories on videogames.category_id = categories.id ";
				// sql = "select "  +  "from videogames join companies on videogames.company_id = companies.id join categories on videogames.category_id = categories.id ";
					
				for(int i = 0;i<tabList.size();i++)
				{
					sb.append(tabList.get(i));
					System.out.println(tabList.get(i));
					
					if(i != tabList.size() - 1)
						sb.append(" , ");
				}
				System.out.println("/n end/n");
				if(entity.equals("categories")) {
					sb.append(" from categories ");
					sql = sb.toString();
				}
////////////NEW!!!!!!!!/////////////////////////////////////////////////////////////////////////////////////////////////////

				else if(entity.equals("companies")) {
					sb.append(" from companies ");
					sql = sb.toString();
				}
////////////NEW!!!!!!!!/////////////////////////////////////////////////////////////////////////////////////////////////////
				else{sb.append(" from videogames join companies on videogames.company_id = companies.id join categories on videogames.category_id = categories.id ");
				sql = sb.toString();
				}
			}
			else if(tabName.equals("CATEGORY_NAME")) {
				sql = "select *  from videogames join categories on videogames.category_id = categories.id ";
			}
			else {
			sql = "select * from " + entity;
			}
		//}
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
		//tabList.clear();
		return model;
	}
	
	public static Model getAllPossibleModel()
	{
		String sql = "select * from videogames join categories on videogames.category_id = categories.id join companies on videogames.company_id = companies.id"; 
		
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
		String sql="";
		if(entity.contains("comp")) {
			sql = "Select " + entity +".company_ID, " + entity +"." + property + ", " + entity + ".company_description, ";
		}
		else if(entity.contains("categ")) {
			sql = "Select " + entity +".category_ID, " + entity +"." + property + ", " + entity + ".category_description, ";
		}
		else {
			sql = "Select " + entity +".ID, " + entity +"." + property + ", " + entity + ".game_description, ";
		}
		//String sql = "Select " + entity +".ID, " + entity +"." + property + ", " + entity + ".description, ";
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		sb.append(sql);
		for(int i = 0;i<foreignEntities.length;i++)
		{
			//	sb.append(foreignEntities[i] + "." + property + " AS " + foreignEntities[i]+ ", ");
			if(foreignEntities[i].contains("comp")) {
				sb.append(foreignEntities[i] + "." + "company_name" + ", "); // CHANGE MADE
			}
			else if(foreignEntities[i].contains("categ")){
				sb.append(foreignEntities[i] + "." + "category_name" + ", "); // CHANGE MADE
			}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
			if(i==0) {
					sb.append(entity + "." + foreignReferences[i] + "_ID"  + " = " + foreignEntities[i] + ".ID " + " AND ");
			}
			else if(i==1) {
					sb.append(entity + "." + foreignReferences[i] + "_ID"  + " = " + foreignEntities[i] + ".ID " + " AND ");
			}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			//sb.append(entity + "." + foreignReferences[i] + "_ID"  + " = " + foreignEntities[i] + ".ID" + " AND ");
			
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
		// System.out.println(sql);
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
		
		/*
		for(int i = 0;i<model.getColumnCount();i++)
		{
			sqlTable.setValueAt(model.getValueAt(i, 2) , i, 3);
			sqlTable.setValueAt(model.getValueAt(i,3), i, 4);
		}
		*/
		
		 companyColumn.setHeaderValue("COMPANY");
		 categoryColumn.setHeaderValue("CATEGORY");
		
		sqlTable.addColumn(companyColumn);
		sqlTable.addColumn(categoryColumn);	
	}
	

	
}