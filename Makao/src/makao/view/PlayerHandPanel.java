package makao.view;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import makao.MakaoStatic;
import makao.model.MakaoCard;
import makao.view.actions.MakaoActions;

public class PlayerHandPanel extends JPanel implements ActionListener{
	private final BlockingQueue<MakaoActions> actionQueue;
	private Image bg = null;
	private MakaoCard lastCard = null;
	private List<MakaoCard> hand = null;
	private boolean isPlayerTurn = false;
	private MakaoCard requestedNumber = null;
	public PlayerHandPanel(BlockingQueue<MakaoActions> actionQueue){
		super();
		this.actionQueue = actionQueue;
		setBackgroundImage(MakaoStatic.CARD_DOCK_BACKGROUND_IMAGE);
	}
	public void setHand(List<MakaoCard> hand){
		this.hand = hand;
		removeAll();
		for(int i=0; i<hand.size(); i++){

			MakaoCardButton cardButton = new MakaoCardButton(hand.get(i));
			cardButton.addActionListener(this);
			add(cardButton);
		}
		validate();
		repaint();
	}
	
	public Image getBackgroundImage() {
		return bg;
	}

	private void setBackgroundImage(Image bg) {
		this.bg = bg;
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg!=null) {
            int iw = bg.getWidth(this);
            int ih = bg.getHeight(this);
            if (iw > 0 && ih > 0) {
                for (int x = 0; x < getWidth(); x += iw) {
                    for (int y = 0; y < getHeight(); y += ih) {
                        g.drawImage(bg, x, y, iw, ih, this);
                    }
                }
            }
        }
    }
	
	public boolean isPlayerTurn() {
		return isPlayerTurn;
	}
	public void setPlayerTurn(boolean isPlayerTurn) {
		this.isPlayerTurn = isPlayerTurn;
	}
	public MakaoCard getLastSelectedCard(){
		return lastCard;
	}
	public MakaoCard getRequestedNumber(){
		return requestedNumber;
	}
	
	public void actionPerformed(ActionEvent e) {
		MakaoCardButton source = (MakaoCardButton)e.getSource();
		lastCard = source.getMakaoCard();
		if(lastCard.getNumber() == 10){
			if(isPlayerTurn){
				requestedNumber = lastCard;
				actionQueue.add(MakaoActions.SHOW_SELECT_CARD_NUMBER_DIALOG);
			}
		}
		else
			actionQueue.add(MakaoActions.GAME_SELECT_CARD);
	}
}
