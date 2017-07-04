package ability;

import main.AbilityLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Cond extends AbilityLogic {

    private String condition;
    private AbilityLogic conditionIsMet;
    private AbilityLogic conditionIsNotMet;

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

            parsePokemonTarget();
        }


        String[] condItems = String.join(":", logic).split(":else:(?![^\\(\\[]*[\\]\\)])", -1);

        if (condItems.length > 2) {
            throw new IllegalArgumentException("Invalid number of conditions, should be 1 or 2");
        }


        // Process condition is Met
        setLogic(new LinkedList<String>(Arrays.asList(condItems[0].split(":(?![^\\(\\[]*[\\]\\)])", -1))));
        String type1 = logic.get(0);
        logic.remove(0);
        conditionIsMet = getCond(type1);

        if (!(conditionIsMet instanceof Null) && conditionIsMet.getLogic().size() == 0) {
            if (condItems.length == 2) {
                setLogic(new LinkedList<String>(Arrays.asList(condItems[1].split(":(?![^\\(\\[]*[\\]\\)])", -1))));
                String type2 = logic.get(0);
                logic.remove(0);
                conditionIsNotMet = getCond(type2);

                if ((conditionIsNotMet instanceof Null) || conditionIsNotMet.getLogic().size() != 0) {

                }

            }
        }
        else {
//            System.out.println(conditionIsMet);
        }
    }

    public AbilityLogic getCond(String type) {


        if (type.equals("dam")) {
            return new Dam(logic);
        }
        else if (type.equals("heal")) {
            return new Heal(logic);
        }
        else if (type.equals("deenergize")) {
            return new Deenergize(logic);
        }
        else if (type.equals("applystat")) {
            return new Applystat(logic);
        }
        else if (type.equals("shuffle")) {
            return new Shuffle(logic);
        }
        // TODO cond = healed, search, (applystat:status:asleep:opponent-active,applystat:status:poisoned:opponent-active)
        else {
//            System.out.println("type = " + type);
//                print();
            return new Null(logic);
        }
    }
}
