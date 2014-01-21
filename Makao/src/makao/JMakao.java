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
	    	Serwer s = new Serwer(4321);
	    }
	    else{
	    	Klient k = new Klient(4321, "localhost");
	    	k.start();
	    	for(int i=0; i<10; i++){
	    		k.wyslij(i+": DZIALA KLIENT "+info);
	    	}
	    }
		
	}

}
