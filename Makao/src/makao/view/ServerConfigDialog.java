package makao.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;


public class ServerConfigDialog extends JDialog{

	public ServerConfigDialog() {

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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 150);
    }
}