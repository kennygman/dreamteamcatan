package server;

import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

import server.database.Database;
import server.database.DatabaseFacade;
import server.facade.ServerFacade;
import server.handlers.*;

public class Server
{

    //--------------------------------------------------------------------------------
    private static int SERVER_PORT_NUMBER = 8081;
    private static final int MAX_WAITING_CONNECTIONS = 10;
    private HttpServer server;
    
    // ===============================================================================
    // USER
    // ===============================================================================
    private LoginHandler loginHandler = new LoginHandler();
    private RegisterHandler registerHandler = new RegisterHandler();
    
    // ===============================================================================
    // GAMES
    // ===============================================================================
    private GameListHandler gameListHandler = new GameListHandler();
    private CreateGameHandler createGameHandler = new CreateGameHandler();
    private JoinGameHandler joinGameHandler = new JoinGameHandler();
    private SaveGameHandler saveGameHandler = new SaveGameHandler();
    private LoadGameHandler loadGameHandler = new LoadGameHandler();
    
    // ===============================================================================
    // GAME
    // ===============================================================================
    private GameModelHandler gameModelHandler = new GameModelHandler();
    private ResetHandler resetHandler = new ResetHandler();
    private CommandsHandler commandsHandler = new CommandsHandler();
    private AddAIHandler addAIHandler = new AddAIHandler();
    private ListAIHandler listAIHandler = new ListAIHandler();
    
    // ===============================================================================
    // MOVES
    // ===============================================================================
    private AcceptTradeHandler acceptTradeHandler = new AcceptTradeHandler();
    private BuildCityHandler buildCityHandler = new BuildCityHandler();
    private BuildRoadHandler buildRoadHandler = new BuildRoadHandler();
    private BuildSettlementHandler buildSettlementHandler = new BuildSettlementHandler();
    private BuyDevCardHandler buyDevCardHandler = new BuyDevCardHandler();
    private DiscardCardsHandler discardCardsHandler = new DiscardCardsHandler();
    private FinishTurnHandler finishTurnHandler = new FinishTurnHandler();
    private MaritimeTradeHandler maritimeTradeHandler = new MaritimeTradeHandler();
    private OfferTradeHandler offerTradeHandler = new OfferTradeHandler();
    private PlayMonopolyHandler playMonopolyHandler = new PlayMonopolyHandler();
    private PlayMonumentHandler playMonumentHandler = new PlayMonumentHandler();
    private PlayRoadBuildingHandler playRoadBuildHandler = new PlayRoadBuildingHandler();
    private PlaySoldierHandler playSoldierHandler = new PlaySoldierHandler();
    private PlayYearOfPlentyHandler playYearHandler = new PlayYearOfPlentyHandler();
    private RobPlayerHandler robPlayerHandler = new RobPlayerHandler();
    private RollNumberHandler rollNumberHandler = new RollNumberHandler();
    private SendChatHandler sendChatHandler = new SendChatHandler();
    
    // ===============================================================================
    // UTIL
    // ===============================================================================
    private ChangeLogLevelHandler changeLogLevelHandler = new ChangeLogLevelHandler();
    
    //--------------------------------------------------------------------------------

	private Server(){}

	private Server(String[] args)
	{
		try {
			
			DatabaseFacade.initialize();

			if(args.length == 3) {
				SERVER_PORT_NUMBER = Integer.valueOf(args[0]);
				DatabaseFacade.load(args[1].toLowerCase());
				
			} else {
				DatabaseFacade.load("sqlite");
				SERVER_PORT_NUMBER = 8081;
			}
			
			
		} catch (Exception e)
		{
			System.out.println("INVALID ARGUMENTS.  USAGE: our-server <PORT: 0-99999> <DB: sqlite, mongo> <COMMAND-LENGTH: 0-99>");
		}
	}

	private void run()
	{

        try
        {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                        MAX_WAITING_CONNECTIONS);
        } catch (IOException e)
        {
                return;
        }

        server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        server.createContext("/docs/api/view", new Handlers.BasicFile(""));
        server.setExecutor(null); // use the default executor


        //--------------------------------------------------------------------------------
        server.createContext("/user/login", loginHandler);
        server.createContext("/user/register", registerHandler);
        //--------------------------------------------------------------------------------
        server.createContext("/games/list", gameListHandler);
        server.createContext("/games/create", createGameHandler);
        server.createContext("/games/join", joinGameHandler);
        server.createContext("/games/save", saveGameHandler);
        server.createContext("/games/load", loadGameHandler);
        //--------------------------------------------------------------------------------
        server.createContext("/game/model", gameModelHandler);
        server.createContext("/game/reset", resetHandler);
        server.createContext("/game/commands", commandsHandler);
        server.createContext("/game/addAI", addAIHandler);
        server.createContext("/game/listAI", listAIHandler);
        //--------------------------------------------------------------------------------
        server.createContext("/moves/acceptTrade", acceptTradeHandler);
        server.createContext("/moves/buildCity", buildCityHandler);
        server.createContext("/moves/buildRoad", buildRoadHandler);
        server.createContext("/moves/buildSettlement", buildSettlementHandler);
        server.createContext("/moves/buyDevCard", buyDevCardHandler);
        server.createContext("/moves/discardCards", discardCardsHandler);
        server.createContext("/moves/finishTurn", finishTurnHandler);
        server.createContext("/moves/maritimeTrade", maritimeTradeHandler);
        server.createContext("/moves/offerTrade", offerTradeHandler);      
        server.createContext("/moves/Monopoly", playMonopolyHandler);
        server.createContext("/moves/Monument", playMonumentHandler);
        server.createContext("/moves/Road_Building", playRoadBuildHandler);
        server.createContext("/moves/Soldier", playSoldierHandler);
        server.createContext("/moves/Year_of_Plenty", playYearHandler);
        server.createContext("/moves/robPlayer", robPlayerHandler);
        server.createContext("/moves/rollNumber", rollNumberHandler);
        server.createContext("/moves/sendChat", sendChatHandler);
        //--------------------------------------------------------------------------------
        server.createContext("/util/changeLogLevel", changeLogLevelHandler);
        //--------------------------------------------------------------------------------
        
        //ServerFacade.createInstance(true);
        ServerFacade.createInstance(false);
        server.start();
    }

    

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
                new Server().run();
        } else
        {
                new Server(args).run();
        }
    }

}
