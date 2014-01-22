package makao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NewSerwer {
	private BlockingQueue<String> kolejka = new LinkedBlockingQueue<>();
	private ServerSocket serverSocket;
	private final Map<Integer, Klient> listaKlientow = new HashMap<>();
	private int unikalnyID = 1;
	private boolean uruchomiony = false;
	
	public void start(){
		try {
			serverSocket = new ServerSocket(4321);
		} catch (IOException e1) {}
		new Thread() { // watek odbierania komunikatow
			public void run() {
				while (true) {
					try {
						System.out.println("odbieraj");
						String komunikat = kolejka.take();
						System.out.println(komunikat);
					} catch (InterruptedException ex) {
						System.out.println("odbieraj DUPA");
					}
				}
			}
		}.start();
		new Thread() { //watek akceptowania nowych polaczen
			public void run() {
				try {
					while (uruchomiony) {
						Socket s = serverSocket.accept();
						new Klient(s,new Integer(unikalnyID)).start();
						++unikalnyID;
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();
	}
	public void stop() {
		uruchomiony = false;
		if (serverSocket == null) {
			return;
		}
		kolejka.clear();
		try {
			serverSocket.close();
		} catch (IOException e) {
			
		}
		serverSocket = null;
		for (Klient wk : listaKlientow.values()) {
			wk.close();
		}
	}
	
	private void akceptujPolaczenie(Klient watek) {
		Integer id = watek.id;
		synchronized (listaKlientow) {
			listaKlientow.put(id, watek);
		}
	}
	public void zakonczPolaczenie(Integer klientId) {
		System.out.println("tutaj");
		synchronized (listaKlientow) {
			if (listaKlientow.containsKey(klientId)) {
				listaKlientow.get(klientId).close();
				listaKlientow.remove(klientId);
			}
		}
	}
	
	private class Klient extends Thread{
		private Socket socket;
		private ObjectOutputStream out;
	    private ObjectInputStream in = null;
	    int id;
	    public Klient(Socket polaczenie,Integer id){
	    	this.id = id;
	    	socket = polaczenie;
	    }
	    private void close() {
			try {
				uruchomiony = false;
				socket.close();
				System.out.println("tutaj");
			} catch (IOException ex) {}
		}
	    public void run() {
	    	try {
				out = new ObjectOutputStream(socket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();				
			}
	    	akceptujPolaczenie(Klient.this);
			while (uruchomiony) {
				try {
					System.out.println("tutaj");
					String wiadomosc = (String) in.readObject();
					kolejka.add(wiadomosc);
				} catch (IOException | ClassNotFoundException ex) {
					zakonczPolaczenie(id);
				}

			}
		}
	}
	 
}
