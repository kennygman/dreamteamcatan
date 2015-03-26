package model;

public class TurnTracker
{
	public static String PLAYING = "Playing";
	public static String ROBBING = "Robbing";
	public static String FIRSTROUND = "FirstRound";
	public static String SECONDROUND = "SecondRound";
	public static String TRADING = "Trading";
	public static String ROLLING = "Rolling";
	public static String DISCARDING = "Discarding";
	
	private int currentTurn;
	private String status;
	int longestRoad;
	int largestArmy;

	/**
	 * This method updates the current TurnTracker with the new given
	 * TurnTracker
	 * 
	 * @param t
	 *            TurnTracker to pull updates from
	 */
	public void update(TurnTracker t)
	{
		this.setCurrentTurn(t.getCurrentTurn());
		this.setStatus(t.getStatus());
		this.setLongestRoad(t.getLongestRoad());
		this.setLargestArmy(t.getLargestArmy());
	}

	public TurnTracker init()
	{
		currentTurn = 0;
		status = FIRSTROUND;
		longestRoad = -1;
		largestArmy = -1;
		return this;
	}
	
	public void nextTurn()
	{
		currentTurn++;
		if(currentTurn>3)
		{
			currentTurn=0;
			if (status.equals(FIRSTROUND)) status = SECONDROUND;
			else if (status.equals(SECONDROUND)) status = ROLLING;
		}
	}
	
	public int getCurrentTurn()
	{
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn)
	{
		this.currentTurn = currentTurn;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getLongestRoad()
	{
		return longestRoad;
	}

	public void setLongestRoad(int longestRoad)
	{
		this.longestRoad = longestRoad;
	}

	public int getLargestArmy()
	{
		return largestArmy;
	}

	public void setLargestArmy(int largestArmy)
	{
		this.largestArmy = largestArmy;
	}

}