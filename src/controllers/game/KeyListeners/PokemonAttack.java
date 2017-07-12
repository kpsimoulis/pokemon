package controllers.game.KeyListeners;

import ability.Dam;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.cardpiles.PrizeCardController;
import controllers.game.GameController;
import javafx.util.Pair;
import main.Attack;
import views.card.CardView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 09-Jun-2017.
 */
public class PokemonAttack implements KeyListener {

    private GameController controller;

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

    private boolean dam(Attack attackCaused) {
        int damage = attackCaused.getAbility().getDamage();
        ActivePokemonController oppPok = getOppActivePok();
        ActivePokemonController activePok = getHumanActivePok();
        return activePok.attackPokemon(oppPok, damage);
    }

    private void search(int attackIndex) {

    }

    private ActivePokemonController getHumanActivePok() {
        return controller.getHumanController().getActivePokemonController();
    }

    private ActivePokemonController getOppActivePok() {
        return controller.getAIController().getActivePokemonController();
    }

    private void attack(int attackIndex){

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

        System.out.println(attackCaused.getAbility());

        if (attackCaused.getAbility().getLogic().get(0) instanceof Dam) {
            defeatedOpp = dam(attackCaused);
        }

        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("Ability used: ");
        strBuilder.append(attackCaused.getAbility().getName()).append(",\nDmg Caused: ")
                .append(attackCaused.getAbility().getDamage()).append("\nTurn Ended.\n");

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
