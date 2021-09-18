package GameState;

import java.awt.image.BufferedImage;

public class Lightning {
	
	private int posX;
	private int posY;
	
	private BufferedImage image;
	private int amount;
	
	private int[] lights;
	
	public Lightning(int amount, int[] lights) 
	{
		amount = amount;
		lights = lights;
		
	}
	public void setX(int x)
	{
		posX = x;
	}
	public void setY(int y)
	{
		posY = y;
	}

}
