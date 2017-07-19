package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            ParserHelper parser = new ParserHelper();

            logic.add(parser.getAbilityByLogic(type, tmpLogic));

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
