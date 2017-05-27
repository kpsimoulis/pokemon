package card;

import java.util.ArrayList;

public class Pokemon extends Card {

    private int healthPoints;
    private int damagePoints;
    private ArrayList<Energy> energy;
    private String stage;

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
                '}';
    }

    private String evolvesFrom;


    public Pokemon(String name, int index, String category, int hp, ArrayList<Energy> energy, String stage, String evolves_from) {
        super(name, index, category);
        this.healthPoints = hp;
        this.damagePoints = 0;
        this.energy = energy;
        this.stage = stage;
        this.evolvesFrom = evolves_from;
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
