package controllers.game.KeyListeners;

import card.Card;
import cardcontainer.CardContainer;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.cardcontainer.CardContainerController;
import controllers.game.GameController;
import javafx.util.Pair;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ListenerActivePok implements KeyListener{

    private GameController controller;
    private CardContainerController appliedContainer;

    public ListenerActivePok(GameController controller, CardContainerController usedContainer){
        this.controller = controller;
        this.appliedContainer = usedContainer;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {

                // Get pokemon from the source the enter key was hit
                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());

                // Search for pokemon controller
                Card pokCard = controller.findCardInContainer(chosenCard, appliedContainer).getCard();

                // Remove from hand and set as active for both player controller and in the view
                Pair<CardController, CardView> pair = appliedContainer.removeCard(pokCard);
                ActivePokemonView activePokemonView = controller.getHumanController().
                        setActivePokemon(true, (PokemonController) pair.getKey(), (PokemonView) pair.getValue());
                controller.getView().setPlayerActive(activePokemonView);

                // Remove all key listeners of this type for the hand & bench
                appliedContainer.removeAllListeners(this);

                controller.getHumanController().getActivePokemonController().removeKeyListener(this);

                // Check if has more pokemon to add bench
                if (controller.getHumanController().handHasPokemon() && controller.isFirstTurn()) {

                    controller.getView().addBoardListerner(new ChooseBenchPok(controller));

                } else if (controller.isFirstTurn()) {

                    // End the first turn if it is the first turn
                    controller.endFirstTurn();

                } else {

                    controller.getView().addBoardListerner(new MainMenuListener(controller));

                }

                break;
            }
            default: {
                System.out.println("Press Again.");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
