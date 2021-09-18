package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameStateManager 
{
	private ArrayList<GameState> gameStates;
	private int currentState;
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int ENDSTATE = 2;

	
	public GameStateManager()
	{
		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));
		gameStates.add(new EndState(this));
	}
	
	public void setState(int s)
	{
		currentState = s;
		gameStates.get(currentState).init();
	}
	
	public void update()
	{
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g)
	{
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int key)
	{
		gameStates.get(currentState).keyPressed(key);
	}
	
	public void keyReleased(int key)
	{
		gameStates.get(currentState).keyReleased(key);
	}
}
