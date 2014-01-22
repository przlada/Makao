package makao.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import makao.MakaoStatic;
public class ConnectToServerDialog extends JDialog{

	public ConnectToServerDialog() {

        initUI();
    }

    public final void initUI() {
    	
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel name = new JLabel("Wpisz adres serwera z którym chesz si´ po∏àczyç");
        name.setAlignmentX(0.5f);
        add(name);

        add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel panel = new JPanel();
        JTextField textField = new JTextField(20);
        JButton close = new JButton("Po∏àcz");
        
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

        panel.add(textField);
        panel.add(close);
        panel.setAlignmentX(0.5f);
        
        add(panel);
        
        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("About Notes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 150);
    }
}