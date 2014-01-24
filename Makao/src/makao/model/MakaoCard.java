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
}
