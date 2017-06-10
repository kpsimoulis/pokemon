package controllers.game.KeyListeners;

import controllers.activepokemon.ActivePokemonController;
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
            strBuilder.append("You defeated opponent's pokemon. Collect a prize card.");
            //TODO: Implement prize card collection
        }else{
            strBuilder.append("Press Enter to continue.");
            controller.getView().setCommand(strBuilder.toString());
            controller.getView().addBoardListerner(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ENTER: {
                            controller.getView().disableKeyListener();
                            controller.setEnergyAdded(false);
                            controller.gameAITurn();
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }

        return true;
    }

}
