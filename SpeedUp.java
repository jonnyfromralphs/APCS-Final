package GameState;

import java.awt.image.BufferedImage;

public class SpeedUp {
	
	private int posX;
	private int posY;
	private BufferedImage image;
	private int amount;
	
	private int[] speedUps;
	
	public SpeedUp(int amount, int[] speedUps) 
	{
		amount = amount;
		speedUps = speedUps;
		
	}
	public void setX(int x)
	{
		posX = x;
	}
	public void setY(int y)
	{
		posY = y;
	}
	public int getX()
	{
		return posX;
	}
	public int getY()
	{
		return posY;
	}
	

}
