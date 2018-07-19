import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GoodButton extends JButton implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	public GoodButton(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.setBackground(Color.GREEN);
		this.setOpaque(true);
		this.setBorderPainted(false);
		this.addActionListener(this);
	}

	public int x()
	{
		return x;
	}

	public int y()
	{
		return y;
	}

	public void actionPerformed(ActionEvent e)
	{
		Grid.getGrid()[x][y].getDisease();
		Grid.getGrid()[x][y].setCount(2);
		this.setBackground(Color.RED);
		//Testing: System.out.println(x + " " + y + " " Grid.getGrid()[x][y].isInfected());
	}


}
