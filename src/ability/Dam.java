package ability;

import main.AbilityLogic;

import java.util.List;

public class Dam extends AbilityLogic {



    public Dam(List<String> logic) {
        super("dam");
        this.logic = logic;
        parse();
    }


    public void parse() {
        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        parsePokemonTarget();
        parseAmount();
    }

}
