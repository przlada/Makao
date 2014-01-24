package makao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelDummy implements Serializable {
	private List<TextMessage> tekstMessages;
	private List<MakaoPlayer> players;

	public List<MakaoPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<MakaoPlayer> players) {
		this.players = players;
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

}
