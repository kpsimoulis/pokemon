package regressionTest;

import cardcontainer.Deck;
import controllers.cardpiles.DeckController;
import org.junit.Test;
import views.cardpiles.DeckView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by zc on 2017/7/14.
 */
public class DealRT {
    private Deck deck;
    private DeckView deckView;
    private DeckController deckController;


    @Test
    public void dealCard() throws Exception {
        deck = new Deck();
        deckView = new DeckView();
        deckController= new DeckController(deck,deckView);
        deck.populateDeck("src/main/resources/deck/deck1.txt");
        deckController.setCardContainer(deck);
        assertEquals(60,deckController.getCardContainer().getNoOfCards());
        for(int i=1;i<61;i++){
            deckController.dealCard();
        }
        assertEquals(0,deckController.getCardContainer().getNoOfCards());
    }
}
