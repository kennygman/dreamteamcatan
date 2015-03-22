package server.facade;

import server.User;
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
	StandardResponse join(JoinGameParam param, User user);
	
	/**
	 * This method validates the command through the ModelFacade
	 * then creates and initializes a new Game with the specified
	 * parameters then adds the Game to the GameManager
	 * @param param Create Game parameters for the command
	 */
	CreateGameResponse create(CreateGameParam param);
	
	/**
	 * This method returns a list of games on the server
	 * @return list of Game objects
	 */
	ListGamesResponse listGames();
	
	/**
	 * This method saves the current state of the server model
	 * @param id the id of the game
	 */
	GameModelResponse save(String name, int id);
	
	/**
	 * This method loads the last saved state of the server model
	 * @param id the id of the game
	 */
	GameModelResponse load(String name);

}
