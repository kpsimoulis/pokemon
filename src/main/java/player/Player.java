package player;

import card.Card;
import card.Pokemon;
import cardcontainer.*;

/**
 * Player.java - Class for defining a player in the pokemon game
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public class Player {

    private Deck deck;
    private Hand hand;
    private Bench bench;
    private DiscardPile discardPile;
    private PrizeCards prizeCards;
    private Pokemon activePokemon;
    private Coin playerCoin;
    private String name;

    /**
     * Default Constructor for the Player class
     */
    public Player() {
        deck = new Deck();
        hand = new Hand();
        bench = new Bench();
        discardPile = new DiscardPile();
        prizeCards = new PrizeCards();
        playerCoin = new Coin();
        name = "default";
    }

    /**
     * Parameterized constructor for the Player class
     *
     * @param newDeck A validated deck to be assigned to the player
     * @param newName The player's name
     */
    public Player(Deck newDeck, String newName) {
        deck = newDeck;
        hand = new Hand();
        bench = new Bench();
        discardPile = new DiscardPile();
        prizeCards = new PrizeCards();
        playerCoin = new Coin();
        name = newName;
    }

    /**
     * Mutator for the player's deck
     *
     * @param newDeck New deck to be assigned
     */
    public void setDeck(Deck newDeck) {
        deck = new Deck(newDeck);
    }

    /**
     * Mutator for the player's hand
     *
     * @param newHand New hand for player
     */
    public void setHand(Hand newHand) {
        hand = new Hand(newHand);
    }

    /**
     * Setter for the player's bench
     *
     * @param newBench New bench for the player
     */
    public void setBench(Bench newBench) {
        bench = new Bench(newBench);
    }

    /**
     * Mutator for the player's prize cards
     *
     * @param newPrizeCards Array of 6 prize cards
     */
    public void setPrizeCards(PrizeCards newPrizeCards) {
        prizeCards = new PrizeCards(newPrizeCards);
    }

    /**
     * Setter for the player's active pokemon
     *
     * @param newActivePokemon New player's active pokemon
     */
    public void setActivePokemon(Pokemon newActivePokemon) {
        if (hasActivePokemon() && bench.containsCard(newActivePokemon)) {
            bench.swapCards(activePokemon, bench.getCardIdx(newActivePokemon));
        } else {
            activePokemon = newActivePokemon;
        }
    }

    /**
     * Setter for player's name
     *
     * @param newName New player's name
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Accessor for the player's deck
     *
     * @return Deck obj - Player's deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Accessor for the player's hand
     *
     * @return Hand obj - Player's hand
     */
    public Hand getHand() {
        return hand;
    }

    public Coin getPlayerCoin() {
        return playerCoin;
    }

    /**
     * Getter for the player's bench
     *
     * @return Bench obj - Player's bench
     */
    public Bench getBench() {
        return bench;
    }

    /**
     * Accessor for the player's discard pile
     *
     * @return Discard Pile obj - Player's discarded cards
     */
    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    /**
     * Accessor for the player's prize cards
     *
     * @return Array obj - Player's prize cards
     */
    public PrizeCards getPrizeCards() {
        return prizeCards;
    }

    /**
     * Getter for the player's active pokemon
     *
     * @return Card obj - Player's Active Pokemon
     */
    public Card getActivePokemon() {
        return activePokemon;
    }

    /**
     * Getter for the player's name
     *
     * @return String - Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Flip player's coin
     *
     * @return True if Head, False if Tail
     */
    public boolean flipCoin() {
        playerCoin.flip();
        return playerCoin.isHead();
    }

    /**
     * Shuffles player's deck
     */
    public void shuffleDeck() {
        deck.shuffle();
    }

    /**
     * Method to draw a card in the deck and put in hand
     */
    public void drawCard() {
        hand.addCard(deck.dealCard());
    }

    /**
     * Put a Pokemon card in the player's bench
     *
     * @param pokemonCard Pokemon to place in bench
     */
    public void putPokOnBench(Pokemon pokemonCard) {
        bench.addCard(pokemonCard);
    }

    /**
     * Method to discard a card from hand, deck or bench
     *
     * @param discardedCard The card to be discarded
     * @param origin        The origin of the card to be discarded
     */
    public void discardCard(Card discardedCard, CardContainer origin) {
        if (origin.getClass() != DiscardPile.class && origin.containsCard(discardedCard) && origin.removeCard(discardedCard)) {
            if (discardedCard.getClass() == Pokemon.class) {
                // TODO: Discard Energies as well
            } else {
                discardPile.addCard(discardedCard);
            }
        } else {
            System.out.println("Cannot discard card from " + origin.getClass());
        }
    }

    /**
     * Method to check if player has an active pokemon
     *
     * @return True if has active pokemon, false otherwise
     */
    public boolean hasActivePokemon() {
        return activePokemon != null;
    }

    /**
     * Method to get no. of cards in player's deck
     *
     * @return No. of cards in player's deck
     */
    public int getNoCardsDeck() {
        return deck.getNoOfCards();
    }

    /**
     * Method to display Player's information
     *
     * @return String - information on player (name, hand, bench, active pokemon, cards left in deck)
     */
    public String toString() {

        StringBuilder returnString = new StringBuilder();

        returnString.append("Player ").append(name).append(":\n");
        returnString.append("Active Pokemon: ");
        try {
            returnString.append(activePokemon.toString());
        } catch (NullPointerException e) {
            returnString.append("None");
        } finally {
            returnString.append("\n");
        }

        returnString.append("Hand: \n").append(hand.toString()).append("\n");
        returnString.append("Bench: \n").append(bench.toString()).append("\n");
        returnString.append("# Cards left in deck: ").append(deck.getNoOfCards()).append("\n");

        return returnString.toString();

    }


    public void removeActivePokemon() {

        activePokemon = null;

    }
}
