package makao.view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JDialog;

import makao.view.actions.MakaoActions;


public class ServerConfigDialog extends JDialog{

	private final BlockingQueue<MakaoActions> actionQueue;
	public ServerConfigDialog(BlockingQueue<MakaoActions> actionQueue) {
		this.actionQueue = actionQueue;
        initUI();
    }

    public final void initUI() {
    	
        setLayout(new GridLayout(2,0));

        JButton serverControl = new JButton("Serwer Start");
        
        serverControl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        add(serverControl);
        JButton gameControl = new JButton("Rozpocznij gr«");
        add(gameControl);
        
        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("About Notes");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(400, 150);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing...");
                actionQueue.add(MakaoActions.CLOSE_CONFIG_DIALOG);
            }
        });
    }
}