package player;

import card.Energy;
import card.Pokemon;
import cardcontainer.Deck;
import cardcontainer.Hand;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class PlayerIT {

    private static final int PRIZE_CARD_LIMIT = 6;
    private Player player;
    private ArrayList<Energy> energyArray;
    private Coin playerCoin;
    private Retreat retreat;
    private ArrayList<Attack> attacks;
    private Deck newDeck;
    private Hand newHand;
    private ArrayList<Pokemon> poks;




    @Before
    public void beforeEachTest(){

        player = new Player();
        energyArray= new ArrayList<Energy>(20);
        retreat = new Retreat("fighting",1);
        Ability ability = new Ability("Rain Splash","damage","put 20 damage points on opponent","opponent-active");
        Requirement requirement=new Requirement("general",2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);

        Attack attack = new Attack(requirements,ability);
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
    }

    @Test
    public void putPokOnBench() throws Exception {


        Pokemon card1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "stage-one","pikachu",retreat,attacks,poks);
        player.putPokOnBench(card1);
        assertEquals(1,player.getBench().getNoOfCards());
    }



    @Test
    public void hasActivePokemon() throws Exception {
        assertFalse((player.hasActivePokemon()));
        Pokemon card1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "stage-one","pikachu",retreat,attacks,poks);
        player.setActivePokemon(card1);
        assertTrue(player.hasActivePokemon());
    }





}