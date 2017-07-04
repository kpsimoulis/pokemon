package ability;

import main.AbilityLogic;

import java.util.List;

public class Deenergize extends AbilityLogic {

    public Deenergize(List<String> logic) {
        super("deenergize");
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
