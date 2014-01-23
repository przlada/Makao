package makao.model;

import java.io.Serializable;

public class ModelDummy implements Serializable {
	private TextMessage textMessage= null;
	private String message = null;
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	
}
