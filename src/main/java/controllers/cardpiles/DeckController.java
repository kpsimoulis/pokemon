package controllers.cardpiles;

import cardcontainer.CardContainer;
import cardcontainer.Deck;
import controllers.card.CardController;
import controllers.card.ControllerViewBuilder;
import javafx.util.Pair;
import views.card.CardView;
import views.cardpiles.DeckView;

public class DeckController extends PileController {

    public DeckController(Deck deck, DeckView deckView) {
        super(deck, deckView);
    }

    public void shuffleDeck() {
        ((Deck) this.getCardContainer()).shuffle();
    }

    public Pair<CardController, CardView> dealCard() {
        CardContainer container = this.getCardContainer();
        if (container.getNoOfCards() == 0){
            return null;
        }
        this.getView().decrementNoOfCards();
        return ControllerViewBuilder.buildControllerView( ((Deck) container).dealCard());
    }
}
