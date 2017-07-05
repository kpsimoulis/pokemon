package controllers.cardcontainer;

import card.Energy;
import card.Pokemon;
import cardcontainer.Bench;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;
import views.cardcontainer.BenchView;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class BenchControllerIT {
    private Bench bench;
    private BenchView benchView;
    private BenchController benchController;
    private Pokemon pokemon1;
    private Energy energy1;
    @Before
    public void beforeEachTest(){
        bench= new Bench();
        benchView = new BenchView();
        benchController = new BenchController(bench,benchView);
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
        pokemon1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks,poks);
        energy1 = new Energy("Fight",20,"fight");


    }
    @Test
    public void addCard() throws Exception {
        assertEquals(0,benchController.getContainer().getNoOfCards());
        benchController.addCard(pokemon1);
        assertEquals(1,benchController.getContainer().getNoOfCards());

    }


    @Test
    public void removeCard() throws Exception {
        benchController.addCard(pokemon1);
        assertEquals(1,benchController.getContainer().getNoOfCards());
        benchController.removeCard(pokemon1);
        assertEquals(0,benchController.getContainer().getNoOfCards());
    }

}