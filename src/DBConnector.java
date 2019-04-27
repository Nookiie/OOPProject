import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	
	static Connection conn = null;
	static Statement state = null;
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
			result = state.executeQuery();
			model = new Model(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}//end method

}
