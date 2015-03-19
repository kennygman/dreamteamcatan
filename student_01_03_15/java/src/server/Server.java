package server;

import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

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
    private LoginHandler loginHandler;
    private RegisterHandler registerHandler;
    
    // ===============================================================================
    // GAMES
    // ===============================================================================
    private GameListHandler gameListHandler;
    private CreateGameHandler createGameHandler;
    private JoinGameHandler joinGameHandler;
    private SaveGameHandler saveGameHandler;
    private LoadGameHandler loadGameHandler;
    
    // ===============================================================================
    // GAME
    // ===============================================================================
    private GameModelHandler gameModelHandler;
    private ResetHandler resetHandler;
    private CommandsHandler commandsHandler;
    private AddAIHandler addAIHandler;
    private ListAIHandler listAIHandler;
    
    // ===============================================================================
    // MOVES
    // ===============================================================================
    private AcceptTradeHandler acceptTradeHandler;
    private BuildCityHandler buildCityHandler;
    private BuildRoadHandler buildRoadHandler;
    private BuildSettlementHandler buildSettlementHandler;
    private BuyDevCardHandler buyDevCardHandler;
    private DiscardCardsHandler discardCardsHandler;
    private FinishTurnHandler finishTurnHandler;
    private MaritimeTradeHandler maritimeTradeHandler;
    private OfferTradeHandler offerTradeHandler;
    private PlayMonopolyHandler playMonopolyHandler;
    private PlayMonumentHandler playMonumentHandler;
    private PlayRoadBuildingHandler playRoadBuildHandler;
    private PlaySoldierHandler playSoldierHandler;
    private PlayYearOfPlentyHandler playYearHandler;
    private RobPlayerHandler robPlayerHandler;
    private RollNumberHandler rollNumberHandler;
    private SendChatHandler sendChatHandler;
    
    // ===============================================================================
    // UTIL
    // ===============================================================================
    private ChangeLogLevelHandler changeLogLevelHandler;
    
    //--------------------------------------------------------------------------------

    private Server()
    {
        ServerFacade.createInstance();
        SERVER_PORT_NUMBER = 8081;
    }

    private Server(String[] args)
    {
        if(args.length == 1)
                SERVER_PORT_NUMBER = Integer.valueOf(args[0]);
        else
                SERVER_PORT_NUMBER = 8081;
    }

    private void run()
    {
        System.out.println("===============:" + SERVER_PORT_NUMBER);

        try
        {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                        MAX_WAITING_CONNECTIONS);
        } catch (IOException e)
        {
                return;
        }

        server.setExecutor(null); // use the default executor
        
        //server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        //server.createContext("/docs/api/view", new Handlers.BasicFile(""));

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
