package unitTest;

import parser.AbilitiesFileParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-05-30.
 */
public class AbilitiesFileParserTest {
    private AbilitiesFileParser abilities ;
    @Before
    public void beforeEachTest(){
        String testString = "Act Cute:deck:target:them:destination:deck:bottom:choice:target:1";
        String[] items = new String[]{"Act Cute","deck","target","them","destination","deck","bottom","choice","target","1"};
        abilities= new AbilitiesFileParser(items);
    }
    @Test
    public void parseName() throws Exception {
        abilities.parseName();
        assertEquals("Act Cute",abilities.getName());
    }

//    @Test
//    public void parseLogic() throws Exception {
//        abilities.parseName();
//        abilities.parseLogic();
//        assertEquals("target:them:destination:deck:bottom:choice:target:1",abilities.getLogic());
//    }



}