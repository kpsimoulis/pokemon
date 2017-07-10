package main;

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

    /**
     * TODO Fix damage parsing
     * @return
     */
    public int getDamage() {
        if (isParsed()) {
            return 20;
//            return logic.get(0).getAmount().getAmount();
        }
        else {
            return 15;
        }
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
}
