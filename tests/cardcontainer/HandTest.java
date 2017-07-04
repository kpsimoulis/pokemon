package cardcontainer;

import card.Card;
import card.Energy;
import card.Pokemon;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-05-23.
 */
public class HandTest {
    private Hand hand;
    private Retreat retreat;
    private ArrayList<Energy> energyArray;
    private ArrayList<Attack> attacks;
    private ArrayList<Pokemon> poks;

    @Before
    public void beforeEachTest(){
        hand = new Hand();
        energyArray= new ArrayList<Energy>(20);
        retreat = new Retreat("fighting",1);
        Ability ability = new Ability("Rain Splash","damage","put 20 damage points on opponent","opponent-active");
        Requirement requirement=new Requirement("general",2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements,ability);
        ArrayList<Attack> attacks =  new ArrayList<Attack>();
        attacks.add(attack);
    }
    @Test
    public void addCard() throws Exception {

        Card card1 = new Pokemon("Glameow", 22, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        hand.addCard(card1);
        assertEquals(1,hand.getNoOfCards());
    }

    @Test

    public void chooseCard() throws Exception {

        Card card1 = new Pokemon("Shellder", 47, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        hand.addCard(card1);
        hand.chooseCard(0);
        assertEquals(0,hand.getNoOfCards());

    }



}