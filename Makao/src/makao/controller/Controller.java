package makao.controller;

import java.awt.EventQueue;
import java.util.concurrent.BlockingQueue;

import makao.MakaoStatic;
import makao.model.Model;
import makao.model.ModelDummy;
import makao.view.View;
import makao.view.actions.MakaoActions;
import makao.view.actions.ServerActionContainer;

public class Controller extends Thread{
	//private final Model model =  null;
	private final View view;
	private final BlockingQueue<MakaoActions> actionQueue;
	//private final Map<ServerActionContainer.ServerActionType, ServerActionContainer> map = new HashMap<, >();
	private Server server;
	private Client client;
	private Model model;
	
	public Controller(View view, Model model, BlockingQueue<MakaoActions> actionQueue){
		this.view = view;
		this.actionQueue = actionQueue;
		this.model = model;
		server = new Server(MakaoStatic.PORT_NUMBER, this);
		client = new Client(MakaoStatic.PORT_NUMBER, this);
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
					break;
				case STOP_STOP_SERVER:
					server.stopServer();
					break;
				case CONNECT_CLIENT:
					client.setHost(view.getHostAddress());
					client.connect();
					//client.send("DZIALA");
					break;
				case DISCONNECT_CLIENT:
					client.disconnect();
				case GAME_END_MY_TURN:
					for(int i=0; i<100; i++)
						view.addTextMessage("Dziala "+i);
					break;
				case GAME_SEND_MESSAGE_TO_ALL:
					ServerActionContainer msg = new ServerActionContainer(ServerActionContainer.ServerActionType.SEND_TEXT_MESSAGE, view.getTextMessage());
					client.send(msg);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void passModelDummyToView(final ModelDummy dummy){
			EventQueue.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					view.drawModelDummy(dummy);
				}

			});
	}
	public void passModelDummy(ModelDummy dummy){
		server.sendMessageToClients(dummy);
	}
	public void passActionToModel(ServerActionContainer action){
		if(action.getType() == ServerActionContainer.ServerActionType.SEND_TEXT_MESSAGE)
		model.doStrategy(action.getId()+": "+(String)action.getData());
	}
	
}
