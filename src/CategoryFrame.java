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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CategoryFrame extends JFrame
{
	Connection conn = null;
	PreparedStatement state = null;
		
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JButton addBtn = new JButton("Add");
	
	JLabel categoryLabel = new JLabel("Add a Category");
	
	JLabel nameLabel = new JLabel("Name:");
	
	JTextField nameTField = new JTextField();
	
	public CategoryFrame() 
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 1));
		this.setName("categories");

		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		
		//upPanel
		
		upPanel.add(categoryLabel);
		
		midPanel.setLayout(new GridLayout(4, 2));
		
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
		
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

			String sql = "insert into categories values (null,?);";
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				
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
	}

}//end class MyFrame
