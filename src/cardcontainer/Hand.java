package cardcontainer;

import card.Card;

import java.util.ArrayList;

/**
 * Hand.java - Class for defining a player's hand in the pokemon game
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public class Hand extends CardContainer {

    /**
     * Default Constructor
     */
    public Hand() {
        super(new ArrayList<Card>(7));
    }

    /**
     * Copy Constructor
     *
     * @param copyHand Hand obj to copy
     */
    public Hand(Hand copyHand) {
        super(new ArrayList<Card>(copyHand.getCards()));
    }

    /**
     * Method to add a card in the hand
     *
     * @param newCard New card obj to add
     */
    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    /**
     * Method to choose a card at an index - will remove from hand
     *
     * @param cardIdx Card index to choose
     * @return Card at index
     */
    public Card chooseCard(int cardIdx) {
        try {
            return cards.remove(cardIdx);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public ArrayList<Card> removeAllCards(){
        ArrayList<Card> removedCard = new ArrayList<>(cards);
        getCards().clear();
        return removedCard;
    }

    /**
     * Method to represent the hand in String
     *
     * @return String representing the hand
     */
    public String toString() {

        StringBuilder outputString = new StringBuilder("HAND OF CARDS:");

        if (cards == null || isEmpty()) {
            outputString.append("Empty Hand").append("\n");
        } else {
            for (Card card : cards) {
                outputString.append(card.toString()).append("\n");
            }
        }

        return outputString.toString();

    }

}
