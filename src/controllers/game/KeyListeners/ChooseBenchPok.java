package controllers.game.KeyListeners;

import controllers.game.GameController;
import views.ChoiceDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 07-Jun-2017.
 */
public class ChooseBenchPok implements KeyListener {

    private GameController controller;

    public ChooseBenchPok(GameController controller){
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1: {

                controller.getView().disableKeyListener();
                controller.getHumanController().getHandController().setPokemonListener(new SetHandToBench(controller));
                break;

            }
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2: {

                if (controller.isFirstTurn()){
                    controller.endFirstTurn();
                }else{
                    controller.decideNextAction();
                }
                break;

            }
            default: {
                System.out.println("Incorrect Key Pressed.");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
