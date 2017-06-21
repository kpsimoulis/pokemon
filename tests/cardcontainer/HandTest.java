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

    @Before
    public void beforeEachTest() {
        hand = new Hand();
        energyArray = new ArrayList<Energy>(20);
        retreat = new Retreat("fighting", 1);
        Ability ability = new Ability("Rain Splash", "damage", "put 20 damage points on opponent", "opponent-active");
        Requirement requirement = new Requirement("general", 2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements, ability);
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
    }

    @Test

    public void chooseCard() throws Exception {

        Card card1 = new Pokemon("Shellder", 47, "pokemon", 60, energyArray, "basic", "", retreat, attacks);
        hand.addCard(card1);
        hand.chooseCard(0);
        assertEquals(0, hand.getNoOfCards());

    }

    @Test
    public void addCard() throws Exception {

        Card card1 = new Pokemon("Glameow", 22, "pokemon", 60, energyArray, "basic", "", retreat, attacks);
        hand.addCard(card1);
        assertEquals(1, hand.getNoOfCards());
    }

    @Test
    public void removeAllCards() throws Exception {
        Card card1 = new Pokemon("Raichu", 27, "pokemon", 90, energyArray, "basic", "", retreat, attacks);
        Card card2 = new Pokemon("Glameow", 22, "pokemon", 60, energyArray, "basic", "", retreat, attacks);
        Card card3 = new Pokemon("Pikachu", 60, "pokemon", 60, energyArray, "basic", "", retreat, attacks);
        Card card4 = new Pokemon("Shellder", 47, "pokemon", 60, energyArray, "basic", "", retreat, attacks);
        Card card5 = new Pokemon("Goldeen", 20, "pokemon", 60, energyArray, "basic", "", retreat, attacks);
        hand.removeAllCards();
        assertEquals(0, hand.getNoOfCards());
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card1);
        hand.removeAllCards();
        assertEquals(0, hand.getNoOfCards());
        hand.addCard(card1);
        assertEquals(1, hand.getNoOfCards());
    }


}