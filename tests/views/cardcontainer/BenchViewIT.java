package views.cardcontainer;

import card.Energy;
import main.Ability;
import main.Attack;
import main.Requirement;
import org.junit.Test;
import views.card.PokemonView;
import views.card.TrainerView;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class BenchViewIT {
    private PokemonView pokemonView;
    private TrainerView trainerView;
    private BenchView benchView;
    @Test
    public void addCardView() throws Exception {
        ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
        Ability ability = new Ability("Rain Splash","damage","put 20 damage points on opponent","opponent-active");
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
