package main;

import java.util.List;

public class Target {

    private String area;
    private String name;
    private Boolean choice;
    private List<String> logic;

    public List<String> getLogic() {
        return logic;
    }

    public Target(List<String> logic) {
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (logic.get(0).equals("choice")) {
            this.choice = true;
            logic.remove(0);
        }
        String[] targetItems = logic.get(0).split("-");
        logic.remove(0);
        if (targetItems[0].equals("your") || targetItems[0].equals("opponent")) {
            this.name = targetItems[0];
        }
        else {
            throw new IllegalArgumentException("Invalid target " + targetItems[0] + " it must be opponent or your");
        }
        if (targetItems.length == 2) {
            if (targetItems[1].equals("pokemon") || targetItems[1].equals("active") || targetItems[1].equals("bench") || targetItems[1].equals("hand") || targetItems[1].equals("deck") || targetItems[1].equals("discard")) {
                this.area = targetItems[1];
            }
            else {
                throw new IllegalArgumentException("Invalid target " + targetItems[1] + " it must be your-active, opponent-active or choice");
            }
        }
    }

    @Override
    public String toString() {
        return "Target{" +
                "area='" + area + '\'' +
                ", name='" + name + '\'' +
                ", choice=" + choice +
                '}';
    }

}
