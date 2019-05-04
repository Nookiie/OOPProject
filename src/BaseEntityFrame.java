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

import javax.swing.JButton;
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
* THIS CLASS IS NOT USED YET
 * MIGHT BE USED LATER IN THE FUTURE 
*/ 
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	String referenceText = "companies";
	
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
	
	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");
	JButton filterBtn = new JButton("Filter");		
	
	public BaseEntityFrame()
	{
		setConstructor();
	}
	/*
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String name = nameTField.getText();
			DBhelper.resetTable(getReferenceText(), sqlTable);
			String sql = "insert into "+ getReferenceText() + " values (null,?);";
			
			conn = DBConnector.getConnection();
			try 
			{
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				
				state.execute();	
				id = -1;
				DBhelper.resetTable(getReferenceText(), sqlTable);
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
	*/
	
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
		this.setLayout(new GridLayout(3, 1));

		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
			
		scrollPane.setPreferredSize(new Dimension(650, 100));
		DBhelper.resetTable(getReferenceText(), sqlTable);
		
		midPanel.setLayout(new GridLayout(4, 2));
				
		midPanel.add(nameLabel);
		midPanel.add(nameTField);
			
		downPanel.add(addBtn);
		downPanel.add(editBtn);
		downPanel.add(delBtn);
		downPanel.add(filterBtn);
		downPanel.add(filterTField);
		downPanel.add(scrollPane);	

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
			// TODO Auto-generated method stub
			
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
}
