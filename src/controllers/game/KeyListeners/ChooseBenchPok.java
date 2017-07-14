package controllers.game.KeyListeners;

import controllers.game.GameController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 07-Jun-2017.
 */
public class ChooseBenchPok implements KeyListener {

    private GameController controller;

    public ChooseBenchPok(GameController controller){
        this.controller = controller;
        this.controller.getView().setCommand("You can now do the following:\n" +
                "1. Add Pokemon to your bench\n" +
                "2. End Turn");
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
                SetHandToBench listener = new SetHandToBench(controller);
                controller.getHumanController().getHandController().setPokemonListener(listener);

                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getHumanController().getHandController().removeAllListeners(listener);
                            controller.getView().addBoardListerner(new ChooseBenchPok(controller));
                        }

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
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
