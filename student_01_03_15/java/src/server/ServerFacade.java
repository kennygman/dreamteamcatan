package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Game;
import shared.parameters.*;

public class ServerFacade implements IServerFacade
{
	private Map<Integer, User> users;
	private Map<Integer, Game> games;
	private Map<Integer, List<Command>> commands;

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
	public List<Command> getCommandList(int id) {return commands.get(id);}
	
	public void setUser(User user){users.put(user.getId(), user);}
	public void setGame(Game game)
	{
		int gameID = games.size()+1;
		games.put(gameID, game);
		commands.put(gameID, new ArrayList<Command>());
	}
	public void addCommand(int id, Command cmd)
	{
		List<Command> commandList = commands.get(id);
		commandList.add(cmd);
		commands.put(id, commandList);
	}
	
	// ===============================================================================
	// GAME COMMANDS
	// ===============================================================================

	//--------------------------------------------------------------------------------
	@Override
	public void sendChat(SendChatParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void acceptTrade(AcceptTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void discardCards(DiscardCardsParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void rollNumber(RollNumParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildRoad(BuildRoadParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildSettlement(BuildSettlementParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildCity(BuildCityParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void offerTrade(OfferTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void maritimeTrade(MaritimeTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void robPlayer(RobPlayerParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void finishTurn(FinishTurnParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buyDevCard(BuyDevCardParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playSoldierCard(PlaySoldierParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playYearOfPlentyCard(PlayYearOfPlentyParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playRoadCard(PlayRoadBuildingParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonopolyCard(PlayMonopolyParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonumentCard(PlayMonumentParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void login(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void register(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void join(JoinGameParam param)
	{
		JoinGame cmd = new JoinGame(param);
		cmd.execute(games.get(param.getId()));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void create(CreateGameParam param)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void addAI(AddAiParam param, int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void listGames()
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void listAI(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void getGameModel(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void save(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void load(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void resetGame(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void getCommands(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void commands(int id)
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------------
}
