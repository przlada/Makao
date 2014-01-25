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
	private MakaoCard playedBefore = null;
	private MakaoCard firstPlayed = null;
	
	private List<MakaoCard> deck;
	private List<MakaoCard> graveyard;
	private int whoseTurn = 0; 
	private int cardToTake = 0;
	private int roundsToStay = 0;
	private boolean cardTaken = false;
	private boolean gameStarted = false;
	public Model(){
		
	}
	private boolean isThatCardGood(int playerId, MakaoCard card){
		if(cardTaken){
			addMessage(TextMessage.getServerMessage(players.get(playerId).getNick(), "Nie moına do¸oıy po pobraniu karty"));
			return false;
		}
		if(firstPlayed == null){
			if(lastPlayed.getNumber() == 11) return true;
			if(lastPlayed.getNumber() == 1 && card.getNumber() == 1) return true;
			if(lastPlayed.getNumber() == 1 && card.getNumber() == 2 && card.getColor()==lastPlayed.getColor()) return true;
			if(lastPlayed.getNumber() == 1 && cardToTake > 0) return false;
			if(lastPlayed.getNumber() == 2 && card.getNumber() == 2) return true;
			if(lastPlayed.getNumber() == 2 && card.getNumber() == 1 && card.getColor()==lastPlayed.getColor()) return true;
			if(lastPlayed.getNumber() == 2 && cardToTake > 0) return false;
			if(lastPlayed.getNumber() == 3 && (card.getNumber() != 3 && roundsToStay > 0)) return false;
			if(lastPlayed.getNumber() == 12 && lastPlayed.getColor() == 1 && card.getNumber() == 12 &&  card.getColor() == 3) return true;
			if(lastPlayed.getNumber() == 12 && lastPlayed.getColor() == 3 && card.getNumber() == 12 &&  card.getColor() == 1) return true;
			if(lastPlayed.getNumber() == 12 && (lastPlayed.getColor() == 3 || lastPlayed.getColor() == 1) && cardToTake > 0) return false;
			if(card.getNumber() == 11) return true;
			if(card.getColor() == lastPlayed.getColor() || card.getNumber() == lastPlayed.getNumber()) return true;
			return false;
		} else{
			if(card.getNumber() == firstPlayed.getNumber())
				return true;
		}
		addMessage(TextMessage.getServerMessage(players.get(playerId).getNick(), "Nie moına do¸oıy tej karty"));
		return false;
	}
	private void addCardConsequences(MakaoCard card){
		int initCardToTake = cardToTake;
		int initRoundsToStay = roundsToStay;
		if(card.getNumber() == 1)
			cardToTake+=2;
		else if(card.getNumber() == 2)
			cardToTake+=3;
		else if(card.getNumber() == 3)
			roundsToStay++;
		else if(card.getNumber() == 12 && (card.getColor() == 1 || card.getColor() == 3))
			cardToTake+=5;
		if(initCardToTake < cardToTake){
			addMessage(TextMessage.getServerMessage("", "Nast«pny gracz pobiera "+cardToTake));
		}
		if(initRoundsToStay < roundsToStay)
			addMessage(TextMessage.getServerMessage("", "Nast«pny gracz czeka "+roundsToStay+" kolejek"));
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
		cardToTake = 0;
		roundsToStay = 0;
		firstPlayed = null;
		lastPlayed = null;
		cardTaken = false;
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
		playedBefore = lastPlayed;
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
					addCardConsequences(lastPlayed);
					graveyard.add(lastPlayed);
					if(firstPlayed == null)
						firstPlayed = lastPlayed;
					controller.passModelDummy(getDummy());
					break;
				}
			isEndOfGame(playerId);
		}
		controller.passModelDummy(getDummy());
	}
	private String getPlayerNick(int playerId){
		return players.get(getNextPlayer()).getNick();
	}
	public void playerGetNextCard(int playerId){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId)) {
			if(firstPlayed != null){
				addMessage(TextMessage.getServerMessage(getPlayerNick(playerId), "Karta juz wy¸oıona"));
			}
			else if(firstPlayed == null && roundsToStay > 0){
				addMessage(TextMessage.getServerMessage(getPlayerNick(playerId), "Nie pobierasz. ZakoÄcz kolejk« aby czeka "+roundsToStay+" rund"));
			}
			else if(cardTaken && cardToTake == 0){
				addMessage(TextMessage.getServerMessage(getPlayerNick(playerId), "Karta juz pobrana"));
			} else {
				List<MakaoCard> hand = players.get(playerId).getHand();
				if (deck.size() == 0) {
					System.out.println(graveyard.size()
							+ "skonczyly si« tasowanie");
					Collections.shuffle(graveyard);
					deck = graveyard;
					graveyard = new ArrayList<MakaoCard>();
				}
				if (deck.size() > 0)
					hand.add(deck.remove(0));
				cardTaken = true;
				if(cardToTake > 0)
					cardToTake--;
			}
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
	private void goToNextTurn(){
		addMessage(TextMessage.getServerMessage(players.get(getNextPlayer()).getNick(),MakaoStatic.nextRound));
		while(players.get(getNextPlayer()).isFreez()){
			whoseTurn = getNextPlayer();
			addMessage(TextMessage.getServerMessage(getPlayerNick(whoseTurn), "Zablokowany uıytkownik"));
			players.get(whoseTurn).lessFreez();
		}
		whoseTurn = getNextPlayer();
		firstPlayed = null;
		cardTaken = false;
		playedBefore = lastPlayed;
	}
	public void playerEndTurn(int playerId){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId)) {
			if(firstPlayed == null && roundsToStay > 0){
				players.get(playerId).setFreezRounds(roundsToStay);
				roundsToStay = 0;
				goToNextTurn();
			}
			else if( firstPlayed == null && (!cardTaken || cardToTake > 0))
				addMessage(TextMessage.getServerMessage(getPlayerNick(getNextPlayer()), "ûadna karta nie wystawiona albo nie wszystkie pobrane"));
			else {
				goToNextTurn();
			}
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
