package makao.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import makao.model.ModelDummy;
import makao.view.actions.ServerActionContainer;

/**
 * Klasa Klient do obsługi komunikacji klienta z serwerem
 * @author przemek
 *
 */
public class Client {
	 private Socket socket; 
	 private final int port;
	 private String host;
	 private boolean connected = false;
	 private ObjectInputStream in;
	 private ObjectOutputStream out;
	 private final BlockingQueue<ModelDummy> messages = new LinkedBlockingQueue<>();
	 private Controller controller;
	 /**
	  * Tworzenie nowego klienta 
	  * @param port Numer portu serwera
	  * @param controller Obiekt kontrolera
	  */
	 public Client(int port, Controller controller){
		 this.port = port;
		 this.controller = controller;
	 }
	 /**
	  * Ustawienie serwera z jakim chcemy się połączyć 
	  * @param host adres serwera
	  */
	 public void setHost(String host){
		 this.host = host;
	 }
	 /**
	  * Nawiąż połączenia 
	  * @return zwraca prawdę jęśli połączono z serwerem
	  */
	 public boolean connect(){
		 try{
			 socket = new Socket(host, port);
			 in = new ObjectInputStream(socket.getInputStream());
			 out = new ObjectOutputStream(socket.getOutputStream());
		 }
		 catch(Exception e){
			 socket = null;
			 in = null;
			 out = null;
			 return false;
		 }
		 connected = true;
		 new Thread(){
			 public void run(){
				while (connected) {
					try {
						messages.add((ModelDummy) in.readObject());
					} catch (Exception e) {
						disconnect();
					}
				}
			 }
		 }.start();
		 new Thread(){
			 public void run(){
				while (connected) {
					try {
						controller.passModelDummyToView(messages.take());
					} catch (Exception e) {
						disconnect();
					}
				}
			 }
		 }.start();
		 return true;
	 }
	 /** Kończy połączenie */
	 public void disconnect(){
		 connected = false;
		 try{
			 if(out != null){
				 out.close();
				 out = null;
			 }
			 if(in != null){
				 in.close();
				 in = null;
			 }
			 if(socket != null){
				 socket.close();
				 socket = null;
			 }
		 }catch(Exception e){}
		 messages.clear();
		 controller.clientConnectionLost();
	 }
	 /** Wysyła wiadomosc poprzez wczesniej ustanowione połączenie */
	 public void send(ServerActionContainer message){
		 if(out != null)
			 try{
				 out.writeObject(message);
				 out.flush();
				 out.reset();
			 }catch(Exception e){
				 disconnect();
			 }
	 }
	 
}
