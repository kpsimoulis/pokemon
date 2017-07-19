package ability;

import parser.AbilityLogic;
import parser.ParserHelper;
import parser.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cond extends AbilityLogic {

    private String condition;
    private Target target; // Applies to cond=healed only
    private AbilityLogic abilityRequirement; // Applies to cond=ability only
    private ArrayList<AbilityLogic> conditionIsMet;
    private ArrayList<AbilityLogic> conditionIsNotMet;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Cond(List<String> logic) {
        super("cond");
        this.logic = logic;
        parse();
    }

    public void parse() {

        ParserHelper parser = new ParserHelper();

        this.conditionIsMet = new ArrayList<AbilityLogic>();
        this.conditionIsNotMet = new ArrayList<AbilityLogic>();

        if (logic.get(0).equals("healed") || logic.get(0).equals("flip") || logic.get(0).equals("ability") || logic.get(0).equals("choice") || logic.get(0).equals("count(target:your-active:energy:psychic)>0")) {
            setCondition(logic.get(0));
        }
        // TODO ask professor for the last condition
        else {
            throw new IllegalArgumentException("Invalid condition " + logic.get(0) + ", expecting healed, flip, ability or choice");
        }
        logic.remove(0);


        if (condition.equals("healed")) {

            if (!logic.get(0).equals("target")) {
                throw new IllegalArgumentException("Expecting word 'target'");
            }
            logic.remove(0);

            // Parse Target
            this.target = new Target(logic);
            this.logic = target.getLogic();

        }
        else if (condition.equals("ability")) {
            String abilityType = logic.get(0);
            logic.remove(0);
            String tmpLogic = logic.get(logic.size() - 1);
            logic.remove(logic.size() - 1);
            this.abilityRequirement = parser.getAbilityByLogic(abilityType, logic);
            this.logic.add(tmpLogic);

        }

        String tmpLogic = String.join(":", logic);
        String[] logicItems;

        Matcher matcher;

        // Multiple logic for Heads only
        matcher = Pattern.compile("^\\((.*)\\)$").matcher(tmpLogic);
        if (matcher.matches()) {
            logicItems = matcher.group(1).split(",");
        }
        else {
            logicItems = new String[1];
            logicItems[0] = tmpLogic;
        }

        for (int i = 0; i < logicItems.length; i++) {

            String[] condItems = logicItems[i].split(":else:");

            if (condItems.length > 2) {
                throw new IllegalArgumentException("Invalid number of conditions, should be 1 or 2");
            }

            // Process condition is Met
            // TODO do a print and check split
            setLogic(new LinkedList<String>(Arrays.asList(condItems[0].split(":(?![^\\(\\[]*[\\]\\)])", -1))));
            String type1 = logic.get(0);
            logic.remove(0);

            AbilityLogic tmp = parser.getAbilityByLogic(type1, logic);

            // Check if Logic is parsed
            if (tmp.getLogic().size() == 0) {
                conditionIsMet.add(tmp);

                if (condItems.length == 2) {
                    setLogic(new LinkedList<String>(Arrays.asList(condItems[1].split(":(?![^\\(\\[]*[\\]\\)])", -1))));
                    String type2 = logic.get(0);
                    logic.remove(0);
                    conditionIsNotMet.add(parser.getAbilityByLogic(type2, logic));
                }
            }
        }
    }

    public Target getTarget() {
        return target;
    }

    public AbilityLogic getAbilityRequirement() {
        return abilityRequirement;
    }

    public ArrayList<AbilityLogic> getConditionIsMet() {
        return conditionIsMet;
    }

    public ArrayList<AbilityLogic> getConditionIsNotMet() {
        return conditionIsNotMet;
    }

    @Override
    public String toString() {
        return "Cond{" +
                "condition='" + condition + '\'' +
                ", target=" + target +
                ", abilityRequirement=" + abilityRequirement +
                ", conditionIsMet=" + conditionIsMet +
                ", conditionIsNotMet=" + conditionIsNotMet +
                '}';
    }
}
