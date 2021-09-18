package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ImageManager.ImageFile;

public class PlayState extends GameState
{

	//gamesettings
	public static int round = 1;
	private static int speed = 5;
	private static int highScore;
	private boolean  isCalled;
	private boolean animate = true;

	//images
	private static ArrayList<ImageFile> speedUps;
	public static ImageFile flash;
	private static ArrayList<ImageFile> reverseFlash;
	private static ImageFile lightningThrow;
	private ImageFile background;
	private boolean isFacingUp;
	private boolean isFacingDown;
	private boolean isFacingRight;
	private boolean isFacingLeft;



	public PlayState( GameStateManager gsm)
	{
		this.gsm = gsm;
		speedUps = new ArrayList<ImageFile>();
		try
		{
			background = new ImageFile("/Background/bg.png",0,0);
			for(int i = 0; i < 1; i++)
			{
				speedUps.add(new ImageFile("/Sprites/speedUps.png", Math.random() * 300, Math.random() * 220));	
			}
			flash = new ImageFile("/Sprites/flash1.png", 5, 220);
			reverseFlash = new ArrayList<ImageFile>();
			reverseFlash.add(new ImageFile("/Sprites/reverseflash.png", 300, 5));
			lightningThrow = new ImageFile("/Sprites/lightning.png",-1,-1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g)
	{
		//sets background
		background.draw(g);

		flash.draw(g);
		for(int i = 0; i < reverseFlash.size(); i++)
		{
			reverseFlash.get(i).draw(g);
			//if intersects, game over
			if(new Rectangle( (int) flash.getPosX(), (int) flash.getPosY(),10,15).intersects(new Rectangle( (int) reverseFlash.get(i).getPosX(), (int) reverseFlash.get(i).getPosY(),10,15)))
			{
				gsm.setState(2);
			}
			if((new Rectangle( (int) lightningThrow.getPosX(), (int) lightningThrow.getPosY(),10,15).intersects(new Rectangle( (int) reverseFlash.get(i).getPosX(), (int) reverseFlash.get(i).getPosY(),10,15))))
			{
				reverseFlash.remove(i);
				if(reverseFlash.size() == 0)
				{
					round++;
					for(int k = 0; k < round; k++)
					{
						reverseFlash.add(new ImageFile("/Sprites/reverseflash.png",Math.random() * 300, Math.random() * 220));	
					}
				}
			}
		}

		//draws power ups
		for(int i = 0; i < speedUps.size(); i++)
		{
				speedUps.get(i).draw(g);
		}

		if(isCalled)
		{
			lightningThrow.draw(g);
		}


		//draws hud
		g.setColor(Color.WHITE);
		g.drawString("Round " + round, 277, 232);

	}

	public static void setRound(int r)
	{
		round = r;
	}

	public static void reset()
	{
		flash.setPosX(5);
		flash.setPosY(220);
		lightningThrow.setPosX(flash.getPosX());
		lightningThrow.setPosY(flash.getPosY());
		setRound(1);
		reverseFlash.clear();
		reverseFlash.add((new ImageFile("/Sprites/reverseflash.png", 300, 5)));
		speedUps.clear();
		for(int i = 0; i < round; i++)
		{
			speedUps.add(new ImageFile("/Sprites/speedUps.png", Math.random() * 300, Math.random() * 220));	
		}
		
		speed = 5;
	}

	public void update()
	{
		//moves reverse flash to follow flash
		for(int i = 0; i < reverseFlash.size(); i++)
		{
			if( (int) reverseFlash.get(i).getPosX() > (int) flash.getPosX())
			{
				reverseFlash.get(i).setPosX(reverseFlash.get(i).getPosX() - 1);

			}
			else if( (int) reverseFlash.get(i).getPosX() < (int) flash.getPosX())
			{
				reverseFlash.get(i).setPosX(reverseFlash.get(i).getPosX() + 1);

			}
			else if( (int) reverseFlash.get(i).getPosY() > (int) flash.getPosY())
			{
				reverseFlash.get(i).setPosY(reverseFlash.get(i).getPosY() - 1);

			}
			else
			{
				reverseFlash.get(i).setPosY(reverseFlash.get(i).getPosY() + 1);

			}
		}
		
		if(speedUps.size() == 0)
		{
			for(int i = 0; i < round; i++)
			{
				speedUps.add(new ImageFile("/Sprites/speedUps.png", Math.random() * 300, Math.random() * 220));	
			}
		}

		if(isCalled && lightningThrow.getPosX() < 300 && lightningThrow.getPosX() > 0 && lightningThrow.getPosY() > 0 && lightningThrow.getPosY() < 240)
		{
			if(isFacingRight)
			{
			lightningThrow.setPosX(lightningThrow.getPosX() + 6);
			}
			else if(isFacingLeft)
			{
				lightningThrow.setPosX(lightningThrow.getPosX() - 6);
			}
			else if(isFacingUp)
			{
				lightningThrow.setPosY(lightningThrow.getPosY() - 6);
			}
			else
			{
				lightningThrow.setPosY(lightningThrow.getPosY() + 6);
			}
		}
		else
		{
			isCalled = false;
		}

		if(round > highScore)
		{
			highScore = round;
		}
		
		pickUpSpeedUp(speedUps);
	}

	public void pickUpSpeedUp(ArrayList<ImageFile> pwrUp)
	{
		//if flash gets speedup
		for(int i = 0; i < pwrUp.size(); i++)
		{
			if(new Rectangle((int) flash.getPosX(),(int) flash.getPosY(),10,15).intersects(new Rectangle( (int) speedUps.get(i).getPosX(), (int) speedUps.get(i).getPosY(), 10, 15)))
			{
				pwrUp.remove(i);
				speed++;
			}
		}
	}
	
	public static int getHighScore()
	{
		return highScore;
	}

	@Override
	public void keyPressed(int key) 
	{
		if(key == KeyEvent.VK_RIGHT)
		{
			moveRight();
		}
		if(key == KeyEvent.VK_LEFT)
		{
			moveLeft();
		}
		if(key == KeyEvent.VK_UP)
		{
			moveUp();
		}
		if(key == KeyEvent.VK_DOWN)
		{
			moveDown();
		}
		if(key == KeyEvent.VK_SPACE)
		{
			lightningThrow.setPosX(flash.getPosX());
			lightningThrow.setPosY(flash.getPosY());
			isCalled = true;
		}

	}


	public void moveRight()
	{
		if(animate)
		{
			flash.setImage("/Sprites/flashr1.png");
			animate = false;
		}
		else
		{
			flash.setImage("/Sprites/flashr1.png");
			animate = true;
		}
		isFacingRight = true;
		isFacingLeft = false;
		isFacingUp = false;
		isFacingDown = false;
		if(flash.getPosX() >= 305)
		{
			flash.setPosX(305);
		}
		else
		{
			flash.setPosX(flash.getPosX() + speed);
		}
	}

	public void moveLeft()
	{
		if(animate)
		{
			flash.setImage("/Sprites/flashr1.png");
			animate = false;
		}
		else
		{
			flash.setImage("/Sprites/flashr1.png");
			animate = true;
		}
		
		isFacingRight = false;
		isFacingLeft = true;
		isFacingUp = false;
		isFacingDown = false;
		
		if(flash.getPosX() < 20)
		{
			flash.setPosX(5);
		}
		else
		{
			flash.setPosX(flash.getPosX() - speed);
		}
	}

	public void moveUp()
	{
		if(animate)
		{
			flash.setImage("/Sprites/flashu1.png");
			animate = false;
		}
		else
		{
			flash.setImage("/Sprites/flashu1.png");
			animate = true;
		}
		
		isFacingRight = false;
		isFacingLeft = false;
		isFacingUp = true;
		isFacingDown = false;
		
		if(flash.getPosY() < 20)
		{
			flash.setPosY(5);
		}
		else
		{
			flash.setPosY(flash.getPosY() - speed);
		}
	}

	public void moveDown()
	{
		if(animate)
		{
			flash.setImage("/Sprites/flashd1.png");
			animate = false;
		}
		else
		{
			flash.setImage("/Sprites/flashd1.png");
			animate = true;
		}
		
		isFacingRight = false;
		isFacingLeft = false;
		isFacingUp = false;
		isFacingDown = true;
		
		if(flash.getPosY() >= 220)
		{
			flash.setPosY(220);
		}
		else
		{
			flash.setPosY(flash.getPosY() + speed);
		}
	}

	@Override
	public void init()
	{

	}

	@Override
	public void keyReleased(int key) 
	{

	}

}
