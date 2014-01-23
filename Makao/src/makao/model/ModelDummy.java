package makao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelDummy implements Serializable {
	private List<TextMessage> tekstMessages;

	public ModelDummy() {
		tekstMessages = new ArrayList<TextMessage>();
	}

	public List<TextMessage> getTekstMessages() {
		return tekstMessages;
	}

	public void addTekstMessages(TextMessage tekstMessage) {
		tekstMessages.add(tekstMessage);
	}

}
