package controllers.game.KeyListeners;

import card.Card;
import card.Pokemon;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.cardpiles.PrizeCardController;
import controllers.game.GameController;
import javafx.util.Pair;
import parser.Attack;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by zc on 2017/8/4.
 */
public class RedamageAlllistener implements KeyListener {
    private GameController controller;
    private Card card;
    private PokemonController sourceController;
    private PokemonController destController;

    public RedamageAlllistener(GameController controller, StringBuilder sb) {
        this.controller = controller;
        this.card = card;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D: {
                controller.getView().setCommand("Good! Source Pokemon has been choosen. Now you can do the following:\n" +
                        "Select Pokemon and press Enter\nto move 1 damage counters on this Pokemon, if your source Pokemon donnot have damage counter, you have to go back. \n");

                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());

                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null) {
                    sourceController = controller.getAIController().
                            getActivePokemonController().getPokemonController();
                } else {
                    sourceController = (PokemonController) controller.findCardInContainer(
                            chosenCard, controller.getAIController().getBenchController());
                }
                controller.getAIController().setSourceController(sourceController);
                break;
            }

            case KeyEvent.VK_ENTER: {
                controller.getView().setCommand("Repeat moving damage counter by selceting Pokemon and press ENTER\n(Press Esc to exit)");

                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                PokemonController chosenController;

                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null) {
                    destController = controller.getAIController().
                            getActivePokemonController().getPokemonController();
                } else {
                    destController = (PokemonController) controller.findCardInContainer(
                            chosenCard, controller.getAIController().getBenchController());
                }
                controller.getAIController().setDestController(destController);

                if (controller.getAIController().getSourceController() != null && controller.getAIController().getDestController() != null) {
                    if (((Pokemon) controller.getAIController().getSourceController().getCard()).getDamagePoints() <= 9) {
                        break;
                    } else {
                        controller.getAIController().getSourceController().heal(10);
                        controller.getAIController().getDestController().causeDamage(10);
                        break;

                    }

                } else
                controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                controller.getHumanController().getBenchController().removeAllListeners(this);
                controller.getView().addBoardListerner(new MainMenuListener(controller));
                break;
            }

            case KeyEvent.VK_ESCAPE:{
                        controller.getAIController().getActivePokemonController().removeKeyListener(this);
                        controller.getAIController().getBenchController().removeAllListeners(this);
                        controller.getView().addBoardListerner(new MainMenuListener(controller));

                Attack attackCaused;
                try {
                    attackCaused = controller.getHumanController().getActivePokemonController().getPokemonController().getAttacks().get(0);
                } catch (IndexOutOfBoundsException exception) {
                    throw exception;
                }
                {

                    boolean defeatedOpp = false;
                    if (controller.getAIController().getActivePokemonCard().getDamagePoints() >=
                            controller.getAIController().getActivePokemonCard().getHealthPoints())
                        defeatedOpp = true;

                    ArrayList<CardController> benches =
                            controller.getAIController().getBenchController().getCardControllers();

                    ArrayList<Card> cardToRemove =  new ArrayList<>();

                    for(CardController con : benches){
                        Pokemon pok = (Pokemon)con.getCard();
                        if(pok.getDamagePoints()>=pok.getHealthPoints()){
                            cardToRemove.add(con.getCard()) ;
                        }
                    }
                    if(cardToRemove.size()>0){
                        for(Card card:cardToRemove){
                            controller.getAIController().getDiscardPileController().addCard(card);
                            controller.getAIController().getBenchController().removeCard(card);

                        }
                    }
                    StringBuilder sb = new StringBuilder("Great if you enjoy it.");
                    sb.append("\nAbility used: ");
                    sb.append(attackCaused.getAbility().getName()).append("\nTurn Ended.\n");

                    if (defeatedOpp) {
                        controller.getAIController().setIsPoisoned(false);
                        controller.getAIController().setStatus("normal");

                        PrizeCardController humanPrizeCard = controller.getHumanController().getPrizeCardController();
                        if (humanPrizeCard.getCardContainer().getNoOfCards() > 1) {
                            sb.append("You defeated opponent's pokemon.\n").append("Collect a prize card:\n");
                            sb.append(humanPrizeCard.getPrizeCardsNo());
                            sb.append("\n").append("Press the correct no.");
                            controller.getView().addBoardListerner(new CollectPrizeCard(controller));
                        } else {
                            Pair<CardController, CardView> pair = controller.getHumanController().getPrizeCardController().chooseCard(0);
                            if (pair != null) {
                                controller.getHumanController().getHandController().addCard(pair);
                                pair.getKey().returnBackCover();
                                controller.getView().disableKeyListener();
                            }
                            sb.append("You defeated opponent's pokemon.\n").append("You have no prize card left\n");
                            sb.append("YOU WON THE GAME");
                            controller.getView().setCommand(sb.toString());
                            controller.endGame();
                        }

                    } else {
                        sb.append("Press Enter to continue.");
                        controller.getView().addBoardListerner(new KeyListener() {
                            @Override
                            public void keyTyped(KeyEvent e) {
                            }

                            @Override
                            public void keyPressed(KeyEvent e) {
                                switch (e.getKeyCode()) {
                                    case KeyEvent.VK_ENTER: {
                                        controller.getView().disableKeyListener();
                                        controller.setEnergyAdded(false);
                                        controller.setHasRetreated(false);

                                        if (controller.getAIController().getDeckController().getCardContainer().isEmpty()) {
                                            String stringBuilder = "AI has no more cards in Deck" + "\nYOU WON THE GAME :)\n" +
                                                    "CONGRATULATIONS!!!";
                                            controller.getView().setCommand(stringBuilder);
                                            controller.endGame();
                                        } else {
                                            controller.gameAITurn();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void keyReleased(KeyEvent e) {
                            }
                        });
                    }
                    controller.getView().setCommand(sb.toString());

                }
                break;

            }

            default: {
                System.out.println("Enter the correct Key.(from RedamageListener)");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
