package ability;

import main.AbilityLogic;

import java.util.List;

public class Swap extends AbilityLogic {

    public Swap(List<String> logic) {
        super("swap");
        this.logic = logic;
        parse();
    }

    public void parse() {

        if (!logic.get(0).equals("source")) {
            throw new IllegalArgumentException("Expecting word 'source'");
        }
        logic.remove(0);

        parsePokemonSource();

        if (!logic.get(0).equals("destination")) {
            throw new IllegalArgumentException("Expecting word 'destination'");
        }
        logic.remove(0);

        parsePokemonDestination();

    }

}
