package controllers.cardpiles;

import cardcontainer.Deck;
import org.junit.Test;
import views.cardpiles.DeckView;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-01.
 */
public class DeckControllerTest {
    private Deck deck;
    private DeckView deckView;
    private DeckController deckController;


    @Test
    public void shuffleDeck() throws Exception {
        deck = new Deck();
        deckView = new DeckView();
        deck.populateDeck("res\\deck\\deck1.txt");
        deckController= new DeckController(deck,deckView);
        assertEquals(60,deckController.getCardContainer().getNoOfCards());
        deckController.shuffleDeck();
        assertEquals(60,deckController.getCardContainer().getNoOfCards());
    }

    @Test
    public void dealCard() throws Exception {
        deck = new Deck();
        deckView = new DeckView();
        deckController= new DeckController(deck,deckView);
        assertNull(deckController.dealCard());
        deck.populateDeck("res\\deck\\deck1.txt");
        deckController.setCardContainer(deck);
        assertEquals(60,deckController.getCardContainer().getNoOfCards());
        deckController.dealCard();
        assertEquals(59,deckController.getCardContainer().getNoOfCards());
    }

}