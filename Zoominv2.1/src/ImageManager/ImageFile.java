package ImageManager;

import java.awt.Graphics2D;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class ImageFile
{
	private BufferedImage image;
	private double x;
	private double y;
	
	public ImageFile(String s, double x, double y)
	{
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			this.x = x;
			this.y = y;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setImage(String s)
	{
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream(s));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public void setPosX(double x)
	{
		this.x = x;
	}
	
	public void setPosY(double y)
	{
		this.y = y;
	}
	
	public double getPosX()
	{
		return x;
	}
	
	public double getPosY()
	{
		return y;
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, (int) x, (int) y, null);
	}
	
}
