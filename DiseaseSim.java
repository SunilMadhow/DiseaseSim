import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;



public class DiseaseSim extends JFrame implements ActionListener, ChangeListener
{
	private int n;
	private Timer timer = new Timer();
	//public static final int EXPONENTIAL_DECAY_RATE = -2;
	public static GoodButton[][] buttons;

	public static JLabel time;
 	JSlider infectious;
 	JPanel buttonsPanel;
 	JSlider recoverTime;
 	JSlider social;
 	Grid grid;

	public static void main(String[] args)
	{
		DiseaseSim main = new DiseaseSim("DiseaseSimulator 3000", 30);
	}

	public DiseaseSim(String s, int n)
	{
		super(s);

		buttons = new GoodButton[n][n];
		this.n = n;


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel bigBoi = new JPanel(new BorderLayout());
		GridLayout g = new GridLayout(n,n);
		JPanel p = new JPanel(g);
		time = new JLabel("Days Past: 0");
		GridLayout gridi = new GridLayout(1,3);
		GridLayout g2 = new GridLayout(2,3);
		JPanel info = new JPanel(gridi);
		buttonsPanel = new JPanel();
		JPanel sliders = new JPanel(g2);
		info.add(buttonsPanel);
		info.add(sliders);
		bigBoi.add(p, BorderLayout.CENTER);
		bigBoi.add(info, BorderLayout.PAGE_END);
		this.add(bigBoi);

		for(int i =0; i < n; i ++)
		{
			for(int j = 0; j < n; j ++)
			{
				buttons[i][j] = (new GoodButton(i, j));
			}
		}

		for(GoodButton[] r : buttons)
		{
			for(GoodButton b : r)
				p.add(b);
		}
		JButton run = new JButton("Run");
		run.setBackground(Color.YELLOW);
		run.setOpaque(true);
		run.setActionCommand("RUN");
		run.addActionListener(this);

        JButton step = new JButton("Step");
        step.setBackground(Color.CYAN);
        step.setOpaque(true);
        step.setActionCommand("STEP");
        step.addActionListener(this);

		JButton reset = new JButton("Reset");
		reset.setBackground(Color.RED);
		reset.setOpaque(true);
		reset.addActionListener(this);
		reset.setActionCommand("RESET");

		social = new JSlider(0, 30, 10);
		JLabel socialLabel = new JLabel("# Social Nodes");
		//socialLabel.setLabelFor(social);
		//social.setTooltip("Test");
		social.addChangeListener(this);
		social.setMajorTickSpacing(10);
		social.setMinorTickSpacing(5);
		social.setPaintTicks(false);
		social.setPaintLabels(true);
		social.addChangeListener(this);


		infectious = new JSlider(0, 100, 50);
		infectious.addChangeListener(this);
		infectious.setMajorTickSpacing(50);
		infectious.setMinorTickSpacing(10);
		infectious.setPaintTicks(false);
		infectious.setPaintLabels(true);

		recoverTime = new JSlider(2, 10, 4);
		recoverTime.addChangeListener(this);
		recoverTime.addChangeListener(this);
		recoverTime.setMajorTickSpacing(2);
		recoverTime.setMinorTickSpacing(1);
		recoverTime.setPaintTicks(false);
		recoverTime.setPaintLabels(true);

        buttonsPanel.add(step);
		buttonsPanel.add(run);
		buttonsPanel.add(reset);
		buttonsPanel.add(time);

		sliders.add(new JLabel("Disease Infectivity"));
		sliders.add(socialLabel);
		sliders.add(new JLabel("Recovery Time (days)"));
		sliders.add(infectious);
		sliders.add(social);
		sliders.add(recoverTime);

		this.setSize(800,810);
		this.setVisible(true);
		//Setting up grid///////
		grid = new Grid(n);
		grid.setUp();
		////////////////////////
	}

	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("RUN"))
		{
			beginSimulation();
		}
		if(cmd.equals("RESET"))
		{
			clear();
		}
        if(cmd.equals("STEP"))
        {
            simulateOnce();
        }


	}
	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider)e.getSource();
		//String cmd = source.getActionCommand();
		if(!source.getValueIsAdjusting())
		{
			if(e.getSource() == infectious)
			{
				Person.INFECTION = source.getValue()/100.;
				//System.out.println("WE MADE IT BOIS DAT STATIC MOFO IS NOW AT " + Person.INFECTION);
			}
			if(e.getSource() == social)
			{
				Person.NUM_CONTACTS = source.getValue();
				grid.setUp();
				//System.out.println("WE MADE IT BOIS DAT OTHER STATIC MOFO IS NOW AT " + Person.NUM_CONTACTS);

			}
			if(e.getSource() == recoverTime)
			{
				Person.RECOVERY_TIME = source.getValue();
			}
		}

	}
	public void beginSimulation()
	{
		TimerTask spreadTask = new TimerTask() {
			public void run() {
				Grid.update();
				time.setText("Days Past :" + Grid.TIME);
				//buttonsPanel.add(time);
				int count = 0;
				for(Person[] r : Grid.getGrid())
				{
					for(Person p : r)
					{
						if(!p.isInfected()) count ++;

						if(p.isInfected())
						{
							DiseaseSim.buttons[p.x()][p.y()].setBackground(Color.RED);
						}

						if(count >= n*n)
						{
							timer.cancel();
							timer.purge();
							timer = new Timer();
							time.setText("Duration:" + Grid.TIME);
						}
					}
				}
			}
		};
		timer.scheduleAtFixedRate(spreadTask, new Date(), 1000);
	}

	public void clear()
	{
		System.out.println("Clear method reached");
		timer.cancel();
		timer.purge();
		timer = new Timer();
		for(Person[] r : Grid.getGrid())
		{
			for(Person p : r)
			{
				p.setInfected(false);
				buttons[p.x()][p.y()].setBackground(Color.GREEN);
			}
		}


		grid.setUp();
	}
    public void simulateOnce()
    {
        Grid.update();
        Grid.printAllInfected();
        time.setText("Days Past :" + Grid.TIME);
        int count = 0;
        for(Person[] r : Grid.getGrid())
        {
            for(Person p : r)
            {

                   if(p.isInfected())
                   {
                       DiseaseSim.buttons[p.x()][p.y()].setBackground(Color.RED);
                    }
            }
        }
    }
}
