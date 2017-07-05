package ability;

import main.AbilityLogic;

import java.util.List;

public class Destat extends AbilityLogic {

    public Destat(List<String> logic) {
        super("destat");
        this.logic = logic;
        parse();
    }

    public void parse() {
//        print();
    }

}
