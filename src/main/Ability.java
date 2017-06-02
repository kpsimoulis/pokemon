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

    /**
     *
     * @param name
     * @param action
     * @param description
     * @param target
     */
    public Ability(String name, String action, String description, String target) {
        this.name = name;
        this.action = action;
        this.target = target;
        this.description = description;
        this.logic = logic;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

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
