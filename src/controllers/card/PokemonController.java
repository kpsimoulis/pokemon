package controllers.card;

import card.Energy;
import card.Pokemon;
import main.Attack;
import views.card.PokemonView;

import java.util.ArrayList;

public class PokemonController extends CardController {

    public PokemonController(Pokemon card){

        super(card, new PokemonView(card.getEnergy(), card.getAttack(), card.getDamagePoints(), card.getHealthPoints(), card.getStage(), card.getRetreat().getEnergyAmount()));

    }

    public PokemonController(PokemonController pokemonController) {
        super(pokemonController.getCard(), new PokemonView( (PokemonView) pokemonController.getView()));
    }

    public void addEnergy(Energy energyCard){

        Pokemon card = (Pokemon) this.getCard();
        ((Pokemon) this.getCard()).addEnergy(energyCard);
        ((PokemonView) this.getView()).setNoEnergies(card.getEnergy().size());
        ((PokemonView) this.getView()).setEnergyTxt(card.getEnergy());

    }

    public Energy removeEnergy(){

        Pokemon pokemonCard = (Pokemon) this.getCard();
        try{
            Energy returnCard = pokemonCard.removeEnergy();
            PokemonView view = (PokemonView) getView();
            view.setNoEnergies(pokemonCard.getEnergy().size());
            ((PokemonView) this.getView()).setEnergyTxt(( (Pokemon) this.getCard()).getEnergy());
            return returnCard;
        }
        catch (NullPointerException e){
            return null;
        }

    }

    public ArrayList<Attack> getAttacks() {
        return ((Pokemon) getCard()).getAttack();
    }


    public void causeDamage(int damage) {

        Pokemon card = (Pokemon) getCard();
        card.setDamagePoints(card.getDamagePoints() + damage);
        ((PokemonView) getView()).setDmgPts(card.getDamagePoints());

    }
}
