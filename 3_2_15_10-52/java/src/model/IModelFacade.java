package model;

import model.player.Resources;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.parameters.SendChatParam;

public interface IModelFacade
{
	/**
	 * This method is called when a player sends a trade offer to another player
	 * @return if the recipient of the trade has sufficient resources for the trade
	 */
	boolean canAcceptTrade();

	/**
	 * This method is called each time the dice is rolled
	 * 
	 * @param n
	 *            the number
	 * @return True if the number is between 2 and 12: False otherwise
	 */
	boolean CanRollNumber();

	/**
	 * This method is called when a player tries to build a road
	 * 
	 */
	boolean CanBuyRoad();

	/**
	 * This method is called when a player tries to build a settlement
	 * 
	 * @return True if the Player has the resources to build a Settlement
	 */
	boolean CanBuySettlement();

	/**
	 * This method is called when a player tries to build a city
	 * 
	 * @return True if the Player has the resources to build a City
	 */
	boolean CanBuyCity();

	/**
	 * This method is called when a player attempts to offer a trade
	 * 
	 * @return True if the turn belongs to the Player: False otherwise
	 */
	boolean CanOfferTrade(Resources offer);

	/**
	 * This method is called when a player attempts to perform a Maritime Trade
	 * 
	 * @return True if the turn belongs to the Player: False otherwise
	 */
	boolean CanMaritimeTrade(int ratio, String inputResource, String outResource);

	/**
	 * This method is called when the player tries to finish his/her turn
	 * 
	 * @return True if the player can finish his/her turn: False otherwise
	 */
	boolean CanFinishTurn();

	/**
	 * This method is called when a player tries to buy a development card
	 * 
	 * @return True if the player has sufficient resources to buy a development
	 *         card: False otherwise
	 */
	boolean CanBuyDevCard();

	/**
	 * This method is called when a player tries to play a development card
	 * 
	 * @return True if it is the players turn and if the player has owned the
	 *         card for at least 1 turn: False otherwise
	 */
	boolean CanUseYearOfPlenty(String resource1, String resource2);

	/**
	 * This method is called when a player tries to play a development card
	 * 
	 * @return True if it is the players turn and if the player has owned the
	 *         card for at least 1 turn: False otherwise
	 */
	boolean CanUseRoadBuilder(EdgeLocation spot1, EdgeLocation spot2);

	/**
	 * This method is called when a player tries to play a development card
	 * 
	 * @return True if it is the players turn and if the player has owned the
	 *         card for at least 1 turn: False otherwise
	 */
	boolean CanUseSoldier(int victimIndex, HexLocation location);

	/**
	 * This method is called when a player tries to play a development card
	 * 
	 * @return True if it is the players turn and if the player has owned the
	 *         card for at least 1 turn: False otherwise
	 */
	boolean CanUseMonopoly(String resource);

	/**
	 * This method is called when a player tries to play a development card
	 * 
	 * @return True if it is the players turn AND if the player has owned the
	 *         card for at least 1 turn OR if it is the players turn AND the
	 *         points gained from the Monument will bring the player to 10
	 *         victory points: False otherwise
	 */
	boolean CanUseMonument();

	/**
	 * This method is called whenever the user is trying to place a road on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the road to be placed at the
	 * specified location.
	 * 
	 * @param edgeLoc
	 *            The proposed road location
	 * @return true if the road can be placed at edgeLoc, false otherwise
	 */
	boolean canPlaceRoad(EdgeLocation edgeLoc, boolean free);

	/**
	 * This method is called whenever the user is trying to place a settlement
	 * on the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the settlement to
	 * be placed at the specified location.
	 * 
	 * @param vertLoc
	 *            The proposed settlement location
	 * @return true if the settlement can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceSettlement(VertexLocation vertLoc, boolean free);

	/**
	 * This method is called whenever the user is trying to place a city on the
	 * map. It is called by the view for each "mouse move" event. The returned
	 * value tells the view whether or not to allow the city to be placed at the
	 * specified location.
	 * 
	 * @param vertLoc
	 *            The proposed city location
	 * @return true if the city can be placed at vertLoc, false otherwise
	 */
	boolean canPlaceCity(VertexLocation vertLoc);

	/**
	 * This method is called whenever the user is trying to place the robber on
	 * the map. It is called by the view for each "mouse move" event. The
	 * returned value tells the view whether or not to allow the robber to be
	 * placed at the specified location.
	 * 
	 * @param hexLoc
	 *            The proposed robber location
	 * @return true if the robber can be placed at hexLoc, false otherwise
	 */
	boolean canPlaceRobber(HexLocation hexLoc);

	/**
	 * This method is called by the Rob View when a player to rob is selected
	 * via a button click.
	 * 
	 * @param victim
	 *            The player to be robbed
	 */
	void robPlayer(HexLocation location, int victimIndex);
	
	/**
	 * This method determines if the turn belongs to this client
	 * @return true if turn belongs to client, false otherwise
	 */
	boolean isPlayerTurn();
	boolean CanDiscardCards(Resources resources);
	boolean canRobPlayer(HexLocation location, int victimIndex);
	
	void playSoldierCard(int victimIndex, HexLocation location);
	void playYearOfPlentyCard(String resource1, String resource2);
	void playRoadCard(EdgeLocation spot1, EdgeLocation spot2);
	void playMonopolyCard(String resource);
	void playMonumentCard();

	void rollNumber(int d1, int d2);
	void sendChat(SendChatParam param);
	void acceptTrade(boolean accept);
	void discardCards(Resources resources);

	void buildRoad(EdgeLocation edge, boolean free);	
	void buildSettlement(VertexLocation vert, boolean free);
	void buildCity(VertexLocation vert);

	void offerTrade(int receiver, Resources resources);
	void maritimeTrade(int ratio, String inputResource, String outResource);
	void finishTurn();
	void buyDevCard();
}
