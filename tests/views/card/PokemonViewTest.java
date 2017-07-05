package views.card;

import card.Energy;
import main.Attack;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-29.
 */
public class PokemonViewTest {
    private PokemonView pokemonView;
    private ArrayList<Energy> energies;
    private ArrayList<Attack> attacks;
    @Before
    public void BeforeEachTest(){
        energies= new ArrayList<Energy>(20);
        attacks = new ArrayList<Attack>(20);
        pokemonView = new PokemonView(energies,attacks,0,90,"basic",1);
    }
    @Test
    public void setDmgPts() throws Exception {
        pokemonView.setDmgPts(20);
        assertEquals(20,pokemonView.getDmgPts());
    }

    @Test
    public void setNoEnergies() throws Exception {
        pokemonView.setNoEnergies(1);
        assertEquals(1,pokemonView.getNoEnergies());
    }

    @Test
    public void setHP() throws Exception {
        pokemonView.setHP(60);
        assertEquals(60,pokemonView.getHP());
    }

    @Test
    public void setStage() throws Exception {
        pokemonView.setStage("stage1");
        assertEquals("stage1",pokemonView.getStage());
    }

    @Test
    public void getDmgPts() throws Exception {
        assertEquals(0,pokemonView.getDmgPts());
    }

    @Test
    public void getNoEnergies() throws Exception {
        assertEquals(0,pokemonView.getNoEnergies());
    }

}