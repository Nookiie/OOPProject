import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IndexFrame extends JFrame
{
	Connection conn = null;
	PreparedStatement statement = null;
	
	JLabel introLabel = new JLabel("Welcome to the VideoGameSite Control Panel");
	
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JButton companiesButton = new JButton("Companies");
	JButton categoriesButton = new JButton("Categories");
	JButton videoGamesButton = new JButton("VideoGames");
	
	VideoGameFrame videoGameTab = new VideoGameFrame();
	CategoryFrame categoryTab = new CategoryFrame();
	CompanyFrame companyTab = new CompanyFrame();
	
	public IndexFrame()
	{
		this.setVisible(true);
		videoGameTab.setVisible(false);
		categoryTab.setVisible(false);
		companyTab.setVisible(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(4, 1));
	
		this.add(introLabel);
		this.add(categoriesButton);
		this.add(videoGamesButton);
		this.add(companiesButton);
		
		categoriesButton.addActionListener(new CategoryView());
		videoGamesButton.addActionListener(new VideoGameView());
		companiesButton.addActionListener(new CompanyView());
	}
}
class CompanyView implements ActionListener
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
