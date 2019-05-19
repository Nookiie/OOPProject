import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

public class VideoGameFrame extends BaseEntityFrame
{	
	JLabel videoGameLabel = new JLabel("Set up the VideoGames");
	JLabel descriptionLabel = new JLabel("Description:");
	JLabel companyLabel = new JLabel("Company: ");
	JLabel categoryLabel = new JLabel("Category:");
	
	JLabel categoryErrorLabel = new JLabel("Please select a category");
	JLabel companyErrorLabel = new JLabel("Please select a company");
	
	JTextField descriptionTField = new JTextField();
	
	Object[] companies = new Object[20];
	Object[] categories = new Object[20];
	
	JComboBox<Object> categoryCombo = new JComboBox<Object>(categories);
	JComboBox<Object> companyCombo = new JComboBox<Object>(companies);
	
	public VideoGameFrame()
	{
			super.referenceText = "videogames";
			
			super.setConstructor();
			
			setForeignFilter();
			setComboList();
			setActionListeners();
			setElements();
			setComboFilter();	
			
	}
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String name = nameTField.getText();
			String description = descriptionTField.getText();

			String company = companyCombo.getSelectedItem().toString();
			String category = categoryCombo.getSelectedItem().toString();
/////////////////////////////////////////////////////////////////////////////////////////////////////////
			int companyID = Integer.parseInt(DBConnector.getDataFromEntity("companies","ID", "company_name", company));
			int categoryID = Integer.parseInt(DBConnector.getDataFromEntity("categories","ID", "category_name", category));
			
			String sql = "insert into videogames values (null,?,?,?,?);"; 
			
			if(name.equals(" ") || name.isEmpty() || description.isEmpty() || company.isEmpty() || category.isEmpty() || companyCombo.getSelectedItem().equals(" ") || categoryCombo.getSelectedItem().equals(" "))
			{
				if(companyCombo.getSelectedItem().equals(" "))
				{
					companyErrorLabel.setVisible(true);
				}
				if(categoryCombo.getSelectedItem().equals(" "))
				{
					categoryErrorLabel.setVisible(true);
				}
				return;	
			}
				
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setString(2, description);
				state.setInt(3, companyID);
				state.setInt(4, categoryID);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				state.execute();	
				id = -1;
				// DBhelper.refreshTable(referenceText, sqlTable);
				DBhelper.refreshForeignKeyTable(referenceText, "game_name", foreignEntities, foreignReferences,sqlTable);
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
			String description = descriptionTField.getText();
			
			String company = companyCombo.getSelectedItem().toString();
			String category = categoryCombo.getSelectedItem().toString();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			int companyID = Integer.parseInt(DBConnector.getDataFromEntity("companies","ID", "company_name", company));
			int categoryID = Integer.parseInt(DBConnector.getDataFromEntity("categories","ID", "category_name", category));
			
			conn = DBConnector.getConnection();
			try
			{
				state = conn.prepareStatement(sql);
				state.setString(1,name);
				state.setString(2,description);
				state.setInt(3,companyID);
				state.setInt(4,categoryID);
				
				state.execute();
				id = -1;
				// DBhelper.refreshTable(referenceText, sqlTable);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				DBhelper.refreshForeignKeyTable(referenceText, "game_name", foreignEntities, foreignReferences,sqlTable);
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
				DBhelper.refreshByEntityTable(referenceText, sqlTable);
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
			
			//NEW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//Decrement all values to make these appear in update boxes after filtering!
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			
			id = Integer.parseInt(sqlTable.getValueAt(row, 0).toString());
			if(e.getClickCount() == 1) {
				nameTField.setText(sqlTable.getValueAt(row, 1).toString());
				descriptionTField.setText(sqlTable.getValueAt(row, 2).toString());
				
				if(!sqlTable.getValueAt(row,3).toString().isEmpty() && !sqlTable.getValueAt(row, 4).toString().isEmpty()) // Setting up the ComboBox implementation
				{
					String companyName = sqlTable.getValueAt(row, 3).toString();
					String categoryName = sqlTable.getValueAt(row,4).toString();
					
					companyCombo.setSelectedItem(companyName);
					categoryCombo.setSelectedItem(categoryName);					
				}
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
	public void setActionListeners()
	{
		addBtn.addActionListener(new AddAction());
		editBtn.addActionListener(new EditAction());
		delBtn.addActionListener(new DelAction());
		// filterBtn.addActionListener(new FilterAction());
		sqlTable.addMouseListener(new MouseTableAction());
	}
	
	@Override
	public void setElements()
	{
		super.upPanel.add(videoGameLabel);

		categoryErrorLabel.setVisible(false);
		companyErrorLabel.setVisible(false);
		
		super.midPanel.add(descriptionLabel);
		super.midPanel.add(descriptionTField);
		super.midPanel.add(companyLabel);
		super.midPanel.add(companyCombo);
		super.midPanel.add(categoryLabel);
		super.midPanel.add(categoryCombo);
		
		super.downPanel.add(companyErrorLabel);
		super.downPanel.add(categoryErrorLabel);
		
	
	}
	public void setComboList()
	{
		 int companiesLength = DBConnector.getDataFromProperty("companies", "company_name", sqlTable).length;
		 int categoriesLength = DBConnector.getDataFromProperty("categories", "category_name", sqlTable).length;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  System.out.println(categoriesLength);
		  System.out.println(companiesLength);
		 
		  categoryCombo.removeAllItems(); // Removing the Set of Blank Object Items before changing them
		  companyCombo.removeAllItems();
		  
		  categoryCombo.addItem(" ");
		  companyCombo.addItem(" ");
		  
		 for(int i = 0;i<companiesLength;i++)
		 {
			 companies[i] = DBConnector.getDataFromProperty("companies", "company_name", sqlTable)[i];
			 companyCombo.addItem(companies[i]);
		 }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		 
		 for(int i = 0;i<categoriesLength;i++)
		 {
			  categories[i] = DBConnector.getDataFromProperty("categories", "category_name", sqlTable)[i];
			  categoryCombo.addItem(categories[i]);
		 }	  
	}
	
	public void setComboFilter() 
	{
		for(int i = 1;i<sqlTable.getColumnCount();i++)
		{	
			if(!sqlTable.getColumnName(i).contains("ID")) // Filtering against ComboBox ID
			{
				filterCombo.insertItemAt(sqlTable.getColumnName(i) , i);
			}
		}
		filterCombo.removeItemAt(4);
		filterCombo.removeItemAt(3);
		
		filterCombo.addItem("COMPANY_NAME");
		filterCombo.addItem("CATEGORY_NAME");
		
	}
}