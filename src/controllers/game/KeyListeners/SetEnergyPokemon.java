package controllers.game.KeyListeners;

import card.Energy;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.game.GameController;
import javafx.util.Pair;
import views.card.CardView;
import views.card.EnergyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 07-Jun-2017.
 */
public class SetEnergyPokemon implements KeyListener {

    private GameController controller;
    private PokemonController pokemonController;

    public SetEnergyPokemon(GameController controller, PokemonController pokemonController){

        this.controller = controller;
        this.pokemonController = pokemonController;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {

                EnergyView chosenCardView = (EnergyView) SwingUtilities.getAncestorOfClass(EnergyView.class, (Component) e.getSource());

                Energy card = (Energy) controller.findCardInContainer(chosenCardView, controller.getHumanController().getHandController()).getCard();
                controller.getHumanController().getHandController().removeCard(card);

                pokemonController.addEnergy(card);

                controller.getHumanController().getHandController().removeAllListeners(this);

                controller.setEnergyAdded(true);

                controller.getView().addBoardListerner(new MainMenuListener(controller));
                break;

            }
            default: {
                System.out.println("Press correct key.");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
