package makao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import makao.MakaoStatic;

public class MakaoPlayer implements Serializable {
	private int id;
	private String nick;
	private List<MakaoCard> hand = new ArrayList<MakaoCard>();

	public MakaoPlayer(int id) {
		this.id = id;
		nick = MakaoStatic.defoltPlayerName+(id+1);
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
