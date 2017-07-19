package card;

import parser.Attack;
import parser.Retreat;

import java.util.ArrayList;

public class Pokemon extends Card {

    private int healthPoints;
    private int damagePoints;
    private ArrayList<Energy> energy;
    private ArrayList<Pokemon> attachedPokemon;
    private String stage;
    private String evolvesFrom;
    private Retreat retreat;
    private ArrayList<Attack> attack;

    public Pokemon(String name, int index, String category, int hp, ArrayList<Energy> energy, String stage, String evolves_from, Retreat retreat, ArrayList<Attack> attack,ArrayList<Pokemon> pokemons) {
        super(name, index, category);
        this.healthPoints = hp;
        this.damagePoints = 0;
        this.energy = energy;
        this.stage = stage;
        this.evolvesFrom = evolves_from;
        this.retreat = retreat;
        this.attack = attack;
        this.attachedPokemon = pokemons;
    }

    public Pokemon(Pokemon pokemon) {
        super(pokemon);
        this.healthPoints = pokemon.healthPoints;
        this.damagePoints = pokemon.healthPoints;
        this.energy = pokemon.energy;
        this.stage = pokemon.stage;
        this.evolvesFrom = pokemon.evolvesFrom;
        this.retreat = pokemon.retreat;
        this.attack = pokemon.attack;
        this.attachedPokemon =pokemon.attachedPokemon;
    }

    @Override
    public String toString() {
        return "Pokemon " +
                "(" + this.getIndex() + ")" +
                " {" +
                " name='" + this.getName() + '\'' +
                ", category='" + this.getCategory() + '\'' +
                ", healthPoints=" + healthPoints +
                ", damagePoints=" + damagePoints +
                ", energy=" + energy +
                ", stage='" + stage + '\'' +
                ", evolvesFrom='" + evolvesFrom + '\'' +
                ", Retreat='" + retreat + '\'' +
                ", Attack='" + attack + '\'' +
                '}';
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public int getDamagePoints() {
        return damagePoints;
    }

    public ArrayList<Energy> getEnergy() {
        return energy;
    }

    public String getStage() {
        return stage;
    }

    public String getEvolvesFrom() {
        return evolvesFrom;
    }

    public Retreat getRetreat() {
        return retreat;
    }

    public ArrayList<Attack> getAttack() {
        return attack;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    public void setCategory(ArrayList<Energy> energy) {
        this.energy = energy;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setEvolvesFrom(String evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public void addEnergy(Energy energyCard) {
        energy.add(energyCard);
    }

    public Energy removeEnergy() throws NullPointerException {
        return energy.remove(energy.size() - 1);
    }

    public void emptyEnergy() {
        energy.clear();
    }

    public void attachPokemon(Pokemon pokemon) {
        attachedPokemon.add(pokemon);
    }

    public ArrayList<Pokemon> getAttachedPokemon() {
        return attachedPokemon;
    }

    public Pokemon removeAttachedBasicPokemon() throws NullPointerException {
        if (!(attachedPokemon.size() == 0))
            return attachedPokemon.remove(attachedPokemon.size() - 1);
        else
            return null;
    }
    public void emptyAttachedPokemon() {
        attachedPokemon.clear();
    }

}
