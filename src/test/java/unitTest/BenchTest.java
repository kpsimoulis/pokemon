package unitTest;

import card.Energy;
import card.Pokemon;
import cardcontainer.Bench;
import parser.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
        ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
        //logic.add(new Dam(new ArrayList<String>(Arrays.asList("dam:target:choice:opponent-bench:30"))));
        Ability ability = new Ability("Rain Splash", "damage", logic);
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