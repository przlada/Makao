package makao.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import makao.MakaoStatic;
import makao.view.actions.MakaoActions;

public class CenterDeckPanel  extends JPanel {
	private final BlockingQueue<MakaoActions> actionQueue;
	
	public CenterDeckPanel(BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		JPanel centerDecks = new JPanel();
		centerDecks.setPreferredSize(new Dimension(160, 110));
		JButton deckNew = new JButton();
		JButton deckOld = new JButton();
		deckNew.setPreferredSize(MakaoStatic.CARD_SIZE);
		deckOld.setPreferredSize(MakaoStatic.CARD_SIZE);
		deckNew.setIcon(MakaoStatic.CARD_ICONS[0][0]);
		deckOld.setIcon(MakaoStatic.CARD_BACK);
		centerDecks.add(deckNew);
		centerDecks.add(deckOld);
		centerDecks.setBackground(MakaoStatic.BACKGROUND_GREEN);
		setLayout(new GridBagLayout());
		setBackground(MakaoStatic.BACKGROUND_GREEN);
		add(centerDecks);
	}
}
