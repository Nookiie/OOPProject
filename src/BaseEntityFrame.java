import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import Entities.BaseEntity;

public abstract class BaseEntityFrame <T extends BaseEntity> extends JFrame
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
	String referenceText = "default";
	
	// JTabbedPane tabPanel = new JTabbedPane();
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JLabel label = new JLabel("What?");
	JTable sqlTable = new JTable();

	
	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Update");
	JButton delBtn = new JButton("Delete");
	
	public BaseEntityFrame()
	{
		Connection conn = null;
		PreparedStatement state = null;
		
			
		JPanel upPanel = new JPanel();
		JPanel midPanel = new JPanel();
		JPanel downPanel = new JPanel();
		
		JButton addBtn = new JButton("Add");
		JButton editBtn = new JButton("Update");
		JButton delBtn = new JButton("Delete");
							
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocation(450, 200);
		this.setLayout(new GridLayout(3, 1));

		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
			
			//upPanel
		midPanel.setLayout(new GridLayout(4, 2));
			
			//midPanel
		downPanel.add(addBtn);
		downPanel.add(editBtn);
		downPanel.add(delBtn);
			
		addBtn.addActionListener(new AddAction());
	}
	class AddAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
