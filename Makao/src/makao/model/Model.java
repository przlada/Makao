package makao.model;

import java.util.ArrayList;
import java.util.List;

import makao.MakaoStatic;
import makao.controller.Controller;
import makao.exceptions.ToManyPlayersException;
import makao.model.TextMessage.Type;


public class Model {
	private Controller controller;
	private List<MakaoPlayer> players = new  ArrayList<MakaoPlayer>();
	private List<TextMessage> messages = new ArrayList<TextMessage>();
	public Model(){
		
	}
	public void setController(Controller controller){
		this.controller = controller;
	}
	public void addPlayer(MakaoPlayer player) throws ToManyPlayersException{
		System.out.println(players.size()+"");
		if(players.size() < MakaoStatic.MAX_PLAYERS)
			players.add(player);
		else
			throw new ToManyPlayersException();
	}
	public void doStrategy(String message){
		System.out.print(message);
		messages = new ArrayList<TextMessage>();
		TextMessage msg = new TextMessage(Type.CHAT_MESSAGE, "autor", message);
		messages.add(msg);
		controller.passModelDummy(getDummy());
	}
	public void playerSelectCard(int playerId, MakaoCard card){
		List<MakaoCard> hand = players.get(playerId).getHand();
		for(int i=0; i<hand.size(); i++)
			if(hand.get(i).equals(card)){
				hand.remove(i);
				controller.passModelDummy(getDummy());
				return;
			}
	}
	public void setPlayerNick(int id, String nick){
		try{
			players.get(id).setNick(nick);
			controller.passModelDummy(getDummy());
		}catch(IndexOutOfBoundsException e){}
	}
	private ModelDummy getDummy(){
		ModelDummy dummy = new ModelDummy();
		dummy.setTekstMessages(messages);
		dummy.setPlayers(players);
		return dummy;
	}

}
