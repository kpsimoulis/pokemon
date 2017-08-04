package controllers.game.KeyListeners;

import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.cardcontainer.CardContainerController;
import controllers.game.GameController;
import javafx.util.Pair;
import views.card.CardView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by zc on 2017/7/2.
 */
public class EvolveListener implements KeyListener {
    private GameController controller;
    private CardContainerController appliedContainer;
    private ArrayList<Pokemon> stage1Pokemons;

    public EvolveListener(GameController controller, CardContainerController appliedContainer, ArrayList<Pokemon> stage1Pokemons) {
        this.controller = controller;
        this.appliedContainer = appliedContainer;
        this.stage1Pokemons = stage1Pokemons;

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {
                StringBuilder builder = new StringBuilder("");
                // Get pokemon from the source the enter key was hit
                PokemonView chosenCard = (PokemonView) SwingUtilities.
                        getAncestorOfClass(PokemonView.class, (Component) e.getSource());

                // Search for pokemon controller
                Card basicCard = controller.findCardInContainer(chosenCard, appliedContainer).getCard();
                Pokemon stage1Pokemon = null;
                for (Pokemon pok : stage1Pokemons) {
                    if (pok.getEvolvesFrom().equals(basicCard.getName())) {
                        stage1Pokemon = pok;
                        break;
                    }
                }
                // Remove from container and set as active for both player controller and in the view
                Pair<CardController, CardView> pairStage1 = controller.getHumanController().getHandController().removeCard(stage1Pokemon);
                Pair<CardController, CardView> pairBasic = appliedContainer.removeCard(basicCard);

                //get pokemon and damage point from activepokemon controller
                ArrayList<Energy> energies =
                        ((Pokemon) pairBasic.getKey().getCard()).getEnergy();
                int exDamage = ((Pokemon) pairBasic.getKey().getCard()).getDamagePoints();
                //TODO: Detach items and clear the stat

                //rewrite the information from ex-activepokemon
                ((PokemonController) pairStage1.getKey()).causeDamage(exDamage);
                for (Energy eng : energies) {
                    ((PokemonController) pairStage1.getKey()).addEnergy(eng);
                }
                ((Pokemon) pairBasic.getKey().getCard()).emptyEnergy();
                ((Pokemon) pairStage1.getKey().getCard()).attachPokemon((Pokemon) basicCard);
                appliedContainer.addCard(pairStage1.getKey().getCard());
                appliedContainer.returnAllCards();

                // Remove all key listeners of this type and go back to menu
                appliedContainer.removeAllListeners(this);
                controller.getView().addBoardListerner(new MainMenuListener(controller));
                break;

            }
            default: {
                System.out.println("Press Again. From evolvelistener");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
