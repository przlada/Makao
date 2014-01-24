package makao.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import makao.exceptions.ToManyPlayersException;
import makao.model.MakaoPlayer;
import makao.model.ModelDummy;
import makao.view.actions.ServerActionContainer;

public class Server {
	private int nextId = 0;
	private BlockingQueue<ServerActionContainer> messages = new LinkedBlockingQueue<>();
	private final Map<Integer, ClientConnection> clients = new HashMap<>();
	private final Controller controller;
	private ServerSocket serverSocket;
	private final int port;
	private boolean started = false;
	
	public Server(int port, Controller controller){
		this.port = port;
		this.controller = controller;
	}
	/** Uruchamia serwer na wybranym wczesniej porcie */
	public void startServer(){
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {}
		started = true;
		new Thread() { // odbieranie informacji od klient—w
			public void run() {
				while (true) {
					try {
						ServerActionContainer action = messages.take();
						//System.out.println(message);
						controller.passActionToModel(action);
					} catch (InterruptedException ex) {

					}
				}
			}
		}.start();
		new Thread() { // nowe po¸ˆczenia
			public void run() {
				try {
					while (started) {
						ClientConnection newClient = new ClientConnection(serverSocket.accept(), nextId);
						newClient.connect();
						try{
							MakaoPlayer player = new MakaoPlayer(nextId);
							controller.addPlayer(player);
							++nextId;
						}catch(ToManyPlayersException e){
							newClient.disconnect();
						}
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();
		
	}
	public void stopServer(){
		started = false;
		if (serverSocket == null) {
			return;
		}
		messages.clear();
		try {
			serverSocket.close();
		} catch (IOException e) {}
		serverSocket = null;
		for (ClientConnection client : clients.values()) {
			client.disconnect();
		}
	}
	private void addNewClientConnection(ClientConnection newClient){
		synchronized (clients) {
			clients.put(newClient.id, newClient);
		}
	}
	public void sendMessageToClients(String message){
		synchronized (clients) {
			for (ClientConnection client : clients.values()){
				System.out.println("KLIENT "+client.id);
				client.sendMessage(message);
			}
		}
	}
	public void sendMessageToClients(ModelDummy dummy){
		synchronized (clients) {
			for (ClientConnection client : clients.values()){
				System.out.println("KLIENT "+client.id);
				client.sendMessage(dummy);
			}
		}
	}
	public void removeClientConnection(int id){
		System.out.println("USUWANIE GRACZA "+id);
		synchronized (clients) {
			if (clients.containsKey(id)) {
				clients.get(id).disconnect();
				clients.remove(id);
			}
		}
	}
	private class ClientConnection{
		private int id;
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private boolean clientConnected = false;
		
		public ClientConnection(Socket socket, int id){
			this.id = id;
			this.socket = socket;
		}
		public void connect(){
			if(socket != null && !socket.isClosed()){
				try{
					out = new ObjectOutputStream(socket.getOutputStream());
					in = new ObjectInputStream(socket.getInputStream());
					addNewClientConnection(ClientConnection.this);
				}catch(Exception e){
					out = null;
					in = null;
					removeClientConnection(id);
				}
				clientConnected = true;
				new Thread(){
					public void run(){
						while(clientConnected){
							try {
								ServerActionContainer action = (ServerActionContainer)in.readObject();
								action.setId(id);
								messages.add(action);
							} catch (Exception e) {
								removeClientConnection(id);
							}
						}
					}
				}.start();
			}
		}
		public void sendMessage(String message){
			if (out != null)
				try {
					out.writeObject(message);
				} catch (Exception e) {}
		}
		public void sendMessage(ModelDummy dummy){
			if (out != null)
				try {
					dummy.setPlayerId(id);
					out.writeObject(dummy);
					out.flush();
					out.reset();
				} catch (Exception e) {}
		}
		public void disconnect(){
			clientConnected = false;
			try{
				if(in != null){
					in.close();
					in = null;
				}
				if(out != null){
					out.close();
					out = null;
				}
				if(socket != null){
					socket.close();
					socket = null;
				}
			}catch(Exception e){
				
			}
		}
	}
}
