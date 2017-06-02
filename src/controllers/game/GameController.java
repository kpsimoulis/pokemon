package controllers.game;

import card.Energy;
import card.Pokemon;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.player.AIPlayerController;
import controllers.player.HumanPlayerController;
import controllers.player.PlayerController;
import javafx.util.Pair;
import main.Attack;
import main.Requirement;
import views.ChoiceDialog;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.EnergyView;
import views.card.PokemonView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.cardpiles.PrizeCardView;
import views.game.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

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
    private String extraMessage = "";

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

        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {

                        PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                        for (CardController cardController : player1Controller.getHandController().getCardControllers()) {
                            if (cardController.getView() == chosenCard) {
                                Pair<CardController, CardView> pair = player1Controller.getHandController().removeCard(cardController.getCard());
                                ActivePokemonView activePokemonView = ((HumanPlayerController) player1Controller).
                                        setActivePokemon(true, (PokemonController) pair.getKey(), (PokemonView) pair.getValue());
                                view.setPlayerActive(activePokemonView);
                                break;
                            }
                        }
                        player1Controller.getHandController().removeAllListeners(this);

                        if (player1Controller.handHasPokemon()) {
                            view.setCommand("You can now do the following:\n" +
                                    "1. Add Pokemon to your bench\n" +
                                    "2. End Turn");
                            firstTurn = true;
                            setPlayerBench();
                        } else {
                            endFirstTurn();
                        }
                        break;
                    }
                    default: {
                        System.out.println("Press Again.");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

        };
        player1Controller.getHandController().setPokemonListener(listener);

    }

    private void startGame() {

        playerDealDeck(player1Controller);
        KeyListener listener = new firstMenuListener();
        view.addBoardListerner(listener);
        energyAdded = false;

    }

    private void playerDealDeck(PlayerController playerController){
        playerController.getHandController().addCard(playerController.getDeckController().dealCard().getKey().getCard());
        if (playerController instanceof HumanPlayerController){
            playerController.getHandController().returnAllCards();
        }
    }

    private void endFirstTurn() {
        view.setCommand("AI is playing");
        view.setOpponentActive(player2Controller.setActivePokemon(true));
        player2Controller.getActivePokemonController().returnCard();
        view.disableKeyListener();
        firstTurn = false;

        playerDealDeck(player2Controller);
        player2Controller.putPokemonOnBench();
        player2Controller.getBenchController().returnAllCards();

        startGame();
    }

    public void setHandToBench(PlayerController playerController) {

        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {
                        PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                        for (CardController cardController : playerController.getHandController().getCardControllers()) {
                            if (cardController.getView() == chosenCard) {
                                Pair<CardController, CardView> pair = playerController.getHandController().removeCard(cardController.getCard());
                                playerController.getBenchController().addCard((Pokemon)pair.getKey().getCard());
                                playerController.getBenchController().returnAllCards();
                                break;
                            }
                        }
                        playerController.getHandController().removeAllListeners(this);
                        if (playerController.handHasPokemon() && firstTurn){
                                view.setCommand("Options:\n" +
                                        "1. Add pokemon to your bench\n" +
                                        "2. End Turn");
                            setPlayerBench();
                        }else{
                            decideNextAction();
                        }
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
        playerController.getHandController().setPokemonListener(listener);

    }

    public void setActiveRetreat() {

        Pokemon card = (Pokemon) player1Controller.getActivePokemonController().getPokemonController().getCard();
        player1Controller.getActivePokemonController().retreatPokemon();
        player1Controller.getBenchController().addCard(card);
        player1Controller.getBenchController().returnAllCards();
        extraMessage = "Your Active Pokemon has been Retreated\n";
        view.addBoardListerner(new ChoiceMenuListener());

    }

    private void decideNextAction() {
        if (!energyAdded){
            view.addBoardListerner(new firstMenuListener());
        }else{
            view.addBoardListerner(new SecondMenuListener());
        }
    }

    public void setPlayerBench(){

        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                    case KeyEvent.VK_NUMPAD1: {
                        view.setCommand("Choose Pokemon from hand (Click on a pokemon and hit enter)");
                        view.disableKeyListener();
                        setHandToBench(player1Controller);
                        break;
                    }
                    case KeyEvent.VK_2:
                    case KeyEvent.VK_NUMPAD2: {
                        if (firstTurn){
                            endFirstTurn();
                        }else{
                            decideNextAction();
                        }
                        break;
                    }
                    default: {
                        System.out.println("Incorrect Key Pressed.");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };

        view.addBoardListerner(listener);
    }

    class firstMenuListener implements KeyListener {

        firstMenuListener() {
            StringBuilder builder = new StringBuilder();
            builder.append("You can now do the following:\n");
            builder.append("1. Add Energy to your active pokemon\n");

            if (player1Controller.handHasPokemon()){
                builder.append("2. Add Pokemon to your bench\n");
            }

            builder.append("3. End Turn");
            view.setCommand(builder.toString());
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1: {
                    view.setCommand("Select an Energy Card and press enter.");
                    KeyListener listener1 = new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {

                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            switch (e.getKeyCode()) {
                                case KeyEvent.VK_ENTER: {
                                    EnergyView chosenCard = (EnergyView) SwingUtilities.getAncestorOfClass(EnergyView.class, (Component) e.getSource());
                                    Pair<CardController, CardView> pair = null;
                                    for (CardController cardController : player1Controller.getHandController().getCardControllers()) {
                                        if (cardController.getView() == chosenCard) {
                                            pair = player1Controller.getHandController().removeCard(cardController.getCard());
                                            break;
                                        }
                                    }
                                    assert pair != null;
                                    player1Controller.getActivePokemonController().getPokemonController().addEnergy((Energy) pair.getKey().getCard());
                                    player1Controller.getHandController().removeAllListeners(this);
                                    energyAdded = true;
                                    SecondMenuListener secondMenuListener = new SecondMenuListener();
                                    view.addBoardListerner(secondMenuListener);
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
                    };
                    player1Controller.getHandController().setEnergyListener(listener1);
                    view.disableKeyListener();
                    break;
                }
                case KeyEvent.VK_2:
                case KeyEvent.VK_NUMPAD2: {
                    view.setCommand("Choose Pokemon from hand (Click on a pokemon and hit enter)");
                    view.disableKeyListener();
                    setHandToBench(player1Controller);
                    break;
                }
                case KeyEvent.VK_3:
                case KeyEvent.VK_NUMPAD3: {
                    if (firstTurn){
                        endFirstTurn();
                    }else{
                        gameAITurn(false);
                    }
                    break;
                }
                default: {
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class SecondMenuListener implements KeyListener {

        public SecondMenuListener() {
            StringBuilder builder = new StringBuilder();
            builder.append("You can now do the following:\n");

            if (player1Controller.canAttack()){
                builder.append("1. Attack with Active Pokemon\n");
            }

            if (player1Controller.handHasPokemon()){
                builder.append("2. Add Pokemon to your bench\n");
            }

            builder.append("3. End Turn");
            view.setCommand(builder.toString());
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1: {

                    StringBuilder builder = new StringBuilder("Press the corresponding number for the attacks:\n");

                    Pokemon card = (Pokemon) player1Controller.getActivePokemonController().getPokemonController().getCard();
                    HashMap<String, Integer> dict = player1Controller.getActivePokemonController().getEnergyOnCard();

                    int index = 1;
                    for (Attack attack : card.getAttack()) {
                        for (Requirement requirement : attack.getRequirement()) {
                            if (dict.containsKey(requirement.getCategory()) && dict.get(requirement.getCategory()) == requirement.getEnergyAmount()) {
                                builder.append(index).append(". ").append(attack.getAbility().getName()).append("\n");
                            }
                        }

                        index++;
                    }

                    view.setCommand(builder.toString());
                    view.addBoardListerner(new AttackMenuListener());
                    break;
                }
                case KeyEvent.VK_2:
                case KeyEvent.VK_NUMPAD2: {
                    view.setCommand("Choose Pokemon from hand (Click on a pokemon and hit enter)");
                    view.disableKeyListener();
                    setHandToBench(player1Controller);
                    break;
                }
                case KeyEvent.VK_3:
                case KeyEvent.VK_NUMPAD3:
                {
                    energyAdded = false;
                    gameAITurn(false);
                    break;
                }
                default: {
                    System.out.println("Press the correct key.");
            }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public boolean gameAITurn(boolean humanCanAttack){

        StringBuilder sb = new StringBuilder();
        Pokemon card1 = (Pokemon) player1Controller.getActivePokemonController().getPokemonController().getCard();
        Attack attack = card1.getAttack().get(0);
        Pokemon card2 = (Pokemon) player2Controller.getActivePokemonController().getPokemonController().getCard();
        Attack attack2 = card2.getAttack().get(0);
        if (humanCanAttack){
            player1Controller.getActivePokemonController().attackPokemon(
                    player2Controller.getActivePokemonController(), attack.getAbility().getDamage());
            sb.append("Attack caused: ").append(attack.getAbility().getDamage()).append("\nTurn Ended.\n");
            view.disableKeyListener();
            if (card2.getHealthPoints() <= card2.getDamagePoints()) {
                sb.append("You won\n");
                view.setCommand(sb.toString());
                return true;
            }
        }

        sb.append("AI is playing...\n");
        playerDealDeck(player2Controller);
        player2Controller.attack(player1Controller.getActivePokemonController());
        sb.append("Attack caused: ").append(attack2.getAbility().getDamage()).append("\nTurn Ended.\n");
        if (card1.getHealthPoints() <= card1.getDamagePoints()) {
            sb.append("Computer won\n");
            view.setCommand(sb.toString());
            return true;
        }

        sb.append("You can now do the following:\n" +
                "1. Re-Attack with Active Pokemon\n" +
                "E. Exit Attack Menu"
        );

        view.setCommand(sb.toString());
        playerDealDeck(player1Controller);
        view.addBoardListerner(new AttackMenuListener());

        return false;
    }

    class AttackMenuListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1: {

                    gameAITurn(true);

                    break;
                }
                case KeyEvent.VK_2:
                case KeyEvent.VK_NUMPAD2: {
                    System.out.println("2");
                    break;
                }
                case KeyEvent.VK_3:
                case KeyEvent.VK_NUMPAD3: {
                    System.out.println("3");
                    break;
                }
                case KeyEvent.VK_E: {
                    view.addBoardListerner(new ChoiceMenuListener());
                    break;
                }
                default: {
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class ChoiceMenuListener implements KeyListener {

        ChoiceMenuListener() {
            view.setCommand(extraMessage + "You can now do the following:\n" +
                    "1. Go to Energy Menu\n" +
                    "2. Go to Attack Menu\n" +
                    "3. Go to Bench Menu\n" +
                    "4. Retreat your Active Pokemon");
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1: {
                    view.addBoardListerner(new firstMenuListener());
                    break;
                }
                case KeyEvent.VK_2:
                case KeyEvent.VK_NUMPAD2: {
                    view.addBoardListerner(new SecondMenuListener());
                    break;
                }
                case KeyEvent.VK_3:
                case KeyEvent.VK_NUMPAD3: {
                    setPlayerBench();
                    break;
                }
                case KeyEvent.VK_4:
                case KeyEvent.VK_NUMPAD4: {
                    setActiveRetreat();
                    break;
                }
                default: {
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
