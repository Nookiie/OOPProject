package Entities;

public class Company extends BaseEntity 
{	
	private String Description;
	
	public Company(int id, String name, String description) 
	{
		super(id,name);
		this.Description = description;
	}
	
}
