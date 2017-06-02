package views.activepokemon;

import org.junit.Test;
import views.card.PokemonView;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-01.
 */
public class ActivePokemonViewTest {
    private PokemonView pokemonView;
    private ActivePokemonView activePokemonView;
    @Test
    public void removePokemonView() throws Exception {
        pokemonView = new PokemonView();
        activePokemonView = new ActivePokemonView(pokemonView);
        activePokemonView.removePokemonView();
        assertNull(activePokemonView.getPokemonView());
    }

//    @Test
//    public void zoomPokemon() throws Exception {
//    }

}