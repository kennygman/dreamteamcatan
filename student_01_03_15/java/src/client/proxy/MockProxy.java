package client.proxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.board.Settlement;
import model.player.Player;
import model.player.Resources;

import com.google.gson.Gson;

import model.Game;
import shared.Translator;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.parameters.*;
import shared.response.*;

public class MockProxy implements IProxy
{

    private static final ResourceType WHEAT = null;
	private static final ResourceType ORE = null;
	private Game fakeGame;
    private GameListObject proxyGame;
    private GameListObject[] games ;

    
    public MockProxy()
    {
   	 	readGameFile();
    }
    
    private void readGameFile()
    {
   	 
   	 try
   	 {
   		 StringBuffer gameBuilder = new StringBuffer();

   		 BufferedReader reader = new BufferedReader(new FileReader("modelJson"));
   		 String game;
   	 
   		 while((game = reader.readLine()) != null)
   		 {
   			 
   			 gameBuilder.append(game);
   			 
   		 }
   		 
   		 
   		 game = gameBuilder.toString();
   		 
   		 Translator t =  new Translator();
   		 GameModelResponse g = new GameModelResponse();
   		 
   		 g = t.translateGetGameModel(game);
   		
   		 
   		 fakeGame = g.getGame();
   		 fakeGame.setTitle("loosers");
   		 fakeGame.setPlayerId(fakeGame.getPlayers()[0].getPlayerID());
   		 fakeGame.getBoard().sort();
   		HexLocation location = new HexLocation(0,0);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.NorthEast);
  		Settlement settlement = new Settlement(0,vertex.getNormalizedLocation());
   		fakeGame.getMap().setSettlement(settlement);
   	 
   	 }
   	 catch(IOException e)
   	 {
   		 System.err.println(e.getMessage());
   	 }
   	 /*
   	 PostCommandsParam commandParam = new PostCommandsParam(game);
   	 proxy.postGameCommands(commandParam);
   	 proxy.saveGame(new SaveGameParam(game.getGameId(),"junit"));
   	 games[0] = proxyGame;
   	  */
    }
    @Override
    public LoginResponse login(CredentialsParam input)
    {
//   	 if(input.getUser() == "hank" && input.getPassword() == "test")
//   	 {
//   		 return new StandardResponse(true);
//   	 }
//   	 else
//   	 {
//   		 return new StandardResponse(false);
//   	 }
   	 return null;
    }

    @Override
    public LoginResponse register(CredentialsParam input)
    {
//   	 
//   	 if(input.getUser() != null || input.getPassword() != null || input.getUser() == "hank")
//   	 {
//
//   		 return new StandardResponse(false);
//   	 }
//   	 else
//   	 {
//   		 return new StandardResponse(true);
//   	 }
   	 return null;

    }

    @Override
    public ListGamesResponse listGames()
    {
   	 
   	 boolean b = true;
   	 
   	 ListGamesResponse result = new ListGamesResponse(games,b);
   	 
   	 return result;
    }

    @Override
    public CreateGameResponse createGame(CreateGameParam input)
    {
   	 String title =  input.getName();
   	 int id = 1;
   	 boolean b = true;
   	 
   	 Player[] players = fakeGame.getPlayers();
   	 
   	 CreateGameResponse result = new CreateGameResponse(title,id,players,b);
   	 
   	 
   	 return result;
    }

    @Override
    public StandardResponse joinGame(JoinGameParam input)
    {
   	 StandardResponse result = new StandardResponse(true);
   	 
   	 
   	 
   	 
   	 return result;
    }

    @Override
    public StandardResponse saveGame(SaveGameParam input)
    {
   	 StandardResponse result = new StandardResponse(true);
    
   	 return result;
    }

    @Override
    public StandardResponse loadGame(LoadGameParam input)
    {
   	 StandardResponse result = new StandardResponse(false);
   	 
   	 if(input.getName()=="loosers")
   	 {
   		 result.setValid(true);
   	 }
    
   	 return result;
    }

    @Override
    public GameModelResponse getGameModel()
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
   	 return result;
    }

    @Override
    public GameModelResponse resetGame()
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse postGameCommands(PostCommandsParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public CommandResponse getGameCommands()
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public StandardResponse addAi(AddAiParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public ListAIResponse listAi()
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse sendChat(SendChatParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 String translation = "How can you tell a blonde's been using the computer? \n There's white-out all over the screen.";
   	 
   	 
   	 return result;
    }

    @Override
    public GameModelResponse rollNumber(RollNumParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
   	 return result;
    }

    @Override
    public GameModelResponse robPlayer(RobPlayerParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse finishTurn(FinishTurnParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse buyDevCard(BuyDevCardParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse playYearOfPlenty(PlayYearOfPlentyParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse playRoadBuilding(PlayRoadBuildingParam input)
    {
    	
   	 GameModelResponse result = new GameModelResponse();
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse playSoldier(PlaySoldierParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse playMonopoly(PlayMonopolyParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse playMonument(PlayMonumentParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse buildRoad(BuildRoadParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse buildSettlement(BuildSettlementParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse buildCity(BuildCityParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	int playeIndex = input.getPlayerIndex();
   	Player currentPlayer = fakeGame.getPlayers()[0];
   	
    currentPlayer.setCities(currentPlayer.getCities()-1);
    
    Resources hand = currentPlayer.getResources();
    
    int wheat = hand.getResourceAmount(ResourceType.WHEAT);
	int ore = hand.getResourceAmount(ResourceType.ORE);
	
	hand.useResource(WHEAT, 2);
	hand.useResource(ORE, 3);
   	currentPlayer.setResources(hand);
   	currentPlayer.setCities(currentPlayer.getCities()-1);
   	currentPlayer.setSettlements(currentPlayer.getSettlements()+1);
   	
   	fakeGame.getPlayers()[0] =currentPlayer;
   		
   	result.setGame(fakeGame);
   	result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse offerTrade(OfferTradeParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   		 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse acceptTrade(AcceptTradeParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
   	 return result;
    }

    @Override
    public GameModelResponse maritimeTrade(MaritimeTradeParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return null;
    }

    @Override
    public GameModelResponse discardCards(DiscardCardsParam input)
    {
   	 
   		 GameModelResponse result = new GameModelResponse();
   	 
   		 result.setGame(fakeGame);
   		 result.setValid(true);
   	 
   		 return null;
    }

    @Override
    public StandardResponse ChangeLogLevel(ChangeLogLevelParam input) {
   	 // TODO Auto-generated method stub
   	 return null;
    }    

}
