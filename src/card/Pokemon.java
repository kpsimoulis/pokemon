package card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.*;

public class Pokemon extends Card {

    private final int maxHealthPoints;
    private int healthPoints;
    private int damagePoints;
    private ArrayList<Energy> energy;
    private String stage;
    private String evolvesFrom;
    private final HashMap<Integer, Energy> retreat;
    private ArrayList<Attack> attacks;
    private int ID;

    @Override
    public String toString() {
        return "Pokemon " +
                "(" + getIndex() + ")" +
                " {" +
                " name='" + getName() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", healthPoints=" + this.healthPoints +
                ", damagePoints=" + this.damagePoints +
                ", energy=" + this.energy +
                ", stage='" + this.stage + '\'' +
                ", evolvesFrom='" + this.evolvesFrom + '\'' +
                ", Retreat='" + this.retreat + '\'' +
                ", Attack='" + this.attacks + '\'' +
                '}';
    }



    public Pokemon(String name, int index,String category, String stage, int hp,int currentHP, HashMap<Energy, Integer> retreat) {
        super(name, index, category);
        maxHealthPoints = hp;
        healthPoints = currentHP;

        this.stage = stage;
        this.retreat = retreat;

    }


    public int getHealthPoints() {
        return this.healthPoints;
    }

    public int getDamagePoints() {
        return this.damagePoints;
    }

    public ArrayList<Energy> getEnergy() {
        return this.energy;
    }

    public String getStage() {
        return this.stage;
    }

    public String getEvolvesFrom() {
        return this.evolvesFrom;
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
        this.energy.add(energyCard);
    }

    public Energy removeEnergy() throws NullPointerException {
       return this.energy.remove(this.energy.size() - 1);
    }

    public void addAttack(Attack attack){
        this.attacks.add(attack);
    }

    public ArrayList<Attack> getAttacks(){
        return attacks;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void attachEnergy(Energy e){
        this.energy.add(e);
    }

    public void removeEnergy(Energy e){
        this.energy.remove(e);
    }

    public boolean hasEnoughEnergy(int attackIndex){
        Attack attack = attacks.get(attackIndex);

        boolean enough = true;
        HashMap<Energy, Integer> energyRequired = attack.getEnergyRequired();
        for (Entry<Energy, Integer> entry : energyRequired.entrySet()) {
            String type = entry.getKey().getType();

            int count = 0;
            for (Energy energy : energy){
                String typeToCompare = energy.getType();
                if (typeToCompare.equals(type)){
                    count++;
                }
            }

            int amount = entry.getValue();
            if (count < amount){
                enough = false;
            }
        }

        return enough;
    }

    public void removeHP(int points){
        this.healthPoints -= points;
    }
}
