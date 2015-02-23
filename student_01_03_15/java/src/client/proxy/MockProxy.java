package client.proxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.board.Road;
import model.board.Settlement;
import model.player.Player;
import model.player.Resources;
import client.data.GameInfo;

import com.google.gson.Gson;

import model.Game;
import model.TradeOffer;
import shared.Translator;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.parameters.*;
import shared.response.*;

public class MockProxy implements IProxy
{



	private Game fakeGame;
    private GameListObject proxyGame;
    private GameInfo[] games ;

    
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
   	 fakeGame.getTurnTracker().setCurrentTurn(fakeGame.getTurnTracker().getCurrentTurn()+1);
   	 fakeGame.getTurnTracker().setStatus("Waiting");
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse buyDevCard(BuyDevCardParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse playYearOfPlenty(PlayYearOfPlentyParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 Player currentPlayer = fakeGame.getPlayers()[0];
   	 
   	 currentPlayer.setPlayedDevCard(true);
  	 currentPlayer.getOldDevCards().setYearOfPlenty(currentPlayer.getOldDevCards().getYearOfPlenty()-1);
  	 
  	 currentPlayer.getResources().addResource(ResourceType.WOOD,  1);
  	 currentPlayer.getResources().addResource(ResourceType.BRICK,1);
  	 
  	//System.out.println("proxy wood is" +  currentPlayer.getResources().getResourceAmount(ResourceType.WOOD));
  	
  	 fakeGame.getPlayers()[0] = currentPlayer;
  	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse playRoadBuilding(PlayRoadBuildingParam input)
    {
    	GameModelResponse result = new GameModelResponse();
    	Road road = new Road(input.getPlayerIndex(),input.getSpot1());
    	Road road2 = new Road(input.getPlayerIndex(),input.getSpot2());
    	
    	fakeGame.getPlayers()[0].setPlayedDevCard(true);
    	fakeGame.getBoard().setRoad(road);
    	fakeGame.getBoard().setRoad(road2);
    	
    	
   	 
   	 	result.setGame(fakeGame);
   	 	result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse playSoldier(PlaySoldierParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	Player currentPlayer = fakeGame.getPlayers()[0];
 	currentPlayer.getOldDevCards().setSolider((currentPlayer.getOldDevCards().getSoldier()-1));
 	 currentPlayer.setPlayedDevCard(true);
 	currentPlayer.getResources().addResource(ResourceType.WOOD,fakeGame.getPlayers()[input.getVictimIndex()].getResources().getResourceAmount("wood"));
 	currentPlayer.playDevcard(DevCardType.SOLDIER);
 	fakeGame.getBoard().setRobber(input.getLocation());
 	fakeGame.getPlayers()[0] = currentPlayer;
 	
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse playMonopoly(PlayMonopolyParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 
   	 Player currentPlayer = fakeGame.getPlayers()[input.getPlayerIndex()];
   	 Player opponent = fakeGame.getPlayers()[1];
   	 
   	 currentPlayer.setPlayedDevCard(true);
   	 currentPlayer.getOldDevCards().setMonopoly(currentPlayer.getOldDevCards().getMonopoly()-1);
   	 
 
   	 
   	 currentPlayer.getResources().addResource(ResourceType.WOOD, opponent.getResources().getResourceAmount(input.getResource()));
   	 
   	 opponent.getResources().useResource(ResourceType.WOOD,opponent.getResources().getResourceAmount(input.getResource()));
   	 
   	 fakeGame.getPlayers()[input.getPlayerIndex()] = currentPlayer;
   	 fakeGame.getPlayers()[1] = opponent;
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse playMonument(PlayMonumentParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 int currentVP = fakeGame.getPlayers()[0].getVictoryPoints();
   	 
   	 fakeGame.getPlayers()[0].setVictoryPoints(currentVP+1);
   	 
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse buildRoad(BuildRoadParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 Player currentPlayer = fakeGame.getPlayers()[0];
   	 currentPlayer.setRoads(currentPlayer.getRoads()-1);
   	 
   	 currentPlayer.getResources().useResource(ResourceType.BRICK, 1);
   	 currentPlayer.getResources().useResource(ResourceType.WOOD, 1);
   	 
   	 fakeGame.getPlayers()[0] = currentPlayer;
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse buildSettlement(BuildSettlementParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	
   	 Player currentPlayer = fakeGame.getPlayers()[0];
   	 currentPlayer.setSettlements(currentPlayer.getSettlements()-1);
   	 
   	 currentPlayer.getResources().useResource(ResourceType.WHEAT, 1);
   	 currentPlayer.getResources().useResource(ResourceType.SHEEP, 1);
   	 currentPlayer.getResources().useResource(ResourceType.BRICK, 1);
   	 currentPlayer.getResources().useResource(ResourceType.WOOD, 1);
   	 
   	 fakeGame.getPlayers()[0]=currentPlayer;
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse buildCity(BuildCityParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
 
   	Player currentPlayer = fakeGame.getPlayers()[0];
   	
    currentPlayer.setCities(currentPlayer.getCities()-1);
   	currentPlayer.setSettlements(currentPlayer.getSettlements()+1);
   	
    Resources hand = currentPlayer.getResources();
    
   
	
	hand.useResource(ResourceType.WHEAT, 2);
	hand.useResource(ResourceType.ORE, 3);
   	currentPlayer.setResources(hand);

   	
   	fakeGame.getPlayers()[0] =currentPlayer;
   		
   	result.setGame(fakeGame);
   	result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse offerTrade(OfferTradeParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 
   	 TradeOffer offer = new TradeOffer(0,input.getreceiver(),input.getOffer());
   	 
   	fakeGame.setTradeOffer(offer);
   	fakeGame.getPlayers()[0].getResources().useResource(ResourceType.WOOD,1);
   	fakeGame.getPlayers()[0].getResources().addResource(ResourceType.SHEEP,1);
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse acceptTrade(AcceptTradeParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	fakeGame.getPlayers()[0].getResources().useResource(ResourceType.WOOD,1);
   	fakeGame.getPlayers()[0].getResources().addResource(ResourceType.SHEEP,1);
   	
   	//System.out.println(fakeGame.getPlayers()[0].getResources().getResourceAmount(ResourceType.WOOD));
   	 result.setGame(fakeGame);
   	 result.setValid(true);
   	 return result;
    }

    @Override
    public GameModelResponse maritimeTrade(MaritimeTradeParam input)
    {
   	 GameModelResponse result = new GameModelResponse();
   	 
   	 fakeGame.getBank().addResource(ResourceType.WOOD,4);
   	fakeGame.getBank().useResource(ResourceType.BRICK,1);
   	 fakeGame.getPlayers()[input.getPlayerIndex()].getResources().useResource(ResourceType.WOOD,4);
   	fakeGame.getPlayers()[input.getPlayerIndex()].getResources().addResource(ResourceType.BRICK,1);
   	 result.setGame(fakeGame);
   	 result.setValid(true);
    
   	 return result;
    }

    @Override
    public GameModelResponse discardCards(DiscardCardsParam input)
    {
   	 
   		 GameModelResponse result = new GameModelResponse();
   	 
   		 result.setGame(fakeGame);
   		 result.setValid(true);
   	 
   		 return result;
    }

    @Override
    public StandardResponse ChangeLogLevel(ChangeLogLevelParam input) {
   	 // TODO Auto-generated method stub
   	 return null;
    }    

}
