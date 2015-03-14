package server.facade;

import shared.parameters.AddAiParam;
import shared.parameters.CreateGameParam;
import shared.parameters.JoinGameParam;
import shared.response.CreateGameResponse;
import shared.response.GameModelResponse;
import shared.response.ListGamesResponse;
import shared.response.StandardResponse;

public interface IPreGameFacade
{
	/**
	 * This method validates the command through the ModelFacade
	 * then adds the user to the specified Game then adds the User
	 * to the UserManager
	 * @param param Join Game parameters for the command
	 */
	StandardResponse join(JoinGameParam param);
	
	/**
	 * This method validates the command through the ModelFacade
	 * then creates and initializes a new Game with the specified
	 * parameters then adds the Game to the GameManager
	 * @param param Create Game parameters for the command
	 */
	CreateGameResponse create(CreateGameParam param);
	
	/**
	 * This method validates the credentials through the ModelFacade then
	 * adds an AI User to the specified Game then adds the AI to the UserManager
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
	 * This method saves the current state of the server model
	 * @param id the id of the game
	 */
	GameModelResponse save(int id);
	
	/**
	 * This method loads the last saved state of the server model
	 * @param id the id of the game
	 */
	GameModelResponse load(int id);

}
