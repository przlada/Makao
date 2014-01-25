package makao.model;

import java.io.Serializable;

public class TextMessage implements Serializable {
	public enum Type {
		CHAT_MESSAGE, SERVER_MESSAGE, GAME_MESSAGE;
	}

	private TextMessage.Type type;
	private String author;
	private String data;

	public TextMessage(TextMessage.Type type, String author, String data) {
		this.type = type;
		this.author = author;
		this.data = data;
	}

	public String toString() {
		if (type == Type.CHAT_MESSAGE)
			return author + ":\n" + data;
		else if (type == Type.SERVER_MESSAGE)
			return "SERWER:\n" + data +" "+author;
		else if	(type == Type.GAME_MESSAGE){
			return author+":\n"+data;
		}
		return data;
	}
	public static TextMessage getServerMessage(String author, String data){
		return new TextMessage(Type.SERVER_MESSAGE, author, data);
	}
	public static TextMessage getGameMessage(String author, String data){
		return new TextMessage(Type.GAME_MESSAGE, author, data);
	}
}