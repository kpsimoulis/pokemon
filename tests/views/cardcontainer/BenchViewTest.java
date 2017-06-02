package views.cardcontainer;

import org.junit.Test;
import views.card.PokemonView;
import views.card.TrainerView;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-01.
 */
public class BenchViewTest {
    private PokemonView pokemonView;
    private TrainerView trainerView;
    private BenchView benchView;
    @Test
    public void addCardView() throws Exception {
        pokemonView= new PokemonView();
        trainerView = new TrainerView();
        benchView = new BenchView();
        benchView.addCardView(trainerView);
        assertEquals(0,benchView.getCardViews().size());
        benchView.addCardView(pokemonView);
        assertEquals(1,benchView.getCardViews().size());

    }

}