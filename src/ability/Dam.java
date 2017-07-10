package ability;

import main.AbilityLogic;
import main.Amount;
import main.Target;

import java.util.List;

public class Dam extends AbilityLogic {

    private Target target;
    private Amount amount;

    public Target getTarget() {
        return target;
    }

    public Amount getAmount() {
        return amount;
    }

    public Dam(List<String> logic) {
        super("dam");
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

}
