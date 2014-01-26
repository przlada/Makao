package makao;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import makao.controller.Controller;
import makao.model.Model;
import makao.view.View;
import makao.view.actions.MakaoActions;

/**
 * G¸—wna klasa aplikacji tworzˆca Model Widok i Kontroler
 * @author przemek
 */
public class JMakao {
	public static void main(String[] args) {
		final BlockingQueue<MakaoActions> actionQueue = new LinkedBlockingQueue<MakaoActions>();
		View view = new View(actionQueue);
		Model model = new Model();
		Controller controller = new Controller(view, model, actionQueue);
		model.setController(controller);
		controller.start();
	}

}
