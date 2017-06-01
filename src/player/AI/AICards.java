package player.AI;

import java.util.*;

import card.Card;
import card.Pokemon;
import card.Energy;
import main.Attack;


/**
 * Created by zc on 2017/6/1.
 */
public class AICards {
    private AIDeck aiDeck;
    private ArrayList<Card> hand;
    private ArrayList<Pokemon> bench;
    private ArrayList<Card> prizeCards;
    private ArrayList<Card> discardPile;
    private Pokemon activePokemon;

    public AICards() {
        buildDeck();
        selectHand();
        selectPrizeCards();
        discardPile = new ArrayList<Card>();
        bench = new ArrayList<Pokemon>();
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public ArrayList<Pokemon> getBench() {
        return this.bench;
    }

    public Pokemon getActivePokemon() {
        return this.activePokemon;
    }

    public void buildDeck() {
        aiDeck = new AIDeck();

        for (int i = 0; i < 14; i++) {
            Energy Energy = new Energy("COLORLESS");
            Energy.setID(i + 1);
            aiDeck.push(Energy);
        }

        // hardcoding Pikachu card
        Energy Energy = new Energy("COLORLESS");
        HashMap<Energy, Integer> retreatMapPikachu = new HashMap<Energy, Integer>();
        retreatMapPikachu.put(Energy, 1);
        Pokemon pikachu = new Pokemon("Pikachu", 4, "LIGHTNING", "BASIC", 60, 60, retreatMapPikachu);

        //hardcoding Pikachu attacks
        Energy = new Energy("COLORLESS");
        Attack nuzzle = new Attack();
        nuzzle.setName("Nuzzle");
        nuzzle.setFlip(true);
        nuzzle.setTarget("opponent-active");
        nuzzle.setApplyStatus("paralyzed");
        nuzzle.addEnergyRequirement(Energy, 1);
        pikachu.addAttack(nuzzle);

        Energy = new Energy("COLORLESS");
        Attack quickAttack = new Attack();
        quickAttack.setName("Quick Attack");
        quickAttack.setDamagePoints(20);
        quickAttack.setTarget("opponent-active");
        quickAttack.setFlip(true);
        quickAttack.setAdditionalDamagePoints(10);
        quickAttack.setAdditionalTarget("opponent-active");
        quickAttack.addEnergyRequirement(Energy, 2);
        pikachu.addAttack(quickAttack);

        pikachu.setID(15);
        aiDeck.push(pikachu);

        // hardcoding Glameow card

        Energy = new Energy("COLORLESS");
        HashMap<Energy, Integer> retreatMapGlameow = new HashMap<Energy, Integer>();
        retreatMapGlameow.put(Energy, 2);
        Pokemon glameow = new Pokemon("Glameow", 2, "Colorless", "BASIC", 60, 60, retreatMapGlameow);

        //hardcoding Glameow attacks
        Energy = new Energy("COLORLESS");
        Attack actCute = new Attack();
        actCute.setName("Act Cute");
        actCute.setTarget("opponent-hand");
        actCute.addEnergyRequirement(Energy, 1);
        actCute.setDestination("aiDeck-bottom");
        glameow.addAttack(actCute);

        Energy = new Energy("COLORLESS");
        Attack scratch = new Attack();
        scratch.setName("Scratch");
        scratch.setDamagePoints(20);
        scratch.setTarget("opponent-active");
        scratch.addEnergyRequirement(Energy, 2);
        glameow.addAttack(scratch);

        glameow.setID(16);
        aiDeck.push(glameow);

        //last step: shuffle
        aiDeck.shuffle();*/
    }

    //7 cards only for test
    public void selectHand() {
        hand = new ArrayList<Card>();

        for (int i = 0; i < 7; i++) {
            Card card = aiDeck.pop();
            hand.add(card);
        }

        while (getFirstPokemon() == null) {
            Iterator<Card> it = hand.iterator();

            while (it.hasNext()) {
                Card card = it.next();
                aiDeck.push(card);
                it.remove();
            }

            aiDeck.shuffle();

            for (int i = 0; i < aiDeck; i++) {
                Card card = aiDeck.pop();
                hand.add(card);
            }
        }
    }

    public void selectPrizeCards() {
        prizeCards = new ArrayList<Card>(6);

        for (int i = 0; i < 6; i++) {
            Card card = aiDeck.pop();
            prizeCards.add(card);
        }
    }

    //find first pokemon in the hand
    public Pokemon getFirstPokemon() {
        for (Card card : hand) {
            if (card instanceof Pokemon) return (Pokemon) card;
        }
        return null;
    }

    //dont need for 1st submission
    public void setActivePokemon(Pokemon pokemon) {
        activePokemon = pokemon;
        hand.remove(pokemon);
    }

    //dont need for 1st submission
    public void removeActivePokemon() {
        discardActivePokemon();
        activePokemon = null;
    }

    //dont need for 1st submission
    public void discardActivePokemon() {
        ArrayList<Energy> energy = activePokemon.getEnergy();
        for (Energy card : energy) {
            discardPile.add(card);
            activePokemon.removeEnergy(card);
        }
        discardPile.add(activePokemon);
    }

    //find first energy in the hand
    public Energy getFirstEnergy() {
        for (Card card : hand) {
            if (card instanceof Energy) return (Energy) card;
        }
        return null;
    }

    public void attachEnergy(Energy energy, Pokemon pokemon) {
        pokemon.attachEnergy(energy);
        hand.remove(energy);
    }


    public void drawCardFromAIDeck(int index) {
        Card card = aiDeck.getCardAtIndex(index);
        hand.add(card);
        aiDeck.removeCardAtIndex(index);
    }

    public void drawCardFromDiscard(int index) {
        Card card = discardPile.get(index);
        hand.add(card);
        discardPile.remove(index);
    }

    public void discardFromHand(int index) {
        Card card = hand.get(index);
        discardPile.add(card);
        hand.remove(index);
    }

    public void discardFromBench(Pokemon pokemon) {
        discardPile.add(pokemon);
        hand.remove(pokemon);
    }

    public void movePokemonToBench(Pokemon pokemon) {
        bench.add(pokemon);
        hand.remove(pokemon);
    }

    public void addPrizeCardToHand(Card card) {
        hand.add(card);
        prizeCards.remove(card);
    }

    public void addToDiscard(Card card) {
        discardPile.add(card);
    }

    public Card getFirstCardOfHand() {
        return this.hand.get(0);
    }

    //ability for Glameow
    public void moveCardFromHandToBottomOfAIDeck(Card card) {
        this.aiDeck.push(card);
        this.hand.remove(card);
    }
}
