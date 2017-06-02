package controllers.game;

import card.Card;
import card.Energy;
import card.Pokemon;
import cardcontainer.CardContainer;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.player.AIPlayerController;
import controllers.player.HumanPlayerController;
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
import views.game.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
                        player1Controller.getHandController().removeAllListeners(this);
                        view.setCommand("AI is playing");
                        view.setOpponentActive(player2Controller.setActivePokemon(true));
                        player2Controller.getActivePokemonController().returnCard();
                        startGame();
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

    private void startGame() {

        KeyListener listener = new firstMenuListener();
        view.addBoardListerner(listener);

    }

    class firstMenuListener implements KeyListener {

        firstMenuListener(){
            view.setCommand("Computer has selected his active pokemon.\n" +
                    "You can now do the following:\n" +
                    "1. Add Energy to your active pokemon");
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1:{
                    view.setCommand("Select an Energy Card and press enter.");
                    KeyListener listener1 = new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {

                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            switch (e.getKeyCode()) {
                                case KeyEvent.VK_ENTER: {
                                    EnergyView chosenCard = (EnergyView) SwingUtilities.getAncestorOfClass(EnergyView.class, (Component)e.getSource());
                                    Pair<CardController, CardView> pair = null;
                                    for (CardController cardController: player1Controller.getHandController().getCardControllers()){
                                        if (cardController.getView() == chosenCard){
                                            pair = player1Controller.getHandController().removeCard(cardController.getCard());
                                            break;
                                        }
                                    }
                                    assert pair != null;
                                    player1Controller.getActivePokemonController().getPokemonController().addEnergy((Energy) pair.getKey().getCard());
                                    player1Controller.getHandController().removeAllListeners(this);
                                    SecondMenuListener secondMenuListener = new SecondMenuListener();
                                    view.addBoardListerner(secondMenuListener);
                                    break;
                                }
                                default:{
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
                default:{
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class SecondMenuListener implements KeyListener{

        public SecondMenuListener(){
            view.setCommand("You can now do the following:\n" +
                    "1. Attack with Active Pokemon");
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
                    int index=1;
                    for (Attack attack: card.getAttack()){

                        HashMap<String, Integer> dict = new HashMap<>();
                        for (Energy energy: card.getEnergy()){
                            String energyCategory = energy.getCategory();
                            if (dict.containsKey(energyCategory)){
                                dict.put(energyCategory, dict.get(energyCategory) + 1);
                            }else{
                                dict.put(energyCategory, 1);
                            }
                        }

                        boolean meetRequirements = true;
                        for (Requirement requirement: attack.getRequirement()){
                            if (dict.containsKey(requirement.getCategory()) && dict.get(requirement.getCategory()) == requirement.getEnergyAmount()){
                                builder.append(index).append(". ").append(attack.getAbility().getName()).append("\n");
                            }
                        }

                        index++;
                    }

                    view.setCommand(builder.toString());
                    view.addBoardListerner(new AttackMenuListener());
                    break;
                }
                default:{
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class AttackMenuListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1: {

                    Pokemon card1 = (Pokemon) player1Controller.getActivePokemonController().getPokemonController().getCard();
                    Attack attack = card1.getAttack().get(0);
                    Pokemon card2 = (Pokemon) player2Controller.getActivePokemonController().getPokemonController().getCard();
                    Attack attack2 = card2.getAttack().get(0);
                    player1Controller.getActivePokemonController().attackPokemon(
                            player2Controller.getActivePokemonController(), attack.getAbility().getDamage());

                    StringBuilder sb = new StringBuilder("Attack caused: " + attack.getAbility().getDamage() + "\nTurn Ended.\n");
                    view.disableKeyListener();
                    if (card2.getHealthPoints() <= card2.getDamagePoints()) {
                        sb.append("You won\n");
                        view.setCommand(sb.toString());
                        break;
                    }
                    sb.append("AI is playing...\n");
                    player2Controller.getActivePokemonController().getPokemonController().addEnergy(new Energy("Fight", 1, "fight"));
                    player2Controller.getActivePokemonController().attackPokemon(
                            player1Controller.getActivePokemonController(), attack2.getAbility().getDamage());
                    sb.append("Attack caused: " + attack2.getAbility().getDamage() + "\nTurn Ended.\n");
                    if (card1.getHealthPoints() <= card1.getDamagePoints()) {
                        sb.append("Computer won\n");
                        view.setCommand(sb.toString());
                        break;
                    }

                    sb.append("You can now do the following:\n" +
                            "1. Re-Attack with Active Pokemon\n" +
                            "E. Exit Attack Menu"
                    );

                    view.setCommand(sb.toString());
                    view.addBoardListerner(new AttackMenuListener());

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
                default:{
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class ChoiceMenuListener implements KeyListener{

        ChoiceMenuListener(){
            view.setCommand("You can now do the following:\n" +
                    "1. Go to Energy Menu\n" +
                    "2. Go to Attack Menu\n" +
                    "3. Go to Bench Menu\n" +
                    "4. Go to Retreat Menu");
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
                    StringBuilder sb = new StringBuilder("");
                    sb.append("Not implemented yet");
                    view.setCommand(sb.toString());
                    break;
                }
                case KeyEvent.VK_4:
                case KeyEvent.VK_NUMPAD4: {
                    StringBuilder sb = new StringBuilder("");
                    sb.append("Not implemented yet");
                    view.setCommand(sb.toString());
                    break;
                }
                default:{
                    System.out.println("Press the correct key.");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
