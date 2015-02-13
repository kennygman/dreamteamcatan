package client.proxy;

import shared.parameters.*;
import shared.response.*;


public interface IProxy {

	//you can't initiate an interface with a constructor. The constructor and connection will be started in Proxy
	

	/**
	 * Will return the result of trying to login into the system.
	 */
	StandardResponse login(CredentialsParam input);
	
	/**
	 * Will return the result of trying to register.
	 */
	StandardResponse register(CredentialsParam input);
	
	/**
	 * Will list the Games currently on the server.
	 */
	ListGamesResponse listGames(); 

	/**
	 * Will return the result after attempting to create a game.
	 */
	CreateGameResponse createGame(CreateGameParam input) ;
	
	/**
	 * Will return the result after attempting to join a game.
	 */
	StandardResponse joinGame(JoinGameParam input);
	
	
	/**
	 * Will return the result after attempting to save a game.
	 */
	StandardResponse saveGame (SaveGameParam input);
		
	/**
	 * Will return the result of trying to load a game.	
	 */
	StandardResponse loadGame(LoadGameParam input);
		
	/**
	 * Will return the result of getting a certain game model.
	 */
	GameModelResponse getGameModel();
	
	
	/**
	 * Will return the result after attempting to reset a game history.
	 */
	GameModelResponse resetGame();
	
	/**
	 * Will return the game model after attempting a list of Commands
	 */
	GameModelResponse postGameCommands(PostCommandsParam input);
	
	/**
	 * Will return the list of commands given in the current game.
	 */
	CommandResponse getGameCommands();
	
	/**
	 * Will return the result of attempting to add an AI character.
	 */
	 StandardResponse addAi(AddAiParam input);
	 /**
	  * Will return the result of attempting to see AI player types.
	  */
	ListAIResponse listAi();
	
	/**
	 * Will return the result of attempting to send a chat message.
	 */
	GameModelResponse  sendChat(SendChatParam input);
	
	
	/**
	 * Will return the result of attempting to roll a number.
	 */
	GameModelResponse  rollNumber(RollNumParam input);
	
	/**
	 * Will return the result of attempting to rob a player.
	 */
	GameModelResponse  robPlayer(RobPlayerParam input);
	
	/**
	 * Will return the result of attempting to finish a turn.
	 */
	GameModelResponse  finishTurn(FinishTurnParam input);
	/**
	 * Will return the result of attempting to buy a development card.
	 */
	GameModelResponse  buyDevCard(BuyDevCardParam input);
	
	/**
	 * Will return the result of attempting to play the year of plenty card.
	 */
	GameModelResponse  playYearOfPlenty(PlayYearOfPlentyParam input);
	
	/**
	 * Will return the result of attempting to play the road building card.
	 */
	GameModelResponse  playRoadBuilding(PlayRoadBuildingParam input); 

	
	/**
	 * Will return the result of attempting to play the soldier card.
	 */
	GameModelResponse  playSoldier(PlaySoldierParam input) ;
	
	/**
	 * Will return the result of attempting to play the monopoly card.
	 */
	GameModelResponse  playMonopoly(PlayMonopolyParam input);
	
	/**
	 * Will return the result of attempting to play a monument card.
	 */
	GameModelResponse  playMonument(PlayMonumentParam input);
	
	/**
	 * Will return the result of attempting to build a road.
	 */
	GameModelResponse  buildRoad(BuildRoadParam input);
	
	/**
	 * Will return the result of attempting to build a settlement.
	 */
	GameModelResponse  buildSettlement(BuildSettlementParam input);
	
	/**
	 * Will return the result of attempting to build a city.
	 */
	GameModelResponse  buildCity(BuildCityParam input);
	
	/**
	 * Will return the result of attempting to offer a trade.
	 */
	GameModelResponse  offerTrade(OfferTradeParam input);
	
	/**
	 * Will return the result of attempting to accept a trade.
	 */
	GameModelResponse  acceptTrade(AcceptTradeParam input);
	/**
	 * Will return the result of attempting to complete a maritime trade.
	 */
	GameModelResponse  maritimeTrade(MaritimeTradeParam input);
	
	/**
	 * Will return the result of attempting to discard cards.
	 */
	GameModelResponse  discardCards(DiscardCardsParam input);
	
	/**
	 * Will return the result of attempting to change the server's logging level.
	 */
	StandardResponse  ChangeLogLevel(ChangeLogLevelParam input);

	
	
}
