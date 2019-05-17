import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JButton companiesButton = new JButton("Companies");
	JButton categoriesButton = new JButton("Categories");
	JButton videoGamesButton = new JButton("VideoGames");
	
	JButton companiesFilterButton = new JButton("Filter by Company");
	JButton categoriesFilterButton= new JButton("Filter by Category");
	
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
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(4,2));
			
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);

		upPanel.add(introLabel);
		midPanel.setLayout(new GridLayout(1,6));
		midPanel.add(categoriesButton);
		midPanel.add(videoGamesButton);
		midPanel.add(companiesButton);
		
		companyInput.setColumns(12);
		categoryInput.setColumns(12);
		
		downPanel.setLayout(new FlowLayout());
		downPanel.add(companyInput);
		downPanel.add(companiesFilterButton);
		downPanel.add(categoryInput);
		downPanel.add(categoriesFilterButton);
		
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
		
	}
}
class CategoryFilterView implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
}
