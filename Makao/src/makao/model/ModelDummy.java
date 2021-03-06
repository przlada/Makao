package makao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelDummy implements Serializable {
	private List<TextMessage> tekstMessages;
	private List<MakaoPlayer> players;
	private int playerId;
	private int whooseTurn;
	private MakaoCard lastPlayed = null;
	private boolean gameEnded = false;
	
	public static ModelDummy getEmpty(){
		ModelDummy empty = new ModelDummy();
		empty.setTekstMessages(new ArrayList<TextMessage>());
		empty.setPlayers(new ArrayList<MakaoPlayer>());
		return empty;
	}
	public MakaoCard getLastPlayed() {
		return lastPlayed;
	}
	
	public boolean isGameEnded(){
		return gameEnded;
	}

	public void setLastPlayed(MakaoCard card) {
		lastPlayed = card;
	}

	public List<MakaoPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<MakaoPlayer> players) {
		this.players = players;
	}
	
	public void setPlayerId(int id){
		playerId = id;
	}
	public int getMyId(){
		return playerId;
	}

	public ModelDummy() {
		tekstMessages = new ArrayList<TextMessage>();
	}

	public List<TextMessage> getTekstMessages() {
		return tekstMessages;
	}

	public void addTekstMessages(TextMessage tekstMessage) {
		tekstMessages.add(tekstMessage);
	}
	public void setTekstMessages(List<TextMessage> messages){
		this.tekstMessages = messages;
	}

	public int getWhooseTurn() {
		return whooseTurn;
	}

	public void setWhooseTurn(int whooseTurn) {
		this.whooseTurn = whooseTurn;
	}

}
