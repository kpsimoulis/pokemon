package cardcontainer;

import card.Card;
import card.Energy;
import card.Pokemon;
import card.Trainer;
import main.DeckParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Deck.java - Class for defining a deck in the pokemon game
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public class Deck extends CardContainer {

    /**
     * Default Constructor
     */
    public Deck() {
        cards = new ArrayList<Card>(60);
    }

    /**
     * Copy Constructor
     *
     * @param copyDeck Deck obj to copy from
     */
    public Deck(Deck copyDeck) {
        cards = new ArrayList<Card>(copyDeck.getCards());
    }

    /**
     * Method to deal a card from the deck
     *
     * @return Card obj at top of deck, null if no cards in the deck
     */
    public Card dealCard() {
        try {
            return cards.remove(cards.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Method to populate the deck from a text file
     *
     * @param fileName Text file path
     */
    public void populateDeck(String fileName) throws IOException {
        System.out.println("Populating: " + fileName);

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            final AtomicInteger idx = new AtomicInteger();

            stream.forEach(listItem -> {

                String line;
                String abilityLine;
                Card tmpCard;

                DeckParser parser;

                try {
                    line = Files.readAllLines(Paths.get("res/deck/cards.txt")).get(Integer.parseInt(listItem) - 1);
                    String[] lineVariables = line.split(":");

                    String[] items = line.split(":");

                    parser = new DeckParser(items);

                    parser.parseName();
                    parser.parseCardType();

                    if (parser.getCardType().equals("pokemon")) {
                        parser.parseStage();
                        ArrayList<Energy> tmpEnergy = new ArrayList<Energy>();
                        if (!parser.getStage().equals("basic")) {
                            parser.parseEvolvesFrom();
                            parser.parseCategory();
                            parser.parseHealthPoints();
                            parser.parseRetreat();
                            parser.parseAttacks();
                            tmpCard = new Pokemon(parser.getName(), // Name
                                    idx.incrementAndGet(), // Index
                                    parser.getCategory(), // Category
                                    parser.getHealthPoints(), // HP
                                    tmpEnergy, // Energy Array
                                    parser.getStage(), // Stage
                                    parser.getEvolvesFrom(), // Evolves From
                                    parser.getRetreat(),
                                    parser.getAttack()
                            );
                            cards.add(tmpCard);
                        } else {
                            parser.parseCategory();
                            parser.parseHealthPoints();
                            parser.parseRetreat();
                            parser.parseAttacks();
                            tmpCard = new Pokemon(parser.getName(), // Name
                                    idx.incrementAndGet(), // Index
                                    parser.getCategory(), // Category
                                    parser.getHealthPoints(), // HP
                                    tmpEnergy, // Energy Array
                                    parser.getStage(), // Stage
                                    "", // Evolves From
                                    parser.getRetreat(),
                                    parser.getAttack()
                            );
                            cards.add(tmpCard);
                        }

                    } else if (parser.getCardType().equals("trainer")) {
                        parser.parseCategory();
                        parser.parseAbilityLineNum();
                        abilityLine = Files.readAllLines(Paths.get("res/deck/abilities.txt")).get(parser.getAbilityLineNum() - 1);
                        String[] abilityLineVariables = abilityLine.split(":");
                        tmpCard = new Trainer(parser.getName(),
                                idx.incrementAndGet(),
                                parser.getCategory(),
                                "",
                                abilityLineVariables[0]
                        );
                        cards.add(tmpCard);
                    } else if (parser.getCardType().equals("energy")) {
                        parser.parseCategory();
                        tmpCard = new Energy(parser.getName(),
                                idx.incrementAndGet(),
                                parser.getCategory()
                        );
                        cards.add(tmpCard);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to validate the deck
     *
     * @return True if deck is valid, False otherwise
     */
    public boolean validate() {

        if (cards == null || cards.size() != 60)
            return false;

        int basicPokemonCounter = 0;

        //TODO: Check stage pokemon and highest amount of energy required

        for (Card card : cards) {

            if (card.getClass() != Energy.class && getSpecificCardCount(card) > 4) {
                return false;
            } else {
                if (card.getClass() == Pokemon.class)
                    basicPokemonCounter++;
                else
                    return false;
            }

        }

        return basicPokemonCounter != 0;

    }

    /**
     * Method to count the no. of times a specific card appear in the deck.
     *
     * @param searchedCard The card to search
     * @return The no. of times this specific card was found
     */
    public int getSpecificCardCount(Card searchedCard) {

        int counter = 0;
        for (Card card : cards) {
            if (card.equals(searchedCard)) {
                counter++;
            }
        }
        return counter;

    }

    /**
     * Method to shuffle the deck
     */
    public void shuffle() {
        //Collections.shuffle(cards);

        ArrayList<Card> tmpList = new ArrayList<Card>(cards);

        while (tmpList.size() > 0) {
            int randomIdx = (int) (Math.random() * tmpList.size());
            cards.add(randomIdx, tmpList.remove(randomIdx));
        }

    }

    /**
     * Method to get a string representation of the deck
     *
     * @return String representation of the deck
     */
    public String toString() {

        StringBuilder printString = new StringBuilder("DECK OF CARDS:\n");

        if (isEmpty()) {
            printString.append("Deck is empty");
        } else {
            for (Card card : cards) {
                printString.append(card.toString()).append("\n");
            }
        }

        return printString.toString();

    }

}
