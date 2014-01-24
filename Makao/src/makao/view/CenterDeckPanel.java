package makao.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import makao.MakaoStatic;
import makao.model.MakaoCard;
import makao.view.actions.MakaoActions;

public class CenterDeckPanel  extends JPanel {
	private final BlockingQueue<MakaoActions> actionQueue;
	private MakaoCardButton deckNew;
	private MakaoCardButton deckOld;
	public CenterDeckPanel(final BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		JPanel centerDecks = new JPanel();
		centerDecks.setPreferredSize(new Dimension(160, 110));
		deckNew = new MakaoCardButton(new MakaoCard(0,0));
		deckOld = new MakaoCardButton();
		deckOld.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	actionQueue.add(MakaoActions.GAME_TAKE_NEXT_CARD);
            }
        });
		centerDecks.add(deckNew);
		centerDecks.add(deckOld);
		centerDecks.setBackground(MakaoStatic.BACKGROUND_GREEN);
		setLayout(new GridBagLayout());
		setBackground(MakaoStatic.BACKGROUND_GREEN);
		add(centerDecks);
	}
	public void setNewCard(MakaoCard card){
		if(card == null)
			deckNew.setVisible(false);
		else{
			deckNew.setVisible(true);
			deckNew.setMakaoCard(card);
			validate();
			repaint();
		}
	}	
}
