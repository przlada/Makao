package makao.view.actions;

import java.io.Serializable;

final public class ServerActionContainer implements Serializable{
	public enum ServerActionType{
		SET_NICK,
		SEND_TEXT_MESSAGE,
		PLAYER_SELECT_CARD,
		PLAYER_GET_NEXT_CARD,
		PLAYER_END_TURN,
		PLAYER_SAY_MAKAO;
	}
	private final Object data;
	private final ServerActionType type;
	private int id = 0;
	public ServerActionContainer(ServerActionType type, Object data){
		this.type = type;
		this.data = data;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public ServerActionType getType(){
		return type;
	}
	public Object getData(){
		return data;
	}
}
