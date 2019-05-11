import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JLabel;

public class CategoryFrame extends BaseEntityFrame 
{	
	JLabel categoryLabel = new JLabel("Set up the Categories");
	
	public CategoryFrame()
	{
			super.referenceText = "categories";
			
			super.setConstructor();
			setActionListeners();
			setElements();
			setFilter();
	}
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String name = nameTField.getText();
			
			if(name.equals(" ") || name.isEmpty())
				return;
			
			DBhelper.refreshTable(referenceText, sqlTable);
			String sql = "insert into "+ referenceText + " values (null,?);";
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				
				state.execute();	
				id = -1;
				DBhelper.refreshTable(referenceText, sqlTable);
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			finally 
			{
				try 
				{
					state.close();
					conn.close();
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
			clearForm();
		}
	}
	class EditAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String sql = "SELECT * FROM " + referenceText;
			StringBuilder stringBuilder = new StringBuilder();
			
			conn = DBConnector.getConnection();
			try {
				state = conn.prepareStatement(sql);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try
			{
				int columns = state.getMetaData().getColumnCount();
				
				sql = "update " + referenceText + " set ";
				stringBuilder.append(sql);
				for(int i = 2;i<=columns;i++)
				{
					stringBuilder.append(state.getMetaData().getColumnName(i) + " = ?, ");	
					if(i == columns)
						stringBuilder.deleteCharAt(stringBuilder.length() - 2); // Removing the comma on the last SET query before when
				}
				
				stringBuilder.append(" where ID = ?");
				sql = stringBuilder.toString();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String name = nameTField.getText();
			
			conn = DBConnector.getConnection();
			try
			{
				state = conn.prepareStatement(sql);
				state.setString(1,name);
				state.setInt(2,id);
				
				state.execute();
				id = -1;
				DBhelper.refreshTable(referenceText, sqlTable);
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
					e.printStackTrace();
				}
				
			}
		}
	}
	class DelAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String sql = "delete from " + referenceText + " where id=?";
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				
				id = -1;
				DBhelper.refreshTable(referenceText, sqlTable);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}
}
	class FilterAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String sql = "Select * from " + referenceText + " where name = ?";
			
			conn = DBConnector.getConnection();
			try
			{
				String text = filterTField.getText();
				state = conn.prepareStatement(sql);
				state.setString(1,text);
				state.execute();
				
				// DBhelper.resetTable(referenceText, sqlTable);
			}
			catch(SQLException e1)
			{
				e1.printStackTrace();
			}
			finally
			{
				try {
					state.close();
					conn.close();
					clearForm();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		}	
	}
	class MouseTableAction implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = sqlTable.getSelectedRow();
			id = Integer.parseInt(sqlTable.getValueAt(row, 0).toString());
			
			if(e.getClickCount() == 1) {
				nameTField.setText(sqlTable.getValueAt(row, 1).toString());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public void setActionListeners()
	{
		addBtn.addActionListener(new AddAction());
		editBtn.addActionListener(new EditAction());
		delBtn.addActionListener(new DelAction());
		filterBtn.addActionListener(new FilterAction());
		sqlTable.addMouseListener(new MouseTableAction());
	}
	
	@Override
	public void setElements()
	{
		super.upPanel.add(categoryLabel);
	}
}
