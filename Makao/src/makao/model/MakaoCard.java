package makao.model;

import java.io.Serializable;

public class MakaoCard implements Serializable {
	private final int number;
	private final int color;
	
	public static final int CARD_NO_2 = 1;
	public static final int CARD_NO_3 = 2;
	public static final int CARD_NO_4 = 3;
	public static final int CARD_NO_5 = 4;
	public static final int CARD_NO_6 = 5;
	public static final int CARD_NO_7 = 6;
	public static final int CARD_NO_8 = 7;
	public static final int CARD_NO_9 = 8;
	public static final int CARD_NO_10 = 9;
	public static final int CARD_JACK = 10;
	public static final int CARD_QUEEN = 11;
	public static final int CARD_KING = 12;
	public static final int CARD_ACE = 0;
	
	public static final int COLOR_CLUBS = 0; //trefl
	public static final int COLOR_DIAMONDS = 3; //karo
	public static final int COLOR_HEARTS = 2; //kier
	public static final int COLOR_SPADES = 1; //pik
	
	/**
	 * 
	 * @param number figura karty
	 * @param color color karty
	 */
	public MakaoCard(int number, int color){
		this.number = number;
		this.color = color;
	}
	public int getColor(){
		return color;
	}
	public int getNumber(){
		return number;
	}
	public boolean isFightingCard(){
		if(number < 4 || number > 9)
			return true;
		return false;
	}

	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (!(this.getClass().equals(object.getClass())))
			return false;
		MakaoCard card = (MakaoCard) object;
		if(number ==  card.getNumber() && color == card.getColor())
			return true;
		return false;
	}
}
