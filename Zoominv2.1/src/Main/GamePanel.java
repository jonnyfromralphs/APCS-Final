package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener
{
	//dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	//game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;//long?
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	//game state manager
	private GameStateManager gsm;
	
	public GamePanel()
	{
		setPreferredSize(new Dimension(WIDTH * 2, HEIGHT * 2));
		setFocusable(true);//search
		requestFocus();//search
	}
	
	private void init()
	{
		image = new BufferedImage(WIDTH ,HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();
	}
	
	public void addNotify()
	{
		super.addNotify();
		if(thread == null)
		{
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	@Override
	public void keyPressed(KeyEvent key) 
	{
		gsm.keyPressed(key.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent key) 
	{
		gsm.keyReleased(key.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent key) {}

	@Override
	public void run() 
	{
		init();
		
		//timers
		long start;
		long elapsed;
		long wait;
		
		while(running)
		{
		start = System.nanoTime();
		update();
		draw();
		drawToScreen();
		elapsed = System.nanoTime() - start;
		wait = targetTime - elapsed / 1000000;
		
			try//search
			{
			Thread.sleep(wait);
			}
		
			catch(Exception e)//search
			{
			e.printStackTrace();
			}
		}
		
	}
	
	private void update()
	{
		gsm.update();
	}
	
	private void draw()
	{
		gsm.draw(g);
	}
	
	private void drawToScreen()
	{
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
	}

}