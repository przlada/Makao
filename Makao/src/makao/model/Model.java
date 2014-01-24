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
	public void setController(Controller controller){
		this.controller = controller;
	}
	private int getNextPlayer(){
		if(whoseTurn >= players.size()-1)
			return 0;
		return (whoseTurn+1);
	}
	public boolean startGame(){
		if(players.size() < 1){
			addMessage(MakaoStatic.notEnoughPlayers);
			controller.passModelDummy(getDummy());
			return false;
		}
		gameStarted = true;
		deck = new  ArrayList<MakaoCard>();
		graveyard = new  ArrayList<MakaoCard>();
		
		for(int i=0; i<MakaoStatic.CARD_NUMBERS; i++)
			for(int j=0; j<MakaoStatic.CARD_COLORS; j++)
				deck.add(new MakaoCard(i,j));
		Collections.shuffle(deck);
		for(MakaoPlayer player : players)
			for(int i=0; i<MakaoStatic.CARD_HAND_START; i++)
				player.getHand().add(deck.remove(0));
		controller.passModelDummy(getDummy());
		return true;
	}
	private void addMessage(TextMessage msg){
		messages.add(msg);
	}
	public void addPlayer(MakaoPlayer player) throws ToManyPlayersException{
		if(players.size() < MakaoStatic.MAX_PLAYERS)
			players.add(player);
		else
			throw new ToManyPlayersException();
	}
	public void doStrategy(int playerId, String message){
		TextMessage msg = new TextMessage(Type.CHAT_MESSAGE, players.get(playerId).getNick(), message);
		messages.add(msg);
		controller.passModelDummy(getDummy());
	}
	public void playerSelectCard(int playerId, MakaoCard card){
		List<MakaoCard> hand = players.get(playerId).getHand();
		for(int i=0; i<hand.size(); i++)
			if(hand.get(i).equals(card)){
				lastPlayed = hand.remove(i);
				controller.passModelDummy(getDummy());
				return;
			}
	}
	public void playerGetNextCard(int playerId){
		List<MakaoCard> hand = players.get(playerId).getHand();
		hand.add(new MakaoCard(0,1));
		controller.passModelDummy(getDummy());
	}
	public void setPlayerNick(int id, String nick){
		try{
			players.get(id).setNick(nick);
			controller.passModelDummy(getDummy());
		}catch(IndexOutOfBoundsException e){}
	}
	public void playerEndTurn(int playerId){
		
		addMessage(TextMessage.getServerMessage(players.get(getNextPlayer()).getNick(),MakaoStatic.nextRound));
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
