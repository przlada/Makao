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

import makao.model.ModelDummy;
import makao.view.actions.MakaoActions;

public class Server {
	private int nextId = 1;
	private BlockingQueue<String> messages = new LinkedBlockingQueue<>();
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
		new Thread() { // odbieranie komunikatów z kolejki
			public void run() {
				while (true) {
					try {
						String message = messages.take();
						//System.out.println(message);
						controller.passActionToModel(message);
					} catch (InterruptedException ex) {

					}
				}
			}
		}.start();
		new Thread() { // akceptowanie połączeń
			public void run() {
				try {
					while (started) {
						new ClientConnection(serverSocket.accept(), nextId).connect();
						++nextId;
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
								messages.add(id+": "+(String)in.readObject());
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
					out.writeObject(dummy);
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
