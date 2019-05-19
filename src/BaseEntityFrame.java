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

public abstract class BaseEntityFrame extends JFrame
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
	
	
	String[] foreignReferences = {"COMPANY", "CATEGORY"};
	String[] foreignEntities = {"companies", "categories"};
	ArrayList<String> tabList = new ArrayList<String>();
	
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
	JLabel nameErrorLabel = new JLabel("Please select a name:");
	
	JTextField nameTField = new JTextField();
	JTextField filterTField = new JTextField();

	String[] tabNames = {"*"};
	
	JComboBox<String> filterCombo = new JComboBox<String>(tabNames);

	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");
	JButton filterBtn = new JButton("Filter");		
	JButton indexBtn = new JButton("Go back");
	
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
		this.setSize(700, 600);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 2));

		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
			
		scrollPane.setPreferredSize(new Dimension(650, 75));
		DBhelper.refreshByEntityTable(referenceText, sqlTable);
		
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
		downPanel.add(indexBtn);
		downPanel.add(scrollPane);
		downPanel.add(nameErrorLabel);
		
		nameErrorLabel.setVisible(false);
		
		filterBtn.addActionListener(new FilterAction());
		indexBtn.addActionListener(new IndexAction());

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
		//	if(tabName.contains("Comp")) {
		//		tabName=filterCombo.getSelectedItem().toString()+"_name";
		//		referenceText="companies";
		//	}
		//	if(tabName.contains("Categ")) {
		//		tabName=filterCombo.getSelectedItem().toString()+"_name";
		//		referenceText="categories";
		//	}
			
			tabName = filterCombo.getSelectedItem().toString();
				
			for(int i = 1;i< filterCombo.getItemCount();i++)
			{
			 if(!filterCombo.getItemAt(i).contains("ID"))
				 tabList.add(filterCombo.getItemAt(i));
			 	System.out.println(tabList.get(i - 1) + " TABLIST");
			}
			DBhelper.refreshNameTable(referenceText, sqlTable, tabName,tabList);
			
			 findText = filterTField.getText();
			 
			 if(!findText.isEmpty() && !findText.equals(" ")) 
			 		search(findText,tabName, tabList);
			 
			 tabList.clear();
			// DBhelper.resetTable(referenceText, sqlTable);		
		}
	}
	
	class IndexAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
				setVisible(false);
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
	
	public void setComboFilter() 
	{
		for(int i = 1;i<sqlTable.getColumnCount();i++)
		{	
			if(!sqlTable.getColumnName(i).contains("ID")) // Filtering against ComboBox ID
				filterCombo.insertItemAt(sqlTable.getColumnName(i) , i);
		}
	}
	public void setForeignFilter()
	{
		 DBhelper.refreshForeignKeyTable(referenceText, "game_name", foreignEntities, foreignReferences,sqlTable);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	/**
	 * @param findText
	 * @param tabName
	 */
	public void search(String findText,String tabName, ArrayList<String> tabList) 
	{
		String sql = null;
		int columns = sqlTable.getColumnCount();

		if(!findText.isEmpty() && !findText.equals(" ")  && tabName.equals("*") && !tabName.isEmpty())
		{
			
			StringBuilder sb = new StringBuilder();
			String currentTab = null;
			//sql = "Select " +tabName + " from " + referenceText + " where ";
			if(referenceText.equals("videogames")){
			sql = "Select * from " + referenceText
					+ " join companies on company_id=companies.id"
					+ " join categories on category_id=categories.id   where ";
			}
			else {
				sql = "Select * from " + referenceText + " where "; // CHANGE MADE
			}
			sb.append(sql);
			
			
			for(int i = 1;i<columns;i++)
			{
				currentTab = sqlTable.getColumnName(i);
				
				if(i<3) {
					sb.append( currentTab + " = " + "'" + findText + "'" + " OR ");
				}
				
				else if(i>=3) {
				sb.append( foreignReferences[i-3] + "_name = " + "'" + findText + "'" + " OR ");

				}
										
				if(i == columns - 1)
				{
					sb.delete(sb.length() - 3, sb.length());
				}
			}
			sql = sb.toString();
		}
		else if(findText.equals(" ")|| findText.isEmpty())
		{
			if(tabName.contains("COMPANY")) {
				//!!! If tabName is from "videogames foreign key" turns into "company",
				//    if it is from "Companies"=> "company_name" and that created a lot of spaghet
				
				//		sql = "Select " +" company_name " + " from " + "companies" + " where " + tabName + " = " + "'" + findText + "'";
						sql = "Select * "  + " from " + "companies" + " where " + " company" + "_name = " + "'" + findText + "'";
				}
				//else if(tabName.equals("CATEGORY")) {
				//		sql = "Select " +" category_name " + " from " + "categories" + " where " + tabName + "_name = " + "'" + findText + "'";
				//	}
			else if(tabName.contains("CATEGORY")) {
				// Same here as in company!
						sql = "Select * from " + "categories" + " where " + " category" + "_name = " + "'" + findText + "'";

				}	
			else {
						sql = "Select * from " + " videogames " + " where " + tabName + " = " + "'" + findText + "'";				
				}
		}
		else 
		{
			//if(tabName.contains("COMPANY"))
		//	{
			System.out.println(tabList.size());
				sql = "Select  ";
				StringBuilder sb = new StringBuilder();
				sb.append(sql);
				for(int i = 0;i<tabList.size();i++)
				{
					sb.append(tabList.get(i));
					if(i != tabList.size() - 1)
						sb.append(", ");
										
				}
				//sb.append(" from " + "videogames join companies on videogames.company_id = companies.id join categories on videogames.category_id = categories.id where company_name = " + "'" + findText + "'");
				if(referenceText.contains("videogames"))
				sb.append(" from " + "videogames join companies on videogames.company_id = companies.id join categories on videogames.category_id = categories.id where "+ tabName +  " = '" + findText + "'");
				else {// Makes filtering with text possible in CategoryFrame and CompanyFrame
//////////////////////NEW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					sb.append(" from "+ referenceText+" where "+ tabName +  " = '" + findText + "'");
				}
				sql = sb.toString();
			//}
			//if(tabName.contains("CATEGORY"))
			//{
				
		//	}
		}
		
		/*else if(tabName.contains("COMPANY")) {
			//!!! If tabName is from "videogames foreign key" turns into "company",
			//    if it is from "Companies"=> "company_name" and that created a lot of spaghet
			
			//		sql = "Select " +" company_name " + " from " + "companies" + " where " + tabName + " = " + "'" + findText + "'";
					sql = "Select * "  + " from " + "companies" + " where " + " company" + "_name = " + "'" + findText + "'";
			}
			//else if(tabName.equals("CATEGORY")) {
			//		sql = "Select " +" category_name " + " from " + "categories" + " where " + tabName + "_name = " + "'" + findText + "'";
			//	}
		else if(tabName.contains("CATEGORY")) {
			// Same here as in company!
					sql = "Select * from " + "categories" + " where " + " category" + "_name = " + "'" + findText + "'";

			}	
		else {
					sql = "Select * from " + " videogames " + " where " +tabName + " = " + "'" + findText + "'";				
			}
		*/
		
		//}
		//
			//	columns<3) {
		//	sql = "Select " +tabName + " from " + referenceText + " where " +tabName + " = " + "'" + findText + "'";
		//}
		//else if(columns>=3) {
		//	sql = "Select " +"*" + " from " + foreignEntities[columns-3] + " where " +tabName + " = " + "'" + findText + "'";
		//}
			
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
	public void getForeignKeys(String entityOne, String entityTwo)
	{
		String sql = "Select * from" + entityOne + " join " + entityTwo + " where " + entityOne + "." + entityTwo + "_ID = " + entityTwo + ".ID";
		
		conn = DBConnector.getConnection();
		try 
		{
			state = conn.prepareStatement(sql);
			
			state.execute();		
			id = -1;
			//DBhelper.refreshTable(referenceText, sqlTable);
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
	}

}