package controllers.activepokemon;

import card.Energy;
import card.Pokemon;
import controllers.card.CardController;
import controllers.card.PokemonController;
import views.activepokemon.ActivePokemonView;

import java.util.HashMap;

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

    public HashMap<String, Integer> getEnergyOnCard(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (Energy energy : ((Pokemon)pokemonController.getCard()).getEnergy()) {
            String energyCategory = energy.getCategory();
            if (hashMap.containsKey(energyCategory)) {
                hashMap.put(energyCategory, hashMap.get(energyCategory) + 1);
            } else {
                hashMap.put(energyCategory, 1);
            }
        }
        return hashMap;
    }
}
