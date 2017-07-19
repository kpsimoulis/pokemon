package regressionTest;

import card.Energy;
import card.Pokemon;
import controllers.card.PokemonController;
import parser.*;
import org.junit.Test;
import views.card.PokemonView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-07-14.
 */
public class EnergyNoRT {
    private Pokemon pokemon1;
    private PokemonView pokemonView;
    private PokemonView pokemonView1;
    private Energy energy1;
    private Energy energy2;
    private PokemonController pokemonController;
    private ArrayList<Pokemon> poks;
    private ArrayList<AbilityLogic> logic;
    @Test
    public void EnergyNoTest() throws Exception{
        ParserHelper helper = new ParserHelper();
        ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
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
        pokemonView = new PokemonView(energyArray,attacks,0,90,"basic",1);
        energy1 = new Energy("Fight",20,"fight");
        energy2 = new Energy("Psychic",22,"psychic");
        pokemonController = new PokemonController(pokemon1);
        pokemonView1 =(PokemonView)pokemonController.getView();
        assertEquals(0,pokemonView1.getNoEnergies());
        pokemonController.addEnergy(energy1);
        assertEquals(1,pokemonView1.getNoEnergies());
    }
}