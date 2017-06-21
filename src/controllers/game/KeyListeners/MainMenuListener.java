package controllers.game.KeyListeners;

import card.Pokemon;
import controllers.card.PokemonController;
import controllers.game.GameController;
import main.Attack;
import main.Requirement;
import views.activepokemon.ActivePokemonView;
import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Created by mikce_000 on 07-Jun-2017.
 */
public class MainMenuListener implements KeyListener {

    private GameController controller;

    public MainMenuListener(GameController controller){

        this.controller = controller;

        StringBuilder builder = new StringBuilder();
        builder.append("You can now do the following:\n");

        if (controller.getHumanController().handHasEnergy() && !controller.isEnergyAdded()){
            builder.append("E. Add Energy to a pokemon\n");
        }

        if (controller.getHumanController().canAttack()){
            builder.append("A. Attack with Active Pokemon\n");
        }

        if (controller.getHumanController().handHasPokemon() && !controller.getHumanController().getBenchController().isFull()){
            builder.append("P. Add Pokemon to your bench\n");
        }

        builder.append("X. End Turn");
        controller.getView().setCommand(builder.toString());

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E: {

                if (!controller.getHumanController().handHasEnergy() && controller.isEnergyAdded()){
                    break;
                }

                controller.getView().setCommand("Select Pokemon and press Enter");

                KeyListener pokemonListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_ENTER: {

                                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                                PokemonController chosenController;

                                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null){
                                    chosenController = controller.getHumanController().
                                            getActivePokemonController().getPokemonController();
                                }else{
                                    chosenController = (PokemonController) controller.findCardInContainer(
                                            chosenCard, controller.getHumanController().getBenchController());
                                }

                                controller.getView().setCommand("Select Energy Card and press Enter");

                                controller.getHumanController().getBenchController().removeAllListeners(this);
                                controller.getHumanController().getActivePokemonController().removeKeyListener(this);

                                controller.getHumanController().getHandController().setEnergyListener(new SetEnergyPokemon(controller, chosenController));

                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                };

                controller.getHumanController().getBenchController().setPokemonListener(pokemonListener);
                controller.getHumanController().getActivePokemonController().setKeyListener(pokemonListener);

                controller.getView().disableKeyListener();
                break;

            }
            case KeyEvent.VK_P: {

                if (!controller.getHumanController().handHasPokemon()){
                    break;
                }

                controller.getView().addBoardListerner(new SetHandToBench(controller));

                break;

            }
            case KeyEvent.VK_A: {

                StringBuilder builder = new StringBuilder("Press the corresponding number for the attacks:\n");

                Pokemon card = (Pokemon) controller.getHumanController().getActivePokemonController().getPokemonController().getCard();
                HashMap<String, Integer> dict = controller.getHumanController().getActivePokemonController().getEnergyOnCard();

                int index = 1;
                for (Attack attack : card.getAttack()) {
                    for (Requirement requirement : attack.getRequirement()) {
                        if (dict.containsKey(requirement.getCategory()) && dict.get(requirement.getCategory()) == requirement.getEnergyAmount()) {
                            builder.append(index).append(". ").append(attack.getAbility().getName()).append("\n");
                        }
                    }
                    index++;
                }

                controller.getView().setCommand(builder.toString());
                controller.getView().addBoardListerner(new PokemonAttack(controller));
                break;

            }
            case KeyEvent.VK_X:{
                controller.setEnergyAdded(false);
                controller.gameAITurn();
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
