package unitTest;

import card.Card;
import cardcontainer.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-05-29.
 */
public class DeckTest {
    private Deck deck1;
    private Deck deck2;
    private Card energyCard;
    @Before
    public void beforeEachTest() throws Exception {
        deck1 = new Deck();
        deck2 = new Deck();

    }


    @Test
    public void dealCard() throws Exception {
        assertNull(deck2.dealCard());
        deck2.populateDeck("src/main/resources/deck/deck1.txt");
        assertEquals(60,deck2.getNoOfCards());
        deck2.dealCard();
        assertEquals(59,deck2.getNoOfCards());
    }

    @Test
    public void populateDeck() throws Exception {
        deck1.populateDeck("src/main/resources/deck/deck3.txt");
        assertEquals(0,deck1.getNoOfCards());
        deck1.populateDeck("src/main/resources/deck/deck1.txt");
        assertEquals(60,deck1.getNoOfCards());
        assertEquals("Jirachi",deck1.dealCard().getName());

    }

    @Test
    public void validate() throws Exception {
        deck2.populateDeck("src/main/resources/deck/deck2.txt");
        assertTrue(deck2.validate());
    }

//    @Test
//    public void getSpecificCardCount() throws Exception {
//      energyCard = new Energy("Psychic",20,"psychic");
//      deck1.populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\parser\\project\\pokemon\\res\\deck\\deck1.txt");
//      assertEquals(9,deck1.getSpecificCardCount(energyCard));
//    }

    @Test
    public void shuffle() throws Exception {
        deck1.populateDeck("src/main/resources/deck/deck1.txt");
        deck1.shuffle();
        assertEquals(60,deck1.getNoOfCards());
    }

}