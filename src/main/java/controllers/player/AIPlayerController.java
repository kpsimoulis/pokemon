package controllers.player;

import ability.Dam;
import card.Card;
import card.Energy;
import card.Pokemon;
import cardcontainer.PrizeCards;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.card.PokemonController;
import javafx.util.Pair;
import parser.Amount;
import parser.Attack;
import parser.Requirement;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.card.PokemonView;

import java.util.*;

public class AIPlayerController extends PlayerController {

    public AIPlayerController() {
        super();
    }

    public ActivePokemonView setActivePokemon(boolean firstTime) throws NullPointerException{

        if (!getPlayer().hasActivePokemon()) {

            Pair<CardController, CardView> pair = null;
            if (firstTime && handHasPokemon()) {
                pair = getHandController().removeCard(chooseActivePokemon(false));
            } else if (!firstTime && benchHasPokemon()) {
                pair = getBenchController().removeCard(chooseActivePokemon(true));
            } else{
                throw new NullPointerException();
            }
            assert pair != null;
            getPlayer().setActivePokemon((Pokemon) pair.getKey().getCard());
            ActivePokemonView view = new ActivePokemonView((PokemonView) pair.getValue());
            setActivePokemonController(new ActivePokemonController((PokemonController) pair.getKey(), view));
            getActivePokemonController().getPokemonController().setBlockedCard(false);
            return view;

        }
        return null;

    }

    private Pokemon chooseActivePokemon(boolean fromBench) {


        ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
        ArrayList<Card> containerCards;
        if (fromBench) {
            containerCards = getBenchController().getContainer().getCards();
        } else {
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
                pokemonInHand.add((Pokemon) card);
            }
        }

        for (Pokemon pokemon : pokemonInHand) {
            if (getBenchController().getContainer().getNoOfCards()<5) {
                getHandController().removeCard(pokemon);
                getBenchController().addCard(pokemon);
            }
        }

    }

    @Override
    public void dealDeckHand() {
        Pair<CardController, CardView> dealtCard = getDeckController().dealCard();
        getHandController().addCard(dealtCard);
        dealtCard.getKey().setBlockedCard(true);
    }

    public void collectPrizeCards() {

        Random rand = new Random();

        PrizeCards cards = (PrizeCards) getPrizeCardController().getCardContainer();
        int noPrizeCards = cards.getNoOfCards();

        int randomIdx = rand.nextInt(noPrizeCards);
        Pair<CardController, CardView> collectedCard = getPrizeCardController().chooseCard(randomIdx);

        cards.getCards().trimToSize();

        getHandController().addCard(collectedCard);

    }

    public Attack play(PlayerController opponentHuman) {

        if (handHasPokemon() && !getBenchController().isFull()){
            putPokemonOnBench();
            getBenchController().returnAllCards();
        }

        //TODO: Evolve Pokemon

        if (handHasEnergy()){

            // Try attaching to active pokemon
            if (!attachPokemonEnergy(getActivePokemonController().getPokemonController())){

                //If not work, attach to a bench pokemon (Only one energy can be attached)
                for (CardController card: getBenchController().getCardControllers()){
                    if (attachPokemonEnergy((PokemonController) card)){
                        break;
                    }
                }
            }

        }

        if (canAttack()){
            return attack(opponentHuman.getActivePokemonController());
        }else{
            return null;
        }

    }

    private Attack attack(ActivePokemonController opponentPokemon) {

        Pokemon activePok = getActivePokemonCard();

        // Get all possible attacks based on energy attached and attacks energy requirements
        ArrayList<Attack> possibleAttacks = new ArrayList<>();
        HashMap<String, Integer> energyOnCard = getActivePokemonController().getEnergyOnCard();

        for (Attack attack : activePok.getAttack()) {

            HashMap<String, Integer> energyPokTmp = new HashMap<>(energyOnCard);

            if (checkAttackEnergy(attack, energyPokTmp)){
                possibleAttacks.add(attack);
            }

        }

        //Temporarily take a random possible attack
        Random rand = new Random();
        int randomIdx = rand.nextInt(possibleAttacks.size());
        Attack randomAttack = possibleAttacks.get(randomIdx);
        // Default damage in case attack has no amount
        int dmg = 0;
        // TODO Handle other attacks for AI with a loop
        if (randomAttack.getAbility().getLogic().get(0) instanceof Dam) {
            Amount amount = ((Dam) randomAttack.getAbility().getLogic().get(0)).getAmount();
            if (amount.isCalculated()) {
                dmg = 5;
            }
            else {
                dmg = amount.getAmount();
            }
        }

        getActivePokemonController().attackPokemon(opponentPokemon, dmg);

        return possibleAttacks.get(randomIdx);
        //TODO: When abilities get implemented, check attack with highest damage

    }

    private boolean attachPokemonEnergy(PokemonController pokCard){

        Pokemon card = (Pokemon) pokCard.getCard();

        // Get energy required for all attacks of pokemon
        for (Attack attack: card.getAttack()) {

            int allEnergyReq = 0;
            for (Requirement requirement: attack.getRequirement()){
                allEnergyReq += requirement.getEnergyAmount();
            }
            if (card.getEnergy().size() >= allEnergyReq){
                break;
            }

            // Get all energy required
            HashMap<String, Integer> energyReq = new HashMap<>();
            for (Requirement requirement : attack.getRequirement()) {
                energyReq.put(requirement.getCategory(), requirement.getEnergyAmount());
            }

            // Remove all energy already attached
            for (Energy energy: card.getEnergy()){
                String cat = energy.getCategory();
                if (energyReq.containsKey(cat)){
                    energyReq.replace(cat, energyReq.get(cat) - 1);
                }
            }

            // Look for energy required in hand
            for (CardController controller: getHandController().getCardControllers()){
                if (controller.getCard() instanceof Energy){
                    Energy energyCard = (Energy) controller.getCard();
                    if (energyReq.containsKey(energyCard.getCategory()) && energyReq.get(energyCard.getCategory()) > 0){
                        pokCard.addEnergy(energyCard);
                        getHandController().removeCard(energyCard);
                        return true;
                    }
                }
            }

            // If nothing found based on specific energy category, look for colorless
            if (energyReq.containsKey("colorless") && handHasEnergy()){

                for (CardController controller: getHandController().getCardControllers()){
                    if (controller.getCard() instanceof Energy){
                        Energy energyCard = (Energy) controller.getCard();
                        pokCard.addEnergy(energyCard);
                        getHandController().removeCard(energyCard);
                        return true;
                    }
                }

            }

        }

        return false;

    }

}
