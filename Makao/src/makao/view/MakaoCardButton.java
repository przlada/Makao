package makao.view;

import javax.swing.JButton;

import makao.MakaoStatic;
import makao.model.MakaoCard;

public class MakaoCardButton extends JButton{
	private MakaoCard card = null;
	public MakaoCardButton(){
		super();
		setPreferredSize(MakaoStatic.CARD_SIZE);
		setIcon(MakaoStatic.CARD_BACK);
	}
	public MakaoCardButton(MakaoCard card){
		this();
		this.card = card;
		setIcon(MakaoStatic.CARD_ICONS[card.getColor()][card.getNumber()]);
	}
	public MakaoCard getMakaoCard(){
		return card;
	}
}
