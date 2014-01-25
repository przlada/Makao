package makao.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import makao.view.actions.MakaoActions;

public class MainMakaoMenuBar extends JMenuBar {
	private final BlockingQueue<MakaoActions> actionQueue;

	public MainMakaoMenuBar(final BlockingQueue<MakaoActions> actionQueue) {
		this.actionQueue = actionQueue;
		JMenu gameMenu = new JMenu("GRA");
		// file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("WYJÂCIE", null);
		JMenuItem eMenuItem2 = new JMenuItem("GRA", null);
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Koniec");
		eMenuItem2.setToolTipText("GRA");
		eMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				actionQueue.add(MakaoActions.OPEN_CONFIG_DIALOG);
			}
		});
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				actionQueue.add(MakaoActions.EXIT_APPLICATION);
			}
		});

		gameMenu.add(eMenuItem2);
		gameMenu.add(eMenuItem);

		add(gameMenu);
	}
}
