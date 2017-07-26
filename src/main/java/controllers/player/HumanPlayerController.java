package controllers.player;

import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.PokemonController;
import views.activepokemon.ActivePokemonView;
import views.card.PokemonView;

/**
 * Created by mikce_000 on 31-May-2017.
 */
public class HumanPlayerController extends PlayerController {

    public HumanPlayerController() {
        super();
    }

    public ActivePokemonView setActivePokemon(boolean firstTime, PokemonController controller, PokemonView pokemonView) {

        if (!getPlayer().hasActivePokemon() && firstTime) {

            ActivePokemonView view = new ActivePokemonView(pokemonView);
            getPlayer().setActivePokemon((Pokemon) controller.getCard());
            setActivePokemonController(new ActivePokemonController(controller, view));
            return view;

        }

        return null;

    }

    public void setChosingCard(boolean chosingCard) {
        isChosingCard = chosingCard;
    }

    public boolean getIsChosingCard() {
        return isChosingCard;
    }

    private boolean isChosingCard = false;


}
