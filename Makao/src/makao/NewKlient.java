package makao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NewKlient {
	 private Socket socket; 
	 private final BlockingQueue<Object> kolejka = new LinkedBlockingQueue<>();
	 private final int port;
	 private final String host;
	 private boolean uruchomiony = false;
	 private ObjectInputStream in;
	 private ObjectOutputStream out;
	 
	 public NewKlient(int port, String host) {
	        this.port = port;
	        this.host = host;
	 }
	 
	public void stop() {
		uruchomiony = false;
		try {
			if (out != null) {
				out.close();
				out = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}
			if (socket != null) {
				socket.close();
				socket = null;
			}
		} catch (IOException e) {

		}
		kolejka.clear();
	}

	public void wyslij(String komunikat) {
		System.out.println("Chce wys∏aç "+komunikat);
		try {
			if (out != null)
				out.writeObject(komunikat);
			else
				System.out.println("DUPA");
		} catch (IOException e) {}
	}

	public Object odbierz() {
		try {
			return kolejka.take();
		} catch (InterruptedException e) {}
		return null;
	}
	
	public void start(){
		System.out.println("O co chodzi?34");
		try {
			socket = new Socket(host, port);
			System.out.println("O co chodzi?666");
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("O co chodzi?666");
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("O co chodzi?666");
		} catch (IOException e) {System.out.println("O co chodzi?2");}
		uruchomiony = true;
		System.out.println("uruchomiony");
		new Thread(){
			public void run() {
	            while (uruchomiony) {
	                try {
	                    Object wiadomosc = in.readObject();
	                    kolejka.add(wiadomosc);
	                } catch (IOException | ClassNotFoundException ex) {
	                        NewKlient.this.stop();
	                } catch (NullPointerException e){
	                    NewKlient.this.stop();
	                }
	            }
	        }
		}.start();
	}
}
