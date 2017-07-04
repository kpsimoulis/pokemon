package ability;

import main.AbilityLogic;

import java.util.List;

public class Shuffle extends AbilityLogic {

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
        parsePlayerTarget();
    }

}
