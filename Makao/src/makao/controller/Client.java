package makao.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import makao.model.ModelDummy;
import makao.view.actions.ServerActionContainer;

public class Client {
	 private Socket socket; 
	 private final int port;
	 private String host;
	 private boolean connected = false;
	 private ObjectInputStream in;
	 private ObjectOutputStream out;
	 private final BlockingQueue<ModelDummy> messages = new LinkedBlockingQueue<>();
	 private Controller controller;
	 
	 public Client(int port, Controller controller){
		 this.port = port;
		 this.controller = controller;
	 }
	 public void setHost(String host){
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
					} catch (Exception e) {}
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
	 public void send(ServerActionContainer message){
		 if(out != null)
			 try{
				 out.writeObject(message);
				 out.flush();
				 out.reset();
			 }catch(Exception e){
				 
			 }
	 }
	 
}
