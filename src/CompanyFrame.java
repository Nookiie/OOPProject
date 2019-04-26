import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CompanyFrame extends JFrame
{
	Connection conn = null;
	PreparedStatement state = null;
	
	// JTabbedPane tabPanel = new JTabbedPane();
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JButton addBtn = new JButton("Add");
	
	JLabel CompanyLabel = new JLabel("Add a Company");
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
		
		upPanel.add(CompanyLabel);
		
		midPanel.setLayout(new GridLayout(4, 2));
		
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
		midPanel.add(descriptionLabel);	
		midPanel.add(descriptionTField);
		
		
		//midPanel
		downPanel.add(addBtn);
		addBtn.addActionListener(new AddAction());
		//downPanel
	}//end constructor
	
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
		
	}//end AddAction
	
	private void clearForm() 
	{
		nameTField.setText("");
		descriptionTField.setText("");		
	}

}//end class MyFrame
