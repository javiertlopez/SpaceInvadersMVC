

import Controller.GameController;
import Model.GameModel;
import View.GameView;

/**
 * Created by javier on 5/2/16.
 */
public class Main {
	public static void main(String []args) {
		GameView gameView = new GameView();
		GameModel gameModel = new GameModel();
		GameController gameController = new GameController(gameView, gameModel);

		gameController.initGame();
	}
}
