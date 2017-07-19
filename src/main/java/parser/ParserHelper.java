package parser;

import ability.*;
import card.*;
import cardcontainer.Deck;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserHelper {

    private ArrayList<Pokemon> pokemons;
    private ArrayList<Trainer> trainers;
    private ArrayList<Energy> energies;
    private ArrayList<Attack> attacks;
    private boolean parsed = false;

    /**
     * Parse deck1 and deck2
     */
    public void parse() {
        this.pokemons = new ArrayList<Pokemon>();
        this.trainers = new ArrayList<Trainer>();
        this.energies = new ArrayList<Energy>();
        this.attacks = new ArrayList<Attack>();

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        try {
            deck1.populateDeck("src/main/resources/deck/deck1.txt");
            deck2.populateDeck("src/main/resources/deck/deck2.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(0);
        }

        for (Card card : deck1.getCards()) {
            parseCard(card);
        }

        for (Card card : deck2.getCards()) {
            parseCard(card);
        }

        parsed = true;

    }

    /**
     * Parse specific card
     * @param card
     */
    private void parseCard(Card card) {
        if(card instanceof Pokemon) {
            if (!pokemons.contains(card)) {
                pokemons.add((Pokemon) card);
            }
            for (Attack attack: ((Pokemon) card).getAttack()) {
                if (!attacks.contains(attack)) {
                    attacks.add(attack);
                }
            }
        }
        else if (card instanceof Trainer) {
            if (!trainers.contains(card)) {
                trainers.add((Trainer) card);
            }
        }
        else if (card instanceof Energy) {
            if (!energies.contains(card)) {
                energies.add((Energy) card);
            }
        }
    }

    /**
     * Print everything that has been parsed
     */
    public void printAll() {
        printPokemon();
        System.out.println();
        printTrainer();
        System.out.println();
        printEnergy();
        System.out.println();
        printAttack();
    }

    public void printPokemon() {
        printObj("pokemon");
    }

    public void printTrainer() {
        printObj("trainer");
    }

    public void printEnergy() {
        printObj("energy");
    }

    public void printAttack() {
        printObj("attack");
    }

    private void printObj(String type) {
        if (parsed) {
            if (type.equals("pokemon")) {
                System.out.println("Pokemon:");
                pokemons.forEach(s -> System.out.println(s));
            }
            else if (type.equals("trainer")) {
                System.out.println("Trainer:");
                trainers.forEach(s -> System.out.println(s));
            }
            else if (type.equals("energy")) {
                System.out.println("Energy:");
                energies.forEach(s -> System.out.println(s));
            }
            else if (type.equals("attack")) {
                System.out.println("Attack:");
                attacks.forEach(s -> System.out.println(s));
            }
        }
        else {
            System.out.println("Please run parse() first");
        }
    }

    public void printAttacksByType(String type) {
        attacks.forEach(s -> {
            if (s.getAbility().getLogic().get(0).getType().equals(type))
                System.out.println(s.getAbility().getName() + ": " + s.getAbility().getLogic().get(0));
        });
    }

    /**
     * Hardest ones to parse, need to verify
     */
    public void printAttacksWithMultipleAbilities() {
        attacks.forEach(s -> {
            if (s.getAbility().getLogic().size() > 1)
            System.out.println(s);
        });
    }

    public Pokemon getPokemonByName(String name) {
        Pokemon pokemon = null;
        if (parsed) {
            for (Pokemon card : pokemons) {
                if (card.getName().contains(name)) {
                    pokemon = card;
                }
            }
        }
        return pokemon;
    }

    public void printPokemonWithoutRetreat() {
        Pokemon pokemon = null;
        if (parsed) {
            for (Pokemon card : pokemons) {
                if (card.getRetreat() == null) {
                    System.out.println(card);
                }
            }
        }
    }

    public Trainer getTrainerByName(String name) {
        Trainer trainer = null;
        if (parsed) {
            for (Trainer card : trainers) {
                if (card.getName().contains(name)) {
                    trainer = card;
                }
            }
        }
        return trainer;
    }

    public Energy getEnergyByName(String name) {
        Energy energy = null;
        if (parsed) {
            for (Energy card : energies) {
                if (card.getName().contains(name)) {
                    energy = card;
                }
            }
        }
        return energy;
    }

    public AbilityLogic getAbilityByLogic(String type, List<String> logic) {
        if (type.equals("dam")) {
            return new Dam(logic);
        }
        else if (type.equals("heal")) {
            return new Heal(logic);
        }
        else if (type.equals("deenergize")) {
            return new Deenergize(logic);
        }
        else if (type.equals("reenergize")) {
            return new Reenergize(logic);
        }
        else if (type.equals("redamage")) {
            return new Redamage(logic);
        }
        else if (type.equals("swap")) {
            return new Swap(logic);
        }
        else if (type.equals("destat")) {
            return new Destat(logic);
        }
        else if (type.equals("applystat")) {
            return new Applystat(logic);
        }
        else if (type.equals("draw")) {
            return new Draw(logic);
        }
        else if (type.equals("search")) {
            return new Search(logic);
        }
        else if (type.equals("deck")) {
            return new ability.Deck(logic);
        }
        else if (type.equals("shuffle")) {
            return new Shuffle(logic);
        }
        else if (type.equals("cond")) {
            return new Cond(logic);
        }
        else if (type.equals("add")) {
            return new Add(logic);
        }
        else {
            throw new IllegalArgumentException("Invalid Ability: " + type);
        }

    }


}
