package makao.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
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




public class MainView extends JFrame implements ActionListener{
	private JTextArea chatTextArea;
	private JTextField chatTextField;
	
	public MainView() {
		//super("Makao");
		setTitle("Makao");
		setSize(MakaoStatic.MAIN_FRAME_SIZE);
		setLocationRelativeTo(null);
		//setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container mainPanel = getContentPane();
		mainPanel.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension(600, 0));
		
		panel.setBackground(new Color(12,176,50));
		panel.setLayout(new BorderLayout());
		
		
		JPanel centerDecks = new JPanel();
		centerDecks.setPreferredSize(new Dimension(160, 110));
		JButton deckNew = new JButton();
		JButton deckOld = new JButton();
		deckNew.setPreferredSize(new Dimension(73, 98));
		deckOld.setPreferredSize(new Dimension(73, 98));
		deckNew.setIcon(MakaoStatic.CARD_ICONS[0][0]);
		deckOld.setIcon(MakaoStatic.CARD_BACK);
		centerDecks.add(deckNew);
		centerDecks.add(deckOld);
		JPanel centerPanelConstrain = new JPanel(new GridBagLayout());
		centerPanelConstrain.setBackground(new Color(12,176,50));
		centerPanelConstrain.add(centerDecks);
		panel.add(centerPanelConstrain, BorderLayout.CENTER);
		
		
		JPanel leftPlayer = new JPanel(new GridBagLayout());
		leftPlayer.setBackground(new Color(12,176,50));
		JButton leftPlayerCard = new JButton();
		leftPlayerCard.setIcon(MakaoStatic.CARD_BACK);
		leftPlayerCard.setPreferredSize(new Dimension(73, 98));
		leftPlayer.add(leftPlayerCard);
		panel.add(leftPlayer, BorderLayout.WEST);
		
		JPanel rightPlayer = new JPanel(new GridBagLayout());
		rightPlayer.setBackground(new Color(12,176,50));
		JButton rightPlayerCard = new JButton();
		rightPlayerCard.setIcon(MakaoStatic.CARD_BACK);
		rightPlayerCard.setPreferredSize(new Dimension(73, 98));
		rightPlayer.add(rightPlayerCard);
		panel.add(rightPlayer, BorderLayout.EAST);
		
		JPanel topPlayer = new JPanel(new GridBagLayout());
		topPlayer.setBackground(new Color(12,176,50));
		JButton topPlayerCard = new JButton();
		topPlayerCard.setIcon(MakaoStatic.CARD_BACK);
		topPlayerCard.setPreferredSize(new Dimension(73, 98));
		topPlayer.add(topPlayerCard);
		panel.add(topPlayer, BorderLayout.NORTH);
		
		
		
		mainPanel.add(panel, BorderLayout.CENTER);
		
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		chatPanel.setPreferredSize(new Dimension(180, 0));
		//chatPanel.setBackground(new Color(255,255,50));
		
		chatTextArea = new JTextArea(0,10);
		chatTextArea.setLineWrap(true);
		chatTextArea.setWrapStyleWord(true);
		chatTextArea.setColumns(10);
		chatTextArea.setEditable(false);
		//JScrollPane chatTextAreaScroll = new JScrollPane(chatTextArea);
		JScrollPane chatTextAreaScroll = new JScrollPane(chatTextArea);
		chatTextAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//chatTextAreaScroll.setPreferredSize(new Dimension(180,200));
		JPanel chatControll = new JPanel();
		chatControll.setLayout(new GridLayout(2, 1));
		//chatControll.setLayout(new BoxLayout(chatControll, BoxLayout.Y_AXIS));
		chatTextField = new JTextField(20);
		JButton sendButton = new JButton("Wyælij");
		sendButton.addActionListener(this);
		chatControll.add(chatTextField);
		chatControll.add(sendButton);
		
		
        chatPanel.add(chatTextAreaScroll, BorderLayout.CENTER);
        chatPanel.add(chatControll, BorderLayout.SOUTH);
        //chatPanel.add(sendButton, BorderLayout.AFTER_LAST_LINE);
 
		
		
		//chatTextAreaScroll, BorderLayout.CENTER);
		//chatPanel.add(chatTextField, BorderLayout.SOUTH);
		
		mainPanel.add(chatPanel, BorderLayout.EAST);
		
		JPanel cardDock = new JPanel();
		cardDock.setBackground(new Color(0,0,0));
		for(int i=0; i<13; i++){
			JButton but = new JButton();
			but.setPreferredSize(new Dimension(73, 98));
			but.setIcon(MakaoStatic.CARD_ICONS[0][i]);
			cardDock.add(but);		
		}
		JScrollPane cardDockScroll = new JScrollPane(cardDock);
		cardDock.setAutoscrolls(true);
		cardDockScroll.setPreferredSize(new Dimension(0,133));
		mainPanel.add(cardDockScroll, BorderLayout.SOUTH);
		
		
		
		

		//JButton quitButton = new JButton("Quit");
		//quitButton.setPreferredSize(new Dimension(200, 100));
		//quitButton.setBounds(50, 60, 80, 30);

		//quitButton.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent event) {
				//System.exit(0);
			//}
		//});

		//panel.add(quitButton, BorderLayout.CENTER);
		
		
		JMenuBar menubar = new JMenuBar();
        //ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", null);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem);

        menubar.add(file);

        setJMenuBar(menubar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chatTextArea.setText(chatTextArea.getText()+chatTextField.getText()+"\n");
		chatTextField.setText("");
		AboutDialog ad = new AboutDialog();
        ad.setVisible(true);
	}
	

}
