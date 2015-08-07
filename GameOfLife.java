import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GameOfLife extends Canvas implements Runnable
{

	private static final long serialVersionUID = 1L;
	private boolean running = true;
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu, subMenu, info;
	private JMenuItem startItem, clearItem, author;
	private JLabel population;
	private JSlider slider;
	private String title;
	private int width, height;
	private Thread thread;
	private Map map;
	private CellsEvolve cells;
	private MouseControl mouse;
	private boolean simulating = false;
	private double ups = 60.0;
	
	
	public static final int BLOCK_SIZE = 5;
	public static final int BLOCKS_OFF = 5;
	public static final int BLOCKS_DELTA = BLOCK_SIZE * BLOCKS_OFF;
	
	public GameOfLife(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		map = new Map((this.width / BLOCK_SIZE)+15, (this.height / BLOCK_SIZE)+15);
		cells = new CellsEvolve(map.getCells(), map.getWidth(), map.getHeight());
		
		menuBar = new JMenuBar();
		menu = new JMenu("Actions");
		menuBar.add(menu);
		startItem = new JMenuItem("Start");
		startItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(startItem.getText().equals("Start"))
				{
					ups = (double)slider.getValue();
					simulating = true;
					startItem.setText("Stop");
				}
				else if(startItem.getText().equals("Stop"))
				{
					simulating = false;
					ups = 60.0;
					startItem.setText("Start");
				}
			}
		});
		menu.add(startItem);
		clearItem = new JMenuItem("Clear");
		clearItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(simulating == true)
				{
					JOptionPane.showMessageDialog(frame, "Stop simulating first!");
					return;
				}
				map.clearMap();
			}
		});
		
		menu.add(clearItem);
		menu.addSeparator();
		subMenu = new JMenu("Speed");
		menu.add(subMenu);
		
		slider = new JSlider(JSlider.HORIZONTAL, 5, 80, 60);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ups = (double)slider.getValue();
			}
		});
		subMenu.add(slider);
		info = new JMenu("Info");
		menuBar.add(info);
		author = new JMenuItem("Author");
		info.add(author);
		author.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Created by Karol Miksztal");
			}
		});
		
		population = new JLabel("Pupulation: ");
		menuBar.add(Box.createRigidArea(new Dimension(this.width-200,0)));
		menuBar.add(population);
		

		// frame settings
		frame = new JFrame(this.title);
		frame.setSize(this.width, this.height);
		frame.setMaximumSize(new Dimension(this.width, this.height));
		frame.setMinimumSize(new Dimension(this.width, this.height));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		
		
		mouse = new MouseControl();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		int frames = 0, updates = 0;
		
		while(running)
		{
			double ns = 1000000000.0 / ups;
			long timeNow = System.nanoTime();
			delta += (timeNow - lastTime) / ns;
			lastTime = timeNow;
			while(delta >= 1)
			{
				update(); // 60 ups
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if((System.currentTimeMillis() - timer) >= 1000)
			{
				frame.setTitle(this.title + " | " + updates + " UPS | " + frames + " FPS|");
				timer += 1000;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	public void update()
	{
		if(simulating)
		{
			cells.evolve();
		}
		else if(mouse.isPressed())
		{
			int tempX = (mouse.getX() + BLOCKS_DELTA) / BLOCK_SIZE;
			int tempY = (mouse.getY() + BLOCKS_DELTA) / BLOCK_SIZE;
			
			if(!(tempX < 0 || tempX >= map.getWidth()-1 || tempY < 0 || tempY >= map.getHeight()-1))
				if(mouse.isEraseMode())
					map.getCell(tempX, tempY).setStatus(CellStatus.DEAD);
				else map.getCell(tempX, tempY).setStatus(CellStatus.ALIVE);
			
		}
	}
	public void render()
	{
		population.setText("Population: " + cells.getPopulation().getNumberOfActiveCells());
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.width, this.height);
		
		for(int y = BLOCKS_OFF; y < map.getHeight(); y++)
		{
			for(int x = BLOCKS_OFF; x < map.getWidth(); x++)
			{
				if(map.getCell(x, y).isAlive())
				{
					g.setColor(Color.BLACK);
				}
				else g.setColor(Color.WHITE);
				g.fillRect(x * BLOCK_SIZE - BLOCKS_DELTA, y * BLOCK_SIZE - BLOCKS_DELTA, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
		}
		g.dispose();
		bs.show();
	}
}
