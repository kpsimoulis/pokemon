package ability;

import main.AbilityLogic;

import java.util.List;

public class Reenergize extends AbilityLogic {

    public Reenergize(List<String> logic) {
        super("reenergize");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        parsePokemonSource();

        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        parsePokemonDestination();

        parseAmount();

    }

}
