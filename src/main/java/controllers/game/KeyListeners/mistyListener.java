package controllers.game.KeyListeners;

import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.card.PokemonController;
import controllers.game.GameController;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;
import views.card.TrainerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by zc on 2017/7/23.
 */
public class mistyListener implements KeyListener {
    private GameController controller;
    private Card card;
    private int sourceAmount;
    private int destAmount;

    public mistyListener(GameController controller, Card card) {
        this.controller = controller;
        this.card = card;

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER: {
                int index = 0;
                int size = controller.getHumanController().getDeckController().getCardContainer().getCards().size();
                StringBuilder builder = new StringBuilder("Select card from hand by pressing:\n");
                if(size<=8) {
                    for (Card cardtmp : controller.getHumanController().getDeckController().getCardContainer().getCards()) {
                        builder.append(index++ + ": "
                                + cardtmp.getName() + " "
                                + "(" + cardtmp.getClass().getSimpleName() + ")"
                                + "\n  ");
                    }
                    controller.getView().setCommand(builder.toString() + "\n");
                }
                else{
                    for(int i = 0;i<8;i++){
                        Card cardtmp =  controller.getHumanController().getDeckController().getCardContainer().getCards().get(i);
                        builder.append(index++ + ": "
                                + cardtmp.getName() + " "
                                + "(" + cardtmp.getClass().getSimpleName() + ")"
                                + "\n");
                    }
                    controller.getView().setCommand(builder.toString() + "\n");
                }
                CardView chosenCardm = (CardView) SwingUtilities.getAncestorOfClass(CardView.class, (Component) e.getSource());

                Card cardm = controller.findCardInContainer(chosenCardm, controller.getHumanController().getHandController()).getCard();

                                          controller.getHumanController().getHandController().removeCard(cardm);
                       controller.getHumanController().getDiscardPileController().addCard(cardm);
                break;
            }

            case KeyEvent.VK_0:
            case KeyEvent.VK_NUMPAD0:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>0) {
                    controller.getHumanController().getHandController().addCard(controller.getHumanController().getDeckController().getCardContainer().removeCard(0));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);
                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>1) {
                    controller.getHumanController().getHandController().addCard(controller.getHumanController().getDeckController().getCardContainer().removeCard(1));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }

            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>2) {
                    controller.getHumanController().getHandController().addCard(controller.getHumanController().getDeckController().getCardContainer().removeCard(2));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }
            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>3) {
                    controller.getHumanController().getHandController().addCard(controller.getHumanController().getDeckController().getCardContainer().removeCard(3));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }
            case KeyEvent.VK_4:
            case KeyEvent.VK_NUMPAD4:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>4) {
                    controller.getHumanController().getHandController().addCard(
                            controller.getHumanController().getDeckController().getCardContainer().removeCard(4));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }

            case KeyEvent.VK_5:
            case KeyEvent.VK_NUMPAD5:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>5) {
                    controller.getHumanController().getHandController().addCard(
                            controller.getHumanController().getDeckController().getCardContainer().removeCard(5));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }
            case KeyEvent.VK_6:
            case KeyEvent.VK_NUMPAD6:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>6) {
                    controller.getHumanController().getHandController().addCard(
                            controller.getHumanController().getDeckController().getCardContainer().removeCard(6));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }

            case KeyEvent.VK_7:
            case KeyEvent.VK_NUMPAD7:{
                if (controller.getHumanController().getDeckController().getCardContainer().getNoOfCards()>7) {
                    controller.getHumanController().getHandController().addCard(
                            controller.getHumanController().getDeckController().getCardContainer().removeCard(7));
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
                else {
                    controller.getHumanController().getHandController().returnAllCards();
                    controller.getHumanController().getDeckController().shuffleDeck();
                    controller.getHumanController().setChosingCard(false);
                    controller.getHumanController().getHandController().removeAllListeners(this);

                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }
            }


            default: {
                System.out.println("Enter the correct Key.(from mistyListener)");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
