package unitTest;

import card.Energy;
import card.Pokemon;
import cardcontainer.Bench;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

/**
 * Created by luckyfang0601 on 2017-05-23.
 */
public class BenchTest {

    private Bench bench;
    private Retreat retreat;
    private ArrayList<Energy> energyArray;
    private ArrayList<Attack> attacks;
    private ArrayList<Pokemon> poks;


    @Before
    public void beforeEachTest()

    {
        bench = new Bench();
        energyArray = new ArrayList<Energy>(20);
        retreat = new Retreat("fighting", 1);
        Ability ability = new Ability("Rain Splash", "damage", "put 20 damage points on opponent", "opponent-active");
        Requirement requirement = new Requirement("general", 2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements, ability);
        attacks = new ArrayList<Attack>();
        attacks.add(attack);
    }


    @Test
    public void validate() throws Exception {

        if (bench.getNoOfCards() != 5) {
            assertFalse(bench.validate());
        }

    }


}