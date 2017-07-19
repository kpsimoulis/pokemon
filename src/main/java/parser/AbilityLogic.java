package parser;

import java.util.List;

public class AbilityLogic {

    protected String type;
    protected List<String> logic;

    public AbilityLogic(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public List<String> getLogic() {
        return logic;
    }

    public void setLogic(List<String> logic) {
        this.logic = logic;
    }


    @Override
    public String toString() {
        return "AbilityLogic{" +
                "type='" + type + '\'' +
                ", logic=" + logic +
                '}';
    }

    /**
     *
     */
    public void print() {
        System.out.println(String.join(":", logic));
    }


}
