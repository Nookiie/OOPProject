package Entities;

import java.sql.Date;

public class VideoGame extends BaseEntity
{
	private String Description;
	
	private Date DateOfMaking;
	
	private int Category_FK_ID; // Needs to be added and fulfilled later on
	private int Company_FK_ID;
	
	public VideoGame(int id, String name, String description) 
	{
		super(id,name);
		this.Description = description;
	}
	 
	
}
