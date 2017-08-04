package controllers.game.KeyListeners;

import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.card.PokemonController;
import controllers.game.GameController;
import views.activepokemon.ActivePokemonView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by zc on 2017/7/23.
 */
public class ReenergizeListener implements KeyListener {
    private GameController controller;
    private Card card;
    private int sourceAmount;
    private int destAmount;
    private PokemonController sourceController;
    private PokemonController destController;

    public ReenergizeListener(GameController controller, Card card, int sourceAmount, int destAmount) {
        this.controller = controller;
        this.card = card;
        this.sourceAmount = sourceAmount;
        this.destAmount = destAmount;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D: {
                controller.getView().setCommand("Good! Source Pokemon has been choosen. Now you can do the following:\nSelect Pokemon and press Enter\nto attach " + sourceAmount + " energy.\n");

                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());

                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null) {
                    sourceController = controller.getHumanController().
                            getActivePokemonController().getPokemonController();
                } else {
                    sourceController = (PokemonController) controller.findCardInContainer(
                            chosenCard, controller.getHumanController().getBenchController());
                }
                controller.getHumanController().setSourceController(sourceController);
                break;
            }

            case KeyEvent.VK_ENTER: {
                controller.getView().setCommand("OOPs from ReenergizeListener\n(Press Esc to exit)");

                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                PokemonController chosenController;

                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null) {
                    destController = controller.getHumanController().
                            getActivePokemonController().getPokemonController();
                } else {
                    destController = (PokemonController) controller.findCardInContainer(
                            chosenCard, controller.getHumanController().getBenchController());
                }
                controller.getHumanController().setDestController(destController);

                if (controller.getHumanController().getSourceController() != null && controller.getHumanController().getDestController() != null) {
                    if (((Pokemon) controller.getHumanController().getSourceController().getCard()).getEnergy().size() == 0) {
                        controller.getHumanController().getHandController().removeCard(this.card);
                        controller.getHumanController().getDiscardPileController().addCard(this.card);
                        controller.getHumanController().setChosingCard(false);
                        controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                        controller.getHumanController().getBenchController().removeAllListeners(this);
                        controller.getView().addBoardListerner(new MainMenuListener(controller));
                        break;
                    } else {
                        for (int i = 0; i < destAmount; i++) {
                            controller.getHumanController().getDestController().addEnergy(controller.getHumanController().getSourceController().removeEnergy());
                        }

                        controller.getHumanController().getHandController().removeCard(this.card);
                        controller.getHumanController().getDiscardPileController().addCard(this.card);
                        controller.getHumanController().setChosingCard(false);
                        controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                        controller.getHumanController().getBenchController().removeAllListeners(this);
                        controller.getView().addBoardListerner(new MainMenuListener(controller));
                        break;

                    }

                } else
                    controller.getHumanController().setChosingCard(false);
                controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                controller.getHumanController().getBenchController().removeAllListeners(this);
                controller.getView().addBoardListerner(new MainMenuListener(controller));
                break;
            }

            default: {
                System.out.println("Enter the correct Key.(from ReenergizeListener)");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
