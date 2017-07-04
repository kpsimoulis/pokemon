package ability;

import main.AbilityLogic;

import java.util.List;

public class Draw extends AbilityLogic {

    private int amount;

    public Draw(List<String> logic) {
        super("draw");
        this.logic = logic;
        parse();
    }

    public void parse() {

        if (logic.get(0).equals("opponent")) {
            setTarget("them");
            logic.remove(0);
        }
        else {
            setTarget("you");
        }
        parseAmount();

    }

}
