package server;

import model.Game;

import java.util.*;

import server.commands.ICommand;
import server.commands.JoinGame;
import shared.parameters.*;
import shared.response.*;

public class ServerFacade implements IServerFacade
{
	private Map<Integer, User> users;
	private Map<Integer, Game> games;
	private Map<Integer, List<ICommand>> commands;
	private static String[] aiTypes = {"LARGEST_ARMY"};

	public ServerFacade()
	{
		users = new HashMap<>();
		games = new HashMap<>();
		commands = new HashMap<>();
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

	//---------------------------------------------------------------------------------
	@Override


	public StandardResponse addAI(AddAiParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override


	public ListGamesResponse listGames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	//---------------------------------------------------------------------------------
	@Override

	public ListAIResponse listAI(int id)
	{
		return new ListAIResponse(aiTypes, true);
	}

	//---------------------------------------------------------------------------------
	@Override

	public GameModelResponse getGameModel(int id)
	{
		// TODO Auto-generated method stub
		return null;
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
	// END
	// ===============================================================================
}
