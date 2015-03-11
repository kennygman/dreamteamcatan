package server;

import shared.parameters.*;

public interface IServerFacade
{
	void sendChat(SendChatParam param, int id);
	void acceptTrade(AcceptTradeParam param, int id);
	void discardCards(DiscardCardsParam param, int id);
	void rollNumber(RollNumParam param, int id);

	void buildRoad(BuildRoadParam param, int id);
	void buildSettlement(BuildSettlementParam param, int id);
	void buildCity(BuildCityParam param, int id);
	void offerTrade(OfferTradeParam  param, int id);
	void maritimeTrade(MaritimeTradeParam param, int id);
	void robPlayer(RobPlayerParam param, int id);
	void finishTurn(FinishTurnParam param, int id);

	void buyDevCard(BuyDevCardParam param, int id);
	void playSoldierCard(PlaySoldierParam param, int id);
	void playYearOfPlentyCard(PlayYearOfPlentyParam param, int id);
	void playRoadCard(PlayRoadBuildingParam param, int id);
	void playMonopolyCard(PlayMonopolyParam param, int id);
	void playMonumentCard(PlayMonumentParam param, int id);

	void login(CredentialsParam param);
	void register(CredentialsParam param);
	void join(JoinGameParam param);
	void create(CreateGameParam param);
	void addAI(AddAiParam param, int id);
	
	void listGames();
	void listAI(int id);
	void getGameModel(int id);
	void save(int id);
	void load(int id);
	void resetGame(int id);
	void getCommands(int id);
	void commands(int id);
}
