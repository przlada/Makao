package makao;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import makao.controller.Client;
import makao.controller.Controller;
import makao.model.Model;
import makao.view.MainView;


public class JMakao {
	public static void main(String[] args) {
		/*
		final BlockingQueue<String> bq = new LinkedBlockingQueue<String>();
		Model model = new Model();
		View widok = new View(bq);
		Controller controler = new Controller(bq, model, widok);
		model.setController(controler);
		controler.start();
		*/
		MainView main = new MainView();
		main.setVisible(true);
		//while(true){}
		
		// TODO Auto-generated method stub
		/*
		MainView main = new MainView();
		main.setVisible(true);
		System.out.println("SERWER CZY KLIENT?");
		Scanner sc = new Scanner (System.in);
	    String info = sc.nextLine();
	    System.out.println(info);

	    if(info.equals("s")){
	    	Server server = new Server(3456);
	    	server.startServer();
	    }
	    else{
	    	Client client = new Client("localhost", 3456);
	    	System.out.println(client.connect());
	    	while(!info.equals("q")){
	    		info = sc.nextLine();
	    		client.send(info);
	    		System.out.println(client.getMessage());
	    	}
	    	System.out.println("Disconnect");
	    	client.disconnect();
	    }
	    
	    */
		
	}

}
