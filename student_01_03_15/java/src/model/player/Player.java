package model.player;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

public class Player
{
	private Resources resources;
	private Developments oldDevCards;
	private Developments newDevCards;
	private int roads;
	private int cities;
	private int settlements;
	private int soldiers;
	private int victoryPoints;
	private int monuments;
	private boolean playedDevCard;
	private boolean discarded;
	private int playerID;
	private int playerIndex;
	private String name;
	private String color;

	/**
	 * All players will begin with boolean isTurn as false,0 VP
	 * points,roadCount,and armyCount. They will start with 5 settlement, 4
	 * cities and 15 roads.
	 */
	public Player()
	{
		reset();
	}

	public Player(String name, String color, int playerIndex, int playerId)
	{
		this.name = name;
		this.color = color;
		this.playerIndex = playerIndex;
		this.playerID = playerId;
	}

	public void updateDevCards()
	{
		oldDevCards.setMonopoly(oldDevCards.getMonopoly()+newDevCards.getMonopoly());
		oldDevCards.setRoadBuilding(oldDevCards.getRoadBuilding()+newDevCards.getRoadBuilding());
		oldDevCards.setSoldier(oldDevCards.getSoldier()+newDevCards.getSoldier());
		oldDevCards.setYearOfPlenty(oldDevCards.getYearOfPlenty()+newDevCards.getYearOfPlenty());
		newDevCards.clear();
		setPlayedDevCard(false);
	}

	public void reset()
	{
		discarded = false;
		playedDevCard = false;
		monuments = 0;
		victoryPoints = 0;
		soldiers = 0;
		roads = 15;
		settlements = 5;
		cities = 4;
		name = "";
		color = "white";
		playerID = -1;
		resources = new Resources();
		oldDevCards = new Developments();
		newDevCards = new Developments();
	}
	public void gameReset()
	{
		discarded = false;
		playedDevCard = false;
		monuments = 0;
		victoryPoints = 0;
		soldiers = 0;
		roads = 15;
		settlements = 5;
		cities = 4;
		resources = new Resources();
		oldDevCards = new Developments();
		newDevCards = new Developments();
	}

	/**
	 * Updates the current player by using the passed in player
	 * 
	 * @param p
	 *            Player model to use to update
	 */
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
		// this.color = catanColor; // NEEDS TO BE FIXED LATER
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

	/**
	 * Gets the number resource cards this player has
	 * 
	 * @return number of resource cards this player has
	 */
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
		if (name==null) return "";
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

	public void setColor(String color)
	{
		this.color = color.toLowerCase();
	}

	public CatanColor getColor()
	{
		if (color == null) return CatanColor.WHITE;
		return CatanColor.fromString(color);
	}

	public void playDevcard(DevCardType soldier)
	{
		// this is empty, but the mockproxy calls it
	}

	@Override
	public String toString()
	{
		return "Player [resources=" + resources + ", oldDevCards="
				+ oldDevCards + ", newDevCards=" + newDevCards + ", roads="
				+ roads + ", cities=" + cities + ", settlements=" + settlements
				+ ", soldiers=" + soldiers + ", victoryPoints=" + victoryPoints
				+ ", monuments=" + monuments + ", playedDevCard="
				+ playedDevCard + ", discarded=" + discarded + ", playerID="
				+ playerID + ", playerIndex=" + playerIndex + ", name=" + name
				+ ", color=" + color + "]";
	}
	
	
}