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

public class SelectCardNumberDialog extends JDialog {
	private final BlockingQueue<MakaoActions> actionQueue;
	private JComboBox petList;
	private MakaoCard card;
	public SelectCardNumberDialog(final BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		setTitle("MAKAO WYBIERZ û„DAN„ FIGUR¢");
        setResizable(false);
        setSize(500, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,0));
		String[] cardNames = { "nic nie ýˆdam", "piˆtek", "szustek", "siudemek", "—semek", "dziewiˆtek", "dziesiˆtek" };
		final int[] values = { 10, 4, 5, 6, 7, 8, 9 };

		petList = new JComboBox(cardNames);
		petList.setSelectedIndex(0);
		add(petList);
		JButton request = new JButton("ûˆdam");
		request.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	card = new MakaoCard(values[petList.getSelectedIndex()],card.getColor());
            	actionQueue.add(MakaoActions.GAME_REQUIRE_NUMBER);
            	dispose();
            }
        });
		add(request);
	}
	public void showSelectCardNumberDialog(MakaoCard card){
		this.card = card;
		setVisible(true);
	}
	public MakaoCard getSelectedCardNumber(){
		return card;
	}
	

}
