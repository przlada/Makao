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
import makao.model.MakaoPlayer;
import makao.view.actions.MakaoActions;

public class MainView extends JFrame implements ActionListener{
	private final BlockingQueue<MakaoActions> actionQueue;
	private TextMessagePanel rightPanel;
	private PlayerHandPanel cardDock;
	private JScrollPane cardDockScroll;
	private CenterDeckPanel centerPanel;
	private JPanel panel;
	private List<OponentPanel> opoenetsPanels = new ArrayList<OponentPanel>();
	public MainView(final BlockingQueue<MakaoActions> actionQueue) {
		this.actionQueue = actionQueue;
		setTitle("Makao");
		setSize(MakaoStatic.MAIN_FRAME_SIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container mainPanel = getContentPane();
		mainPanel.setLayout(new BorderLayout());
		panel = new JPanel();
		
		panel.setBackground(MakaoStatic.BACKGROUND_GREEN);
		panel.setLayout(new BorderLayout());
		
		
		centerPanel = new CenterDeckPanel(actionQueue);
		panel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.add(panel, BorderLayout.CENTER);
		rightPanel = new TextMessagePanel(actionQueue);
		
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		cardDock = new PlayerHandPanel(actionQueue);
		//cardDock.setHand(hand);
		cardDockScroll = new JScrollPane(cardDock);
		cardDock.setAutoscrolls(true);
		cardDockScroll.setPreferredSize(new Dimension(0,133));
		mainPanel.add(cardDockScroll, BorderLayout.SOUTH);
		
		MainMakaoMenuBar menuBar = new MainMakaoMenuBar(actionQueue);
        setJMenuBar(menuBar);

	}
	public void clearGame(){
		opoenetsPanels = new ArrayList<OponentPanel>();
		panel.removeAll();
		validate();
		repaint();
	}
	public void drawPlayers(int playerId, List<MakaoPlayer> players){
		int panelCounter = 0;
		for(int i=0; i<players.size(); i++){
			if(i == playerId)
				setHand(players.get(i).getHand());
			else{
				if(opoenetsPanels.size() <= panelCounter)
					addOponentPanel(players.get(i).getNick());
				opoenetsPanels.get(panelCounter).setOponentNick(players.get(i).getNick());
				panelCounter++;
			}
		}
	}
	private void addOponentPanel(String oponentNick){
		OponentPanel oponent = new OponentPanel();
		oponent.setOponentNick(oponentNick);
		opoenetsPanels.add(oponent);
		switch(opoenetsPanels.size()){
		case 1:
			panel.add(oponent, BorderLayout.WEST);
			break;
		case 2:
			panel.add(oponent, BorderLayout.NORTH);
			break;
		case 3:
			panel.add(oponent, BorderLayout.EAST);
			break;
		}
		panel.validate();
		panel.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		AboutDialog ad = new AboutDialog();
        ad.setVisible(true);
	}
	public void haveToStartGame(boolean haveTo){
		rightPanel.setHaveToStartGame(haveTo);
	}
	public void addTextMessage(String message){
		rightPanel.addMessage(message); 
	}
	public String getTextMessage(){
		return rightPanel.getMessage();
	}
	public void setHand(List<MakaoCard> hand){
		cardDock.setHand(hand);
		cardDockScroll.validate();
		cardDockScroll.repaint();
	}
	public MakaoCard getSelectedPlayerCard(){
		return cardDock.getLastSelectedCard();
	}
	public void setNewCard(MakaoCard card){
		centerPanel.setNewCard(card);
	}
	public MakaoCard getRequestedNumber(){
		return cardDock.getRequestedNumber();
	}
	public void setPlayerTurn(boolean isPlayerTurn){
		cardDock.setPlayerTurn(isPlayerTurn);
	}

}
