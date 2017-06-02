package main;

import java.util.ArrayList;

public class Attack {

    /**
     * Conditions of the Attack
     */
    private ArrayList<Requirement> requirement;

    /**
     *
     */
    private Ability ability;

    /**
     *
     * @param requirement
     * @param ability
     */
    public Attack(ArrayList<Requirement> requirement, Ability ability) {
        this.requirement = requirement;
        this.ability = ability;
    }

    /**
     *
     * @return
     */
    public ArrayList<Requirement> getRequirement() {
        return requirement;
    }

    /**
     *
     * @return
     */
    public Ability getAbility() {
        return ability;
    }


    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Attack{" +
                "requirement=" + requirement +
                ", ability=" + ability +
                '}';
    }
}
