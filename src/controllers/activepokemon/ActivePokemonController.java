package controllers.activepokemon;

import controllers.card.PokemonController;
import views.activepokemon.ActivePokemonView;

public class ActivePokemonController {

    private PokemonController pokemonController;
    private ActivePokemonView activePokemonView;

    public ActivePokemonController(PokemonController pokController, ActivePokemonView actPokView){

        pokemonController = pokController;
        activePokemonView = actPokView;

    }

    public PokemonController getPokemonController() {
        return pokemonController;
    }

    public void setPokemonController(PokemonController pokemonController) {
        this.pokemonController = pokemonController;
    }

    public PokemonController retreatPokemon(){
        PokemonController tmpController = pokemonController;
        pokemonController = null;
        activePokemonView.removePokemonView();
        return tmpController;
    }

}
