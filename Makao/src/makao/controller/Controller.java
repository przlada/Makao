package makao.controller;

import java.awt.EventQueue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import makao.MakaoStatic;
import makao.exceptions.ToManyPlayersException;
import makao.model.MakaoCard;
import makao.model.MakaoPlayer;
import makao.model.Model;
import makao.model.ModelDummy;
import makao.view.View;
import makao.view.actions.MakaoActions;
import makao.view.actions.ServerActionContainer;
import makao.view.actions.ServerActionContainer.ServerActionType;

public class Controller extends Thread{
	//private final Model model =  null;
	private final View view;
	private final BlockingQueue<MakaoActions> actionQueue;
	private final Map<ServerActionContainer.ServerActionType, Strategy> strategyMap = new HashMap<ServerActionContainer.ServerActionType, Strategy>();
	private Server server;
	private Client client;
	private Model model;
	
	public Controller(View view, Model model, BlockingQueue<MakaoActions> actionQueue){
		this.view = view;
		this.actionQueue = actionQueue;
		this.model = model;
		server = new Server(MakaoStatic.PORT_NUMBER, this);
		client = new Client(MakaoStatic.PORT_NUMBER, this);
		makeStrategyMap();
	}
	public void run()
	{
		while (true)
		{
			System.out.println("controler");
			try {
				MakaoActions action = actionQueue.take();
				switch (action) {
				case OPEN_CONFIG_DIALOG:
					view.showConfigDialog();
					break;
				case CLOSE_CONFIG_DIALOG:
					view.closeConfigDialog();
					break;
				case START_LOCAL_SERVER:
					server.startServer();
					view.setServerStarted(true);
					view.haveToStartGame(true);
					break;
				case STOP_STOP_SERVER:
					server.stopServer();
					break;
				case SERVER_START_GAME:
					if(model.startGame())
						view.haveToStartGame(false);
					break;
				case CONNECT_CLIENT:
					client.setHost(view.getHostAddress());
					client.connect();
					view.setClientConnected(true);
					client.send(new ServerActionContainer(ServerActionType.SET_NICK, view.getPlayerNick()));
					break;
				case DISCONNECT_CLIENT:
					client.disconnect();
				case GAME_END_MY_TURN:
					client.send(new ServerActionContainer(ServerActionType.PLAYER_END_TURN, null));
					break;
				case GAME_SEND_MESSAGE_TO_ALL:
					ServerActionContainer msg = new ServerActionContainer(ServerActionType.SEND_TEXT_MESSAGE, view.getTextMessage());
					client.send(msg);
					break;
				case GAME_SELECT_CARD:
					client.send(new ServerActionContainer(ServerActionType.PLAYER_SELECT_CARD, view.getSelectedPlayerCard()));
					break;
				case GAME_TAKE_NEXT_CARD:
					client.send(new ServerActionContainer(ServerActionType.PLAYER_GET_NEXT_CARD, null));
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void passModelDummyToView(final ModelDummy dummy) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				view.drawModelDummy(dummy);
			}
		});
	}
	public void gameHaveEnded(){
		view.haveToStartGame(true);
	}
	public void passModelDummy(final ModelDummy dummy){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				server.sendMessageToClients(dummy);
			}
		});
	}
	public void passActionToModel(ServerActionContainer action){
		strategyMap.get(action.getType()).doStrategy(action);
	}

	public void makeStrategyMap(){
		strategyMap.put(ServerActionType.SEND_TEXT_MESSAGE, new Strategy(){
			public void doStrategy(ServerActionContainer action){
				model.doStrategy(action.getId(), (String)action.getData());
			}
		});
		strategyMap.put(ServerActionType.SET_NICK, new Strategy(){
			public void doStrategy(ServerActionContainer action){
				model.setPlayerNick(action.getId(), (String)action.getData());
			}
		});
		strategyMap.put(ServerActionType.PLAYER_SELECT_CARD, new Strategy(){
			public void doStrategy(ServerActionContainer action){
				model.playerSelectCard(action.getId(), (MakaoCard)action.getData());
			}
		});
		strategyMap.put(ServerActionType.PLAYER_GET_NEXT_CARD, new Strategy(){
			public void doStrategy(ServerActionContainer action){
				model.playerGetNextCard(action.getId());
			}
		});
		strategyMap.put(ServerActionType.PLAYER_END_TURN, new Strategy(){
			public void doStrategy(ServerActionContainer action){
				model.playerEndTurn(action.getId());
			}
		});
	}
	public void addPlayer(MakaoPlayer player) throws ToManyPlayersException{
		model.addPlayer(player);
	}
	private interface Strategy {
		public void doStrategy(ServerActionContainer action);
	}

}
