package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ability.*;

public class AbilitiesFileParser {

    private List<String> itemList;
    private String name;
    private String description;
    private ArrayList<AbilityLogic> logic;
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
    public String getName() {
        return name;
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

    public ArrayList<AbilityLogic> getLogic() {
        return logic;
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
    public void parseLogic() {

        String logicLine = String.join(":", itemList);
        String[] logicItems = logicLine.split(",(?![^\\(\\[]*[\\]\\)])", -1);
        this.logic = new ArrayList<AbilityLogic>();

        for (int i = 0; i < logicItems.length; i++) {
            String[] logicVariables = logicItems[i].split(":(?![^\\(\\[]*[\\]\\)])", -1);

            List<String> tmpLogic = new ArrayList<String>(Arrays.asList(logicVariables));
            String type = tmpLogic.get(0);
            tmpLogic.remove(0);

            if (type.equals("dam")) {
                logic.add(new Dam(tmpLogic));
            }
            else if (type.equals("heal")) {
                logic.add(new Heal(tmpLogic));
            }
            else if (type.equals("deenergize")) {
                logic.add(new Deenergize(tmpLogic));
            }
            else if (type.equals("reenergize")) {
                logic.add(new Reenergize(tmpLogic));
            }
            else if (type.equals("redamage")) {
                logic.add(new Redamage(tmpLogic));
            }
            else if (type.equals("swap")) {
                logic.add(new Swap(tmpLogic));
            }
            else if (type.equals("destat")) {
                logic.add(new Destat(tmpLogic));
            }
            else if (type.equals("applystat")) {
                logic.add(new Applystat(tmpLogic));
            }
            else if (type.equals("draw")) {
                logic.add(new Draw(tmpLogic));
            }
            else if (type.equals("search")) {
                logic.add(new Search(tmpLogic));
            }
            else if (type.equals("deck")) {
                logic.add(new Deck(tmpLogic));
            }
            else if (type.equals("shuffle")) {
                logic.add(new Shuffle(tmpLogic));
            }
            else if (type.equals("cond")) {
                logic.add(new Cond(tmpLogic));
            }
            else if (type.equals("add")) {
                logic.add(new Add(tmpLogic));
            }
            else {
                throw new IllegalArgumentException("Invalid Ability: " + type);
            }
        }
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
