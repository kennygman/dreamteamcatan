package server.facade;

import model.Game;
import model.ModelFacade;

import java.util.*;

import client.proxy.IProxy;
import server.User;
import server.commands.ICommand;
import server.commands.JoinGame;
import shared.parameters.*;
import shared.response.*;

public class ServerFacade implements IServerFacade
{
	private Map<Integer, User> users;
	private Map<Integer, Game> games;
	private Map<Integer, List<ICommand>> commands;
	
	public ServerFacade()
	{
		users = new HashMap<>();
		games = new HashMap<>();
		commands = new HashMap<>();
		initAiUsers();
	}
	
	// ===============================================================================
	// SINGLETON IMPLEMENTATION
	// ===============================================================================
	private static ServerFacade instance;
	public static ServerFacade getInstance()
	{
		if (instance == null) {
			throw new IllegalStateException("Tried to get instance of ServerFacade"
					+ " without initializing it first!");
		}
		return instance;
	}

	// ===============================================================================
	// INITIALIZE SERVER VARIABLES
	// ===============================================================================
	public void initAiUsers()
	{
		int id = users.size();
		
		for (String s: FacadeUtils.aiNames)
			users.put(-(++id), new User(-id,s,s.toLowerCase()));
	}
	
	// ===============================================================================
	// GETTERS AND SETTERS
	// ===============================================================================
	
	public User getUser(int id){return users.get(id);}
	public Game getGame(int id) { return games.get(id);}
	public List<ICommand> getCommandList(int id) {return commands.get(id);}

	public void setUser(User user){users.put(user.getId(), user);}
	
	/**
	 * Increment gameID by 1, add game to server game list, then add new key with
	 * gameID to commands list
	 * @param game the game
	 */
	public void setGame(Game game)
	{
		int gameID = games.size()+1;
		games.put(gameID, game);
		commands.put(gameID, new ArrayList<ICommand>());
	}
	
	/**
	 * Add the command to the game's command list
	 * @param id the game's id
	 * @param cmd the command
	 */
	public void setCommand(int id, ICommand cmd)
	{
		List<ICommand> commandList = commands.get(id);
		commandList.add(cmd);
		commands.put(id, commandList);
	}
	
	// ===============================================================================
	// GAME COMMANDS
	// ===============================================================================

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse sendChat(SendChatParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse discardCards(DiscardCardsParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse rollNumber(RollNumParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse buildRoad(BuildRoadParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse buildSettlement(BuildSettlementParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse buildCity(BuildCityParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse offerTrade(OfferTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse maritimeTrade(MaritimeTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse robPlayer(RobPlayerParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse finishTurn(FinishTurnParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse buyDevCard(BuyDevCardParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse playSoldierCard(PlaySoldierParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param,
			int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse playMonumentCard(PlayMonumentParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public LoginResponse login(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public LoginResponse register(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public StandardResponse join(JoinGameParam param)
	{
		JoinGame cmd = new JoinGame(param);
		cmd.execute(games.get(param.getId()));
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override


	public CreateGameResponse create(CreateGameParam param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// ===============================================================================
	// SERVER COMMANDS
	// ===============================================================================

	//---------------------------------------------------------------------------------
	@Override

	public StandardResponse addAI(AddAiParam param, int id)
	{
		boolean valid = false;
		if (this.canAddAi(0, id, param.getType()))
		{
			valid = true;
		}
		return new StandardResponse(valid);
	}

	//---------------------------------------------------------------------------------
	@Override

	public ListGamesResponse listGames()
	{
		List<GameListObject> gamesList = new ArrayList<GameListObject>();
		for (int i = 0; i < games.size(); i++)
		{
			gamesList.add(games.get(i).getGameListObject());
		}
		ListGamesResponse response = new ListGamesResponse(gamesList.toArray(new GameListObject[0]), true);
		
		return response;
	}

	//---------------------------------------------------------------------------------
	@Override

	public ListAIResponse listAI(int id)
	{
		return new ListAIResponse(FacadeUtils.aiTypes, true);
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse getGameModel(int id)
	{
		GameModelResponse response = new GameModelResponse();
		Game game = games.get(id);
		response.setGame(game);
		return response;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse save(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse load(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse resetGame(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public CommandResponse getCommands(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public StandardResponse commands(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	// ===============================================================================
	// SERVER CAN DO METHODS
	// ===============================================================================

	/**
	 * Validates if the preconditions are met for adding the AI:
	 * - The caller is logged into the server
	 * - The caller has joined a game
	 * - The type is a valid AI Type
	 * @param userId
	 * @param gameId
	 * @param type
	 * @return True if all preconditions are satisfied, False otherwise
	 */
	public boolean canAddAi(int userId, int gameId, String type)
	{
		return users.get(userId).isLoggedIn() &&
				FacadeUtils.isGameFull(games.get(gameId)) &&
				FacadeUtils.aiTypes[0].equals(type);
	}
	
	//---------------------------------------------------------------------------------
	
	
	// ===============================================================================
	// END
	// ===============================================================================
}
