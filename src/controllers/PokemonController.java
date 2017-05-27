package controllers;

import card.Pokemon;
import views.PokemonView;

public class PokemonController extends CardController {

    private Pokemon pokemonCard;
    private PokemonView pokemonView;

    public PokemonController(Pokemon card, PokemonView view){

        super(card, view);
        view.setDmgPts(card.getDamagePoints());
        view.setHP(card.getHealthPoints());
        view.setNoEnergies(card.getEnergy().size());
        view.setStage(card.getStage());

    }

}
