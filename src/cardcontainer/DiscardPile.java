package cardcontainer;

import card.Card;

import java.util.ArrayList;

/**
 * DiscardPile.java - Class for defining a player's discard pile
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public class DiscardPile extends CardContainer {

    /**
     * Default Constructor
     */
    public DiscardPile() {
        super(new ArrayList<Card>(1));
    }

    /**
     * Copy Constructor
     *
     * @param copyPile Discard Pile obj to copy
     */
    public DiscardPile(DiscardPile copyPile) {
        cards = new ArrayList<Card>(copyPile.getCards());
    }

    /**
     * Method to add Card to pile
     *
     * @param newCard Card to add
     */
    public void addCard(Card newCard) {
        cards.add(newCard);
    }

}
