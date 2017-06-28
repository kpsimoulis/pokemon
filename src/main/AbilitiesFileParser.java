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
    private int damagePoints;
    private String damageLogic;
    private int times;
    private String statusEffect;
    private AbilityDescriptionMap descriptionMap;
    private boolean parsed = false;

    /**
     * @param items
     */
    public AbilitiesFileParser(String[] items) {
        this.itemList = new ArrayList<String>(Arrays.asList(items));
        this.descriptionMap = new AbilityDescriptionMap();
    }

    /**
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     * @return
     */
    public int getDamagePoints() {
        return damagePoints;
    }

    /**
     * @return
     */
    public int getTimes() {
        return times;
    }

    /**
     * @return
     */
    public String getStatusEffect() {
        return statusEffect;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * @return
     */
    public String getLogic() {
        return logic;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ability is parsed
     * @return
     */
    public boolean isParsed() {
        return parsed;
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

        String logicLine = String.join(":", itemList);
        String[] logicItems = logicLine.split(",");

        if (logicItems.length == 1) {
            if (action.equals("dam")) {
                if (!this.itemList.get(0).equals("target")) {
                    throw new IllegalArgumentException("Expecting word 'target'");
                }
//                itemList.remove(0);
                this.target = itemList.get(1);
                if (target.equals("opponent-active")) {
//                    itemList.remove(0);
                    try {
                        this.damagePoints = Integer.parseInt(itemList.get(0));
                        this.parsed = true;
//                        itemList.remove(0);
                    } catch (Exception e) {
                        // TODO process damageLogic
                        // print();
                        this.damageLogic = String.join(":", itemList);
                    }
                }
                else {
                    // TODO process remaining
//                    this.parsed = false;
//                    System.out.print(getName() + ": ");
//                    print();
                }

            } else {
                // TODO process other Actions
//                this.parsed = false;
//                System.out.print(getName() + " (" + getAction() + "): ");
//                print();
            }


        }
        else {
            // TODO process multi-abilities, length > 1
            this.parsed = false;
            System.out.print(getName() + " (" + getAction() + "): ");
            print();
        }


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
    public void print() {
        System.out.println(String.join(":", itemList));
    }

}
