package UnusedClasses;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CategoryFrame extends JFrame
{
	/*
	Connection conn = null;
	PreparedStatement state = null;
	
	int id = -1;
	DBConnector DBhelper = new DBConnector();
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JTable sqlTable = new JTable();
	JScrollPane scrollPane = new JScrollPane(sqlTable);
	
	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");
	JButton searchBtn = new JButton("Filter");
	
	JTextField filterTField = new JTextField();
	
	JLabel categoryLabel = new JLabel("Add a Category");
	
	JLabel nameLabel = new JLabel("Name:");
	
	JTextField nameTField = new JTextField();
	
	
	public CategoryFrame() 
	{
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 1));
		this.setName("categories");		
		
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		
		//upPanel
		
		upPanel.add(categoryLabel);
		
		midPanel.setLayout(new GridLayout(4, 2));
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
		
		scrollPane.setPreferredSize(new Dimension(350, 100));
		DBhelper.resetTable(getReferenceText(), sqlTable);
		sqlTable.addMouseListener(new MouseTableAction());
		
		downPanel.add(addBtn);
		downPanel.add(editBtn);
		downPanel.add(delBtn);
		downPanel.add(searchBtn);
		downPanel.add(scrollPane);
		midPanel.add(filterTField);
	
		
		addBtn.addActionListener(new AddAction());
		editBtn.addActionListener(new EditAction());
		delBtn.addActionListener(new DelAction());
		searchBtn.addActionListener(new FilterAction());
		//downPanel
	}//end constructor
	
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String name = nameTField.getText();
			DBhelper.resetTable(getReferenceText(), sqlTable);
			String sql = "insert into "+ getReferenceText() + " values (null,?);";
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				
				state.execute();	
				id = -1;
				DBhelper.resetTable(getReferenceText(), sqlTable);
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
			String sql = "SELECT * FROM " + getReferenceText();
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
				
				sql = "update " + getReferenceText() + " set ";
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
				DBhelper.resetTable(getReferenceText(), sqlTable);
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
			
			String sql = "delete from " + getReferenceText() + " where id=?";
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				
				id = -1;
				DBhelper.resetTable(getReferenceText(), sqlTable);
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
			String sql = "Select * from " + getReferenceText() + " where name = ?";
			
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
	
	public void clearForm() 
	{	
		nameTField.setText("");
	}
*/
}//end class MyFrame
