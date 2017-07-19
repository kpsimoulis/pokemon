package controllers.game.KeyListeners;

import controllers.game.GameController;

/**
 * Created by mikce_000 on 11-Jun-2017.
 */
public abstract class AbstractGameListener {

    protected GameController controller;

    public AbstractGameListener(GameController controller){
        this.controller = controller;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }
}
