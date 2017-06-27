package controllers.game.KeyListeners;

import controllers.activepokemon.ActivePokemonController;
import controllers.cardpiles.PrizeCardController;
import controllers.game.GameController;
import main.Attack;

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
                if (!attack(1)){
                    break;
                }
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

    private boolean attack(int attackIndex){

        ActivePokemonController activePok = controller.getHumanController().getActivePokemonController();

        Attack attackCaused;
        try{
            attackCaused = activePok.getPokemonController().getAttacks().get(attackIndex-1);
        }
        catch (IndexOutOfBoundsException exception){
            return false;
        }

        int damage = attackCaused.getAbility().getDamage();
        ActivePokemonController oppPok = controller.getAIController().getActivePokemonController();

        boolean defeatedOpp = activePok.attackPokemon(oppPok, damage);

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Attack caused: ").append(damage).append("\nTurn Ended.\n");

        if (defeatedOpp){

            PrizeCardController humanPrizeCard = controller.getHumanController().getPrizeCardController();
            if (humanPrizeCard.getCardContainer().getNoOfCards() > 1){
                strBuilder.append("You defeated opponent's pokemon.\n").append("Collect a prize card:\n");
                strBuilder.append(humanPrizeCard.getPrizeCardsNo());
                strBuilder.append("\n").append("Press the correct no.");
                controller.getView().addBoardListerner(new CollectPrizeCard(controller));
            }else{
                strBuilder.append("You defeated opponent's pokemon.\n").append("You have only one prize card left\n");
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

        return true;
    }

}
