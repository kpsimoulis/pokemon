package controllers.game.KeyListeners;

import ability.*;
import parser.*;
import card.Card;
import card.Energy;
import card.Pokemon;
import card.Trainer;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.game.GameController;
import javafx.util.Pair;
import parser.Attack;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;
import views.card.TrainerView;

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

        if (controller.getHumanController().isTrainerCardAvailable()) {
            builder.append("T. Use your trainer card.\n");

        }

        if (controller.getHumanController().isEvolvable()) {
            builder.append("V. Evolve your pokemon.\n");
        }

        builder.append("X. End Turn\n");


        builder.append("\n");
        builder.append("C. Cheating List for Test.\n");
        builder.append("D. Look at deck and pile.\n");
        controller.getView().setCommand(builder.toString());

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E: {

                if (!controller.getHumanController().handHasEnergy() || controller.isEnergyAdded()) {
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

                if (!controller.getHumanController().handHasPokemon() || controller.getHumanController().getBenchController().isFull()) {
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

                if (!controller.getHumanController().canAttack()) {
                    break;
                }

                StringBuilder builder = new StringBuilder("Press the corresponding number for the attacks:\n");

                Pokemon card = (Pokemon) controller.getHumanController().getActivePokemonController().getPokemonController().getCard();
                HashMap<String, Integer> energyOnCard = controller.getHumanController().getActivePokemonController().getEnergyOnCard();

                int index = 1;
                for (Attack attack : card.getAttack()) {

                    HashMap<String, Integer> energyPokTmp = new HashMap<>(energyOnCard);

                    if (controller.getHumanController().checkAttackEnergy(attack, energyPokTmp)) {
                        builder.append(index).append(". ").append(attack.getAbility().getName()).append("\n");
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
                        + "1. Discard "
                        + energyNeed + " energy card(s), and\n"
                        + "2. Remove all of the stat.\n"
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

            //cheat
            case KeyEvent.VK_C: {
                Energy cheatEngrey1 = new Energy("TestFight", 99, "fight");
                Energy cheatEngrey2 = new Energy("TestPsy", 99, "psychic");
                Energy cheatEngrey3 = new Energy("TestLightning", 99, "lightning");
                Energy cheatEngrey4 = new Energy("TestWater", 99, "water");

                //build menu
                StringBuilder builder = new StringBuilder("Cheat list, for testing:\n");
                builder.append("Choose one of the following by pressing:\n"
                        + "1. Add 4 energies to your pokemon \n"
                        + "2. Heal your active Pokemon 15 points\n"
                        + "3. Add 4 energies to AI's pokemon\n"
                        + "4. Heal AI's active Pokemon 15 points\n"
                        + "5. Damage your Pokemon 15 points\n"
                        + "6. Damage AI's Pokemon 15 points"

                );
                controller.getView().setCommand(builder.toString() + "\n"
                        + "(Press Esc to exit)");
                //add listener
                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_1) {
                            controller.getHumanController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey1);
                            controller.getHumanController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey2);
                            controller.getHumanController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey3);
                            controller.getHumanController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey4);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_2) {
                            controller.getHumanController().getActivePokemonController().getPokemonController().heal(15);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_3) {
                            Energy cheatEngrey = new Energy("Test", 99, "Test");
                            controller.getAIController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey1);
                            controller.getAIController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey2);
                            controller.getAIController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey3);
                            controller.getAIController().getActivePokemonController().getPokemonController().addEnergy(cheatEngrey4);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_4) {
                            controller.getAIController().getActivePokemonController().getPokemonController().heal(15);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_5) {
                            controller.getHumanController().getActivePokemonController().getPokemonController().causeDamage(15);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_6) {
                            controller.getAIController().getActivePokemonController().getPokemonController().causeDamage(15);
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                break;

            }

            //show discardpile
            case KeyEvent.VK_D: {
                //build menu
                StringBuilder builder = new StringBuilder("Look at pile: \n\n");
                builder.append("Choose one of the following by pressing:\n"
                        + "1. Look at your discard pile \n"
                        + "2. Look at AI's discard pile \n"
                        + "3. Look at your deck \n"
                        + "4. Look at AI's deck \n"

                );
                controller.getView().setCommand(builder.toString() + "\n"
                        + "(Press Esc to exit)");
                //add listener
                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_1) {
                            StringBuilder builder = new StringBuilder("Your discardpile: \n\n");
                            int index = 1;
                            for (Card card : controller.getHumanController().getDiscardPileController().getCardContainer().getCards()) {
                                builder.append(index++ + ": "
                                        + card.getName() + " "
                                        + "(" + card.getClass().getSimpleName() + ")"
                                        + "\n");
                            }
                            controller.getView().setCommand(builder.toString() + "\n"
                                    + "(Press Esc to exit)");

                        } else if (e.getKeyCode() == KeyEvent.VK_2) {
                            StringBuilder builder = new StringBuilder("AI's discardpile: \n\n");
                            int index = 1;
                            for (Card card : controller.getAIController().getDiscardPileController().getCardContainer().getCards()) {
                                builder.append(index++ + ": "
                                        + card.getName() + " "
                                        + "(" + card.getClass().getSimpleName() + ")"
                                        + "\n");
                            }
                            controller.getView().setCommand(builder.toString() + "\n"
                                    + "(Press Esc to exit)");

                        } else if (e.getKeyCode() == KeyEvent.VK_3) {
                            StringBuilder builder = new StringBuilder("");
                            int index = 1;
                            for (Card card : controller.getHumanController().getDeckController().getCardContainer().getCards()) {
                                builder.append(index++ + ": "
                                        + card.getName() + " "
                                        + "(" + card.getClass().getSimpleName() + ")"
                                        + "  ");
                            }
                            controller.getView().setCommand(builder.toString() + "\n"
                                    + "(Press Esc to exit)");

                        } else if (e.getKeyCode() == KeyEvent.VK_4) {
                            StringBuilder builder = new StringBuilder("");
                            int index = 1;
                            for (Card card : controller.getAIController().getDeckController().getCardContainer().getCards()) {
                                builder.append(index++ + ": "
                                        + card.getName() + " "
                                        + "(" + card.getClass().getSimpleName() + ")"
                                        + "  ");
                            }
                            controller.getView().setCommand(builder.toString() + "\n"
                                    + "(Press Esc to exit)");

                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });


                break;

            }


            case KeyEvent.VK_V: {
                // make sure pressing V wont crash the game, when player cannot evolve, and more instruction.
                if (!controller.getHumanController().isEvolvable()) {
                    StringBuilder builder = new StringBuilder("You cannot evolve your any of your Pokemon now! \n");
                    builder.append("(Press Esc to exit)");
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
                StringBuilder builder = new StringBuilder("");
                builder.append("Now you can do the following by Pressing: \n\n");

                if (controller.getHumanController().isActivePokemonEvolvable()) {
                    builder.append("V. Evolve your active Pokemon! \n\n");
                }
                if (controller.getHumanController().isBenchPokemonEvolvable()) {
                    builder.append("B. Evolve one of your bench Pokemon! \n\n");

                }

                builder.append("(Press Esc to exit)\n\n");
                controller.getView().setCommand(builder.toString());

                builder.append("Available card(s): \n");
                for (int i = 0; i < controller.getHumanController().getStage1PokemonInHand().size(); i++) {
                    builder.append(controller.getHumanController().getStage1PokemonInHand().get(i).getName() + " evolves from " + controller.getHumanController().getStage1PokemonInHand().get(i).getEvolvesFrom() + "\n");
                }
                controller.getView().setCommand(builder.toString());
                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                            controller.getHumanController().getBenchController().removeAllListeners(this);
                            controller.getView().disableKeyListener();
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        } else if (e.getKeyCode() == KeyEvent.VK_V &&
                                controller.getHumanController().isActivePokemonEvolvable()) {
                            //get pokemon and damage point from activepokemon controller
                            Pokemon exActive = new Pokemon(controller.getHumanController().getActivePokemonCard());
                            Pokemon stage1Pokemon = null;
                            ArrayList<Energy> energies =
                                    ((Pokemon) controller.getHumanController().getActivePokemonController().getPokemonController().getCard()).getEnergy();
                            int exDamage = controller.getHumanController().getActivePokemonCard().getDamagePoints();
                            ArrayList<Pokemon> stage1Pokemons = controller.getHumanController().getStage1PokemonInHand();
                            for (Pokemon pok : stage1Pokemons) {
                                if (pok.getEvolvesFrom().equals(controller.getHumanController().getActivePokemonCard().getName())) {
                                    stage1Pokemon = pok;
                                    break;
                                }
                            }
                            //TODO: Detach items and clear the stat

                            //clear active panel
                            controller.getHumanController().setActivePokemonController(null);
                            controller.getHumanController().getPlayer().removeActivePokemon();
                            controller.getView().getBoard().getPlayerActivePanel().removeAll();


                            // Remove from container and set as active for both player controller and in the view
                            Pair<CardController, CardView> pair = controller.getHumanController().getHandController().removeCard(stage1Pokemon);

                            ActivePokemonView activePokemonView = controller.getHumanController().
                                    setActivePokemon(true, (PokemonController) pair.getKey(), (PokemonView) pair.getValue());
                            controller.getView().setPlayerActive(activePokemonView);


                            //rewrite the information from ex-activepokemon
                            controller.getHumanController().getActivePokemonController().getPokemonController().causeDamage(exDamage);
                            for (Energy eng : energies) {
                                controller.getHumanController().getActivePokemonController().getPokemonController().addEnergy(eng);
                            }
                            exActive.emptyEnergy();
                            controller.getHumanController().getActivePokemonCard().attachPokemon(exActive);
                            // Remove all key listeners of this type and go back to menu
                            controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                            controller.getView().disableKeyListener();
                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                        }
                        //evolve bench pokemon
                        else if (e.getKeyCode() == KeyEvent.VK_B &&
                                controller.getHumanController().isBenchPokemonEvolvable()) {
                            controller.getView().setCommand("Choose a basic Pokemon from your bench to evolve!"
                                    + "\n(Press Esc to exit)");


                            //get pokemon and damage point from activepokemon controller
                            ArrayList<Pokemon> stage1Pokemons = controller.getHumanController().getStage1PokemonInHand();

                            EvolveListener evolveListener = new EvolveListener(controller, controller.getHumanController().getBenchController(), stage1Pokemons);
                            for (int i = 0; i < controller.getHumanController().getBenchController().getContainer().getCards().size(); i++) {
                                for (Pokemon pok : stage1Pokemons) {

                                    if (pok.getEvolvesFrom().equals(controller.getHumanController().getBenchController().getCardControllers().get(i).getCard().getName())) {
                                        controller.getHumanController().getBenchController().getCardControllers().get(i).getView().setListeners(evolveListener);
                                        break;
                                    }
                                }
                            }
                            controller.getView().addBoardListerner(new KeyListener() {
                                @Override
                                public void keyTyped(KeyEvent e) {

                                }

                                @Override
                                public void keyPressed(KeyEvent e) {
                                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                        controller.getView().disableKeyListener();
                                        controller.getHumanController().getBenchController().removeAllListeners(evolveListener);
                                        controller.getView().addBoardListerner(new MainMenuListener(controller));
                                    }
                                }

                                @Override
                                public void keyReleased(KeyEvent e) {

                                }
                            });
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });


                break;


            }

            case KeyEvent.VK_T: {
                if (!controller.getHumanController().isTrainerCardAvailable()) {
                    StringBuilder builder = new StringBuilder("You donot have any trainer card in your hand now! \n");
                    builder.append("(Press Esc to exit)");
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

                KeyListener trainerListener = new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_ENTER: {
                                TrainerView chosenCard = (TrainerView) SwingUtilities.getAncestorOfClass(TrainerView.class, (Component) e.getSource());

                                Card card = controller.findCardInContainer(chosenCard, controller.getHumanController().getHandController()).getCard();
                                Trainer tc = (Trainer) card;
                                Ability ability = tc.getAbility();
                                String type = tc.getAbility().getLogic().get(0).getClass().getSimpleName();
                                controller.getHumanController().setChosingCard(true);

                                switch (type) {
                                    case ("Draw"): {
                                        Pair<CardController, CardView> pair = controller.getHumanController().getHandController().removeCard(card);
                                        controller.getHumanController().getDiscardPileController().addCard((Trainer) pair.getKey().getCard());
                                        Amount amount = ((Draw) tc.getAbility().getLogic().get(0)).getAmount();
                                        Target target = ((Draw) ability.getLogic().get(0)).getTarget();
                                        int number = amount.getAmount();
                                        for (int i = 0; i < number; i++) {
                                            if (target.getName().equals("your"))
                                                controller.getHumanController().dealDeckHand();
                                            else
                                                controller.getAIController().dealDeckHand();
                                        }
                                        controller.getHumanController().getHandController().returnAllCards();

                                        //remove listener combo
                                        controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                                        controller.getHumanController().getBenchController().removeAllListeners(this);
                                        controller.getHumanController().getHandController().removeAllListeners(this);
                                        controller.getView().addBoardListerner(new MainMenuListener(controller));
                                        controller.getHumanController().setChosingCard(false);

                                        break;
                                    }//draw

                                    case ("Heal"): {
                                        Amount amount = ((Heal) tc.getAbility().getLogic().get(0)).getAmount();
                                        Target target = ((Heal) tc.getAbility().getLogic().get(0)).getTarget();

                                        int healAmount = amount.getAmount();
                                        if (target.getChoice() == false) {
                                            controller.getHumanController().getActivePokemonController().getPokemonController().heal(healAmount);
                                            controller.getHumanController().getHandController().removeCard(card);
                                            controller.getHumanController().setChosingCard(false);
                                            controller.getView().addBoardListerner(new MainMenuListener(controller));
                                        } else {
                                            HealListener healListener = new HealListener(controller, card, healAmount);
                                            controller.getView().setCommand("Select Pokemon and press Enter\nto heal " + healAmount + " HP points.");

                                            controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                                            controller.getHumanController().getBenchController().removeAllListeners(this);
                                            controller.getHumanController().getBenchController().setPokemonListener(healListener);
                                            controller.getHumanController().getActivePokemonController().setKeyListener(healListener);
                                        }
                                        controller.getHumanController().getHandController().removeAllListeners(this);

                                        break;
                                    }//heal


                                    default:
                                        controller.getView().setCommand("Haven't implement yet. default from Trainer card in MainMenu.\nPress ESC to go back");

                                        break;
                                }//switch type

                                break;
                            }//key


                            default: {
                                System.out.println("Enter the correct Key.(from MainMenulistener)");
                                break;
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                };//trainerListener
                controller.getHumanController().getHandController().setTrainerListener(trainerListener);
                //build menu
                StringBuilder builder = new StringBuilder("Now you can use your trainer card \n\n");
                builder.append("Choose one of the your trainer card and press Enter\n\n"
                );
                controller.getView().setCommand(builder.toString() + "\n"
                        + "(Press Esc to exit)");


                controller.getView().addBoardListerner(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            if (controller.getHumanController().getIsChosingCard() == false) {

                                controller.getHumanController().getActivePokemonController().removeKeyListener(this);
                                controller.getHumanController().getBenchController().removeAllListeners(this);
                                controller.getHumanController().getHandController().removeAllListeners(this);
                                controller.getView().disableKeyListener();
                                controller.getHumanController().getHandController().removeAllListeners(trainerListener);
                                controller.getView().addBoardListerner(new MainMenuListener(controller));
                            }
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
