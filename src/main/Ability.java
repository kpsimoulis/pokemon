package main;

public class Ability {

    private String name;
    private String action;
    private String description;
    private String logic;
    private String target;
    private int amount;
    private int times;
    private String statusEffect;
    private boolean parsed = false;

    /**
     *
     * @param name
     * @param action
     * @param description
     * @param logic
     */
    public Ability(String name, String action, String description, String logic, Boolean parsed) {
        this.name = name;
        this.action = action;
//        this.target = target;
        this.description = description;
        this.logic = logic;
        this.parsed = parsed;
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
        if (name.equals("Rollout") || name.equals("Flail")) {
            return 10;
        }
        else if (name.equals("Aurora Beam")) {
            return 80;
        }
        else if (name.equals("Heart Sign")) {
            return 50;
        }
        else {
            return 20;
        }
    }

    public String getAction() {
        return action;
    }

    public boolean isParsed() {
        return parsed;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                ", logic='" + logic + '\'' +
                '}';
    }
}
