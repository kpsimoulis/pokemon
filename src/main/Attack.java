package main;

import java.util.ArrayList;

public class Attack {

    private ArrayList<Requirement> requirement;
    private Ability ability;

    public Attack(ArrayList<Requirement> requirement, Ability ability) {
        this.requirement = requirement;
        this.ability = ability;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "requirement=" + requirement +
                ", ability=" + ability +
                '}';
    }
}
