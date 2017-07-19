package ability;

import parser.AbilityLogic;
import parser.Amount;
import parser.Target;

import java.util.List;

public class Deenergize extends AbilityLogic {

    private Target target;
    private Amount amount;

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

        // Parse Target
        this.target = new Target(logic);
        this.logic = target.getLogic();

        // Parse Amount
        this.amount = new Amount(logic);
        this.logic = amount.getLogic();
    }

    public Target getTarget() {
        return target;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Deenergize{" +
                "target=" + target +
                ", amount=" + amount +
                '}';
    }
}
