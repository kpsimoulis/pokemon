package cardcontainer;

import ability.Dam;
import card.Card;
import card.Energy;
import card.Pokemon;
import main.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-05-23.
 */
public class DiscardPileTest {
    @Test
    public void addCard() throws Exception {
        DiscardPile cards = new DiscardPile();
        ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
        Retreat retreat = new Retreat("fighting",1);
        ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
        logic.add(new Dam(new ArrayList<String>(Arrays.asList("dam:target:choice:opponent-bench:30"))));
        Ability ability = new Ability("Rain Splash","damage", logic);
        Requirement requirement=new Requirement("general",2);
       ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements,ability);
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
        Card card1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks);
        cards.addCard(card1);
       assertEquals(1,cards.getNoOfCards());
    }

}