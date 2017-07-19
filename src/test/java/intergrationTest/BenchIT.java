package intergrationTest;

import card.Card;
import card.Energy;
import card.Pokemon;
import cardcontainer.Bench;
import parser.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by luckyfang0601 on 2017-07-14.
 */
public class BenchIT {

    private Bench bench;
    private Retreat retreat;
    private ArrayList<Energy> energyArray;
    private ArrayList<Attack> attacks;
    private ArrayList<Pokemon> poks;


    @Before
    public void beforeEachTest()

    {
        bench = new Bench();
        energyArray= new ArrayList<Energy>(20);
        retreat = new Retreat("fighting",1);
        ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
        //logic.add(new Dam(new ArrayList<String>(Arrays.asList("dam:target:choice:opponent-bench:30"))));
        Ability ability = new Ability("Rain Splash","damage", logic);
        Requirement requirement=new Requirement("general",2);
        ArrayList<Requirement>  requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements,ability);
        attacks = new ArrayList<Attack>();
        attacks.add(attack);
    }

    @Test
    public void addCard() throws Exception {

        Card card1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "stage-one","pikachu",retreat,attacks,poks);
        int size = bench.getNoOfCards();
        if(bench.isFull()) {
            bench.addCard(card1);
            assertEquals(size,bench.getNoOfCards());
        }
        else{
            bench.addCard(card1);
            assertEquals(size+1,bench.getNoOfCards());
        }


    }



    @Test
    public void isFull() throws Exception {

        Card card1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks,poks);
        Card card2 = new Pokemon("Glameow", 22, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        Card card3 = new Pokemon("Pikachu", 60, "pokemon", 60,energyArray, "basic","",retreat,attacks,poks);
        Card card4 = new Pokemon("Shellder", 47, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        Card card5 = new Pokemon("Goldeen", 20, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks );
        bench.addCard(card1);
        bench.addCard(card2);
        bench.addCard(card3);
        bench.addCard(card4);
        assertFalse(bench.isFull());
        bench.addCard(card5);
        assertTrue(bench.isFull());

    }

    @Test
    public void swapCards() throws Exception {

        Card card1 = new Pokemon("Shellder", 47, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        Card card2 = new Pokemon("Glameow", 22, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        Card card3 = new Pokemon("Pikachu", 60, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        bench.addCard(card1);
        bench.addCard(card2);
        bench.swapCards(card3,1);
        assertEquals(1,bench.getCardIdx(card3));
    }

}