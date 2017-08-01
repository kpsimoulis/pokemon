package cardcontainer;

import card.Card;

import java.util.ArrayList;

/**
 * Bench.java - Class for defining a bench in the pokemon game
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public class Bench extends CardContainer {

    private static final int BENCH_LIMIT = 5;

    /**
     * Default Constructor
     */
    public Bench() {
        super(new ArrayList<Card>(BENCH_LIMIT));
    }

    /**
     * Copy constructor
     *
     * @param copyBench Bench obj to copy
     */
    public Bench(Bench copyBench) {
        cards = new ArrayList<Card>(copyBench.getCards());
    }

    /**
     * Method to add card in the bench
     *
     * @param newCard New Card to add in the bench
     */
    public void addCard(Card newCard) {
        if (isFull()) { // Check if becnh is full before adding new card
            System.out.println("Bench is full.\n");
        } else {
            cards.add(newCard);
        }
    }

    /**
     * Method to validate bench
     *
     * @return True if Bench is valid, False otherwise
     */
    public boolean validate() {
        return getNoOfCards() == 5;
    }

    /**
     * Method to have a string representation of the Bench
     *
     * @return String representation of the Bench
     */
    public String toString() {

        StringBuilder printString = new StringBuilder("BENCHED CARDS:\n");

        if (isEmpty()) {
            printString.append("Bench is empty");
        } else {
            for (Card card : cards) {
                printString.append(card.toString()).append("\n");
            }
        }

        return printString.toString();

    }

    /**
     * Method to check if bench has reached size limit
     *
     * @return True if full, False otherwise
     */
    public boolean isFull() {
        return getNoOfCards() >= BENCH_LIMIT;
    }

    /**
     * Method to swap a card with a becnh card
     *
     * @param card1     Card to put in bench
     * @param targetIdx Index to put card
     * @return Card object swapped, null if no card at that position
     */
    public Card swapCards(Card card1, int targetIdx) {

        if (cards.get(targetIdx) != null) {
            return cards.set(targetIdx, card1);
        }
        return null;

    }

}
