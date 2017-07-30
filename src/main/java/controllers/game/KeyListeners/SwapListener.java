package controllers.game.KeyListeners;

import card.Card;
import card.Energy;
import card.Pokemon;
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
import java.util.ArrayList;

/**
 * Created by zc on 2017/6/23.
 */
public class SwapListener implements KeyListener {

    private GameController controller;
    private CardContainerController appliedContainer;

    public SwapListener(GameController controller,CardContainerController usedContainer) {
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
                //get pokemon and damage point from activepokemon controller
                Pokemon exActive = new Pokemon(controller.getHumanController().getActivePokemonCard());
                int exDamage = controller.getHumanController().getActivePokemonCard().getDamagePoints();
                controller.getHumanController().setActivePokemonController(null);
                controller.getHumanController().getPlayer().removeActivePokemon();

                //TODO: Detach items and clear the stat
                //DiscardPileController().addCard(Items);
                //exActive.removeStat;
                //exActive.removeItems;

                //clear active panel
                controller.getView().getBoard().getPlayerActivePanel().removeAll();

                // Get pokemon from the source the enter key was hit
                PokemonView chosenCard = (PokemonView) SwingUtilities.
                        getAncestorOfClass(PokemonView.class, (Component) e.getSource());

                // Search for pokemon controller
                Card pokCard = controller.findCardInContainer(chosenCard, appliedContainer).getCard();

                // Remove from container and set as active for both player controller and in the view
                Pair<CardController, CardView> pair = appliedContainer.removeCard(pokCard);
                ActivePokemonView activePokemonView = controller.getHumanController().
                        setActivePokemon(true, (PokemonController) pair.getKey(), (PokemonView) pair.getValue());
                controller.getView().setPlayerActive(activePokemonView);

                //rewrite the information from ex-activepokemon
                exActive.setDamagePoints(exDamage);
                controller.getHumanController().getBenchController().addCard(exActive);
                controller.getHumanController().getBenchController().returnAllCards();


                // Remove all key listeners of this type and go back to menu
                appliedContainer.removeAllListeners(this);
                controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                controller.getView().addBoardListerner(new MainMenuListener(controller));

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
