package controllers.cardpiles;

import card.Card;
import cardcontainer.CardContainer;
import cardcontainer.Deck;
import views.cardpiles.DeckView;
import views.cardpiles.PileView;

public abstract class PileController {

    private CardContainer cardContainer;
    private PileView view;

    public PileController(CardContainer container, PileView pileView) {

        this.cardContainer = container;
        this.view = pileView;
        this.view.setNoOfCards(this.cardContainer.getNoOfCards());

    }

    public PileView getView() {
        return view;
    }

    public void setView(DeckView view) {
        this.view = view;
    }

    public CardContainer getCardContainer() {
        return cardContainer;
    }

    public void setCardContainer(Deck cardContainer) {
        this.cardContainer = cardContainer;
    }

    public void addCard(Card newCard){
        cardContainer.addCard(newCard);
        view.incrementNoOfCards();
    }

}
