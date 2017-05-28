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
