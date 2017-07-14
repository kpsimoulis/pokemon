package controllers.game.KeyListeners;

import ability.Cond;
import ability.Dam;
import ability.Search;
import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.cardpiles.PrizeCardController;
import controllers.coin.CoinController;
import controllers.game.GameController;
import controllers.player.PlayerController;
import javafx.util.Pair;
import main.*;
import views.card.CardView;
import views.card.PokemonView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PokemonAttack implements KeyListener {

    private GameController controller;
    private int totalDamage = 0;

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
                attack(1);
                break;
            }
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2: {
                attack(2);
                break;
            }
            case KeyEvent.VK_ESCAPE:{
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
        }
        else {
            pok = controller.getAIController();
        }
        if (target.getArea().equals("bench")) {
            count = pok.getBenchController().getContainer().getNoOfCards();
        }
        else if (target.getCardType().equals("damage")) {
            count = pok.getActivePokemonCard().getDamagePoints();
        }
        else if (target.getCardType().equals("energy")) {

            ArrayList<Energy> energyArrayList = pok.getActivePokemonCard().getEnergy();

            for (Energy card : energyArrayList) {
                if (target.getCardCategory() == null || target.getCardCategory().isEmpty()) {
                    count++;
                }
                else if (target.getCardCategory().equals(card.getCategory())) {
                    count++;
                }
            }

        }
        return amount.getAmount(count);
    }

    private boolean dam(Dam dam) {
        Amount amount = dam.getAmount();
        int damage = 0;
        Target target = dam.getTarget();
        ActivePokemonController targetPok = getOppActivePok();
        ActivePokemonController srcPok = getHumanActivePok();

        if (amount.isCalculated()) {
            damage = countAmount(amount);
        }
        else {
            damage = amount.getAmount();
        }

        if (target.getName().equals("your") && target.getArea().equals("bench")) {
            ArrayList<CardController> cardControllers = controller.getHumanController().getBenchController().getCardControllers();
            for(CardController controller: cardControllers){
                int curDmg = ((Pokemon) controller.getCard()).getDamagePoints();
                ((Pokemon) controller.getCard()).setDamagePoints(curDmg+damage);
                ((PokemonView) controller.getView()).setDmgPts(curDmg+damage);
            }
            damage = 0;
        }
        else if (target.getName().equals("your") && target.getArea().equals("active")) {
            targetPok = getHumanActivePok();
        }


        this.totalDamage += damage;
        return srcPok.attackPokemon(targetPok, damage);
    }

    private boolean search(Attack attackCaused) {
        return false;
    }

    private ActivePokemonController getHumanActivePok() {
        return controller.getHumanController().getActivePokemonController();
    }

    private ActivePokemonController getOppActivePok() {
        return controller.getAIController().getActivePokemonController();
    }

    private void attack(int attackIndex){

        ParserHelper helper = new ParserHelper();

        ActivePokemonController activePok = getHumanActivePok();

        Attack attackCaused;
        try{
            attackCaused = activePok.getPokemonController().getAttacks().get(attackIndex-1);
        }
        catch (IndexOutOfBoundsException exception){
            throw exception;
        }

        if (!controller.getHumanController().checkAttackEnergy(attackCaused, activePok.getEnergyOnCard())){
            throw new NullPointerException();
        }

        boolean defeatedOpp = false;

        // Process Ability
        for (AbilityLogic ability : attackCaused.getAbility().getLogic()) {

            if (ability instanceof Cond) {
                CoinController coinController = controller.getHumanController().getCoinController();
                int coin = coinController.flipCoin();
                // Coin is head
                if (coin == 1) {
                    // TODO process multiple conditions for coin
                    System.out.println("coin is head");
                    ability = ((Cond) ability).getConditionIsMet().get(0);
                }
                // Coin is tails
                else {
                    System.out.println("coin is tails");
                    if (((Cond) ability).getConditionIsNotMet().size() > 0) {
                        ability = ((Cond) ability).getConditionIsNotMet().get(0);
                    }
                }
            }


            if (ability instanceof Dam) {
                defeatedOpp = dam((Dam) ability);
            }
            else if (ability instanceof Search) {
                defeatedOpp = search(attackCaused);
            }
            else {
                System.out.println("Skipping ability: " + ability);
            }


        }


        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("Ability used: ");
        strBuilder.append(attackCaused.getAbility().getName()).append(",\nDmg Caused: ")
                .append(totalDamage).append("\nTurn Ended.\n");

        if (defeatedOpp){

            PrizeCardController humanPrizeCard = controller.getHumanController().getPrizeCardController();
            if (humanPrizeCard.getCardContainer().getNoOfCards() > 1){
                strBuilder.append("You defeated opponent's pokemon.\n").append("Collect a prize card:\n");
                strBuilder.append(humanPrizeCard.getPrizeCardsNo());
                strBuilder.append("\n").append("Press the correct no.");
                controller.getView().addBoardListerner(new CollectPrizeCard(controller));
            }else{
                Pair<CardController, CardView> pair = controller.getHumanController().getPrizeCardController().chooseCard(0);
                if (pair != null) {
                    controller.getHumanController().getHandController().addCard(pair);
                    pair.getKey().returnBackCover();
                    controller.getView().disableKeyListener();
                }
                strBuilder.append("You defeated opponent's pokemon.\n").append("You have no prize card left\n");
                strBuilder.append("YOU WON THE GAME");
                controller.getView().setCommand(strBuilder.toString());
                controller.endGame();
            }

        }else{
            strBuilder.append("Press Enter to continue.");
            controller.getView().addBoardListerner(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ENTER: {
                            controller.getView().disableKeyListener();
                            controller.setEnergyAdded(false);

                            if (controller.getAIController().getDeckController().getCardContainer().isEmpty()){
                                String stringBuilder = "AI has no more cards in Deck" + "\nYOU WON THE GAME :)\n" +
                                        "CONGRATULATIONS!!!";
                                controller.getView().setCommand(stringBuilder);
                                controller.endGame();
                            }else {
                                controller.gameAITurn();
                            }
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }
        controller.getView().setCommand(strBuilder.toString());

    }

}
