package makao.model;

import java.io.Serializable;

public class MakaoCard implements Serializable {
	private final int number;
	private final int color;
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
