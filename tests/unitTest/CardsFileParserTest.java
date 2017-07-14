package unitTest;

import main.Attack;
import main.CardsFileParser;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-05-30.
 */
public class CardsFileParserTest {
    private CardsFileParser cardsFile ;
    private CardsFileParser cardsFile1 ;
    private Retreat retreat;
    private Attack[] attacks;
    @Before
    public void beforeEachTest(){
        String testString = "Glameow:pokemon:cat:basic:cat:colorless:60:retreat:cat:colorless:2:attacks:cat:colorless:1:1,cat:colorless:2:2";
        String[] items = new String[]{"Glameow","pokemon","cat","basic","cat","colorless","60","retreat","cat","colorless","2",
                "attacks","cat","colorless","1","1,cat","colorless","2","2"};
        cardsFile = new CardsFileParser(items);
    }
    @Test
    public void parseName() throws Exception {
        cardsFile.parseName();
        assertEquals("Glameow",cardsFile.getName());
    }

    @Test
    public void parseCardType() throws Exception {
        cardsFile.parseName();
        cardsFile.parseCardType();
        assertEquals("pokemon",cardsFile.getCardType());
    }

    @Test
    public void parseStage() throws Exception {
        cardsFile.parseName();
        cardsFile.parseCardType();
        cardsFile.parseStage();
        assertEquals("basic",cardsFile.getStage());
    }

    @Test
    public void parseEvolvesFrom() throws Exception {
        String[] items = new String[]{"Raichu","pokemon","cat","stage-one","Pikachu","cat","lightning","90","attacks","cat",
        "colorless","2","7","cat","colorless","1,cat","lightning","2","8"};
        cardsFile1= new CardsFileParser(items);
        cardsFile1.parseName();
        cardsFile1.parseCardType();
        cardsFile1.parseStage();
        cardsFile1.parseEvolvesFrom();
        assertEquals("Pikachu",cardsFile1.getEvolvesFrom());

    }

    @Test
    public void parseCategory() throws Exception {
        cardsFile.parseName();
        cardsFile.parseCardType();
        cardsFile.parseStage();
        cardsFile.parseCategory();
        assertEquals("colorless",cardsFile.getCategory());
    }

    @Test
    public void parseHealthPoints() throws Exception {
        cardsFile.parseName();
        cardsFile.parseCardType();
        cardsFile.parseStage();
        cardsFile.parseCategory();
        cardsFile.parseHealthPoints();
        assertEquals(60,cardsFile.getHealthPoints());
    }



}