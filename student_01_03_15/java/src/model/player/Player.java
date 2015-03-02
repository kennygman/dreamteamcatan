package model.player;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

public class Player
{
	private String name;
	private int playerIndex;
	private int playerID;
	private boolean isTurn;

	private int victoryPoints;
	private int soldiers;

	private int roads;
	private int settlements;
	private int cities;

	private Resources resources;

	private boolean discarded;
	private boolean playedDevCard;
	private int monuments;

	private Developments newDevCards;
	private Developments oldDevCards;
	private String color;
	private CatanColor catanColor;

	/**
	 * All players will begin with boolean isTurn as false,0 VP
	 * points,roadCount,and armyCount. They will start with 5 settlement, 4
	 * cities and 15 roads.
	 */
	public Player()
	{

		isTurn = false;
		discarded = false;
		playedDevCard = false;
		monuments = 0;
		victoryPoints = 0;
		soldiers = 0;
		roads = 15;
		settlements = 5;
		cities = 4;

	}
	
	public void update(Player p)
	{
		this.setVictoryPoints(p.getVictoryPoints());
		this.setSoldiers(p.getSoldiers());
		this.setRoads(p.getRoads());
		this.setSettlements(p.getSettlements());
		this.setCities(p.getCities());
		resources.update(p.getResources());
		this.setDiscarded(p.isDiscarded());
		this.setPlayedDevCard(p.isPlayedDevCard());
		this.setMonuments(p.getMonuments());
		this.setNewDevCards(p.getNewDevCards());
		this.setOldDevCards(p.getOldDevCards());
	}

	/**
	 * Every player needs to be initiated with a playerId, userId and a color
	 * 
	 * @param playerId
	 *            We will get the player id for the order of who goes first and
	 *            such
	 * @param userId
	 *            This is the id from the user that actually logged in
	 * @param catanColor
	 *            The color assigned to the player
	 */
	public Player(int playerIndex, int userId, CatanColor catanColor)
	{
		this.playerIndex = playerIndex;
//		this.color = catanColor;		// NEEDS TO BE FIXED LATER
	}

	/**
	 * 
	 * @return true if player has 10 VP.
	 */
	public boolean winCheck()
	{
		return false;
	}

	/**
	 * 
	 * @return true if player's turn is over;
	 */
	public boolean endTurn()
	{
		return false;
	}

	/**
	 * places a road on the board
	 */
	public void buildRoad()
	{

	}

	/**
	 * places settlement on the board
	 */
	public void buildSettlement()
	{

	}

	/**
	 * places city on the board
	 */
	public void buildCity()
	{

	}

	/**
	 * This plays devCard that player selects
	 * 
	 * @param devCard
	 *            from DevelopmentList
	 */
	public void playDevcard(DevCardType devCard)
	{

	}

	/**
	 * 
	 * @return true if player can trade
	 */
	public boolean canTrade()
	{
		return false;
	}

	public int getPlayerIndex()
	{
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}


	public boolean isTurn()
	{
		return isTurn;
	}

	public void setTurn(boolean isTurn)
	{
		this.isTurn = isTurn;
	}

	public int getVictoryPoints()
	{
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints)
	{
		this.victoryPoints = victoryPoints;
	}

	public int getSoldiers()
	{
		return soldiers;
	}

	public void setSoldiers(int soldiers)
	{
		this.soldiers = soldiers;
	}

	public int getRoads()
	{
		return roads;
	}

	public void setRoads(int roads)
	{
		this.roads = roads;
	}

	public int getSettlements()
	{
		return settlements;
	}

	public void setSettlements(int settlements)
	{
		this.settlements = settlements;
	}

	public int getCities()
	{
		return cities;
	}

	public void setCities(int cities)
	{
		this.cities = cities;
	}

	public Resources getResources()
	{
		return resources;
	}
        
        public int getNumCards()
        {
            int numResources = 0;
            numResources += resources.getResourceAmount(ResourceType.WOOD);
            numResources += resources.getResourceAmount(ResourceType.BRICK);
            numResources += resources.getResourceAmount(ResourceType.WHEAT);
            numResources += resources.getResourceAmount(ResourceType.SHEEP);
            numResources += resources.getResourceAmount(ResourceType.ORE);
            return numResources;
        }

	public void setResources(Resources resources)
	{
		this.resources = resources;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isDiscarded()
	{
		return discarded;
	}

	public void setDiscarded(boolean discarded)
	{
		this.discarded = discarded;
	}

	public int getMonuments()
	{
		return monuments;
	}

	public void setMonuments(int monuments)
	{
		this.monuments = monuments;
	}

	public Developments getNewDevCards()
	{
		return newDevCards;
	}

	public void setNewDevCards(Developments newDevCards)
	{
		this.newDevCards = newDevCards;
	}

	public Developments getOldDevCards()
	{
		return oldDevCards;
	}

	public void setOldDevCards(Developments oldDevCards)
	{
		this.oldDevCards = oldDevCards;
	}

	public boolean isPlayedDevCard()
	{
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard)
	{
		this.playedDevCard = playedDevCard;
	}
        
        public CatanColor getColor()
        {
            return CatanColor.stringToColor(color);
        }
}