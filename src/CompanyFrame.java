import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	// JTabbedPane tabPanel = new JTabbedPane();
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JTable sqlTable = new JTable();
	JScrollPane sqlPane = new JScrollPane(sqlTable);
	
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
		
		showItems(sqlTable);
		sqlTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sqlPane.getViewport().add(sqlTable);
		
		sqlTable.setSize(200,500);
		downPanel.add(sqlPane);
		
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

			String sql = "insert into companies values (null,?,?);";
			
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
	class EditAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class DelAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private void clearForm() 
	{
		nameTField.setText("");
		descriptionTField.setText("");		
	}

}//end class MyFrame
