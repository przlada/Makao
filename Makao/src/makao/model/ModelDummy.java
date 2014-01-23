package makao.model;

import java.io.Serializable;

public class ModelDummy implements Serializable {
	
	private boolean isConfigured = false;
	private boolean exitAction = false;
	
	
	
	
	public boolean isConfigured() {
		return isConfigured;
	}
	public void setConfigured(boolean isConfigured) {
		this.isConfigured = isConfigured;
	}
	public boolean isExitAction() {
		return exitAction;
	}
	public void setExitAction(boolean exitAction) {
		this.exitAction = exitAction;
	}

}
