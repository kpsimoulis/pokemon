package controllers.game.KeyListeners;

import card.Energy;
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
import java.util.ArrayList;
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
        builder.append("Retreat Requirement: " + controller.getHumanController().getActivePokemonCard().getRetreat().getEnergyAmount() + " energy card(s)");
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
                /*
                This operation will somehow violate the limit of bench's size when player's bench is full,
                because the active pokemon will show on the bench before player choose new active pokemon.
                but it is good because we do not need creating new Keylistener.

                 */
                int energyNeed = controller.getHumanController().getActivePokemonCard().getRetreat().getEnergyAmount();

                //build menu
                StringBuilder builder = new StringBuilder("Choose a Pokemon from bench and Press enter:\n");
                builder.append("(You cannot active the Pokemon who has retreated this turn)\n");
                builder.append("You discard: " + energyNeed + " energy cards and " + "now damage point is " + controller.getHumanController().getActivePokemonCard().getDamagePoints());
                controller.getView().setCommand(builder.toString());

                // make sure pressing R when player cannot retreat wont crash the game.
                if (!controller.getHumanController().benchHasPokemon()
                        || controller.getHumanController().getActivePokemonCard().getEnergy().size() <
                        controller.getHumanController().getActivePokemonCard().getRetreat().getEnergyAmount()) {
                    controller.getView().addBoardListerner(new MainMenuListener(controller));
                    break;
                }


                //get pokemon and damage point from activepokemon controller
                Pokemon exActive = new Pokemon(controller.getHumanController().getActivePokemonCard());
                ArrayList<Energy> energys = exActive.getEnergy();

                int exDamage = controller.getHumanController().getActivePokemonCard().getDamagePoints();
                controller.getHumanController().setActivePokemonController(null);
                controller.getHumanController().getPlayer().removeActivePokemon();
                for (int i = 0; i < energyNeed; i++) {
                    controller.getHumanController().getDiscardPileController().addCard(exActive.removeEnergy());

                }
                //TODO: Detach items and clear the stat
                //DiscardPileController().addCard(Items);
                //exActive.removeStat;
                //exActive.removeItems;

                //clear active panel
                controller.getView().getBoard().getPlayerActivePanel().removeAll();
                controller.getView().disableKeyListener();


                //call listener, here the 1st parameter of setActivePokemon() is always true caz I dont wanna rewrite it.
                ListenerActivePok listenerActivePok = new ListenerActivePok(controller,
                        controller.getHumanController().getBenchController());
                controller.getHumanController().getBenchController().setPokemonListener(listenerActivePok);

                //rewrite the information from ex-activepokemon
                exActive.setDamagePoints(exDamage);
                controller.getHumanController().getBenchController().addCard(exActive);
                controller.getHumanController().getBenchController().returnAllCards();
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
