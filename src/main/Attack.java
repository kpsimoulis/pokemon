package main;

import java.util.ArrayList;

public class Attack {

    private ArrayList<Requirement> requirement;
    private int abilityLine;

    public Attack(ArrayList<Requirement> requirement, int abilityLine) {
        this.requirement = requirement;
        this.abilityLine = abilityLine;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "requirement=" + requirement +
                ", abilityLine=" + abilityLine +
                '}';
    }
}
