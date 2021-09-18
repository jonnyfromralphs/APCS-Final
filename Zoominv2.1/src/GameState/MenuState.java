package GameState;

import ImageManager.ImageFile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuState extends GameState
{
	
	private ImageFile background;
	private String[] options;
	private int currentSelection;
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private boolean isCalled = false;
	
	public MenuState(GameStateManager gsm)
	{
		this.gsm = gsm;
		options = new String[3];
		options[0] = "Play Game";
		options[1] = "How to Play";
		options[2] = "Exit";
		try
		{
			background = new ImageFile("/Background/flash2.jpg",0,0);
			titleColor = new Color(0 ,0, 0);
			titleFont = new Font("Times New Roman", Font.PLAIN, 30);
			font = new Font("Times New Roman", Font.PLAIN, 12);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void init() { }

	@Override
	public void draw(Graphics2D g) 
	{
		//draws background
		background.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Zoomin'", 110, 30);
		
		//menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++)
		{
			if(i == currentSelection)
			{
				g.setColor(Color.YELLOW);
			}
			else
				g.setColor(titleColor);
			g.drawString(options[i], 110 + i * 75, 200);
		}
		
		if(isCalled)
		{
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Move with arrow keys", 160, 120);
			g.drawString("Throw lightning with space", 150, 140);

		}
	}

	@Override
	public void update() 
	{
	
	}
	
	public void select()
	{
		if(currentSelection == 0)
		{
			gsm.setState(1);
		}
		if(currentSelection == 1)
		{
			isCalled = !isCalled;
		}
		if(currentSelection == 2)
		{
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int key) 
	{
		if(key == KeyEvent.VK_ENTER)
			select();
		if(key == KeyEvent.VK_LEFT)
		{
			currentSelection--;
			if(currentSelection == -1)
				currentSelection = options.length - 1;
		}
		if(key == KeyEvent.VK_RIGHT)
		{
			currentSelection++;
			if(currentSelection == options.length)
				currentSelection = 0;
		}
			
	}

	@Override
	public void keyReleased(int key) { }

}
