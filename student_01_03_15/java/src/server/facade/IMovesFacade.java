package server.facade;

import shared.parameters.AcceptTradeParam;
import shared.parameters.BuildCityParam;
import shared.parameters.BuildRoadParam;
import shared.parameters.BuildSettlementParam;
import shared.parameters.BuyDevCardParam;
import shared.parameters.DiscardCardsParam;
import shared.parameters.FinishTurnParam;
import shared.parameters.MaritimeTradeParam;
import shared.parameters.OfferTradeParam;
import shared.parameters.PlayMonopolyParam;
import shared.parameters.PlayMonumentParam;
import shared.parameters.PlayRoadBuildingParam;
import shared.parameters.PlaySoldierParam;
import shared.parameters.PlayYearOfPlentyParam;
import shared.parameters.RobPlayerParam;
import shared.parameters.RollNumParam;
import shared.parameters.SendChatParam;
import shared.response.GameModelResponse;

public interface IMovesFacade
{
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Chat parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse sendChat(SendChatParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Trade parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse acceptTrade(AcceptTradeParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Discard parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse discardCards(DiscardCardsParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Roll parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse rollNumber(RollNumParam param, int id);

	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Build Road parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buildRoad(BuildRoadParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Build Settlement parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buildSettlement(BuildSettlementParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Build City parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buildCity(BuildCityParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Trade Offer parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse offerTrade(OfferTradeParam  param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Maritime Trade parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse maritimeTrade(MaritimeTradeParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Rob Player parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse robPlayer(RobPlayerParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Finish Turn parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse finishTurn(FinishTurnParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Buy Development Card parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse buyDevCard(BuyDevCardParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Soldier parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playSoldierCard(PlaySoldierParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Year of Plenty parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Road Card parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Monopoly parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id);
	
	/**
	 * This method creates a command object for the command
	 * then validates the command through the ModelFacade
	 * then calls the execute method on the command object
	 * @param param Play Monument parameters for the command
	 * @param id the id of the game
	 */
	GameModelResponse playMonumentCard(PlayMonumentParam param, int id);
}
