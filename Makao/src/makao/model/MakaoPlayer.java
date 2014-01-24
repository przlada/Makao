package makao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MakaoPlayer implements Serializable {
	private int id;
	private String nick;
	private List<MakaoCard> hand = new ArrayList<MakaoCard>();

	public MakaoPlayer(int id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	public List<MakaoCard> getHand(){
		return hand;
	}
}
