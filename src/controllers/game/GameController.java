package controllers.game;

import card.Pokemon;
import controllers.card.CardController;
import controllers.cardcontainer.CardContainerController;
import controllers.game.KeyListeners.ListenerActivePok;
import controllers.game.KeyListeners.MainMenuListener;
import controllers.player.AIPlayerController;
import controllers.player.HumanPlayerController;
import controllers.player.PlayerController;
import main.Attack;
import views.ChoiceDialog;
import views.card.CardView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.cardpiles.PrizeCardView;
import views.game.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;

/**
 * The Game Controller Class
 * This class contains two player controllers and a game view. This class will be the main class that will control the game.
 * It will start by initiating the player controllers, views and setting the choice dialog. Once the user choose the options
 * in the dialog, the game will start by loading the deck and distribute cards. The player will then be able to choose an active
 * pokemon followed by bench pokemon. After ending the turn, the AI will also choose his active pokemon as well as his benched pokemon.
 * A menu will then be displayed for the player to know the options to choose for different actions (e.g. add energy, add pokemon to bench
 * or end turn). For each turn, a card will be dealt from the deck and added to the Hand of each player.
 */
public class GameController {

    private GameView view;
    private HumanPlayerController player1Controller;
    private AIPlayerController player2Controller;
    private boolean firstTurn;
    private boolean energyAdded;

    public GameController(GameView newView) {

        view = newView;
        player1Controller = new HumanPlayerController();
        player2Controller = new AIPlayerController();
        view.setVisible(true);
        displayChoiceDialog();
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    public boolean isEnergyAdded() {
        return energyAdded;
    }

    public void setEnergyAdded(boolean energyAdded) {
        this.energyAdded = energyAdded;
    }

    public GameView getView() {
        return view;
    }

    public HumanPlayerController getHumanController() {
        return player1Controller;
    }

    public AIPlayerController getAIController() {
        return player2Controller;
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
                player1Controller.getCoinController().getView(),
                (PrizeCardView) player1Controller.getPrizeCardController().getView());

        player1Controller.initiateGame();
        player1Controller.getHandController().returnAllCards();

        player2Controller.buildViewController();
        view.setOpponentViews((DeckView) player2Controller.getDeckController().getView(),
                (HandView) player2Controller.getHandController().getView(),
                (DiscardPileView) player2Controller.getDiscardPileController().getView(),
                (BenchView) player2Controller.getBenchController().getView(),
                player2Controller.getCoinController().getView(),
                (PrizeCardView) player2Controller.getPrizeCardController().getView());

        player2Controller.initiateGame();

        playerChooseActive();

    }

    public void playerChooseActive() {

        view.setCommand("Choose Active Pokemon (Click on a pokemon and hit enter)");
        firstTurn = true;
        player1Controller.getHandController().setPokemonListener(new ListenerActivePok(this));

    }

    public void endFirstTurn() {

        view.setCommand("AI is playing");
        view.setOpponentActive(player2Controller.setActivePokemon(true));
        player2Controller.getActivePokemonController().returnCard();
        firstTurn = false;

        player2Controller.putPokemonOnBench();
        player2Controller.getBenchController().returnAllCards();

        startGame();
    }

    private void startGame() {

        playerDealDeck(player1Controller);
        decideNextAction();
        energyAdded = false;

    }

    public void decideNextAction() {

        view.addBoardListerner(new MainMenuListener(this));
    }

    public void playerDealDeck(PlayerController playerController) {

        playerController.dealDeck();
        if (playerController instanceof HumanPlayerController) {
            playerController.getHandController().returnAllCards();
        }

    }

    public CardController findCardInContainer(CardView view, CardContainerController container) {

        for (CardController cardController : container.getCardControllers()) {
            if (cardController.getView() == view) {
                return cardController;
            }
        }
        throw new NullPointerException();

    }

    public boolean gameAITurn() {

        StringBuilder sb = new StringBuilder();
        Pokemon card1 = player1Controller.getActivePokemonCard();
        Pokemon card2 = player2Controller.getActivePokemonCard();
        Attack attack2 = card2.getAttack().get(0);

        sb.append("AI is playing...\n");
        playerDealDeck(player2Controller);
        player2Controller.attack(player1Controller.getActivePokemonController());
        sb.append("Attack caused: ").append(attack2.getAbility().getDamage()).append("\nTurn Ended.\n");
        if (card1.getHealthPoints() <= card1.getDamagePoints()) {
            sb.append("Computer won\n");
            view.setCommand(sb.toString());
            return true;
        }

        sb.append("Press Enter to continue.");
        view.setCommand(sb.toString());

        view.addBoardListerner(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {
                        playerDealDeck(player1Controller);
                        decideNextAction();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        return false;
    }

    public void endGame() {

        getView().disableKeyListener();

    }
}
