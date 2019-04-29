import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class Model extends AbstractTableModel
{
	private ResultSet result;
	private int rowCount;
	private int columnCount;
	private ArrayList<Object> items = new ArrayList<Object>();
	
	public Model(ResultSet result) throws Exception
	{
		setResult(result); // I am setting a result here	
	}
	public void setResult(ResultSet rs) throws Exception
	{
		this.result = rs;
		ResultSetMetaData metaData = rs.getMetaData();
		rowCount = 0;
		columnCount = metaData.getColumnCount();
		
		while(rs.next())
		{
			Object[]row = new Object[columnCount];
			for(int i = 0;i<columnCount;i++)
			{
				row[i] = rs.getObject(i+1);
			}
			items.add(row);
			rowCount++;
		}
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnCount;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowCount;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object[] row = (Object[])items.get(rowIndex);
		return row[columnIndex];
	}
	
	@Override
	public String getColumnName(int columnIndex) 
	{
		try
		{
			ResultSetMetaData metaData = result.getMetaData();
			return metaData.getColumnName(columnIndex + 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
}
