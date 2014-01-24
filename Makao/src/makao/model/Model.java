package makao.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import makao.MakaoStatic;
import makao.controller.Controller;
import makao.exceptions.ToManyPlayersException;
import makao.model.TextMessage.Type;


public class Model {
	private Controller controller;
	private List<MakaoPlayer> players = new  ArrayList<MakaoPlayer>();
	private List<TextMessage> messages = new ArrayList<TextMessage>();
	private MakaoCard lastPlayed = null;
	
	private List<MakaoCard> deck;
	private List<MakaoCard> graveyard;
	private int whoseTurn = 0; 
	private boolean gameStarted = false;
	public Model(){
		
	}
	private boolean isThatCardGood(int playerId, MakaoCard card){
		if(card.getColor() == lastPlayed.getColor() || card.getNumber() == lastPlayed.getNumber())
			return true;
		addMessage(TextMessage.getServerMessage(players.get(playerId).getNick(), "Nie moýna do¸oýy tej karty"));
		return false;
	}
	public void setController(Controller controller){
		this.controller = controller;
	}
	private int getNextPlayer(){
		if(whoseTurn >= players.size()-1)
			return 0;
		return (whoseTurn+1);
	}
	private boolean isPlayerTurn(int playerId){
		if(whoseTurn == playerId)
			return true;
		addMessage(TextMessage.getServerMessage(players.get(playerId).getNick(), "Teraz nie kolej gracza"));
		return false;
	}
	private boolean isEndOfGame(int playerId){
		if(players.get(playerId).getHand().size() == 0){
			endTheGame(playerId);
			return true;
		}
		else
			return false;
	}
	private void endTheGame(int winnerId){
		gameStarted = false;
		addMessage(TextMessage.getServerMessage(players.get(winnerId).getNick(), "KONICE GRY\nWYGRAü GRACZ"));
		controller.passModelDummy(getDummy());
		controller.gameHaveEnded();
	}
	public boolean startGame(){
		if(players.size() < 1){
			addMessage(MakaoStatic.notEnoughPlayers);
			controller.passModelDummy(getDummy());
			return false;
		}
		whoseTurn = 0;
		gameStarted = true;
		lastPlayed = null;
		deck = new  ArrayList<MakaoCard>();
		graveyard = new  ArrayList<MakaoCard>();
		
		for(int i=0; i<MakaoStatic.CARD_NUMBERS; i++)
			for(int j=0; j<MakaoStatic.CARD_COLORS; j++)
				deck.add(new MakaoCard(i,j));
		Collections.shuffle(deck);
		for(MakaoPlayer player : players)
			for(int i=0; i<MakaoStatic.CARD_HAND_START; i++)
				player.getHand().add(deck.remove(0));
		lastPlayed = deck.remove(0);
		controller.passModelDummy(getDummy());
		return true;
	}
	private void addMessage(TextMessage msg){
		messages.add(msg);
	}
	public void addPlayer(MakaoPlayer player) throws ToManyPlayersException{
		if(players.size() < MakaoStatic.MAX_PLAYERS){
			players.add(player);
			addMessage(TextMessage.getServerMessage(player.getNick(), MakaoStatic.newUserMsg));
			controller.passModelDummy(getDummy());
		}
		else
			throw new ToManyPlayersException();
	}
	public void doStrategy(int playerId, String message){
		TextMessage msg = new TextMessage(Type.CHAT_MESSAGE, players.get(playerId).getNick(), message);
		messages.add(msg);
		controller.passModelDummy(getDummy());
	}
	public void playerSelectCard(int playerId, MakaoCard card){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId) && isThatCardGood(playerId, card)) {
			List<MakaoCard> hand = players.get(playerId).getHand();
			for (int i = 0; i < hand.size(); i++)
				if (hand.get(i).equals(card)) {
					lastPlayed = hand.remove(i);
					graveyard.add(lastPlayed);
					controller.passModelDummy(getDummy());
					break;
				}
			isEndOfGame(playerId);
		} else
			controller.passModelDummy(getDummy());
	}
	public void playerGetNextCard(int playerId){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId)) {
			List<MakaoCard> hand = players.get(playerId).getHand();
			if(deck.size() == 0){
				System.out.println(graveyard.size()+"skonczyly si« tasowanie");
				Collections.shuffle(graveyard);
				deck = graveyard;
				graveyard = new  ArrayList<MakaoCard>();
			}
			if(deck.size() > 0)
				hand.add(deck.remove(0));
		}
		controller.passModelDummy(getDummy());
	}
	public void setPlayerNick(int id, String nick){
		if (nick != null && !nick.isEmpty()) {
			try {
				players.get(id).setNick(nick);
				controller.passModelDummy(getDummy());
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	public void playerEndTurn(int playerId){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId)) {
			addMessage(TextMessage.getServerMessage(players.get(getNextPlayer()).getNick(), MakaoStatic.nextRound));
			whoseTurn = getNextPlayer();
		}
		controller.passModelDummy(getDummy());
	}
	private ModelDummy getDummy(){
		ModelDummy dummy = new ModelDummy();
		dummy.setTekstMessages(messages);
		dummy.setPlayers(players);
		dummy.setLastPlayed(lastPlayed);
		messages = new ArrayList<TextMessage>();
		return dummy;
	}

}
