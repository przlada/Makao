package makao.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import makao.model.MakaoCard;
import makao.view.actions.MakaoActions;

public class SelectCardColorDialog extends JDialog {
	private final BlockingQueue<MakaoActions> actionQueue;
	private JComboBox petList;
	private MakaoCard card;
	public SelectCardColorDialog(final BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		setTitle("MAKAO WYBIERZ û„DANY KOLOR");
        setResizable(false);
        setSize(500, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,0));
		String[] cardNames = { "trefl", "karo", "kier", "pik" };
		final int[] values = { MakaoCard.COLOR_CLUBS, MakaoCard.COLOR_DIAMONDS, MakaoCard.COLOR_HEARTS, MakaoCard.COLOR_SPADES };

		petList = new JComboBox(cardNames);
		petList.setSelectedIndex(0);
		add(petList);
		JButton request = new JButton("ýˆdam");
		request.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	card = new MakaoCard(values[petList.getSelectedIndex()], card.getColor());
            	actionQueue.add(MakaoActions.GAME_REQUIRE_COLOR);
            	dispose();
            }
        });
		add(request);
	}
	public void showSelectCardColorDialog(MakaoCard card){
		this.card = card;
		setVisible(true);
	}
	public MakaoCard getSelectedCardColor(){
		return card;
	}
	

}
