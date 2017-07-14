package unitTest;

import main.Ability;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class AbilityTest {
    private Ability ability;
    @Before
    public void BeforeEachTest(){
        ability = new Ability("Act Tough","damage","If this Pokémon has any Energy attached to it, this attack does 20 more damage.","opponent");
    }
    @Test
    public void getName() throws Exception {
        assertEquals("Act Tough",ability.getName());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("If this Pokémon has any Energy attached to it, this attack does 20 more damage.",ability.getDescription());
    }

    @Test
    public void getDamage() throws Exception {
        assertEquals(20,ability.getDamage());
    }

    @Test
    public void getAction() throws Exception {
        assertEquals("damage",ability.getAction());
    }

}