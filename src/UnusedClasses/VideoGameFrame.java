package UnusedClasses;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Entities.Category;
import Entities.Company;

public class VideoGameFrame extends JFrame
{
	/*
	Connection conn = null;
	PreparedStatement state = null;
	String referenceText = "videogames";
	ResultSet result = null;
	int id = -1;
	DBConnector DBhelper = new DBConnector();
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");
	
	JTable sqlTable = new JTable();
	JScrollPane scrollPane = new JScrollPane(sqlTable);
	
	JLabel videoGameLabel = new JLabel("Add a Video Game");
	JLabel nameLabel = new JLabel("Name:");
	JLabel descriptionLabel = new JLabel("Description");
	JLabel categoryLabel = new JLabel("Category:");
	JLabel companyLabel = new JLabel("Company:");
	
	JTextField nameTField = new JTextField();
	JTextArea descriptionTField = new JTextArea();
	
	 String[] companies = {"","Company"}; // Argument needs to be list of companies
	 String[] categories = {"","Category"}; // Argument needs to be list of categories
	
	// ArrayList<Company> companies = new ArrayList<Company>();
	// ArrayList<Category> categories = new ArrayList<Category>();
	
	String querySelectCompanies = "select ID from companies";
	String querySelectCategories = "select ID from categories";
		
	JComboBox<Object> categoryCombo = new JComboBox<Object>(companies);
	JComboBox<Object> companyCombo = new JComboBox<Object>(categories);
	
	public VideoGameFrame() 
	{
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 1));

		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		
		//upPanel
		
		upPanel.add(videoGameLabel);
		
		midPanel.setLayout(new GridLayout(4, 2));
		
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
		midPanel.add(descriptionLabel);	
		midPanel.add(descriptionTField);
		midPanel.add(categoryLabel);
		midPanel.add(categoryCombo);
		midPanel.add(companyLabel);
		midPanel.add(companyCombo);
		
		scrollPane.setPreferredSize(new Dimension(350, 100));
		DBhelper.resetTable(referenceText, sqlTable);
		sqlTable.addMouseListener(new MouseTableAction());
		
		sqlTable.getColumnModel().getColumn(0).setMinWidth(0);
		sqlTable.getColumnModel().getColumn(0).setMaxWidth(0); // Hiding the ID from the Index Table
		
		downPanel.add(scrollPane);
		downPanel.add(addBtn);
		downPanel.add(editBtn);
		downPanel.add(delBtn);

		addBtn.addActionListener(new AddAction());
		editBtn.addActionListener(new EditAction());
		delBtn.addActionListener(new DelAction());
	
	}//end constructor
	
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String name = nameTField.getText();
			String description = descriptionTField.getText();

			String company = companyCombo.getSelectedItem().toString();
			String category = categoryCombo.getSelectedItem().toString();
			String sql = "insert into videogames values (null,?,?);";
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setString(2, description);
				// state.setString(3, category);
				// state.setString(4, company);
				
				state.execute();	
				id = -1;
				DBhelper.resetTable(referenceText, sqlTable);
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
		
	}//end AddAction
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
				DBhelper.resetTable(referenceText, sqlTable);
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
				DBhelper.resetTable(referenceText, sqlTable);
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
	
	private void clearForm() 
	{
		nameTField.setText("");
		descriptionTField.setText("");
		
		companyCombo.setSelectedIndex(0);
		categoryCombo.setSelectedIndex(0);
	}
*/
}//end class MyFrame
