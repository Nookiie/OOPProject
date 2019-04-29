import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;

public class DBConnector {
	
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
		try {
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
	}//end method
	public void resetTable(String entity, JTable sqlTable)
	{
		sqlTable.setModel(DBConnector.getAllModel(entity));
		sqlTable.getColumnModel().getColumn(0).setMinWidth(0);
		sqlTable.getColumnModel().getColumn(0).setMaxWidth(0); // Hiding the ID from the Index Table	
	}
	public static Model getAllExceptIDModel(String entity) // NOT YET COMPLETE
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
}
