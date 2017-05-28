package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbilitiesFileParser {

    private List<String> itemList;

    private String name;
    private String action;
    private String description;
    private String logic;
    private AbilityDescriptionMap descriptionMap;


    public AbilitiesFileParser(String[] items) {
        this.itemList = new ArrayList<String>(Arrays.asList(items));
        this.descriptionMap = new AbilityDescriptionMap();
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public String getLogic() {
        return logic;
    }

    public String getDescription() {
        return description;
    }

    public void parseName() {
        this.name = itemList.get(0);
        itemList.remove(0);
    }

    public void parseAction() {
        this.action = itemList.get(0);
        itemList.remove(0);
    }

    public void parseLogic() {
        this.logic = String.join(":", itemList);
    }

    public void parseDescription() {
        this.description = (String) descriptionMap.consts.get(name);
    }

    public void print(){
        System.out.println(String.join(":", itemList));
    }

}
