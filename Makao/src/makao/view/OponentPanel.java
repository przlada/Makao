package makao.view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import makao.MakaoStatic;

public class OponentPanel extends JPanel {
	private JButton playerCards;
	private JLabel oponentNick;
	public OponentPanel(){
		setLayout(new GridBagLayout());
		setBackground(MakaoStatic.BACKGROUND_GREEN);
		oponentNick = new JLabel("Przeciwnik");
		playerCards = new JButton();
		playerCards.setIcon(MakaoStatic.CARD_BACK);
		playerCards.setPreferredSize(MakaoStatic.CARD_SIZE);
		
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.anchor = GridBagConstraints.CENTER; 
	    gbc.weightx = 1;
	    gbc.gridy = 1;
	    gbc.gridx = 1;
	    add(playerCards, gbc);
	    gbc.gridy = 2;
	    add(oponentNick, gbc);
	}
	public void showPlayerCards(boolean show){
		playerCards.setVisible(show);
	}
	public void setOponentNick(String nick){
		oponentNick.setText(nick);
	}

}
