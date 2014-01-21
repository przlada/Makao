package makao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Klient {
	  private Socket socket;
	    /** Kolejka z wiadomościami od serwera. */
	    private final BlockingQueue<Object> kolejka = new LinkedBlockingQueue<>();
	    /** Port, pod którym nawiązywane jest połączenie. */
	    private final int port;
	    /** Adres hosta, pod którym nawiązywane jest połączenie. */
	    private final String host;
	    /** Informacja, czy klient jest podłączony. */
	    private boolean uruchomiony = false;
	    /** */
	    private ObjectInputStream in;
	    /** */
	    private ObjectOutputStream out;

	    /**
	     * Utworzenie nowego klienta, bez uruchamiania.
	     *
	     * @param port Port, pod którym nawiązywane jest połączenie.
	     * @param host Adres hosta, pod którym nawiązywane jest połączenie.
	     */
	    public Klient(int port, String host) {
	        this.port = port;
	        this.host = host;
	    }

	    /**
	     * Zatrzymuje pracę klienta.
	     *
	     *
	     */
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

	    /**
	     * Rozpoczyna nasłuch komunikatów od serwera.
	     *
	     *
	     */
	    public void start() {
	        try {
	            socket = new Socket(host, port);
	            in = new ObjectInputStream(socket.getInputStream());
	            out = new ObjectOutputStream(socket.getOutputStream());
	        } catch (IOException e) {
	        	System.out.println(e.getMessage());
	        }
	        uruchomiony = true;
	        new WatekOdbierania().start();
	    }

	    /**
	     * Odbiera element wysłany przez serwer z kolejki.
	     *
	     * @return Obiekt wyciągnięty z kolejki
	     */
	    public Object odbierz() {
	        try {
	            return kolejka.take();
	        } catch (InterruptedException e) {
	           
	        }
	        return null;
	    }

	    /**
	     * Wysyła wiadomość do serwera.
	     *
	     * @param komunikat Komunikat, który zostanie przekazany serwerowi.
	     */
	    public void wyslij(Object komunikat) {
	        try {
	            if (out == null) {
	            	System.out.println("DUPA");
	            }
	            out.writeObject(komunikat);
	        } catch (IOException e) {

	        }
	    }

	    /**
	     * Klasa nasłuchująca komunikatów od kliena.
	     */
	    private class WatekOdbierania extends Thread {

	        @Override
	        public void run() {
	            while (uruchomiony) {
	                try {
	                    Object wiadomosc = in.readObject();
	                    kolejka.add(wiadomosc);
	                } catch (IOException | ClassNotFoundException ex) {
	                        Klient.this.stop();
	                    
	                } catch (NullPointerException e){
	                    Klient.this.stop();
	                }

	            }
	        }
	    }
	}