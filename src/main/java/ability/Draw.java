package ability;

import parser.AbilityLogic;
import parser.Amount;
import parser.Target;

import java.util.List;

public class Draw extends AbilityLogic {

    private Amount amount;
    private Target target;

    public Draw(List<String> logic) {
        super("draw");
        this.logic = logic;
        parse();
    }

    public void parse() {

        // Add missing target for your
        if (logic.get(0).matches("[0-9]+")) {
            logic.add(0, "your");
        }

        // Parse Target
        this.target = new Target(logic);
        this.logic = target.getLogic();

        // Parse Amount
        this.amount = new Amount(logic);
        this.logic = amount.getLogic();

    }

    public Amount getAmount() {
        return amount;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Draw{" +
                "amount=" + amount +
                ", target=" + target +
                '}';
    }
}
