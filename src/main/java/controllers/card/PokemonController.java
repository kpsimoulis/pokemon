package controllers.card;

import card.Energy;
import card.Pokemon;
import parser.Attack;
import views.card.PokemonView;

import java.util.ArrayList;

public class PokemonController extends CardController {
    public int selfHeal;
    public boolean isHealed;

    public PokemonController(Pokemon card){

        super(card, new PokemonView(card.getEnergy(), card.getAttack(), card.getDamagePoints(), card.getHealthPoints(), card.getStage(), card.getRetreat().getEnergyAmount()));
        this.selfHeal = 0;
        this.isHealed = false;

    }

    public PokemonController(PokemonController pokemonController) {
        super(pokemonController.getCard(), new PokemonView( (PokemonView) pokemonController.getView()));
        this.selfHeal = 0;
        this.isHealed = false;
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
    public void attachPokemon(Pokemon pokemonCard){

        Pokemon card = (Pokemon) this.getCard();
        ((Pokemon) this.getCard()).attachPokemon(pokemonCard);
    }

    public Pokemon removeAttatchedPokemon(){

        Pokemon pokemonCard = (Pokemon) this.getCard();
        try{
            Pokemon returnCard = pokemonCard.removeAttachedBasicPokemon();
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

    public void heal(int healPoints) {

        Pokemon card = (Pokemon) getCard();
        card.setDamagePoints(Math.max(0,card.getDamagePoints() - healPoints));
        ((PokemonView) getView()).setDmgPts(card.getDamagePoints());
        this.isHealed = true;

    }

    public void setSelfHeal(int num) {
        this.selfHeal = 20;
    }
}
