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

    public MainMenuListener(GameController controller) {

        this.controller = controller;

        StringBuilder builder = new StringBuilder();
        builder.append("You can now do the following:\n");

        if (controller.getHumanController().handHasEnergy() && !controller.isEnergyAdded()) {
            builder.append("E. Add Energy to a pokemon\n");
        }

        if (controller.getHumanController().canAttack()) {
            builder.append("A. Attack with Active Pokemon\n");
        }

        if (controller.getHumanController().handHasPokemon() && !controller.getHumanController().getBenchController().isFull()) {
            builder.append("P. Add Pokemon to your bench\n");
        }


        if (controller.getHumanController().getActivePokemonCard().getEnergy().size() >=
                controller.getHumanController().getActivePokemonCard().getRetreat().getEnergyAmount() && controller.getHumanController().benchHasPokemon()) {
            builder.append("R. Retreat your Active Pokemon.\n");
        }
        builder.append("X. End Turn\n");
        controller.getView().setCommand(builder.toString());

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E: {

                if (!controller.getHumanController().handHasEnergy() && controller.isEnergyAdded()) {
                    break;
                }

                controller.getView().setCommand("Select Pokemon and press Enter.\n(Press Esc to exit)");

                KeyListener pokemonListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_ENTER: {

                                PokemonView chosenCard = (PokemonView) SwingUtilities.getAncestorOfClass(PokemonView.class, (Component) e.getSource());
                                PokemonController chosenController;

                                if (SwingUtilities.getAncestorOfClass(ActivePokemonView.class, (Component) e.getSource()) != null) {
                                    chosenController = controller.getHumanController().
                                            getActivePokemonController().getPokemonController();
                                } else {
                                    chosenController = (PokemonController) controller.findCardInContainer(
                                            chosenCard, controller.getHumanController().getBenchController());
                                }

                                controller.getView().setCommand("Select Energy Card and press Enter.\n (Press Esc to exit)");

                                controller.getHumanController().getBenchController().removeAllListeners(this);
                                controller.getHumanController().getActivePokemonController().removeKeyListener(this);

                                SetEnergyPokemon energyListener = new SetEnergyPokemon(controller, chosenController);
                                controller.getHumanController().getHandController().setEnergyListener(energyListener);

                                controller.getView().addBoardListerner(new KeyListener() {
                                    @Override
                                    public void keyTyped(KeyEvent e) {

                                    }

                                    @Override
                                    public void keyPressed(KeyEvent e) {
                                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                            controller.getHumanController().getHandController().removeAllListeners(energyListener);
                                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                                        }
                                    }

                                    @Override
                                    public void keyReleased(KeyEvent e) {

                                    }
                                });

                                break;
                            }

                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                };

                KeyListener exitBoard = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getHumanController().getBenchController().removeAllListeners(pokemonListener);
                            controller.getHumanController().getActivePokemonController().removeKeyListener(pokemonListener);

                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                };

                controller.getView().addBoardListerner(exitBoard);
                controller.getHumanController().getBenchController().setPokemonListener(pokemonListener);
                controller.getHumanController().getActivePokemonController().setKeyListener(pokemonListener);

                break;

            }
            case KeyEvent.VK_P: {

                if (!controller.getHumanController().handHasPokemon()) {
                    break;
                }

                SetHandToBench handToBench = new SetHandToBench(controller);
                controller.getHumanController().getHandController().setPokemonListener(handToBench);

                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getHumanController().getHandController().removeAllListeners(handToBench);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });

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
                builder.append("(Press Esc to exit)");
                controller.getView().setCommand(builder.toString());
                controller.getView().addBoardListerner(new PokemonAttack(controller));
                break;

            }
            case KeyEvent.VK_X: {
                controller.setEnergyAdded(false);
                controller.gameAITurn();
                break;
            }
            case KeyEvent.VK_R: {
                int energyNeed = controller.getHumanController().getActivePokemonCard().getRetreat().getEnergyAmount();
                // make sure pressing R wont crash the game, when player cannot retreat.
                if (!controller.getHumanController().benchHasPokemon()
                        || (controller.getHumanController().getActivePokemonCard().getEnergy().size()
                        < controller.getHumanController().getActivePokemonCard().getRetreat().getEnergyAmount())
                        ) {
                    StringBuilder builder = new StringBuilder("You cannot retreat now! \n");
                    builder.append("To retreat you need:\n"
                            + "1. Your bench has at least 1 Pokemon, and\n"
                            + "2. Your active Pokemon has attached at least " + energyNeed + " energy card(s).\n"
                            + "(Press Esc to exit)");
                    controller.getView().setCommand(builder.toString());

                    controller.getView().addBoardListerner(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {

                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                controller.getView().addBoardListerner(new MainMenuListener(controller));
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {

                        }
                    });

                    break;
                }

                //build menu
                StringBuilder builder = new StringBuilder("Choose a Pokemon from bench and Press enter:\n");
                builder.append("Your pokemon will:\n"
                        +"1. Discard "
                        + energyNeed + " energy card(s), and\n"
                        +"2. Remove all of the stat.\n"
                        + "Now damage point is " +
                        controller.getHumanController().getActivePokemonCard().getDamagePoints());
                controller.getView().setCommand(builder.toString() + "\n"
                        + "(Press Esc to exit)");

                RetreatListener retreatListener = new RetreatListener(controller, controller.getHumanController().getBenchController());
                controller.getHumanController().getBenchController().setPokemonListener(retreatListener);
                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getHumanController().getBenchController().removeAllListeners(retreatListener);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });


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
