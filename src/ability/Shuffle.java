package ability;

import main.AbilityLogic;
import main.Target;

import java.util.List;

public class Shuffle extends AbilityLogic {

    private Target target;

    public Shuffle(List<String> logic) {
        super("shuffle");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        // Parse Target
        this.target = new Target(logic);
        this.logic = target.getLogic();

    }

}