package main;

public class Ability {

    private String name;
    private String action;
    private String description;
    private String logic;

    public Ability(String name, String action, String description, String logic) {
        this.name = name;
        this.action = action;
        this.description = description;
        this.logic = logic;
    }

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
