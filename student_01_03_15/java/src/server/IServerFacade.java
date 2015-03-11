package server;

import shared.parameters.*;

public interface IServerFacade
{
	void sendChat(SendChatParam param);
	void acceptTrade(AcceptTradeParam param);
	void discardCards(DiscardCardsParam param);
	void rollNumber(RollNumParam param);

	void buildRoad(BuildRoadParam param);
	void buildSettlement(BuildSettlementParam param);
	void buildCity(BuildCityParam param);
	void offerTrade(OfferTradeParam  param);
	void maritimeTrade(MaritimeTradeParam param);
	void robPlayer(RobPlayerParam param);
	void finishTurn(FinishTurnParam param);

	void buyDevCard(BuyDevCardParam param);
	void playSoldierCard(PlaySoldierParam param);
	void playYearOfPlentyCard(PlayYearOfPlentyParam param);
	void playRoadCard(PlayRoadBuildingParam param);
	void playMonopolyCard(PlayMonopolyParam param);
	void playMonumentCard(PlayMonumentParam param);

	void login(CredentialsParam param);
	void register(CredentialsParam param);
	void join(JoinGameParam param);
	void create(CreateGameParam param);
	void addAI(AddAiParam param);
	
	void listGames();
	void listAI();
	void getGameModel();
	void save();
	void load();
	void resetGame();
	void getCommands();
	void commands();
}
