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

public class Add extends AbilityLogic {

    private Target target;
    private Target triggerTarget;
    private String triggerCondition;
    private ArrayList<AbilityLogic> ability;

    public Add(List<String> logic) {
        super("add");
        this.logic = logic;
        parse();
    }

    public void parse() {
        ParserHelper parser = new ParserHelper();

        this.ability = new ArrayList<AbilityLogic>();

        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        // Parse Target
        this.target = new Target(logic);
        this.logic = target.getLogic();

        // TODO Ask professor if trigger is required
        if (!logic.get(0).equals("trigger")) {
            throw new IllegalArgumentException("Expecting word 'trigger'");
        }
        logic.remove(0);

        // Parse Trigger
        this.triggerTarget = new Target(logic);
        this.logic = triggerTarget.getLogic();
        this.triggerCondition = logic.get(0);
        logic.remove(0);

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
            setLogic(new LinkedList<String>(Arrays.asList(logicItems[i].split(":"))));
            String type = logic.get(0);
            logic.remove(0);
            this.ability.add(parser.getAbilityByLogic(type, logic));
        }
    }

    public Target getTarget() {
        return target;
    }

    public Target getTriggerTarget() {
        return triggerTarget;
    }

    public String getTriggerCondition() {
        return triggerCondition;
    }

    public ArrayList<AbilityLogic> getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return "Add{" +
                "target=" + target +
                ", triggerTarget=" + triggerTarget +
                ", triggerCondition='" + triggerCondition + '\'' +
                ", ability=" + ability +
                '}';
    }
}
