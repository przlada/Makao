package makao.view;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import makao.view.actions.MakaoActions;

public class ConfigMenuDialog extends JDialog {

	private final BlockingQueue<MakaoActions> actionQueue;
	private JPanel connect;
	private JPanel server;
	private JTextField textFieldAdres;
	private JTextField textFieldNick;
	private JButton connectButton;
	private JButton serverStart;
	
	private boolean connected = false;
	private boolean serverStarted = false;
	public ConfigMenuDialog(BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		initUI();
	}
	private final void initUI(){
		setTitle("MAKAO Konfiguracja");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(600, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0,2));
        
        connect = new JPanel();
        server = new JPanel(new GridLayout(3,0));
        server.add(Box.createRigidArea(new Dimension(0, 10)));
        serverStart = new JButton("Uruchom serwer");
        //serverStart.setEnabled(false);
        serverStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(!serverStarted)
            		actionQueue.add(MakaoActions.START_LOCAL_SERVER);
            }
        });
        server.add(serverStart);
        
        connect.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel adress = new JLabel("Adres serwera:");
        JLabel nick = new JLabel("Tw—j nick:");
        
        connect.add(Box.createRigidArea(new Dimension(0, 20)));
        
        textFieldAdres = new JTextField(20);
        textFieldNick = new JTextField(20);
        connectButton = new JButton("Po¸ˆcz");
        
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if(!connected)
            		actionQueue.add(MakaoActions.CONNECT_CLIENT);
            }
        });
        
        connect.add(adress);
        connect.add(textFieldAdres);
        connect.add(nick);
        connect.add(textFieldNick);
        connect.add(connectButton);
        
        add(connect);
        add(server);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                actionQueue.add(MakaoActions.CLOSE_CONFIG_DIALOG);
            }
        });
        //setEnabledConnect(true);
        //setEnabledServer(true);
	}
	/*
	public void setEnabledConnect(boolean enable){
		for (Component component : connect.getComponents())
			component.setEnabled(enable);
	}
	public void setEnabledServer(boolean enable){
		for (Component component : server.getComponents())
			component.setEnabled(enable);
	}
	*/
	public void setConnect(boolean connected){
		this.connected = connected;
		if(connected)
			connectButton.setText("Roz¸ˆcz");
		else
			connectButton.setText("Po¸ˆcz");
		validate();
		repaint();
	}
	public void setServerStarted(boolean started){
		serverStarted = started; 
		if(started)
			serverStart.setText("Wy¸ˆcz serwer");
		else
			serverStart.setText("Uruchom serwer");
		validate();
		repaint();
	}
	public String getHostAddress(){
		return textFieldAdres.getText();
	}
	public String getPlayerNick(){
		return textFieldNick.getText();
	}
	

}
