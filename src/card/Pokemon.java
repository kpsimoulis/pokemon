package card;

import java.util.ArrayList;
import main.*;

public class Pokemon extends Card {

    private int healthPoints;
    private int damagePoints;
    private ArrayList<Energy> energy;
    private String stage;
    private String evolvesFrom;
    private Retreat retreat;
    private ArrayList<Attack> attack;

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



    public Pokemon(String name, int index, String category, int hp, ArrayList<Energy> energy, String stage, String evolves_from, Retreat retreat, ArrayList<Attack> attack) {
        super(name, index, category);
        this.healthPoints = hp;
        this.damagePoints = 0;
        this.energy = energy;
        this.stage = stage;
        this.evolvesFrom = evolves_from;
        this.retreat = retreat;
        this.attack = attack;
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

}
