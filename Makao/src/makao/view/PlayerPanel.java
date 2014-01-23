package makao.view;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import makao.MakaoStatic;

public class PlayerPanel extends JPanel {
	private JButton playerCards;
	public PlayerPanel(){
		setLayout(new GridBagLayout());
		setBackground(MakaoStatic.BACKGROUND_GREEN);
		playerCards = new JButton();
		playerCards.setIcon(MakaoStatic.CARD_BACK);
		playerCards.setPreferredSize(MakaoStatic.CARD_SIZE);
		add(playerCards);
	}
	public void showPlayerCards(boolean show){
		playerCards.setVisible(show);
	}

}
