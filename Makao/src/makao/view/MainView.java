package makao.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import makao.MakaoStatic;
import makao.model.MakaoCard;
import makao.view.actions.MakaoActions;

public class MainView extends JFrame implements ActionListener{
	private final BlockingQueue<MakaoActions> actionQueue;
	private TextMessagePanel rightPanel;
	public MainView(final BlockingQueue<MakaoActions> actionQueue) {
		this.actionQueue = actionQueue;
		setTitle("Makao");
		setSize(MakaoStatic.MAIN_FRAME_SIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container mainPanel = getContentPane();
		mainPanel.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		
		panel.setBackground(MakaoStatic.BACKGROUND_GREEN);
		panel.setLayout(new BorderLayout());
		
		
		CenterDeckPanel centerPanel = new CenterDeckPanel(actionQueue);
		panel.add(centerPanel, BorderLayout.CENTER);
		
		
		JPanel leftPlayer = new PlayerPanel();
		panel.add(leftPlayer, BorderLayout.WEST);
		
		JPanel rightPlayer = new PlayerPanel();
		panel.add(rightPlayer, BorderLayout.EAST);
		
		JPanel topPlayer = new PlayerPanel();
		panel.add(topPlayer, BorderLayout.NORTH);
		
		
		
		mainPanel.add(panel, BorderLayout.CENTER);
		rightPanel = new TextMessagePanel(actionQueue);
		
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		PlayerHandPanel cardDock = new PlayerHandPanel(actionQueue);
		List<MakaoCard> hand = new ArrayList<MakaoCard>();
		for(int i=0; i<13; i++){
			MakaoCard card = new MakaoCard(i,0);
			hand.add(card);
		}
		Collections.shuffle(hand, new Random());
		cardDock.setHand(hand);
		JScrollPane cardDockScroll = new JScrollPane(cardDock);
		cardDock.setAutoscrolls(true);
		cardDockScroll.setPreferredSize(new Dimension(0,133));
		mainPanel.add(cardDockScroll, BorderLayout.SOUTH);
		
		MainMakaoMenuBar menuBar = new MainMakaoMenuBar(actionQueue);
        setJMenuBar(menuBar);

	}

	public void actionPerformed(ActionEvent e) {
		AboutDialog ad = new AboutDialog();
        ad.setVisible(true);
	}
	
	public void addTextMessage(String message){
		rightPanel.addMessage(message); 
	}
	public String getTextMessage(){
		return rightPanel.getMessage();
	}
	

}
