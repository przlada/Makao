package makao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import makao.MakaoStatic;
/**
 * Klasa reperzentująca gracza
 * @author przemek
 *
 */
public class MakaoPlayer implements Serializable {
	private int id;
	private String nick;
	private List<MakaoCard> hand = new ArrayList<MakaoCard>();
	private int freezRounds = 0;
	/**
	 * Tworzenie nowego gracza 
	 * @param id Id nowego gracza
	 */
	public MakaoPlayer(int id) {
		this.id = id;
		nick = MakaoStatic.defoltPlayerName+(id+1);
	}
	/**
	 * Czy gracz jest zablokowany
	 * @return zwraca prawdę jęsli zablokowany
	 */
	public boolean isFreez(){
		return (freezRounds > 0);
	}
	/** 
	 * Ustawia blokadę na gracza
	 * @param freez ilość kolejek na jaką jest zablokowany
	 */
	public void setFreezRounds(int freez){
		freezRounds = freez;
	}
	/**
	 * Zmniejsz liczbę kolejek zablokowania
	 */
	public void lessFreez(){
		if(freezRounds > 0)
			freezRounds--;
	}
	/**
	 * Pobierz nik gracza
	 * @return nik garcza
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * Usuwanie gart z ręki gracza
	 */
	public void resteHand(){
		hand = null;
		hand = new ArrayList<MakaoCard>();
	}
	/**
	 * Ustawienie nowego niku gracza
	 * @param nick nik gracza
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * Pobierz kart jakie gracz ma w ręku
	 * @return ręka gracza
	 */
	public List<MakaoCard> getHand(){
		return hand;
	}
}
