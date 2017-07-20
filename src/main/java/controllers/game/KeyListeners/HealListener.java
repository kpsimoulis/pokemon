package controllers.game.KeyListeners;

import card.Card;
import controllers.card.PokemonController;
import controllers.game.GameController;
import views.activepokemon.ActivePokemonView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by zc on 2017/7/14.
 */
public class HealListener implements KeyListener {

    private GameController controller;
    private Card card;
    private int amount;

    public HealListener(GameController gameController, Card card, int amount) {
        this.controller = gameController;
        this.card = card;
        this.amount = amount;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {

                controller.getView().setCommand("S\n(Press Esc to exit)");

                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                PokemonController chosenController;

                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null) {
                    chosenController = controller.getHumanController().
                            getActivePokemonController().getPokemonController();
                } else {
                    chosenController = (PokemonController) controller.findCardInContainer(
                            chosenCard, controller.getHumanController().getBenchController());
                }

                chosenController.heal(amount);
                controller.getHumanController().getHandController().removeCard(this.card);

                // Remove listener
                controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                controller.getHumanController().getBenchController().removeAllListeners(this);
                controller.getView().addBoardListerner(new MainMenuListener(controller));
                break;
            }


            default: {
                System.out.println("Enter the correct Key.(from UseTrainerCard)");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
