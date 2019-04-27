package Entities;

public abstract class BaseEntity 
{
	private int ID;
	private String Name;
	
	public BaseEntity(int id,String name)
	{
		this.ID = id;
		this.Name = name;
	}
	
	public String GetName()
	{
		return Name;
	}
	public int GetID()
	{
		return ID;
	}
}
