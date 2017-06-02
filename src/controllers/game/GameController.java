package controllers.game;

import card.Card;
import card.Pokemon;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.player.AIPlayerController;
import controllers.player.HumanPlayerController;
import controllers.player.PlayerController;
import javafx.util.Pair;
import views.ChoiceDialog;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.game.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameController {

    private GameView view;
    private HumanPlayerController player1Controller;
    private AIPlayerController player2Controller;

    public GameController(GameView newView) {

        view = newView;
        player1Controller = new HumanPlayerController();
        player2Controller = new AIPlayerController();
        view.setVisible(true);
        displayChoiceDialog();

    }

    public void displayChoiceDialog() {

        ChoiceDialog dialog = new ChoiceDialog();

        dialog.getBtnLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    player1Controller.getPlayer().getDeck().populateDeck(dialog.getP1DeckFile());
                    player2Controller.getPlayer().getDeck().populateDeck(dialog.getP2DeckFile());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(0);
                }
                player1Controller.getPlayer().setName(dialog.getP1Name());
                player2Controller.getPlayer().setName(dialog.getP2Name());
                System.out.println(player1Controller.getPlayer().getName());
                loadBoard();
                dialog.setVisible(false);
            }
        });

    }

    public void loadBoard() {

        player1Controller.buildViewController();
        view.setPlayerViews((DeckView) player1Controller.getDeckController().getView(),
                (HandView) player1Controller.getHandController().getView(),
                (DiscardPileView) player1Controller.getDiscardPileController().getView(),
                (BenchView) player1Controller.getBenchController().getView(),
                player1Controller.getCoinController().getView());

        player1Controller.initiateGame();
        player1Controller.getHandController().returnAllCards();

        player2Controller.buildViewController();
        view.setOpponentViews((DeckView) player2Controller.getDeckController().getView(),
                (HandView) player2Controller.getHandController().getView(),
                (DiscardPileView) player2Controller.getDiscardPileController().getView(),
                (BenchView) player2Controller.getBenchController().getView(),
                player2Controller.getCoinController().getView());

        player2Controller.initiateGame();

        playerChooseActive();

    }

    public void playerChooseActive() {

        view.setCommand("Choose Active Pokemon (Click on a pokemon and hit enter)");

        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:{

                        PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component)e.getSource());
                        for (CardController cardController: player1Controller.getHandController().getCardControllers()){
                            if (cardController.getView() == chosenCard){
                                Pair<CardController, CardView> pair = player1Controller.getHandController().removeCard(cardController.getCard());
                                ActivePokemonView activePokemonView = ((HumanPlayerController) player1Controller).
                                        setActivePokemon(true, (PokemonController) pair.getKey(), (PokemonView) pair.getValue());
                                view.setPlayerActive(activePokemonView);
                                break;
                            }
                        }
                        player1Controller.getHandController().removeCardActiveListener(this);
                        view.setCommand("AI is playing");
                        view.setOpponentActive(player2Controller.setActivePokemon(true));
                        break;
                    }
                    default:{
                        System.out.println("Press Again.");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        player1Controller.getHandController().setActiveListener(listener);


    }


}
