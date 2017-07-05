package cardcontainer;

import card.Card;
import card.Energy;
import card.Pokemon;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class DiscardPileIT {
    @Test
    public void addCard() throws Exception {
        DiscardPile cards = new DiscardPile();
        ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
        ArrayList<Pokemon> poks = null;

        Retreat retreat = new Retreat("fighting",1);
        Ability ability = new Ability("Rain Splash","damage","put 20 damage points on opponent","opponent-active");
        Requirement requirement=new Requirement("general",2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements,ability);
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
        Card card1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks,poks);
        cards.addCard(card1);
        assertEquals(1,cards.getNoOfCards());
    }

}
