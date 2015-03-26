package server.facade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Game;
import model.board.Settlement;
import model.player.Player;
import server.GameManager;
import server.User;
import server.UserManager;
import shared.Translator;
import shared.definitions.CatanColor;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.parameters.AddAiParam;
import shared.parameters.CommandsParam;
import shared.response.CommandResponse;
import shared.response.GameListObject;
import shared.response.GameModelResponse;
import shared.response.ListAIResponse;
import shared.response.StandardResponse;

public class MockGameFacade implements IGameFacade
{
	private static final CatanColor BLUE = null;
	private static final CatanColor YELLOW = null;
	private static final CatanColor WHITE = null;
	private static final CatanColor GREEN = null;
	private static final CatanColor ORANGE = null;
	private static final CatanColor RED = null;
	private static final CatanColor BROWN = null;
	private static final CatanColor PUCE = null;
	private static final CatanColor PURPLE = null;
	private Game fakeGame;
	 private UserManager users;
	    
	public MockGameFacade()
	{
           createFakeGame();
	   users = new UserManager();
	   users.initAi();
	}
	private void createFakeGame()
	{
		try
		{
			StringBuffer gameBuilder = new StringBuffer();

			BufferedReader reader = new BufferedReader(new FileReader(
					"modelJson.txt"));
			String game;

			while ((game = reader.readLine()) != null)
			{

				gameBuilder.append(game);

			}

			game = gameBuilder.toString();

			Translator t = new Translator();
			GameModelResponse g = new GameModelResponse();

			g = t.translateGetGameModel(game);

			fakeGame = g.getGame();
			fakeGame.setTitle("loosers");
			fakeGame.setPlayerId(fakeGame.getPlayers()[0].getPlayerID());
			fakeGame.getBoard().sort();
			HexLocation location = new HexLocation(0, 0);
			VertexLocation vertex = new VertexLocation(location,
					VertexDirection.NorthEast);
			Settlement settlement = new Settlement(0,
					vertex.getNormalizedLocation());
			fakeGame.getMap().setSettlement(settlement);

		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
	@Override
	public GameModelResponse getGameModel(int id)
	{
		
		GameModelResponse response = new GameModelResponse();
		response.setGame(fakeGame);
		response.setValid(true);
		 
		return response;
		
	}

	@Override
	public StandardResponse addAI(AddAiParam param, int id)
	{
		StandardResponse response = new StandardResponse(false);
		boolean insertedAI = false;
		if(param.getType()=="LARGEST_ARMY")
		{
			insertedAI=true;
			response = new StandardResponse(insertedAI);
			Player newPlayer = new Player();
			
			//Should be kunkka everytime
			User currentUser = users.getAi(4);
			newPlayer.setColor("PUCE");
			newPlayer.setName(currentUser.getName());
			newPlayer.setPlayerIndex(3);
			newPlayer.setPlayerID(currentUser.getId());
		}
		else
		{
		
		}
		
		response = new StandardResponse(insertedAI);
		
		return response;
	}

	@Override
	public ListAIResponse listAI()
	{
		String[] aiType = new String[]{"LARGEST_ARMY"};
		ListAIResponse response = new ListAIResponse(aiType,true);
		return response;
	}

	@Override
	public GameModelResponse resetGame(int id)
	{
		createFakeGame();
		GameModelResponse response = new GameModelResponse();
		response.setGame(fakeGame);
		response.setValid(true);;
		
		return response;
	}

	@Override
	public CommandResponse getCommands(int id)
	{
		String commands = "whateverIwantCommand";
		
		CommandResponse response = new CommandResponse(commands,true);
		
		return response;
	}

	@Override
	public GameModelResponse commands(CommandsParam param, int id)
	{
		//We need to figure out how this function is suppose to work. I don't think is right.
		return null;
	}

}
