import controllers.game.GameController;
import views.game.GameView;


public class Main {

    public static void main(String[] args) {

        GameView mainView = new GameView();
        GameController mainControl = new GameController(mainView);

    }

}
