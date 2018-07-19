import java.awt.Color;
import javax.swing.JLabel;


public class Grid
{
	private static Person[][] grid;
	public static int TIME = 0;


	public Grid(int n)
	{
		grid = new Person[n][n];

		for(int i = 0; i < n; i ++)
		{
			for(int j = 0; j < n; j++)
			{
				grid[i][j] = new Person(i, j, this);
			}
		}
	}
	public static Person[][] getGrid(){return grid;}

	public void setUp()
	{
		DiseaseSim.time.setText("Days Past: 0");
		Grid.TIME = 0;
		for(Person[] r : grid)
		{
			for(Person p : r)
			{
				p.wipeContacts();
				p.recover(false);
				p.setCount(0);
				setContacts(p);
				//p.printContacts();
			}
		}
	}

	public void setContacts(Person p)
	{
		for(Person[] r : grid)
		{
			for(Person o : r)
			{
				if((p.getContacts().size() >= Person.NUM_CONTACTS))
				{
					//System.out.println("Oversized returning");
					return;
				}
				double prob = 1/Math.pow(p.getDistanceToOther(o), 4);

				if(prob > .90)
				{
					prob = .90;
				}
				double d = Math.random();
				if(d < prob && o!=p)
				{
					p.addContact(o);
					// System.out.println("*********************added" + o.x() + " " + o.y());
				}

			}
		}
	}
	public static void printAllInfected()
	{
		int count = 0;
		for(Person[] r : Grid.grid)
		{
			for(Person p : r)
			{
				if(p.isInfected())
				{
					count ++;
				}
			}
		}
		System.out.println(count);
	}
	public static void update()
	{
		TIME += 1;
		//System.out.println(time);
		for(Person[] r : grid)
		{
			for(Person p : r)
			{
				if(p.isInfected())
				{
					p.countUp();
					if(p.getCount() >= Person.RECOVERY_TIME)
					{
						p.setInfected(false);
						p.recover();
						DiseaseSim.buttons[p.x()][p.y()].setBackground(Color.GREEN);
					}
					System.out.println("Calling spread disease for person at " + p.x() + " " + p.y());
					if(p.getCount() != 1) p.spreadDisease();

					// System.out.println("Person at "+p.x() + " " + p.y() + " may have spread their disease to [" + p.getContacts());
				}

			}
		}
	}
//***************** Testing *******************//
	public void printStuff()
	{
		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid.length; j ++)
			{

				System.out.print(" " + grid[i][j].isInfected() + " ");
			}
			System.out.println();
		}
	}
}
