package controllers.player;

import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.card.PokemonController;
import javafx.util.Pair;
import main.Attack;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;

import java.util.ArrayList;
import java.util.Collections;

public class AIPlayerController extends PlayerController {

    public AIPlayerController() {
        super();
    }

    public ActivePokemonView setActivePokemon(boolean firstTime) {

        if (!getPlayer().hasActivePokemon()) {

            Pair<CardController, CardView> pair = null;
            if ((firstTime || !benchHasPokemon()) && handHasPokemon()){
                pair = getHandController().removeCard(chooseActivePokemon(false));
            }else if(!firstTime && benchHasPokemon()){
                pair = getBenchController().removeCard(chooseActivePokemon(true));
            }
            assert pair != null;
            System.out.print(getActivePokemonController());
            getPlayer().setActivePokemon((Pokemon) pair.getKey().getCard());
            ActivePokemonView view = new ActivePokemonView( (PokemonView) pair.getValue());
            setActivePokemonController(new ActivePokemonController((PokemonController) pair.getKey(), view));
            getActivePokemonController().getPokemonController().setBlockedCard(false);
            return view;

        }
        return null;

    }

    private Pokemon chooseActivePokemon(boolean fromBench) {


        ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
        ArrayList<Card> containerCards;
        if (fromBench){
            containerCards = getBenchController().getContainer().getCards();
        }else{
            containerCards = getHandController().getContainer().getCards();
        }
        for (Card card : containerCards) {
            if (card instanceof Pokemon) {
                pokemonArrayList.add((Pokemon) card);
            }
        }
        Collections.shuffle(pokemonArrayList);
        return pokemonArrayList.get(0);


    }

    public void putPokemonOnBench() {

        ArrayList<Pokemon> pokemonInHand = new ArrayList<>();
        for (Card card : getHandController().getContainer().getCards()) {
            if (card instanceof Pokemon) {
                pokemonInHand.add( (Pokemon) card);
            }
        }

        for (Pokemon pokemon: pokemonInHand){
            getHandController().removeCard(pokemon);
            getBenchController().addCard(pokemon);
        }

    }

    public void attack(ActivePokemonController opponentPokemon){

        for (Card card: getHandController().getContainer().getCards()){
            if (card instanceof Energy && card.getCategory().equals("fight")){
                Pair<CardController, CardView> pair = getHandController().removeCard(card);
                getActivePokemonController().getPokemonController().addEnergy((Energy)card);
                break;
            }
        }

        Attack attack = getActivePokemonController().getPokemonController().getAttacks().get(0);
        getActivePokemonController().attackPokemon(opponentPokemon, attack.getAbility().getDamage());

    }

    @Override
    public void dealDeck(){
        Pair<CardController, CardView> dealtCard = getDeckController().dealCard();
        getHandController().addCard(dealtCard);
        dealtCard.getKey().setBlockedCard(true);
    }
}
