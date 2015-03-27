package server.facade;

import model.Game;
import model.player.Player;
import server.GameManager;
import server.User;
import server.UserManager;
import shared.parameters.*;
import shared.response.*;

public class ServerFacade
{
	private static IUserFacade userInstance;
	private static IPreGameFacade pregameInstance;
	private static IGameFacade gameInstance;
	private static IMovesFacade movesInstance;

	private static UserManager users;
	private static GameManager games;

	private static void init()
	{
		users = new UserManager();
		users.initAi();
		games = new GameManager();
		initDefault();
	}
	private static void initDefault()
	{
		Game game = new Game().initialize("Default", false, false, false);
		
		Player p1 = new Player();
		p1.setName("Sam");
		p1.setColor("red");
		p1.setPlayerID(-1);
		p1.setPlayerIndex(0);
		game.addPlayer(p1);
		
		Player p2 = new Player();
		p2.setName("Pete");
		p2.setColor("blue");
		p2.setPlayerID(-2);
		p2.setPlayerIndex(1);
		game.addPlayer(p2);
		
		Player p3 = new Player();
		p3.setName("Sven");
		p3.setColor("green");
		p3.setPlayerID(-3);
		p3.setPlayerIndex(2);
		game.addPlayer(p3);
		
		Player p4 = new Player();
		p4.setName("Kunkka");
		p4.setColor("orange");
		p4.setPlayerID(-4);
		p4.setPlayerIndex(3);
		game.addPlayer(p4);

		games.addGame(game);
	}
	// ---------------------------------------------------------------------------------
	/*public static void createInstance()
	{
		init();
		userInstance = new UserFacade(users);
		pregameInstance = new PreGameFacade(games);
		gameInstance = new GameFacade(games);
		movesInstance = new MovesFacade(games);
	}*/

	// ---------------------------------------------------------------------------------
	public static void createInstance(boolean testing)
	{
		init();
		 if(testing)
		 {
			 userInstance = new MockUserFacade();
			 pregameInstance =new MockPreGameFacade();
			 gameInstance = new MockGameFacade();
			 movesInstance = new MockMovesFacade();
		 }
		 else
		{
			userInstance = new UserFacade(users);
			pregameInstance = new PreGameFacade(games);
			gameInstance = new GameFacade(games,users);
			movesInstance = new MovesFacade(games);
		}
	}
	
	public static Game getGame(int id)
	{
		return games.getGame(id);
	}

	// ===============================================================================
	// HANDLER METHODS
	// ===============================================================================

	public static GameModelResponse sendChat(SendChatParam param, int id)
	{
		return movesInstance.sendChat(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		return movesInstance.acceptTrade(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse discardCards(DiscardCardsParam param, int id)
	{
		return movesInstance.discardCards(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse rollNumber(RollNumParam param, int id)
	{
		return movesInstance.rollNumber(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse buildRoad(BuildRoadParam param, int id)
	{
		return movesInstance.buildRoad(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse buildSettlement(BuildSettlementParam param,
			int id)
	{
		return movesInstance.buildSettlement(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse buildCity(BuildCityParam param, int id)
	{
		return movesInstance.buildCity(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse offerTrade(OfferTradeParam param, int id)
	{
		return movesInstance.offerTrade(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse maritimeTrade(MaritimeTradeParam param,
			int id)
	{
		return movesInstance.maritimeTrade(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse robPlayer(RobPlayerParam param, int id)
	{
		return movesInstance.robPlayer(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse finishTurn(FinishTurnParam param, int id)
	{
		return movesInstance.finishTurn(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse buyDevCard(BuyDevCardParam param, int id)
	{
		return movesInstance.buyDevCard(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse playSoldierCard(PlaySoldierParam param,
			int id)
	{
		return movesInstance.playSoldierCard(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse playYearOfPlentyCard(
			PlayYearOfPlentyParam param, int id)
	{
		return movesInstance.playYearOfPlentyCard(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse playRoadCard(PlayRoadBuildingParam param,
			int id)
	{
		return movesInstance.playRoadCard(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse playMonopolyCard(PlayMonopolyParam param,
			int id)
	{
		return movesInstance.playMonopolyCard(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse playMonumentCard(PlayMonumentParam param,
			int id)
	{
		return movesInstance.playMonumentCard(param, id);
	}

	// ---------------------------------------------------------------------------------
	public static LoginResponse login(CredentialsParam param)
	{
		return userInstance.login(param);
	}

	// ---------------------------------------------------------------------------------
	public static LoginResponse register(CredentialsParam param)
	{
		return userInstance.register(param);
	}

	// ---------------------------------------------------------------------------------
	public static StandardResponse join(JoinGameParam param, User user)
	{
		return pregameInstance.join(param, user);
	}

	// ---------------------------------------------------------------------------------
	public static CreateGameResponse create(CreateGameParam param)
	{
		return pregameInstance.create(param);
	}

	// ---------------------------------------------------------------------------------
	public static ListGamesResponse listGames()
	{
		return pregameInstance.listGames();
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse getGameModel(int id)
	{
		return gameInstance.getGameModel(id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse save(SaveGameParam param)
	{
		return pregameInstance.save(param.getName(), param.getId());
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse load(LoadGameParam param)
	{
		return pregameInstance.load(param.getName());
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse resetGame(int id)
	{
		return gameInstance.resetGame(id);
	}

	// ---------------------------------------------------------------------------------
	public static CommandResponse getCommands(int id)
	{
		return gameInstance.getCommands(id);
	}

	// ---------------------------------------------------------------------------------
	public static GameModelResponse commands(CommandsParam param, int id)
	{
		return gameInstance.commands(param, id);
	}
	public static StandardResponse addAI(AddAiParam param, int id)
	{
		return gameInstance.addAI(param,id);
	}
	// ---------------------------------------------------------------------------------
	public static ListAIResponse listAi()
	{
		return gameInstance.listAI();
	}
        
}
