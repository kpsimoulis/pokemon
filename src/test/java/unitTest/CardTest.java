package unitTest;

import card.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class CardTest {
    private Card card;
    @Before
    public void beforeEachTest(){
        card = new Card("pichaqiu",20,"pokemon");
    }
    @Test
    public void getName() throws Exception {
        assertEquals("pichaqiu",card.getName());
    }

    @Test
    public void getIndex() throws Exception {
        assertEquals(20,card.getIndex());
    }

    @Test
    public void getCategory() throws Exception {
        assertEquals("pokemon",card.getCategory());
    }

    @Test
    public void setName() throws Exception {
        card.setName("robio");
        assertEquals("robio",card.getName());
    }

    @Test
    public void setIndex() throws Exception {
        card.setIndex(21);
        assertEquals(21,card.getIndex());
    }

    @Test
    public void setCategory() throws Exception {
        card.setCategory("trainer");
        assertEquals("trainer",card.getCategory());
    }

}