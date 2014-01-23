package makao.model;

import makao.controller.Controller;
import makao.model.TextMessage.Type;

public class Model {
	private Controller controller;
	public Model(){
		
	}
	public void setController(Controller controller){
		this.controller = controller;
	}
	public void doStrategy(String message){
		System.out.print(message);
		TextMessage msg = new TextMessage(Type.CHAT_MESSAGE, "autor", message);
		ModelDummy dummy = new ModelDummy();
		dummy.addTekstMessages(msg);
		controller.passModelDummy(dummy);
	}
}
