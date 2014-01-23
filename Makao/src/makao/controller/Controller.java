package makao.controller;

import java.util.concurrent.BlockingQueue;

import makao.MakaoStatic;
import makao.view.View;
import makao.view.actions.MakaoActions;

public class Controller extends Thread{
	//private final Model model =  null;
	private final View view;
	private final BlockingQueue<MakaoActions> actionQueue;
	private Server server;
	private Client client;
	//Server server = new Server(3456);
	//server.startServer();
	public Controller(View view, BlockingQueue<MakaoActions> actionQueue){
		this.view = view;
		this.actionQueue = actionQueue;
		server = new Server(MakaoStatic.PORT_NUMBER, this);
		client = new Client(MakaoStatic.PORT_NUMBER);
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
					client.send("DZIALA");
					break;
				case DISCONNECT_CLIENT:
					client.disconnect();
				case GAME_END_MY_TURN:
					for(int i=0; i<100; i++)
						view.addTextMessage("Dziala "+i);
					break;
				case GAME_SEND_MESSAGE_TO_ALL:
					client.send(view.getTextMessage());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("B¸ˆd");
			}
		}
	}
	public void appendMessage(String message){
		view.addTextMessage(message);
	}
	
}
