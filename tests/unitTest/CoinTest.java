package unitTest;

import org.junit.Before;
import org.junit.Test;
import player.Coin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by luckyfang0601 on 2017-05-24.
 */
public class CoinTest {

    private Coin coin;
    private Boolean getFace;

    @Before
    public void setUp() throws Exception {
        this.coin = new Coin();
    }

    @Test
    public void getFaceUp() throws Exception {
        String face1 = coin.getFaceUp();

        if(face1=="HEAD"||face1=="TAIL"){
            getFace=true;
        }
        assertTrue(getFace);
    }

    @Test
    public void isHead() throws Exception {
        Boolean headUp = coin.isHead();
        if(headUp == true){
            assertEquals("HEAD",coin.getFaceUp());
        }
    }



}