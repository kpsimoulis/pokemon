package ability;

import main.AbilityLogic;

import java.util.List;

public class Search extends AbilityLogic {

    public Search(List<String> logic) {
        super("search");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        // TODO ask professor why choice is missing and why there is choice:your-pokemon

//        print();

//        parsePokemonTarget();

    }

}
