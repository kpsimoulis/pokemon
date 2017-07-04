package ability;

import main.AbilityLogic;

import java.util.List;

public class Add extends AbilityLogic {

    public Add(List<String> logic) {
        super("add");
        this.logic = logic;
        parse();
    }

    public void parse() {
//        print();
    }

}
