package regressionTest;

import card.Card;
import cardcontainer.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zc on 2017/7/14.
 */
public class ShuffleRT {


    private Deck deck1 = new Deck();

    @Test
    public void shuffle() throws Exception {
        deck1.populateDeck("src/main/resources/deck/deck1.txt");
        Card card1 = deck1.getCards().get(0);
        Card card2 = deck1.getCards().get(1);
        Card card3 = deck1.getCards().get(2);
        deck1.shuffle();
        Card card4 = deck1.getCards().get(0);
        Card card5 = deck1.getCards().get(1);
        Card card6 = deck1.getCards().get(2);

        assertFalse((card1.equals(card4))&&(card2.equals(card5))&&(card3.equals(card6)));
    }


}
