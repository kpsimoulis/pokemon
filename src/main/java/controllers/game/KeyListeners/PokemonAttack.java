package controllers.game.KeyListeners;

import ability.Cond;
import ability.Dam;
import ability.Search;
import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.cardpiles.PrizeCardController;
import controllers.coin.CoinController;
import controllers.game.GameController;
import controllers.player.PlayerController;
import javafx.util.Pair;
import parser.*;
import views.card.CardView;
import views.card.PokemonView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PokemonAttack implements KeyListener {

    private GameController controller;
    private int totalDamage = 0;
    public StringBuilder sb = new StringBuilder();

    public PokemonAttack(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1: {
                ArrayList<AbilityLogic> logics = controller.getHumanController().getActivePokemonController().getPokemonController()
                        .getAttacks().get(0).getAbility().getLogic();
                boolean chooseOccur = false;
                for (AbilityLogic logic : logics) {
                    String type1 = logic.getClass().getSimpleName();
                    if (type1.equals("Cond")) {
                        Cond cond = (Cond) logic;
                        if (cond.getCondition().equals("flip")) {
                            if (controller.getHumanController().getCoinController().flipCoin() == 1) {
                                sb.append("You flip a coin and got a HEAD!\n");
                                for (AbilityLogic condl : cond.getConditionIsMet()) {
                                    controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                            condl,
                                            controller.getHumanController().getActivePokemonCard(), sb);
                                }
                            } else {
                                sb.append("You flip a coin and got a TAIL!\n");
                                for (AbilityLogic condl : cond.getConditionIsNotMet()) {
                                    controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                            condl,
                                            controller.getHumanController().getActivePokemonCard(), sb);
                                }
                            }
                        }

                        else if(cond.getCondition().equals("count(target:your-active:energy:psychic)>0")) {
                            boolean match = false;
                            Pokemon exActive = new Pokemon(controller.getHumanController().getActivePokemonCard());
                            ArrayList<Energy> energys = exActive.getEnergy();
                            for(Energy en : energys){
                                if(en.getCategory().equals("psychic")) {
                                    match = true;
                                    break;
                                }
                            }
                            if(match){
                                sb.append("Your pokemon has psychic energy, then cause more damage.\n");
                                for (AbilityLogic condl : cond.getConditionIsMet()) {
                                    controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                            condl,
                                            controller.getHumanController().getActivePokemonCard(), sb);
                                }
                            }
                        }//else if
                        else if(cond.getCondition().equals("healed")){
                            if(controller.getHumanController().getActivePokemonController().getPokemonController().isHealed){
                                sb.append("Your pokemon has been healed, then cause more damage.\n");
                                for (AbilityLogic condl : cond.getConditionIsMet()) {
                                    controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                            condl,
                                            controller.getHumanController().getActivePokemonCard(), sb);
                                }
                            }
                        }
                    }
                    else if (type1.equals("Dam")) {
                        if (((Dam) logic).getTarget().getChoice()) {
                            chooseOccur = true;
                            controller.getView().setCommand("Select AI's Pokemon and press Enter\nto damage " + ((Dam) logic).getAmount().getAmount() + " points.");
                            DamAlllistener damAlllistener = new DamAlllistener(controller, ((Dam) logic).getAmount().getAmount(), sb);
                            controller.getAIController().getBenchController().setAllPokemonListener(damAlllistener);
                            if (!((Dam) logic).getTarget().getArea().equals("bench")) {
                                controller.getAIController().getActivePokemonController().setKeyListener(damAlllistener);
                            }
                            controller.getView().disableKeyListener();

                        } else {
                            controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                    logic,
                                    controller.getHumanController().getActivePokemonCard(), sb);
                        }
                    }
                    else if(type1.equals("Redamage")){
                        chooseOccur = true;
                        controller.getView().setCommand("Select AI's Pokemon and press D \nto set this Pokemon as source of damage counters");
                        RedamageAlllistener redamagelistener = new RedamageAlllistener(controller, sb);
                        controller.getAIController().getActivePokemonController().setKeyListener(redamagelistener);
                        controller.getAIController().getBenchController().setAllPokemonListener(redamagelistener);
                        controller.getView().disableKeyListener();

                    }
                    else {
                        controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                logic,
                                controller.getHumanController().getActivePokemonCard(), sb);

                    }
                }
                if(!chooseOccur)
                        attackEnd(1);

                break;
            }
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2: {
                if (controller.getHumanController().getActivePokemonController().getPokemonController().getAttacks().size() > 1) {
                    ArrayList<AbilityLogic> logics = controller.getHumanController().getActivePokemonController().getPokemonController()
                            .getAttacks().get(1).getAbility().getLogic();
                    for (AbilityLogic logic : logics) {
                        String type2 = logic.getClass().getSimpleName();

                        if (type2.equals("Cond")) {
                            Cond cond = (Cond) logic;
                            if (cond.getCondition().equals("flip")) {
                                if (controller.getHumanController().getCoinController().flipCoin() == 1) {
                                    sb.append("You flip a coin and got a HEAD!\n");
                                    for (AbilityLogic condl : cond.getConditionIsMet()) {
                                        controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                                condl,
                                                controller.getHumanController().getActivePokemonCard(), sb);
                                    }
                                } else {
                                    sb.append("You flip a coin and got a TAIL!\n");
                                    for (AbilityLogic condl : cond.getConditionIsNotMet()) {
                                        controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                                condl,
                                                controller.getHumanController().getActivePokemonCard(), sb);
                                    }
                                }
                            } else {
                            }
                        }


                            else {
                            controller.applyAbility(controller.getHumanController(), controller.getAIController(),
                                    logic,
                                    controller.getHumanController().getActivePokemonCard(), sb);
                        }
                    }
                    attackEnd(2);

                    break;


                }//if
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                controller.getView().addBoardListerner(new MainMenuListener(controller));
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // TODO process calculated amount for Pokemon
    private int countAmount(Amount amount) {
        int count = 0;
        Target target = amount.getTarget();
        PlayerController pok;
        if (target.getName().equals("your")) {
            pok = controller.getHumanController();
        } else {
            pok = controller.getAIController();
        }
        if (target.getArea().equals("bench")) {
            count = pok.getBenchController().getContainer().getNoOfCards();
        } else if (target.getCardType().equals("damage")) {
            count = pok.getActivePokemonCard().getDamagePoints();
        } else if (target.getCardType().equals("energy")) {

            ArrayList<Energy> energyArrayList = pok.getActivePokemonCard().getEnergy();

            for (Energy card : energyArrayList) {
                if (target.getCardCategory() == null || target.getCardCategory().isEmpty()) {
                    count++;
                } else if (target.getCardCategory().equals(card.getCategory())) {
                    count++;
                }
            }

        }
        return amount.getAmount(count);
    }

    private void attackEnd(int attackIndex) {
        Attack attackCaused;
        try {
            attackCaused = controller.getHumanController().getActivePokemonController().getPokemonController().getAttacks().get(attackIndex - 1);
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

            ArrayList<Card> cardToRemove = new ArrayList<>();

            for (CardController con : benches) {
                Pokemon pok = (Pokemon) con.getCard();
                if (pok.getDamagePoints() >= pok.getHealthPoints()) {
                    cardToRemove.add(con.getCard());
                }
            }
            if (cardToRemove.size() > 0) {
                for (Card card : cardToRemove) {
                    controller.getAIController().getDiscardPileController().addCard(card);
                    controller.getAIController().getBenchController().removeCard(card);

                }
            }

            boolean beDefeated = false;
            ArrayList<CardController> benchesPlayers =
                    controller.getHumanController().getBenchController().getCardControllers();

            ArrayList<Card> cardToRemovePlayer = new ArrayList<>();

            for (CardController con1 : benchesPlayers) {
                Pokemon pok = (Pokemon) con1.getCard();
                if (pok.getDamagePoints() >= pok.getHealthPoints()) {
                    cardToRemovePlayer.add(con1.getCard());
                }
            }
            if (cardToRemovePlayer.size() > 0) {
                for (Card card : cardToRemovePlayer) {
                    controller.getHumanController().getDiscardPileController().addCard(card);
                    controller.getHumanController().getBenchController().removeCard(card);
                    controller.getAIController().collectPrizeCards();
                    sb.append("YOUR POKEMON HAS BEEN DEFEATED.\n").append("Opponent has collected a prize card.\n");

                    if (controller.getAIController().getPrizeCardController().getCardContainer().getNoOfCards() == 0) {
                        sb.append("OPPONENT HAS NO MORE PRIZE CARDS.\n").append("YOU LOST THE GAME. :(");
                        controller.getView().setCommand(sb.toString());
                        controller.endGame();

                    }

                }
            }





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
    }

}
