package controllers.game.KeyListeners;

import card.Card;
import card.Pokemon;
import card.Trainer;
import controllers.card.CardController;
import controllers.game.GameController;
import javafx.util.Pair;
import views.card.CardView;
import views.card.PokemonView;
import views.card.TrainerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by zc on 2017/7/14.
 */
public class UseTrainerCard implements KeyListener {
    private GameController controller;

    public UseTrainerCard(GameController gameController) {
        this.controller = gameController;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {

                TrainerView chosenCard = (TrainerView) SwingUtilities.getAncestorOfClass(TrainerView.class, (Component) e.getSource());

                Card trainCard = controller.findCardInContainer(chosenCard, controller.getHumanController().getHandController()).getCard();
                Pair<CardController, CardView> pair = controller.getHumanController().getHandController().removeCard(trainCard);
                controller.getHumanController().getDiscardPileController().addCard((Trainer) pair.getKey().getCard());

                int amount = 3;
                for (int i = 0; i < amount; i++) {
                    controller.getHumanController().dealDeckHand();
                }
                controller.getHumanController().getHandController().returnAllCards();
                controller.getHumanController().getHandController().removeAllListeners(this);
                // Remove all key listeners of this type and go back to menu
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
