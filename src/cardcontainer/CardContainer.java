package cardcontainer;

import card.Card;

import java.util.ArrayList;

/**
 * CardContainer.java - Abstract Class for defining all card containers in the game
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public abstract class CardContainer {

    ArrayList<Card> cards;

    /**
     * Default container
     */
    CardContainer() {
        cards = new ArrayList<Card>();
    }

    /**
     * Parametrized Contructor
     *
     * @param cardsSet A set of cards
     */
    CardContainer(ArrayList<Card> cardsSet) {
        cards = cardsSet;
    }

    /**
     * Method to check if container is empty
     *
     * @return True if holds card, False otherwise
     */
    public boolean isEmpty() {
        return cards.size() == 0;
    }

    /**
     * Function to get the no of cards in the container
     *
     * @return The number of cards in the container
     */
    public int getNoOfCards() {
        return cards.size();
    }

    /**
     * Getter for the cards list
     *
     * @return List of cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Setter for list of cards
     *
     * @param newCards List of new cards
     */
    public void setCards(ArrayList<Card> newCards) {
        cards = new ArrayList<Card>(newCards);
    }

    /**
     * Method to get a card's index in the list
     *
     * @param searchCard Card to search
     * @return Searched card's index
     */
    public int getCardIdx(Card searchCard) {
        return cards.indexOf(searchCard);
    }

    /**
     * Method to check if a particular card is present in the container
     *
     * @param searchedCard Card to search
     * @return True if card is in container, False otherwise
     */
    public boolean containsCard(Card searchedCard) {

        for (Card card : cards) {
            if (card.equals(searchedCard)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Method to remove a card from the Container
     *
     * @param removedCard Card to be removed
     * @return True if removed, False otherwise
     */
    public boolean removeCard(Card removedCard) {
        return cards.remove(removedCard);
    }

    /**
     * Method to remove card from the Container with index
     *
     * @param cardIdx Card index to remove
     * @return Card object removed
     */
    public Card removeCard(int cardIdx) {
        return cards.remove(cardIdx);
    }

    /**
     * Method to check if two card containers are the same
     *
     * @param anotherContainer Container to compare with
     * @return True if containers are the same, False otherwise
     */
    public boolean equals(CardContainer anotherContainer) {

        if ((cards.size() != anotherContainer.cards.size()) || (cards == null && anotherContainer.cards != null) ||
                (cards != null && anotherContainer.cards == null)) {
            return false;
        }

        if (cards == null && anotherContainer.cards == null) {
            return true;
        }

        for (Card card : cards) {
            if (!anotherContainer.cards.contains(card)) {
                return false;
            }
        }
        return true;

    }

    public abstract void addCard(Card newCard);
}
