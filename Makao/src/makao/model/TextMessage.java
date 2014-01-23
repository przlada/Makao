package makao.model;

import java.io.Serializable;

public class TextMessage implements Serializable {
	public enum Type {
		CHAT_MESSAGE, SERVER_MESSAGE;
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
			return author + ": " + data;
		else if (type == Type.SERVER_MESSAGE)
			return "SERWER: " + data;
		return data;
	}
}