package regressionTest;

import card.Energy;
import card.Pokemon;
import cardcontainer.Bench;
import controllers.cardcontainer.BenchController;
import parser.*;
import org.junit.Test;
import views.cardcontainer.BenchView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-07-14.
 */
public class BenchLimitRT {
    private Bench bench;
    private BenchView benchView;
    private BenchController benchController;
    private Pokemon pokemon1;

    @Test
    public void addCard(){
        ParserHelper helper = new ParserHelper();
        bench= new Bench();
        benchView = new BenchView();
        benchController = new BenchController(bench,benchView);
        ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
        ArrayList<Pokemon> poks = null;

        Retreat retreat = new Retreat("fighting",1);
        ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
        logic.add(helper.getAbilityByLogic("dam", new ArrayList<String>(Arrays.asList("target:opponent-active:30".split(":")))));
        Ability ability = new Ability("Rain Splash","damage", logic);
        Requirement requirement=new Requirement("general",2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements,ability);
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
        pokemon1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks,poks);
        benchController.addCard(pokemon1);
        assertEquals(1,benchController.getContainer().getNoOfCards());
        benchController.addCard(pokemon1);
        assertEquals(2,benchController.getContainer().getNoOfCards());
        benchController.addCard(pokemon1);
        benchController.addCard(pokemon1);
        benchController.addCard(pokemon1);
        assertEquals(5,benchController.getContainer().getNoOfCards());
        benchController.addCard(pokemon1);
        assertEquals(5,benchController.getContainer().getNoOfCards());



    }
}
