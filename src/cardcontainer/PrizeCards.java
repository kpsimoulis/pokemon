package cardcontainer;

import card.Card;

import java.util.ArrayList;

/**
 * Created by mikce_000 on 02-Jun-2017.
 */
public class PrizeCards extends CardContainer {

    /**
     * Default Constructor
     */
    public PrizeCards() {
        super(new ArrayList<Card>(6));
    }

    public PrizeCards(PrizeCards newPrizeCards) {
        cards = new ArrayList<Card>(newPrizeCards.getCards());
    }

    @Override
    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<getCards().size(); i++){
            builder.append(i+1).append(" ");
        }
        return builder.toString();
    }

}
