package makao.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
	 private Socket socket; 
	 private final int port;
	 private final String host;
	 private boolean connected = false;
	 private ObjectInputStream in;
	 private ObjectOutputStream out;
	 private final BlockingQueue<String> messages = new LinkedBlockingQueue<>();
	 
	 public Client(String host, int port){
		 this.port = port;
		 this.host = host;
	 }
	 /** Nawiˆzuje po¸ˆczenie z serwerem */
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
						messages.add((String) in.readObject());
					} catch (Exception e) {
						disconnect();
					}
				}
			 }
		 }.start();
		 return true;
	 }
	 /** KoÄczy po¸ˆczenie */
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
		 }catch(Exception e){
			 
		 }
		 messages.clear();
	 }
	 /** Wysy¸a wiadomosc poprzez wczesniej ustanowione po¸ˆczenie */
	 public void send(String message){
		 if(out != null)
			 try{
				 out.writeObject(message);
			 }catch(Exception e){
				 
			 }
	 }
	 /** Pobieranie wiadomosci od serwera. Czeka az pojawi si« wiadomosc */
	 public String getMessage(){
		 try{
			 return messages.take();
		 }catch(Exception e){
		 	return null;
		 }
	 }
	 
}
