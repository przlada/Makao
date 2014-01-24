package makao.exceptions;

import java.io.IOException;

public abstract class GameException extends IOException {
	public GameException() {
		super();
	}

	public GameException(String message) {
		super(message);
	}
}