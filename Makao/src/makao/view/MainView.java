package makao.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;




public class MainView extends JFrame implements ActionListener{
	private JTextArea chatTextArea;
	private JTextField chatTextField;
	
	public MainView() {
		//super("Makao");
		setTitle("Makao");
		setSize(800, 600);
		setLocationRelativeTo(null);
		//setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container mainPanel = getContentPane();

		JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension(620, 0));
		panel.setBackground(new Color(12,176,50));
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
		for(int i=0; i<15; i++){
			JButton but = new JButton("karta "+i);
			but.setPreferredSize(new Dimension(70, 80));
			cardDock.add(but);			
		}
		JScrollPane cardDockScroll = new JScrollPane(cardDock);
		cardDock.setAutoscrolls(true);
		cardDockScroll.setPreferredSize(new Dimension(0,110));
		mainPanel.add(cardDockScroll, BorderLayout.SOUTH);
		
		
		
		

		JButton quitButton = new JButton("Quit");
		quitButton.setPreferredSize(new Dimension(200, 100));
		//quitButton.setBounds(50, 60, 80, 30);

		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		panel.add(quitButton, BorderLayout.CENTER);
		
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
	}
	

}
