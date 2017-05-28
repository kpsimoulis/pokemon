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
    private String target;
    private int amount;
    private int times;
    private String statusEffect;
    private AbilityDescriptionMap descriptionMap;


    /**
     *
     * @param items
     */
    public AbilitiesFileParser(String[] items) {
        this.itemList = new ArrayList<String>(Arrays.asList(items));
        this.descriptionMap = new AbilityDescriptionMap();
    }

    /**
     *
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     *
     * @return
     */
    public int getTimes() {
        return times;
    }

    /**
     *
     * @return
     */
    public String getStatusEffect() {
        return statusEffect;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @return
     */
    public String getLogic() {
        return logic;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     */
    public void parseName() {
        this.name = itemList.get(0);
        itemList.remove(0);
    }

    /**
     *
     */
    public void parseAction() {
        this.action = itemList.get(0);
        itemList.remove(0);
    }

    /**
     *
     */
    public void parseLogic() {

        this.logic = String.join(":", itemList);
    }

    /**
     *
     */
    public void parseDescription() {
        this.description = (String) descriptionMap.consts.get(name);
    }

    /**
     *
     */
    public void print(){
        System.out.println(String.join(":", itemList));
    }

}
