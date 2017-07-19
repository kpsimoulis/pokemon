package unitTest;

import parser.*;
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

    @Test
    public void parseAbilityLineNum() throws Exception {

    }

    @Test
    public void parseRetreat() throws Exception {
        cardsFile.parseName();
        cardsFile.parseCardType();
        cardsFile.parseStage();
        cardsFile.parseCategory();
        cardsFile.parseHealthPoints();
        cardsFile.parseRetreat();
        retreat = new Retreat("colorless",2);
        assertEquals(retreat.toString(),cardsFile.getRetreat().toString());

    }

//    @Test
//    public void parseAttacks() throws Exception {
//        cardsFile.parseName();
//        cardsFile.parseCardType();
//        cardsFile.parseStage();
//        cardsFile.parseCategory();
//        cardsFile.parseHealthPoints();
//        cardsFile.parseRetreat();
//        cardsFile.parseAttacks();
//        Requirement requirement1= new Requirement("colorless",1);
//        ArrayList<Requirement> requirements1 = new ArrayList<Requirement>();
//        requirements1.add(requirement1);
//        String[] abilityText1 = new String[]{
//                "Act Cute","deck","target","them","destination","deck","bottom","choice","target","1"
//        };
//        AbilitiesFileParser abilityFile1 = new AbilitiesFileParser(abilityText1);
//        abilityFile1.parseName();
//        abilityFile1.parseLogic();
//        abilityFile1.parseDescription();
//       // assertEquals("target",abilityFile1.getLogic());
//        Ability ability1 = new Ability(abilityFile1.getName(),abilityFile1.getDescription(),abilityFile1.getLogic());
//        Attack attack1 = new Attack(requirements1,ability1);
//        Requirement requirement2= new Requirement("colorless",2);
//        ArrayList<Requirement> requirements2 = new ArrayList<Requirement>();
//        requirements2.add(requirement2);
//        String[] abilityText2 = new String[]{
//                "Scratch","dam","target","opponent-active","20"
//        };
//        AbilitiesFileParser abilityFile2 = new AbilitiesFileParser(abilityText2);
//        abilityFile2.parseName();
//        abilityFile2.parseLogic();
//        abilityFile2.parseDescription();
//        Ability ability2 = new Ability(abilityFile2.getName(),abilityFile2.getDescription(),abilityFile2.getLogic());
//        Attack attack2 = new Attack(requirements2,ability2);
//        attacks = new Attack[]{attack1,attack2};
//
//
//        assertEquals(attack1.toString(),cardsFile.getAttack().toArray(new Attack[2])[0].toString());
//        assertEquals(attack2.toString(),cardsFile.getAttack().toArray(new Attack[2])[1].toString());
//
//    }

}