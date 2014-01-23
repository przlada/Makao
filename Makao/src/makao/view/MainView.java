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
		JPanel centerPanelConstrain = new JPanel(new GridBagLayout());
		centerPanelConstrain.setBackground(MakaoStatic.BACKGROUND_GREEN);
		centerPanelConstrain.add(centerDecks);
		panel.add(centerPanelConstrain, BorderLayout.CENTER);
		
		
		JPanel leftPlayer = new JPanel(new GridBagLayout());
		leftPlayer.setBackground(MakaoStatic.BACKGROUND_GREEN);
		JButton leftPlayerCard = new JButton();
		leftPlayerCard.setIcon(MakaoStatic.CARD_BACK);
		leftPlayerCard.setPreferredSize(MakaoStatic.CARD_SIZE);
		leftPlayer.add(leftPlayerCard);
		panel.add(leftPlayer, BorderLayout.WEST);
		
		JPanel rightPlayer = new JPanel(new GridBagLayout());
		rightPlayer.setBackground(MakaoStatic.BACKGROUND_GREEN);
		JButton rightPlayerCard = new JButton();
		rightPlayerCard.setIcon(MakaoStatic.CARD_BACK);
		rightPlayerCard.setPreferredSize(MakaoStatic.CARD_SIZE);
		rightPlayer.add(rightPlayerCard);
		panel.add(rightPlayer, BorderLayout.EAST);
		
		JPanel topPlayer = new JPanel(new GridBagLayout());
		topPlayer.setBackground(MakaoStatic.BACKGROUND_GREEN);
		JButton topPlayerCard = new JButton();
		topPlayerCard.setIcon(MakaoStatic.CARD_BACK);
		topPlayerCard.setPreferredSize(MakaoStatic.CARD_SIZE);
		topPlayer.add(topPlayerCard);
		panel.add(topPlayer, BorderLayout.NORTH);
		
		
		
		mainPanel.add(panel, BorderLayout.CENTER);
		rightPanel = new TextMessagePanel(actionQueue);
		
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		CardDeckPanel cardDock = new CardDeckPanel();
		cardDock.setBackgroundImage(MakaoStatic.CARD_DOCK_BACKGROUND_IMAGE);
		cardDock.setBackground(new Color(0,0,0));
		for(int i=0; i<13; i++){
			JButton but = new JButton();
			but.setPreferredSize(MakaoStatic.CARD_SIZE);
			but.setIcon(MakaoStatic.CARD_ICONS[0][i]);
			cardDock.add(but);		
		}
		JScrollPane cardDockScroll = new JScrollPane(cardDock);
		cardDock.setAutoscrolls(true);
		cardDockScroll.setPreferredSize(new Dimension(0,133));
		mainPanel.add(cardDockScroll, BorderLayout.SOUTH);
		

		JMenuBar menubar = new JMenuBar();
        //ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));

        JMenu file = new JMenu("GRA");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", null);
        JMenuItem eMenuItem2 = new JMenuItem("GRA", null);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem2.setToolTipText("Po¸ˆcz si« z serwerem gry");
        eMenuItem2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		actionQueue.add(MakaoActions.OPEN_CONFIG_DIALOG);
            }
        });
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem2);
        file.add(eMenuItem);
        

        menubar.add(file);

        setJMenuBar(menubar);

	}

	public void actionPerformed(ActionEvent e) {
		//chatTextArea.setText(chatTextArea.getText()+chatTextField.getText()+"\n");
		//chatTextField.setText("");
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
