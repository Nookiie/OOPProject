import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Entities.BaseEntity;

public abstract class BaseEntityFrame <T> extends JFrame
{
/*
* MY POOR ATTEMPT TO START USING GENERICS
* THIS IS WHEN I FOUND OUT JAVA GENERICS SUCK
* 
* THIS CLASS IS USED AS A BASIS FOR ALL ENTITY FRAMES
*/ 
	
	Connection conn = null;
	PreparedStatement state = null;
	Statement stateNormal = null;
	ResultSet result = null;
	String referenceText = "companies";
	String tabName = null;
	String findText = null;
	
	int id = -1;
	DBConnector DBhelper = new DBConnector();
	
	// JTabbedPane tabPanel = new JTabbedPane();
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	// JLabel label = new JLabel("Categories");
	
	JTable sqlTable = new JTable();
	JScrollPane scrollPane = new JScrollPane(sqlTable);

	JLabel nameLabel = new JLabel("Name:");
	JTextField nameTField = new JTextField();
	JTextField filterTField = new JTextField();

	String[] tabNames = {"*"};
	
	JComboBox<String> filterCombo = new JComboBox<String>(tabNames);

	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");
	JButton filterBtn = new JButton("Filter");		
	
	public BaseEntityFrame()
	{
		setConstructor();
	}
		
	public  void clearForm()
	{
		nameTField.setText("");
	}
	
	public String getReference(String reference)
	{
		return reference;
	}

	public String getReferenceText() {
		return referenceText;
	}

	public void setReferenceText(String referenceText) {
		this.referenceText = referenceText;
	}
	
	public void setConstructor()
	{
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 2));

		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
			
		scrollPane.setPreferredSize(new Dimension(650, 75));
		DBhelper.refreshTable(referenceText, sqlTable);
		
		midPanel.setLayout(new GridLayout(4,2));
		
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
		
		filterTField.setColumns(12);
		downPanel.add(filterTField);
		downPanel.add(filterCombo);
		downPanel.add(filterBtn);	
		downPanel.add(addBtn);
		downPanel.add(editBtn);
		downPanel.add(delBtn);
		downPanel.add(scrollPane);
		
		filterBtn.addActionListener(new FilterAction());

	}
	class AddAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class EditAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class DelAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class FilterAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			tabName = filterCombo.getSelectedItem().toString();

			DBhelper.refreshNameTable(referenceText, sqlTable, tabName);
			
			 findText = filterTField.getText();
			 
			 if(!findText.isEmpty() && !findText.equals(" ")) 
			 		search(findText,tabName);
			// DBhelper.resetTable(referenceText, sqlTable);		
		}
		
	}
	class MouseTableAction implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
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
	
	public void setElements()
	{
		
	}
	
	public void setFilter() 
	{
		for(int i = 1;i<sqlTable.getColumnCount();i++)
		{	
			if(sqlTable.getColumnName(i).contains("ID")) // Filtering against IDs
				continue;
			
			filterCombo.insertItemAt(sqlTable.getColumnName(i) , i );
		}
	}
	
	public void search(String findText,String tabName) 
	{
		String sql = null;
		
		if(!findText.isEmpty() && !findText.equals(" ") && tabName.equals("*") && !tabName.isEmpty())
		{
			int columns = sqlTable.getColumnCount();
			StringBuilder sb = new StringBuilder();
			String currentTab = null;
			sql = "Select " +tabName + " from " + referenceText + " where ";
			sb.append(sql);
			for(int i = 1;i<columns;i++)
			{
				currentTab = sqlTable.getColumnName(i);
				
				sb.append(currentTab + " = " + "'" + findText + "'" + " OR ");
				if(i == columns - 1)
				{
					sb.delete(sb.length() - 3, sb.length());
				}
			}
			sql = sb.toString();
		}
		else
		{
			sql = "Select " +tabName + " from " + referenceText + " where " +tabName + " = " + "'" + findText + "'";
		}
		conn = DBConnector.getConnection();
		try 
		{
			state = conn.prepareStatement(sql);
			state.execute();	
			
			DBhelper.refreshQueryTable(referenceText, sqlTable, tabName, sql);
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
	}	
}
