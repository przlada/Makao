package makao.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import makao.MakaoStatic;
import makao.model.MakaoCard;
import makao.view.actions.MakaoActions;

public class PlayerHandPanel extends JPanel{
	private final BlockingQueue<MakaoActions> actionQueue;
	private Image bg = null;
	private List<MakaoCard> hand = null;
	public PlayerHandPanel(BlockingQueue<MakaoActions> actionQueue){
		super();
		this.actionQueue = actionQueue;
		setBackgroundImage(MakaoStatic.CARD_DOCK_BACKGROUND_IMAGE);
	}
	public void setHand(List<MakaoCard> hand){
		this.hand = hand;
		for(int i=0; i<hand.size(); i++){
			MakaoCard card = hand.get(i);
			JButton but = new JButton();
			but.setPreferredSize(MakaoStatic.CARD_SIZE);
			but.setIcon(MakaoStatic.CARD_ICONS[card.getColor()][card.getNumber()]);
			add(but);		
		}
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
}
