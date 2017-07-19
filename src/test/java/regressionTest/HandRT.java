package regressionTest;

import card.Card;
import card.Energy;
import card.Pokemon;
import cardcontainer.Hand;
import parser.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class HandRT {
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
        ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
//        logic.add(new Dam(new ArrayList<String>(Arrays.asList("dam:target:choice:opponent-bench:30"))));
        Ability ability = new Ability("Rain Splash","damage", logic);
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
        Card card2 = new Pokemon("Duoduo", 22, "pokemon", 60, energyArray, "basic","",retreat,attacks,poks);
        for(int i=1;i<16;i++) {
            hand.addCard(card1);
            hand.addCard(card2);
            hand.addCard(card2);
            hand.addCard(card1);
        }
        assertEquals(60,hand.getNoOfCards());
    }





}