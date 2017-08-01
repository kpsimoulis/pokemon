package controllers.game;

import ability.*;
import controllers.cardpiles.PrizeCardController;
import controllers.game.*;
import controllers.card.CardController;
import controllers.cardcontainer.CardContainerController;
import controllers.game.KeyListeners.CollectPrizeCard;
import controllers.game.KeyListeners.HealListener;
import controllers.game.KeyListeners.ListenerActivePok;
import controllers.game.KeyListeners.MainMenuListener;
import controllers.player.AIPlayerController;
import controllers.player.HumanPlayerController;
import controllers.player.PlayerController;
import javafx.util.Pair;
import parser.*;
import views.ChoiceDialog;
import views.card.CardView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.cardpiles.PrizeCardView;
import views.game.GameView;
import card.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * The Game Controller Class
 * This class contains two player controllers and a game view. This class will be the parser class that will control the game.
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
    private boolean hasRetreated;

    public GameController() {

        view = new GameView();
        player1Controller = new HumanPlayerController();
        player2Controller = new AIPlayerController();
        view.setVisible(true);
        displayChoiceDialog();
        this.hasRetreated = false;
    }

    public boolean getHasRetreated() {
        return hasRetreated;
    }

    public void setHasRetreated(boolean hasRetreated) {
        this.hasRetreated = hasRetreated;
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

        if (!player1Controller.handHasPokemon()) {
            view.setCommand("YOU HAVE A MULLIGAN.\nOpponent looked at your hand and drew a card." +
                    "\nYour hand will get shuffled into your deck" +
                    "\nand you will get a new hand." +
                    "\nPress ENTER to continue.");
            playerDealDeck(player2Controller);
            view.addBoardListerner(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        player1Controller.shuffleHandInDeck();
                        player1Controller.getHandController().returnAllCards();
                        playerChooseActive();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        } else {
            view.setCommand("Choose Active Pokemon (Click on a pokemon and hit enter)");
            firstTurn = true;
            view.disableKeyListener();
            player1Controller.getHandController().setPokemonListener(new ListenerActivePok(this,
                    player1Controller.getHandController()));
        }
    }

    public void endFirstTurn() {

        view.setCommand("AI is playing");

        if (!player2Controller.handHasPokemon()) {

            view.setCommand("OPPONENT HAS A MULLIGAN.\nYou can look at his hand." +
                    "\nPress ENTER to draw a card.");
            player2Controller.getHandController().returnAllCards();
            view.addBoardListerner(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        playerDealDeck(player1Controller);
                        player2Controller.shuffleHandInDeck();
                        endFirstTurn();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

        } else {
            view.setOpponentActive(player2Controller.setActivePokemon(true));
            player2Controller.getActivePokemonController().returnCard();
            firstTurn = false;

            player2Controller.putPokemonOnBench();
            player2Controller.getBenchController().returnAllCards();

            startGame();
        }

    }

    private void startGame() {

        view.setCommand("Game is about to start.\n Press ENTER to continue.");
        view.addBoardListerner(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    playerDealDeck(player1Controller);
                    decideNextAction();
                    energyAdded = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public void decideNextAction() {

        view.addBoardListerner(new MainMenuListener(this));
    }

    public void playerDealDeck(PlayerController playerController) {

        playerController.dealDeckHand();
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

    public void gameAITurn() {

        StringBuilder sb = new StringBuilder();

        sb.append("AI is playing...\n" +
                "AI's status is \n" +
                getAIController().getStatus() + "!\n");
        if (getAIController().isPoisoned) {
            sb.append("AI is poisoned and will lost 10 HP. \n");
        }

        playerDealDeck(player2Controller);
        if (getAIController().getStatus().equals("asleep") || getAIController().getStatus().equals("paralyzed")) {
            sb.append("AI cannot use any ability due to his status").append("\nTurn Ended.\n");
        } else {
            Attack attack_caused = player2Controller.play(player1Controller);
            sb.append("Ability used: ");
            if (attack_caused == null) {
                sb.append("None").append("\nTurn Ended.\n");
            } else {
                // Default damage amount
                int dmg = 0;
                if (attack_caused.getAbility().getLogic().get(0) instanceof Dam) {
                    Amount amount = ((Dam) attack_caused.getAbility().getLogic().get(0)).getAmount();
                    if (amount.isCalculated()) {
                        // TODO process calculated amount for AI
                        dmg = 5;
                    } else {
                        dmg = amount.getAmount();
                    }
                }

                sb.append(attack_caused.getAbility().getName()).append(",\nDmg Caused: ")
                        .append(dmg).append("\nTurn Ended.\n");
            }
        }

        if (player1Controller.getActivePokemonCard().getHealthPoints() <= player1Controller.getActivePokemonCard().getDamagePoints()) {

            player2Controller.collectPrizeCards();
            sb.append("YOUR POKEMON HAS BEEN DEFEATED.\n").append("Opponent has collected a prize card.\n");
            player1Controller.discardActivePokemon();
            view.getBoard().getPlayerActivePanel().removeAll();

            if (player2Controller.getPrizeCardController().getCardContainer().getNoOfCards() == 0) {
                sb.append("OPPONENT HAS NO MORE PRIZE CARDS.\n").append("YOU LOST THE GAME. :(");
                view.setCommand(sb.toString());
                endGame();

            } else {

                if (player1Controller.benchHasPokemon()) {

                    view.disableKeyListener();
                    player1Controller.getBenchController().setPokemonListener(new ListenerActivePok(this,
                            player1Controller.getBenchController()));
                    sb.append("Choose a new active pokemon\nfrom your bench").append("\n(Click on a card and press Enter)");
                    view.setCommand(sb.toString());

                } else {
                    sb.append("YOU DO NOT HAVE A BASIC POKEMON TO PLAY\n").append("YOU LOST THE GAME. :(");
                    view.setCommand(sb.toString());
                    endGame();
                }
            }

        } else if (player1Controller.getDeckController().getCardContainer().isEmpty()) {

            view.setCommand("YOU DO NOT HAVE ANY CARDS IN YOUR DECK\nYOU LOST THE GAME. :(");
            endGame();

        } else {
            if (getAIController().getStatus().equals("stuck")
                    || getAIController().getStatus().equals("paralyzed")) {
                getAIController().setStatus("normal");
                sb.append("\nAI's status are normal now!");
            } else if (getAIController().getStatus().equals("asleep")) {
                if (getAIController().getCoinController().flipCoin() == 1) {
                    getAIController().setStatus("normal");
                    sb.append("\nAI flip a coin and got a HEAD!\nAI's status are normal now!");
                } else {
                    sb.append("\nAI flip a coin and got a TAIL!\nAI's status are asleep now!");
                }

            }
            sb.append("\n\nPress Enter to continue.");
            view.setCommand(sb.toString());

            view.addBoardListerner(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

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
                public void keyReleased(KeyEvent e) {
                }
            });

            if (getAIController().isPoisoned()) {
                StringBuilder sb1 = new StringBuilder();
                getAIController().getActivePokemonController().getPokemonController().causeDamage(10);
                Pokemon aipok = (Pokemon) getAIController().getActivePokemonController().getPokemonController().getCard();

                if (aipok.getDamagePoints()>=aipok.getHealthPoints()){
                    getAIController().setIsPoisoned(false);
                    PrizeCardController humanPrizeCard = getHumanController().getPrizeCardController();
                    if (humanPrizeCard.getCardContainer().getNoOfCards() > 1) {
                        sb1.append("Opponent's pokemon is knoched out due to poison.\n").append("Collect a prize card:\n");
                        sb1.append(humanPrizeCard.getPrizeCardsNo());
                        sb1.append("\n").append("Press the correct no.");
                        view.setCommand(sb1.toString());
                        view.addBoardListerner(new CollectPrizeCard(this));
                    } else {
                        Pair<CardController, CardView> pair = getHumanController().getPrizeCardController().chooseCard(0);
                        if (pair != null) {
                            getHumanController().getHandController().addCard(pair);
                            pair.getKey().returnBackCover();
                            getView().disableKeyListener();
                        }
                        sb1.append("You defeated opponent's pokemon.\n").append("You have no prize card left\n");
                        sb1.append("YOU WON THE GAME");
                        view.setCommand(sb1.toString());
                        endGame();
                    }
                }
            }




        }

    }

    public void endGame() {

        getView().disableKeyListener();

    }

    public void aiActiveDefeated() {

        player2Controller.discardActivePokemon();
        if (player2Controller.benchHasPokemon()) {
            view.setOpponentActive(player2Controller.setActivePokemon(false));
            player2Controller.getActivePokemonController().returnCard();
            gameAITurn();
        } else {
            view.setCommand("AI has no basic pokemon to set as active.\n YOU WON THE GAME.\nCONGRATULATIONS :D !!!!");
            endGame();
        }

    }

    public void applyAbility(HumanPlayerController humanPlayerController, AIPlayerController aiPlayerController, AbilityLogic abilityLogic, Pokemon pokemon) {
        StringBuilder strBuilder = new StringBuilder();

        String type1 = abilityLogic.getClass().getSimpleName();
        System.out.print("applied: " + type1 + "\n");
//        String type2 = ability.getLogic().get(1).getClass().getSimpleName();
//        if (ability.getLogic().size() < 2) {
            switch (type1) {
//                case ("Dam"): {
//                    Amount amount = ((Dam) ability.getLogic().get(0)).getAmount();
//                    Target target = ((Dam) ability.getLogic().get(0)).getTarget();
//                    int damAmount = amount.getAmount();
//                    humanPlayerController.getActivePokemonController().getPokemonController().causeDamage(damAmount);
//                    break;
//                }//heal
                case ("Draw"): {
                    Amount amount = ((Draw) abilityLogic).getAmount();
                    Target target = ((Draw) abilityLogic).getTarget();
                    int number = amount.getAmount();
                    for (int i = 0; i < number; i++) {
                        if (target.getName().equals("your"))
                            humanPlayerController.dealDeckHand();
                        else
                            aiPlayerController.dealDeckHand();
                    }
                    humanPlayerController.getHandController().returnAllCards();
                    strBuilder.append("You have draw " + number + " card(s)\n");

                    break;
                }//draw
                case ("Heal"): {
                    Amount amount = ((Heal) abilityLogic).getAmount();
                    Target target = ((Heal) abilityLogic).getTarget();

                    int healAmount = amount.getAmount();
                    humanPlayerController.getActivePokemonController().getPokemonController().heal(healAmount);
                    break;
                }//heal
                case ("Applystat"): {
                    String status = ((Applystat) abilityLogic).getStatus();
                    if (!status.equals("poisoned")) {
                        aiPlayerController.setStatus(status);
                        strBuilder.append("AI's status now is " + status + "!\n");
                    } else {
                        aiPlayerController.setIsPoisoned(true);
                        strBuilder.append("AI' now is " + status + " and will lost 10HP between each turn!\n");

                    }
                    break;
                }//applys
                case ("Destat"): {
                 humanPlayerController.setStatus("normal");
                 humanPlayerController.setIsPoisoned(false);
                    strBuilder.append("Removed all the status!\n");

                    break;
                }//applys



                default:
                    this.getView().setCommand("Havnt implement this ability(from applyAbility)\n"
                            + "Press ESC to go back");

                    break;
            }//switch type1
//        }//if

        strBuilder.append("Press Enter to continue.");
        getView().addBoardListerner(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: {
                        getView().disableKeyListener();
                        setEnergyAdded(false);
                        setHasRetreated(false);

                        if (aiPlayerController.getDeckController().getCardContainer().isEmpty()) {
                            String stringBuilder = "AI has no more cards in Deck" + "\nYOU WON THE GAME :)\n" +
                                    "CONGRATULATIONS!!!";
                            getView().setCommand(stringBuilder);
                            endGame();
                        } else {
                            gameAITurn();
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        getView().setCommand(strBuilder.toString());

    }

}
