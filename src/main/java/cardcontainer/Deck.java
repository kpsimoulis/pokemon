package cardcontainer;

import card.Card;
import card.Energy;
import card.Pokemon;
import card.Trainer;
import parser.AbilitiesFileParser;
import parser.Ability;
import parser.CardsFileParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public void addCard(Card newCard) {
        cards.add(newCard);
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
                Ability tmpAbility;

                CardsFileParser cardsParser;
                AbilitiesFileParser abilitiesParser;

                try {
                    line = Files.readAllLines(Paths.get("src/main/resources/deck/cards.txt")).get(Integer.parseInt(listItem) - 1);
                    String[] items = line.split(":");

                    cardsParser = new CardsFileParser(items);

                    cardsParser.parseName();
                    cardsParser.parseCardType();

                    if (cardsParser.getCardType().equals("pokemon")) {
                        cardsParser.parseStage();
                        ArrayList<Energy> tmpEnergy = new ArrayList<Energy>();
                        ArrayList<Pokemon> tmpPokemons = new ArrayList<Pokemon>();

                        if (!cardsParser.getStage().equals("basic")) {
                            cardsParser.parseEvolvesFrom();
                            cardsParser.parseCategory();
                            cardsParser.parseHealthPoints();
                            cardsParser.parseRetreat();
                            cardsParser.parseAttacks();
                            tmpCard = new Pokemon(cardsParser.getName(), // Name
                                    idx.incrementAndGet(), // Index
                                    cardsParser.getCategory(), // Category
                                    cardsParser.getHealthPoints(), // HP
                                    tmpEnergy, // Energy Array
                                    cardsParser.getStage(), // Stage
                                    cardsParser.getEvolvesFrom(), // Evolves From
                                    cardsParser.getRetreat(),
                                    cardsParser.getAttack(),
                                    tmpPokemons
                            );
                            cards.add(tmpCard);
                        } else {
                            cardsParser.parseCategory();
                            cardsParser.parseHealthPoints();
                            cardsParser.parseRetreat();
                            cardsParser.parseAttacks();
                            tmpCard = new Pokemon(cardsParser.getName(), // Name
                                    idx.incrementAndGet(), // Index
                                    cardsParser.getCategory(), // Category
                                    cardsParser.getHealthPoints(), // HP
                                    tmpEnergy, // Energy Array
                                    cardsParser.getStage(), // Stage
                                    "", // Evolves From
                                    cardsParser.getRetreat(),
                                    cardsParser.getAttack(),
                                    tmpPokemons
                            );
                            cards.add(tmpCard);
                        }

                    } else if (cardsParser.getCardType().equals("trainer")) {
                        cardsParser.parseCategory();
                        cardsParser.parseAbilityLineNum();
                        abilityLine = Files.readAllLines(Paths.get("src/main/resources/deck/abilities.txt")).get(cardsParser.getAbilityLineNum() - 1);
                        String[] abilityLineVariables = abilityLine.split(":");

                        abilitiesParser = new AbilitiesFileParser(abilityLineVariables);
                        abilitiesParser.parseName();
                        abilitiesParser.parseDescription();
                        abilitiesParser.parseLogic();

                        tmpAbility = new Ability(abilitiesParser.getName(), abilitiesParser.getDescription(), abilitiesParser.getLogic());

                        tmpCard = new Trainer(cardsParser.getName(),
                                idx.incrementAndGet(),
                                cardsParser.getCategory(),
                                tmpAbility
                        );
                        cards.add(tmpCard);
                    } else if (cardsParser.getCardType().equals("energy")) {
                        cardsParser.parseCategory();
                        tmpCard = new Energy(cardsParser.getName(),
                                idx.incrementAndGet(),
                                cardsParser.getCategory()
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

        if (cards == null || cards.size() != 60){
            return false;
        }

        int basicPokemonCounter = 0;

        //TODO: Check stage pokemon and highest amount of energy required

        for (Card card : cards) {

            if (card.getClass() != Energy.class && getSpecificCardCount(card) > 4) {
                return false;
            } else {
                if (card.getClass() == Pokemon.class)
                    basicPokemonCounter++;
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
        Collections.shuffle(cards);
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

    public ArrayList<Card> Search(int searchAmount, String filterType, String filterCategory, int filterTotal, String filterTarget) {
        ArrayList<Card> resultCards = new ArrayList<Card>(filterTotal);
        int currentTotal = 0;
        if (!isEmpty()) {
            for (Card card : cards) {
                if (filterType.equals("energy") && card instanceof Energy) {
                    resultCards.add(card);
                    currentTotal++;
                }
                else if (filterType.equals("pokemon") && card instanceof Pokemon) {
                    resultCards.add(card);
                    currentTotal++;
                }
                else if (card.getCategory().equals(filterCategory)) {
                    resultCards.add(card);
                    currentTotal++;
                }

                if (currentTotal == searchAmount) {
                    break;
                }
            }
        }

        return resultCards;

    }

}
