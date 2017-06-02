package controllers.activepokemon;

import card.Pokemon;
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

    public void returnCard() {
        pokemonController.returnBackCover();
    }

    public void attackPokemon(ActivePokemonController activePokemonController, int damage) {

        activePokemonController.getPokemonController().causeDamage(damage);

    }
}
