package controllers.card;

import card.Energy;
import card.Pokemon;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;
import views.card.PokemonView;

import java.security.PrivateKey;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-01.
 */
public class PokemonControllerTest {
    private Pokemon pokemon1;
    private PokemonView pokemonView;
    private PokemonView pokemonView1;
    private Energy energy1;
    private Energy energy2;
    private PokemonController pokemonController;
    @Before
    public void beforeEachTest(){
      ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
      Retreat  retreat = new Retreat("fighting",1);
      Ability ability = new Ability("Rain Splash","damage","put 20 damage points on opponent","opponent-active");
       Requirement requirement=new Requirement("general",2);
       ArrayList<Requirement> requirements = new ArrayList<Requirement>();
       requirements.add(requirement);
       Attack attack = new Attack(requirements,ability);
       ArrayList<Attack> attacks = new ArrayList<Attack>();
        attacks.add(attack);
        pokemon1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks);
        pokemonView = new PokemonView();
        energy1 = new Energy("Fight",20,"fight");
        energy2 = new Energy("Psychic",22,"psychic");
        pokemonController = new PokemonController(pokemon1,pokemonView);
    }
    @Test
    public void addEnergy() throws Exception {
        pokemonController.addEnergy(energy1);
        pokemonView1 =(PokemonView)pokemonController.getView();
        assertEquals(1,pokemonView1.getNoEnergies());

    }

    @Test
    public void removeEnergy() throws Exception {
    }

}