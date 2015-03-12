package server;

import shared.parameters.*;
import shared.response.*;

public interface IServerFacade
{
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Chat parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse sendChat(SendChatParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Trade parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse acceptTrade(AcceptTradeParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Discard parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse discardCards(DiscardCardsParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Roll parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse rollNumber(RollNumParam param, int id);

	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Build Road parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buildRoad(BuildRoadParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Build Settlement parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buildSettlement(BuildSettlementParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Build City parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buildCity(BuildCityParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Trade Offer parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse offerTrade(OfferTradeParam  param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Maritime Trade parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse maritimeTrade(MaritimeTradeParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Rob Player parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse robPlayer(RobPlayerParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Finish Turn parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse finishTurn(FinishTurnParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Buy Development Card parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buyDevCard(BuyDevCardParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Soldier parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playSoldierCard(PlaySoldierParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Year of Plenty parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Road Card parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Monopoly parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Monument parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playMonumentCard(PlayMonumentParam param, int id);
	
	/**
	 * This method validates the credentials through the ModelFacade
	 * then validates if the credentials match a valid User for the server
	 * @param param Login parameters for the command
	 * @return login response
	 */
	LoginResponse login(CredentialsParam param);
	
	/**
	 * This method validates the credentials through the ModelFacade
	 * then creates and adds a new User account the the server User list
	 * @param param Register parameters for the command
	 * @return register response
	 */
	LoginResponse register(CredentialsParam param);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Join Game parameters for the command
	 */
	StandardResponse join(JoinGameParam param);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Create Game parameters for the command
	 */
	CreateGameResponse create(CreateGameParam param);
	
	/**
	 * This method validates the credentials through the ModelFacade then
	 * adds an AI User to the specified game.
	 * @param param Add AI parameters for the command
	 * @param id the id of the game
	 */
	StandardResponse addAI(AddAiParam param, int id);
	
	/**
	 * This method returns a list of games on the server
	 * @return list of Game objects
	 */
	ListGamesResponse listGames();
	
	/**
	 * This method returns a list of AI players
	 * @param id the id of the game
	 */
	ListAIResponse listAI(int id);
	
	/**
	 * This method returns the server Game model object
	 * @param id the id of the game
	 */
	GameModelResponse getGameModel(int id);
	
	/**
	 * This method saves the current state of the server model
	 * @param id the id of the game
	 */
	GameModelResponse save(int id);
	
	/**
	 * This method loads the last saved state of the server model
	 * @param id the id of the game
	 */
	GameModelResponse load(int id);
	
	/**
	 * This method resets the game to its initial state
	 * @param id the id of the game
	 */
	GameModelResponse resetGame(int id);
	
	/**
	 * This method returns a list of commands executed in the game
	 * @param id the id of the game
	 */
	CommandResponse getCommands(int id);
	
	/**
	 * This method executes a list of commands
	 * @param id the id of the game
	 */
	StandardResponse commands(int id);
}
