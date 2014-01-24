package makao.model;

import java.io.Serializable;

public class MakaoPlayer implements Serializable {
	private int id;
	private String nick;

	public MakaoPlayer(int id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
