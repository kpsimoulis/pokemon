package ability;

import parser.AbilityLogic;

import java.util.List;

public class Destat extends AbilityLogic {

    // TODO ask professor for valid targets
    private String target;

    public Destat(List<String> logic) {
        super("destat");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        this.target = logic.get(0);
        logic.remove(0);
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Destat{" +
                "target='" + target + '\'' +
                '}';
    }
}
