package cardcontainer;

import card.Card;
import card.Energy;
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

//    @Test
//    public void addCard() throws Exception {
//        Card card1 = new Energy("Psychic",20, "energy");
//        Card card2 = new Energy("Fighting",5, "energy");
//        deck1.addCard(card1);
//        assertEquals(1, deck1.getNoOfCards());
//    }

    @Test
    public void dealCard() throws Exception {
        assertNull(deck2.dealCard());
    }

    @Test
    public void populateDeck() throws Exception {
        deck1.populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        assertEquals(60,deck1.getNoOfCards());
        assertEquals("Jirachi",deck1.dealCard().getName());
    }

    @Test
    public void validate() throws Exception {
        deck2.populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck2.txt");
        assertTrue(deck2.validate());
    }

//    @Test
//    public void getSpecificCardCount() throws Exception {
//      energyCard = new Energy("Psychic",20,"psychic");
//      deck1.populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
//      assertEquals(9,deck1.getSpecificCardCount(energyCard));
//    }

    @Test
    public void shuffle() throws Exception {
        deck1.populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        deck1.shuffle();
        assertEquals(60,deck1.getNoOfCards());
    }

}