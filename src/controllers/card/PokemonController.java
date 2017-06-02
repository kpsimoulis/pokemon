package controllers.card;

import card.Energy;
import card.Pokemon;
import main.Attack;
import views.card.PokemonView;

import java.util.ArrayList;

public class PokemonController extends CardController {

    public PokemonController(Pokemon card, PokemonView view){

        super(card, view);
        view.setDmgPts(card.getDamagePoints());
        view.setHP(card.getHealthPoints());
        view.setNoEnergies(card.getEnergy().size());
        view.setStage(card.getStage());
        view.setAttacks(card.getAttack());

    }

    public void addEnergy(Energy energyCard){

        Pokemon card = (Pokemon) this.getCard();
        ((Pokemon) this.getCard()).addEnergy(energyCard);
        ((PokemonView) this.getView()).setNoEnergies(card.getEnergy().size());

    }

    public Energy removeEnergy(){

        Pokemon pokemonCard = (Pokemon) this.getCard();
        try{
            Energy returnCard = pokemonCard.removeEnergy();
            PokemonView view = (PokemonView) getView();
            view.setNoEnergies(pokemonCard.getEnergy().size());
            return returnCard;
        }
        catch (NullPointerException e){
            return null;
        }

    }

}
