package makao;
import java.util.Scanner;

public class JMakao {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	    
		
	}

}
