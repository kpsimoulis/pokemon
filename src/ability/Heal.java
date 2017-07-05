package ability;

import main.AbilityLogic;

import java.util.List;

public class Heal extends AbilityLogic {

    public Heal(List<String> logic) {
        super("heal");
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
