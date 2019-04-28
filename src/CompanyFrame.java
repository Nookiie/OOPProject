import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Entities.Company;

public class CompanyFrame extends JFrame
{
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	int id = -1;
	
	String referenceText = "companies";
	
	// JTabbedPane tabPanel = new JTabbedPane();
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JTable sqlTable = new JTable();
	JScrollPane scrollPane = new JScrollPane(sqlTable);
	
	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");

	JLabel companyLabel = new JLabel("Add a Company");
	JLabel nameLabel = new JLabel("Name:");
	JLabel descriptionLabel = new JLabel("Description");
		
	JTextField nameTField = new JTextField();
	JTextArea descriptionTField = new JTextArea();	
	
	public CompanyFrame() 
	{
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 1));

		// this.add(tabPanel);
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		
		//upPanel
		
		upPanel.add(companyLabel);
		
		midPanel.setLayout(new GridLayout(4, 2));
		
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
		midPanel.add(descriptionLabel);	
		midPanel.add(descriptionTField);
		
		//midPanel
		
		downPanel.add(addBtn);
		downPanel.add(editBtn);
		downPanel.add(delBtn);
		
		downPanel.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(350, 100));
		sqlTable.setModel(DBConnector.getAllModel(referenceText));
		sqlTable.addMouseListener(new MouseTableAction());
		
		addBtn.addActionListener(new AddAction());
		editBtn.addActionListener(new EditAction());
		delBtn.addActionListener(new DelAction());
		
		//downPanel
	}//end constructor
	
	public void showItems(JTable sqlTable)
	{
		// ArrayList<Company> companies = new ArrayList<Company>();
		try
		{
			Class.forName("org.h2.Driver");
			String url = "jdbc:h2:tcp://localhost/server~/OOPProjectDB";
			conn = DBConnector.getConnection();
			String selectAllQuery = "select * from companies";
			state = conn.prepareStatement(selectAllQuery);
			result = state.executeQuery();
			
			while(sqlTable.getRowCount() > 0) 
	        {
	            ((DefaultTableModel) sqlTable.getModel()).removeRow(0);
	        }
			
			int columns = result.getMetaData().getColumnCount();
			while(result.next())
			{
				Object[] row = new Object[columns];
				for(int i = 1;i<=columns;i++)
				{
					row[i - 1] = result.getObject(i);
				}	
				((DefaultTableModel)sqlTable.getModel()).insertRow(result.getRow()-1,row);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				state.close();
				conn.close();
				result.close();
			}
			catch(SQLException e1)
			{
				e1.printStackTrace();
			}	
		}
		// return result;
	}
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String name = nameTField.getText();
			String description = descriptionTField.getText();

			String sql = "insert into " + referenceText + " values (null,?,?);";
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setString(2, description);
				
				state.execute();	
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
	class MouseTableAction implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = sqlTable.getSelectedRow();
			id = Integer.parseInt(sqlTable.getValueAt(row, 0).toString());
			if(e.getClickCount() == 1) {
				nameTField.setText(sqlTable.getValueAt(row, 1).toString());
				descriptionTField.setText(sqlTable.getValueAt(row, 2).toString());
			}			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
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
			String description = descriptionTField.getText();
			
			conn = DBConnector.getConnection();
			try
			{
				state = conn.prepareStatement(sql);
				state.setString(1,name);
				state.setString(2,description);
				state.setInt(3,id);
				
				state.execute();
				id = -1;
				sqlTable.setModel(DBConnector.getAllModel(referenceText));
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
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					id = -1;
					sqlTable.setModel(DBConnector.getAllModel(referenceText));
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

	private void clearForm() 
	{
		nameTField.setText("");
		descriptionTField.setText("");		
	}

}//end class MyFrame
