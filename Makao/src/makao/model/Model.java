package makao.model;

import makao.controller.Controller;

public class Model {
	private Controller controller;
	public Model(){
		
	}
	public void setController(Controller controller){
		this.controller = controller;
	}
	public void doStrategy(String message){
		System.out.print(message);
		ModelDummy dummy = new ModelDummy();
		dummy.setMessage(message);
		controller.passModelDummy(dummy);
	}
}
