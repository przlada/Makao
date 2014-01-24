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
		for(int i=0; i<13; i++)
			hand.add(new MakaoCard(i,2));
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
