package controllers.cardcontainer;

import card.Card;
import cardcontainer.Deck;
import controllers.card.CardController;
import controllers.card.ControllerViewBuilder;
import javafx.util.Pair;
import views.card.CardView;
import views.cardcontainer.DeckView;

public class DeckController {

    private Deck deck;
    private DeckView deckView;

    public DeckController(Deck deck, DeckView deckView) {

        this.deck = deck;
        this.deckView = deckView;
        this.deckView.setNoOfCards(this.deck.getNoOfCards());

    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public DeckView getDeckView() {
        return deckView;
    }

    public void setDeckView(DeckView deckView) {
        this.deckView = deckView;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Pair<CardController, CardView> dealCard() {
        if (deck.getNoOfCards() == 0){
            return null;
        }
        deckView.setNoOfCards(deck.getNoOfCards() - 1);
        return ControllerViewBuilder.buildControllerView(deck.dealCard());
    }

    public void addCard(Card newCard){
        deck.addCard(newCard);
        deckView.setNoOfCards(deck.getNoOfCards());
    }

}
