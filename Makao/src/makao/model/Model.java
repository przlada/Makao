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
	private boolean seadMakao = false;
	private int cardToTake = 0;
	private int makaoCardToTake = 0;
	private int roundsToStay = 0;
	private MakaoCard requestedNumber = null;
	private MakaoCard requestedColor = null;
	private boolean cardTaken = false;
	private boolean gameStarted = false;
	public Model(){
		
	}
	private boolean isThatCardGood(int playerId, MakaoCard card){
		if(cardTaken){
			addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.cantGetAfterChoose));
			return false;
		}
		if(firstPlayed == null){
			if(lastPlayed.getNumber() == MakaoCard.CARD_QUEEN) return true;
			if(lastPlayed.getNumber() == 1 && card.getNumber() == 1) return true;
			if(lastPlayed.getNumber() == 1 && card.getNumber() == 2 && card.getColor()==lastPlayed.getColor()) return true;
			if(lastPlayed.getNumber() == 1 && cardToTake > 0) return false;
			if(lastPlayed.getNumber() == 2 && card.getNumber() == 2) return true;
			if(lastPlayed.getNumber() == 2 && card.getNumber() == 1 && card.getColor()==lastPlayed.getColor()) return true;
			if(lastPlayed.getNumber() == 2 && cardToTake > 0) return false;
			if(lastPlayed.getNumber() == 3 && (card.getNumber() != 3 && roundsToStay > 0)) return false;
			if(lastPlayed.getNumber() == MakaoCard.CARD_KING && lastPlayed.getColor() == MakaoCard.COLOR_SPADES && card.getNumber() == MakaoCard.CARD_KING &&  card.getColor() == MakaoCard.COLOR_HEARTS) return true;
			if(lastPlayed.getNumber() == MakaoCard.CARD_KING && lastPlayed.getColor() == MakaoCard.COLOR_HEARTS && card.getNumber() == MakaoCard.CARD_KING &&  card.getColor() == MakaoCard.COLOR_SPADES) return true;
			if(lastPlayed.getNumber() == MakaoCard.CARD_KING && (lastPlayed.getColor() == MakaoCard.COLOR_HEARTS || lastPlayed.getColor() == MakaoCard.COLOR_SPADES) && cardToTake > 0) return false;
			if(card.getNumber() == MakaoCard.CARD_QUEEN) return true;
			if(card.getColor() == lastPlayed.getColor() || card.getNumber() == lastPlayed.getNumber()) return true;
			return false;
		} else{
			if(card.getNumber() == firstPlayed.getNumber())
				return true;
		}
		addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.notGoodCard));
		return false;
	}
	private void addCardConsequences(int playerId, MakaoCard card){
		int initCardToTake = cardToTake;
		int initRoundsToStay = roundsToStay;
		if(card.getNumber() == MakaoCard.CARD_NO_2)
			cardToTake+=2;
		else if(card.getNumber() == MakaoCard.CARD_NO_3)
			cardToTake+=3;
		else if(card.getNumber() == MakaoCard.CARD_NO_4)
			roundsToStay++;
		else if(card.getNumber() == MakaoCard.CARD_KING && (card.getColor() == MakaoCard.COLOR_HEARTS || card.getColor() == MakaoCard.COLOR_SPADES))
			cardToTake+=5;
		if(initCardToTake < cardToTake){
			addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.nextPlayerGet+" "+cardToTake));
		}
		if(initRoundsToStay < roundsToStay)
			addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.nextPlayerWait+" "+roundsToStay+" kolejek"));
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
		addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.notYourTurn));
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
		addMessage(TextMessage.getGameMessage(getPlayerNick(winnerId), MakaoStatic.endOfTheGameYouWin));
		controller.passModelDummy(getDummy());
		controller.gameHaveEnded();
	}
	public boolean startGame(){
		if(players.size() < MakaoStatic.MIN_PLAYERS){
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
		makaoCardToTake = 0;
		cardTaken = false;
		requestedNumber = null;
		seadMakao = false;
		deck = new  ArrayList<MakaoCard>();
		graveyard = new  ArrayList<MakaoCard>();
		
		for(int i=0; i<MakaoStatic.CARD_NUMBERS; i++)
			for(int j=0; j<MakaoStatic.CARD_COLORS; j++)
				deck.add(new MakaoCard(i,j));
		Collections.shuffle(deck);
		for(MakaoPlayer player : players){
			player.resteHand();
			for(int i=0; i<MakaoStatic.CARD_HAND_START; i++)
				player.getHand().add(deck.remove(0));
		}
		lastPlayed = deck.remove(0);
		while(lastPlayed.isFightingCard()){
			graveyard.add(lastPlayed);
			lastPlayed = deck.remove(0);
		}
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
	public void playerRequireColor(int playerId, MakaoCard card){
		MakaoCard nc = new MakaoCard(MakaoCard.CARD_ACE, card.getColor());
		if (!gameStarted) return;
		if (isPlayerTurn(playerId) && isThatCardGood(playerId, nc)){
			addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.requestColor+MakaoCard.colorToString(card.getNumber())));
			requestedColor = card;
			removeCardFromPlayer(playerId, nc);
			isEndOfGame(playerId);
		}
	}
	public void playerRequireNumber(int playerId, MakaoCard card){
		MakaoCard nc = new MakaoCard(10, card.getColor());
		if (!gameStarted) return;
		if (isPlayerTurn(playerId) && isThatCardGood(playerId, nc)){
			if(card.getNumber() != 10){
				addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.requestNumber+(card.getNumber()+1)+"-ek"));
				requestedNumber = card;
			}
			else
				addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.noRequest));
			removeCardFromPlayer(playerId, nc);
			isEndOfGame(playerId);
		}
	}
	private void removeCardFromPlayer(int playerId, MakaoCard card){
		List<MakaoCard> hand = players.get(playerId).getHand();
		for (int i = 0; i < hand.size(); i++)
			if (hand.get(i).equals(card)) {
				lastPlayed = hand.remove(i);
				addCardConsequences(playerId, lastPlayed);
				graveyard.add(lastPlayed);
				if (firstPlayed == null)
					firstPlayed = lastPlayed;
				controller.passModelDummy(getDummy());
				break;
			}
	}

	public void playerSelectCard(int playerId, MakaoCard card){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId)){
			if(firstPlayed == null && requestedNumber != null){
				if(card.getNumber() != requestedNumber.getNumber()){
					addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.notThatNumber));
					controller.passModelDummy(getDummy());
				}
				else{
					requestedNumber = null;
					removeCardFromPlayer(playerId, card);
					isEndOfGame(playerId);
				}
			}
			else if(firstPlayed == null && requestedColor != null){
				if(card.getColor() != requestedColor.getNumber() ){
					addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.notThatColor));
					controller.passModelDummy(getDummy());
				}
				else{
					requestedColor = null;
					removeCardFromPlayer(playerId, card);
					isEndOfGame(playerId);
				}
			}
			else if(isThatCardGood(playerId, card)) {
				removeCardFromPlayer(playerId, card);
				isEndOfGame(playerId);
			}
			controller.passModelDummy(getDummy());
		}
	}
	private String getPlayerNick(int playerId){
		return players.get(playerId).getNick();
	}
	private MakaoCard getCardFromDeck(){
		if (deck.size() == 0) {
			Collections.shuffle(graveyard);
			deck = graveyard;
			graveyard = new ArrayList<MakaoCard>();
		}
		if (deck.size() > 0)
			return deck.remove(0);
		return null;
	}
	public void playerGetNextCard(int playerId){
		if (!gameStarted) return;
		List<MakaoCard> hand = players.get(playerId).getHand();
		if (isPlayerTurn(playerId)) {
			if(makaoCardToTake > 0){
				hand.add(getCardFromDeck());
				makaoCardToTake--;
			}
			else if(firstPlayed != null){
				addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.cardAlredyServerd));
			}
			else if(firstPlayed == null && roundsToStay > 0){
				addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.endToWait+roundsToStay+" rund"));
			}
			else if(cardTaken && cardToTake == 0){
				addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.cardAlreadyGet));
			} else {
				hand.add(getCardFromDeck());
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
		controller.passModelDummy(getDummy());
	}
	private boolean czyMakao(int playerId){
		if(players.get(playerId).getHand().size() == 1 && !seadMakao){
			addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.rememberToMakao));
			makaoCardToTake+=5;
			seadMakao = true;
			controller.passModelDummy(getDummy());
			return true;
		}
		else if(makaoCardToTake > 0){
			addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.moreCardToGet+makaoCardToTake+" kart"));
			return true;
		}
		return false;
	}
	private void goToNextTurn(int playerId){
		while(players.get(getNextPlayer()).isFreez()){
			whoseTurn = getNextPlayer();
			addMessage(TextMessage.getGameMessage(getPlayerNick(whoseTurn), MakaoStatic.userBlocked));
			players.get(whoseTurn).lessFreez();
		}
		whoseTurn = getNextPlayer();
		addMessage(TextMessage.getGameMessage(getPlayerNick(whoseTurn), MakaoStatic.nextRound));
		firstPlayed = null;
		cardTaken = false;
		seadMakao = false;
		playedBefore = lastPlayed;
	}
	public void playerSayMakao(int playerId){
		addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.sayMakao));
		seadMakao = true;
		controller.passModelDummy(getDummy());
	}
	public void playerEndTurn(int playerId){
		if (!gameStarted) return;
		if (isPlayerTurn(playerId)) {
			if(firstPlayed == null && roundsToStay > 0){
				players.get(playerId).setFreezRounds(roundsToStay);
				players.get(playerId).lessFreez();
				roundsToStay = 0;
				goToNextTurn(playerId);
			}
			else if( firstPlayed == null && (!cardTaken || cardToTake > 0))
				addMessage(TextMessage.getGameMessage(getPlayerNick(playerId), MakaoStatic.enyCardOutOrMoreToGet));
			else {
				if(!czyMakao(playerId))
					goToNextTurn(playerId);
			}
		}
		controller.passModelDummy(getDummy());
	}
	private ModelDummy getDummy(){
		ModelDummy dummy = new ModelDummy();
		dummy.setTekstMessages(messages);
		dummy.setPlayers(players);
		dummy.setLastPlayed(lastPlayed);
		dummy.setWhooseTurn(whoseTurn);
		messages = new ArrayList<TextMessage>();
		return dummy;
	}

}
