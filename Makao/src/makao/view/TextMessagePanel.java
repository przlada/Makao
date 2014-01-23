package makao.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import makao.view.actions.MakaoActions;

public class TextMessagePanel extends JPanel {
	private final BlockingQueue<MakaoActions> actionQueue;
	private JTextArea chatTextArea;
	private JScrollPane chatTextAreaScroll;
	private JTextField chatTextField;
	private boolean haveToStartGame = false;
	private JButton sendButton;
	private JButton makaoButton;
	private JButton endTurnButton;
	
	public TextMessagePanel(final BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(180, 0));
		
		chatTextArea = new JTextArea(0,10);
		chatTextArea.setLineWrap(true);
		chatTextArea.setWrapStyleWord(true);
		chatTextArea.setColumns(10);
		chatTextArea.setEditable(false);

		chatTextAreaScroll = new JScrollPane(chatTextArea);
		chatTextAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel chatControll = new JPanel();
		chatControll.setLayout(new GridLayout(4, 0));

		chatTextField = new JTextField(20);
		sendButton = new JButton("Wyælij");
		makaoButton = new JButton("MAKAO");
		endTurnButton = new JButton("ZAKOÁCZ KOLEJK¢");
		sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	actionQueue.add(MakaoActions.GAME_SEND_MESSAGE_TO_ALL);
            }
        });
		
		endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(haveToStartGame)
            		actionQueue.add(MakaoActions.SERVER_START_GAME);
            	else
            		actionQueue.add(MakaoActions.GAME_END_MY_TURN);
            }
        });
		
		//sendButton.addActionListener(this);
		chatControll.add(chatTextField);
		chatControll.add(sendButton);
		chatControll.add(makaoButton);
		chatControll.add(endTurnButton);
		
		
        add(chatTextAreaScroll, BorderLayout.CENTER);
        add(chatControll, BorderLayout.SOUTH);
	}
	
	public void addMessage(String msg){
		chatTextArea.append(msg+"\n");
		chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());		
	}
	public void setHaveToStartGame(boolean haveTo){
		haveToStartGame = haveTo;
		if(haveTo)
			endTurnButton.setText("ROZPOCZNIJ GR¢");
		else
			endTurnButton.setText("ZAKOÁCZ KOLEJK¢");
	}
	public String getMessage(){
		String message = chatTextField.getText();
		chatTextField.setText("");
		return message;
	}

}
