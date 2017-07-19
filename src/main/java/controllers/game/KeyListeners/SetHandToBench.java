package controllers.game.KeyListeners;

import card.Card;
import card.Pokemon;
import controllers.card.CardController;
import controllers.game.GameController;
import javafx.util.Pair;
import views.card.CardView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 07-Jun-2017.
 */
public class SetHandToBench implements KeyListener {

    private GameController controller;

    public SetHandToBench(GameController gameController){
        this.controller = gameController;
        controller.getView().setCommand("Choose Pokemon from hand\n(Click on a pokemon and hit ENTER)\n(Press Esc to exit)");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {

                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());

                Card pokCard = controller.findCardInContainer(chosenCard, controller.getHumanController().getHandController()).getCard();
                Pair<CardController, CardView> pair = controller.getHumanController().getHandController().removeCard(pokCard);
                controller.getHumanController().getBenchController().addCard((Pokemon)pair.getKey().getCard());
                controller.getHumanController().getBenchController().returnAllCards();

                controller.getHumanController().getHandController().removeAllListeners(this);

                if (controller.getHumanController().handHasPokemon() && controller.isFirstTurn() &&
                        !controller.getHumanController().getBenchController().isFull()) {

                    controller.getView().setCommand("Options:\n" +
                                                    "1. Add pokemon to your bench\n" +
                                                    "2. End Turn");
                    controller.getView().addBoardListerner(new ChooseBenchPok(controller));

                }else if (controller.isFirstTurn()){

                    controller.endFirstTurn();

                }else{

                    controller.decideNextAction();

                }
                break;
            }
            default:{
                System.out.println("Enter the correct Key.");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
