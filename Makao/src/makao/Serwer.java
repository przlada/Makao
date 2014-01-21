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



public class Serwer {
	private int unikalnyID = 1;
	private BlockingQueue<String> kolejka = new LinkedBlockingQueue<>();
	private final Map<Integer, Klient> listaKlientow = new HashMap<>();
	private ServerSocket serverSocket;
	private final int port;
	private boolean uruchomiony = false;
	
	public Serwer(int port) {
		this.port = port;
	}
	
	public void start() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			
		}
		uruchomiony = true;
		new Thread() { 
			public void run() {
				while (true) {
					try {
						String komunikat = kolejka.take();
						System.out.println(komunikat);
					} catch (InterruptedException ex) {

					}
				}
			}
		}.start();
		new Thread() {
			public void run() {
				try {
					while (uruchomiony) {
						new Klient(new Integer(unikalnyID), serverSocket.accept());
						//if(unikalnyID == 2)
							//wyslijWybranemu(new Komunikat(1, new Wiadomosc(TypKomunikatu.KLIENT_POLACZONY, null)));
						++unikalnyID;
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();
		
	}
	private class Klient{
		private final Integer id;
		private Socket polaczenie;
		
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private Thread watekOdbierania;
		
		public Klient(Integer id, Socket polaczenie){
			this.id = id;
			this.polaczenie = polaczenie;
			
			if (this.polaczenie != null && !this.polaczenie.isClosed()) {
				try {
					out = new ObjectOutputStream(this.polaczenie.getOutputStream());
					out.flush();
					in = new ObjectInputStream(this.polaczenie.getInputStream());
					synchronized (listaKlientow) {
						listaKlientow.put(id, Klient.this);
					}
					System.out.println("Dodano nowego usera o id="+id);
				} catch (IOException ex) {
					//zakonczPolaczenie(id);
				}
				watekOdbierania = new WatekOdbierania();
				watekOdbierania.start();
			}
		}
		private class WatekOdbierania extends Thread {

			@Override
			public void run() {
				while (uruchomiony) {
					try {
						String wiadomosc = (String) in.readObject();
						kolejka.add(id+": "+wiadomosc);
					} catch (IOException | ClassNotFoundException ex) {
						//zakonczPolaczenie(id);
					}

				}
			}
		}
		
	}
	
}
