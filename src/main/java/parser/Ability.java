package parser;

import java.util.ArrayList;

public class Ability {

    private String name;
    private String description;
    private ArrayList<AbilityLogic> logic;

    public ArrayList<AbilityLogic> getLogic() {
        return logic;
    }


    /**
     *
     * @param name
     * @param description
     * @param logic
     */
    public Ability(String name, String description, ArrayList<AbilityLogic> logic) {
        this.name = name;
        this.description = description;
        this.logic = logic;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public boolean isParsed() {
        for (AbilityLogic tmpLogic : logic) {
            if (tmpLogic.getLogic().size() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logic='" + logic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ability ability = (Ability) o;

        return name != null ? name.equals(ability.name) : ability.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
