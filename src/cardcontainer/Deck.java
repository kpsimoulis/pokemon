package cardcontainer;

import card.Card;
import card.Energy;
import card.Pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
     * @param textFile Text file path
     */
    public void populateDeckFromTcgo(String textFile) {
        System.out.println("Populating: " + textFile);


        try (BufferedReader br = new BufferedReader(new FileReader("files/" + textFile))) {

            String line;
            String cardType = "";
            Card tmpCard;
            int idx = 0;

            while ((line = br.readLine()) != null) {

                String[] lineVariables = line.split(" ");
                if (lineVariables[0].equals("##Pokémon")) {
                    System.out.println("Reading " + lineVariables[2] + " Pokemon cards");
                    cardType = "pokemon";
                } else if (lineVariables[0].equals("##Trainer")) {
                    System.out.println("Reading " + lineVariables[3] + " Trainer cards");
                    cardType = "trainer";
                } else if (lineVariables[0].equals("##Energy")) {
                    System.out.println("Reading " + lineVariables[2] + " Energy cards");
                    cardType = "energy";
                } else if (lineVariables[0].equals("*")) {
                    if (cardType.equals("energy")) {
                        tmpCard = new Energy(lineVariables[2], idx, lineVariables[3]);
                        cards.add(tmpCard);
                    }
                    idx++;
//                    System.out.printf("Cards (%d), Face(%s), Category(%s), HP(%s)\n", Integer.parseInt(lineVariables[1]), lineVariables[2], lineVariables[3], lineVariables[4]);
                } else if (lineVariables[0].equals("Total")) {
                    System.out.println("Processed a total of " + lineVariables[3] + " cards");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void populateDeck(String fileName) throws IOException {
        System.out.println("Populating: " + fileName);


        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


//            stream.forEach(System.out::println);

            stream.forEach(listItem -> {

                String line;
                int cardLineNum;
                try {
                    cardLineNum = Integer.parseInt(listItem) - 1;
                    line = Files.readAllLines(Paths.get("files/cards.txt")).get(cardLineNum);
                    String[] lineVariables = line.split(":");
                    if (lineVariables[1].equals("pokemon")) {
                        if (!lineVariables[3].equals("basic")) {
//                            System.out.printf("POKEMON(%d) - Name: %s, Stage: %s, Evolves From: %s, Category:%s, HP: %s\n", Integer.parseInt(listItem), lineVariables[0], lineVariables[3], lineVariables[4], lineVariables[6], lineVariables[7]);
                        } else {
//                            System.out.printf("POKEMON(%d) - Name: %s, Stage: %s, Category:%s, HP: %s\n", Integer.parseInt(listItem), lineVariables[0], lineVariables[3], lineVariables[5], lineVariables[6]);
                        }

                    } else if (lineVariables[1].equals("trainer")) {
                        System.out.printf("TRAINER(%d) - Name: %s, Category:%s, Ability Line: %s\n", Integer.parseInt(listItem), lineVariables[0], lineVariables[3], lineVariables[4]);

                    } else if (lineVariables[1].equals("energy")) {
//                        System.out.printf("ENERGY(%d) - Name: %s, Category:%s\n", Integer.parseInt(listItem), lineVariables[0], lineVariables[3]);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                System.out.println(listItem + ":");
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
