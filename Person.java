import java.util.*;
public class Person
{
	public static int RECOVERY_TIME = 4;
	public static int NUM_CONTACTS = 10;
	private int x;
	private int y;
	public static double INFECTION = .5;
	private ArrayList<Person> contacts;
	private Grid myGrid;
	private boolean infected;
	private boolean recovered;
	private int count = 0;

	public Person(int x, int y, Grid grid)
	{
		boolean infected = false;
		contacts = new ArrayList<Person>(NUM_CONTACTS);
		myGrid = grid;
		this.x = x;
		this.y = y;
	}
	public ArrayList<Person> getContacts()
	{
		return contacts;
	}
	public int x()
	{
		return x;
	}

	public int y()
	{
		return y;
	}

	public void recover()
	{
		this.recovered = true;
	}

	public void recover(boolean r)
	{
		recovered = r;
	}

	public boolean recovered()
	{
		return recovered;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public void addContact(Person o)
	{
		contacts.add(o);
	}
	public void getDisease()
	{
		this.infected = true;
	}
	public void setInfected(boolean b)
	{
		this.infected = b;
	}

	public boolean isInfected()
	{
		return infected;
	}

	public double getDistanceToOther(Person other) //get distance between this and other
	{
		return (double)(Math.sqrt(Math.pow(Math.abs(this.x - other.x),2)+ Math.pow(Math.abs(this.y - other.y),2))); //pythagorean thm
	}

	public void spreadDisease()
	{
		for(Person p : contacts)
		{
			if(Math.random()< this.INFECTION && !p.recovered())
				p.setInfected(true);
		}
		System.out.println("Spread Disease Called for Person at (" + x + "," + y + ")");
		// for(int i = 0; i < contacts.size(); i ++)
		// {
		// 	Person p = contacts.get(i);
		// 	if(Math.random()< this.INFECTION && !p.recovered())
		// 	{
		// 		System.out.println("Person at ("+ this.x +"," + this.y + "Spreading disease to person @ (" +p.x() + "," + p.y() + ")");
		// 		p.getDisease();
		// 	}
		// }
	}

	public void wipeContacts()
	{
		contacts = new ArrayList<Person>(NUM_CONTACTS);
	}
	public int getCount()
	{
		return count;
	}
	public void countUp()
	{
		count ++;
	}
	//Methods designed for debugging

	public void printContacts()
	{
		System.out.println("Person at (" + x + "," + y + ") has contact at");
		for(Person p : contacts)
		{
			System.out.println("("+ p.x() + ", " + p.y()+")");
		}
		System.out.println("***********");
	}




}
