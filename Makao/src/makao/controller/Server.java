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

/** 
 * Klasa s∏u˝àca do za∏o˝enia serwera na wybranym porcie
 * @author przemek
 */
public class Server {
	private int nextId = 0;
	private BlockingQueue<ServerActionContainer> messages = new LinkedBlockingQueue<>();
	private final Map<Integer, ClientConnection> clients = new HashMap<>();
	private final Controller controller;
	private ServerSocket serverSocket;
	private final int port;
	private boolean started = false;
	/**
	 * Tworzenie nowego serwera
	 * @param port Port na jakim ma dzia∏aç serwer
	 * @param controller Kontroler do krótego przekazywane b´dà wszelkie akcje
	 */
	public Server(int port, Controller controller){
		this.port = port;
		this.controller = controller;
	}
	/**
	 * 
	 * @return zwraca prawd´ jeÊli serwer zosta∏ uruchomiony
	 */
	public boolean isServerStarted(){
		return started;
	}
	/**
	 * Start serwera
	 * @return zwraca prawd´ jeÊli uruchomienie sewera przebieg∏o bez problemu
	 */
	public boolean startServer(){
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			return false;
		}
		started = true;
		new Thread() { // wàtek zarzàdzajàcy odebranymi danymi
			public void run() {
				while (true) {
					try {
						ServerActionContainer action = messages.take();
						controller.passActionToModel(action);
					} catch (InterruptedException ex) {

					}
				}
			}
		}.start();
		new Thread() { // wàtek obs∏ugi nowych po∏àczeƒ
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
					stopServer();
				}
			}
		}.start();
		return true;
	}
	/**
	 * Zatrzymanie serwera
	 */
	public void stopServer(){
		started = false;
		if (serverSocket == null) {
			return;
		}
		messages.clear();
		for (ClientConnection client : clients.values()) {
			client.disconnect();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {}
		serverSocket = null;
		nextId =0;
		clients.clear();
		controller.serverStoped();
	}
	/**
	 * Dodawanie nowego po∏àczenia z klientem do listy po∏àczeƒ 
	 * @param newClient Obiekt nowego po∏àczenia z klientem
	 */
	private void addNewClientConnection(ClientConnection newClient){
		synchronized (clients) {
			clients.put(newClient.id, newClient);
		}
	}
	/**
	 * Rozes∏anie wszystkim klientom wiadomoÊci tekstowej
	 * @param message TreÊç wiadomoÊci
	 */
	public void sendMessageToClients(String message){
		synchronized (clients) {
			for (ClientConnection client : clients.values()){
				System.out.println("KLIENT "+client.id);
				client.sendMessage(message);
			}
		}
	}
	/**
	 * Rozes∏anie wszystkim klientom wiadomoÊci
	 * @param dummy Obiekt makiet modelu do wys∏ania 
	 */
	public void sendMessageToClients(ModelDummy dummy){
		synchronized (clients) {
			for (ClientConnection client : clients.values()){
				client.sendMessage(dummy);
			}
		}
	}
	/**
	 * Usuni´cie po∏àczenia z klientem
	 * @param id Id klienta do usuni´cia
	 */
	public void removeClientConnection(int id){
		synchronized (clients) {
			for (ClientConnection client : clients.values()){
				client.disconnect();
			}
			clients.clear();
		}
		stopServer();
	}
	/**
	 * Wewn´trzna klasa nowego po∏àczenia z klientem
	 */
	private class ClientConnection{
		private int id;
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private boolean clientConnected = false;
		/**
		 * Tworzenie nowego po∏àczenia z klientem
		 * @param socket Po∏àczenie nawiàzane z klientem
		 * @param id Id przypisane do nowego klienta
		 */
		public ClientConnection(Socket socket, int id){
			this.id = id;
			this.socket = socket;
		}
		/**
		 * Nawiàzanie po∏àczenia z klientem
		 */
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
		/**
		 * Wys∏anie wiadomoÊci do klienta 
		 * @param message TreÊç wiadomoÊci
		 */
		public void sendMessage(String message){
			if (out != null)
				try {
					out.writeObject(message);
				} catch (Exception e) {}
		}
		/**
		 * Wys∏anie wiadomoÊci do klienta
		 * @param dummy TreÊç wiadomoÊci
		 */
		public void sendMessage(ModelDummy dummy){
			if (out != null)
				try {
					dummy.setPlayerId(id);
					out.writeObject(dummy);
					out.flush();
					out.reset();
				} catch (Exception e) {}
		}
		/**
		 * Zakoƒczenie po∏àczenia z klientem
		 */
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
			}catch(Exception e){}
		}
	}
}
