package makao.view;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import makao.MakaoStatic;
import makao.model.MakaoCard;
import makao.model.MakaoPlayer;
import makao.model.ModelDummy;
import makao.model.TextMessage;
import makao.model.TextMessage.Type;
import makao.view.actions.MakaoActions;

public class View {
	private MainView main;
	private ConfigMenuDialog configMenuDialog;
	private final BlockingQueue<MakaoActions> actionQueue;
	public View(BlockingQueue<MakaoActions> actionQueue){
		this.actionQueue = actionQueue;
		main = new MainView(actionQueue);
		configMenuDialog = new ConfigMenuDialog(actionQueue);
		main.setVisible(true);
	}
	
	public void closing() {
		Object[] options = { "Tak", "Nie" };
		int ans = JOptionPane.showOptionDialog(main, "Czy napewno chcesz zakonczyc gr«", "Czy koniec gry?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, null);
		if (JOptionPane.YES_OPTION == ans) {
			main.dispose();
			System.exit(0);
		}
	}
	public void addTextMessage(String message){
		main.addTextMessage(message);
	}
	
	public void showConfigDialog(){
		configMenuDialog.setVisible(true);
	}
	public void closeConfigDialog(){
		configMenuDialog.dispose();
	}
	public String getTextMessage(){
		return main.getTextMessage();
	}
	public String getHostAddress(){
		return configMenuDialog.getHostAddress();
	}
	public String getPlayerNick(){
		return configMenuDialog.getPlayerNick();
	}
	public MakaoCard getSelectedPlayerCard(){
		return main.getSelectedPlayerCard();
	}
	public void haveToStartGame(boolean haveTo){
		main.haveToStartGame(haveTo);
	}
	public void drawModelDummy(ModelDummy dummy){
		for(TextMessage msg : dummy.getTekstMessages())
			addTextMessage(msg.toString());
		main.setNewCard(dummy.getLastPlayed());
		main.drawPlayers(dummy.getMyId(), dummy.getPlayers());
	}
}
