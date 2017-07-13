package ability;

import main.AbilityLogic;
import main.Amount;
import main.Target;

import java.util.List;

public class Reenergize extends AbilityLogic {

    private Target source;
    private Target destination;
    private Amount amount;

    public Reenergize(List<String> logic) {
        super("reenergize");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        // Parse Source
        this.source = new Target(logic);
        this.logic = source.getLogic();

        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        // Parse Destination
        this.destination = new Target(logic);
        this.logic = destination.getLogic();

        // Parse Amount
        this.amount = new Amount(logic);
        this.logic = amount.getLogic();

    }

    @Override
    public String toString() {
        return "Reenergize{" +
                "source=" + source +
                ", destination=" + destination +
                ", amount=" + amount +
                '}';
    }
}
