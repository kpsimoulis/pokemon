package parser;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attack attack = (Attack) o;

        if (requirement != null ? !requirement.equals(attack.requirement) : attack.requirement != null) return false;
        return ability != null ? ability.equals(attack.ability) : attack.ability == null;
    }

    @Override
    public int hashCode() {
        int result = requirement != null ? requirement.hashCode() : 0;
        result = 31 * result + (ability != null ? ability.hashCode() : 0);
        return result;
    }
}
