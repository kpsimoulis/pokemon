package intergrationTest;

import card.Energy;
import parser.*;
import org.junit.Test;
import views.card.PokemonView;
import views.card.TrainerView;
import views.cardcontainer.BenchView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-07-14.
 */
public class BenchViewIT {
    private PokemonView pokemonView;
    private TrainerView trainerView;
    private BenchView benchView;
    @Test
    public void addCardView() throws Exception {
        ParserHelper helper = new ParserHelper();
        ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
        ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
        logic.add(helper.getAbilityByLogic("dam", new ArrayList<String>(Arrays.asList("target:opponent-active:30".split(":")))));
        Ability ability = new Ability("Rain Splash","damage", logic);

        Requirement requirement=new Requirement("general",2);
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        requirements.add(requirement);
        Attack attack = new Attack(requirements,ability);
        ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
        pokemonView= new PokemonView(energyArray,attacks,0,60,"basic",1);
        trainerView = new TrainerView();
        benchView = new BenchView();
        benchView.addCardView(trainerView);
        assertEquals(0,benchView.getCardViews().size());
        benchView.addCardView(pokemonView);
        assertEquals(1,benchView.getCardViews().size());

    }

}
