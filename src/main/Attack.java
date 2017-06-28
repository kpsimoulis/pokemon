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
     * Attack is supported
     *
     * supported:
     * dam
     *
     * TODO:
     * applystat
     * cond
     * deck
     * draw
     * redamage
     * search
     *
     * @return boolean
     */
    public boolean isSupported() {
        if (getAbility().isParsed()) {
            return true;
        }
        else return false;
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
