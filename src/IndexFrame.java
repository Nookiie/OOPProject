import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class IndexFrame extends JFrame
{
	Connection conn = null;
	PreparedStatement statement = null;
	static DBConnector DBhelper = new DBConnector();
	
	JLabel introLabel = new JLabel("Welcome to the Site Control Panel");
	JLabel gameNameLabel = new JLabel("Game Name:");
	JLabel gameDescriptionLabel = new JLabel("Game Desc:");
	JLabel companyLabel = new JLabel("Company Name:");
	JLabel categoryLabel = new JLabel("Category Name:");
	
	String referenceText = "videogames";
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel downMidPanel = new JPanel();
	JPanel downMidAltPanel = new JPanel();
	
	JButton companiesButton = new JButton("Companies");
	JButton categoriesButton = new JButton("Categories");
	JButton videoGamesButton = new JButton("VideoGames");
	
	JButton companiesFilterButton = new JButton("Filter by Company");
	JButton categoriesFilterButton= new JButton("Filter by Category");
	
	JTextField gameCompanyInput = new JTextField();
	JTextField gameCategoryInput = new JTextField();
	JTextField companyInput = new JTextField();
	JTextField categoryInput = new JTextField();
	
	JTable sqlTable = new JTable();
	JScrollPane scrollPane = new JScrollPane(sqlTable);
	
	VideoGameFrame videoGameTab = new VideoGameFrame();
	CategoryFrame categoryTab = new CategoryFrame();
	CompanyFrame companyTab = new CompanyFrame();
	
	public IndexFrame()
	{
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 800);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(7,1));
			
		this.add(upPanel);
		this.add(midPanel);
		this.add(downMidAltPanel);
		this.add(downMidPanel);
		//this.add(downPanel);
		
		upPanel.add(introLabel);
		midPanel.setLayout(new GridLayout(1,6));
		midPanel.add(categoriesButton);
		midPanel.add(videoGamesButton);
		midPanel.add(companiesButton);
		
		companyInput.setColumns(12);
		categoryInput.setColumns(12);
		gameCompanyInput.setColumns(12);
		gameCategoryInput.setColumns(12);
		
		downMidAltPanel.setLayout(new FlowLayout());
		
		downMidAltPanel.add(gameDescriptionLabel);
		downMidAltPanel.add(gameCompanyInput);
		scrollPane.setPreferredSize(new Dimension(650,500));
		
		downMidAltPanel.add(companyLabel);
		downMidAltPanel.add(companyInput);
		
		downMidAltPanel.add(companiesFilterButton);
		
		downMidPanel.setLayout(new FlowLayout());

		downMidPanel.add(gameNameLabel);
		downMidPanel.add(gameCategoryInput);
		
		downMidPanel.add(categoryLabel);
		downMidPanel.add(categoryInput);
		
		downMidPanel.add(categoriesFilterButton);

		
		//downPanel.setLayout(new GridLayout(4,1));
	
		
		this.add(scrollPane);
		
		DBhelper.refreshAllTable(sqlTable);
		
		categoriesButton.addActionListener(new CategoryView());
		videoGamesButton.addActionListener(new VideoGameView());
		companiesButton.addActionListener(new CompanyView());
		
		companiesFilterButton.addActionListener(new CompanyFilterView());
		categoriesFilterButton.addActionListener(new CategoryFilterView());
		
}
public void setVisibility(boolean value)
{
	this.setVisible(value);
}

public class CompanyView implements ActionListener
{	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CompanyFrame company = new CompanyFrame();
		company.setVisible(true);
	}
}

class CategoryView implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0) {
				CategoryFrame category = new CategoryFrame();
				category.setVisible(true);
	}
}

class VideoGameView implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0) {
			VideoGameFrame videoGame = new VideoGameFrame();
			videoGame.setVisible(true);
	}
}
class CompanyFilterView implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		String findTextCompany = companyInput.getText();		

		String findTextGameDescription = gameCompanyInput.getText();
		String sql = null;
		
		if(!findTextCompany.isEmpty() && !findTextCompany.equals(" ") ) {
			sql = "Select game_name, game_description, category_name, company_name, company_description "
					+ "from videogames join companies on company_id=companies.id "
					+ "join categories on category_id=categories.id where "
					+ "game_description = '"+ findTextGameDescription + "' AND company_name = '" + findTextCompany + "'";
					//findTextCompany+"' or company_name = '"+findTextCompany+"'";
			conn = DBConnector.getConnection();
			try 
			{
				statement = conn.prepareStatement(sql);
				statement.execute();	
				
				DBhelper.refreshQueryTable(referenceText, sqlTable, "", sql);
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			finally 
			{
				try 
				{
					statement.close();
					conn.close();
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
			
		}
		else {
			DBhelper.refreshAllTable(sqlTable);
		}						
	}
}


class CategoryFilterView implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String findTextCategoryName = categoryInput.getText();
		String findTextGameName = gameCategoryInput.getText();
		
		String sql = null;
		if(!findTextCategoryName.isEmpty() && !findTextCategoryName.equals(" ") ) {
			sql = "Select game_name, game_description, category_name, company_name "
					+ "from videogames join companies on company_id=companies.id "
					+ "join categories on category_id=categories.id where "
					+ "game_name = '"+ findTextGameName + "' AND category_name = '" + findTextCategoryName + "'"; 
					//findTextCategoryName+"' or category_name = '"+findTextCategoryName+"'";
		
			conn = DBConnector.getConnection();
			try 
			{
				statement = conn.prepareStatement(sql);
				statement.execute();	
				
				DBhelper.refreshQueryTable(referenceText, sqlTable, "", sql);
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			finally 
			{
				try 
				{
					statement.close();
					conn.close();
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		else {
			DBhelper.refreshAllTable(sqlTable);
		}			
	}
}
}
