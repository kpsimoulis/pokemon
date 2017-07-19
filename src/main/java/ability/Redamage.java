package ability;

import parser.AbilityLogic;
import parser.Amount;
import parser.Target;

import java.util.List;

public class Redamage extends AbilityLogic {

    private Target source;
    private Target destination;
    private Amount amount;

    public Redamage(List<String> logic) {
        super("redamage");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("source")) {
            throw new IllegalArgumentException("Expecting word 'source'");
        }
        logic.remove(0);

        // Parse Source
        this.source = new Target(logic);
        this.logic = source.getLogic();

        if (!logic.get(0).equals("destination")) {
            throw new IllegalArgumentException("Expecting word 'destination'");
        }
        logic.remove(0);

        // Parse Destination
        this.destination = new Target(logic);
        this.logic = destination.getLogic();

        // Parse Amount
        this.amount = new Amount(logic);
        this.logic = amount.getLogic();

    }

    public Target getSource() {
        return source;
    }

    public Target getDestination() {
        return destination;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Redamage{" +
                "source=" + source +
                ", destination=" + destination +
                ", amount=" + amount +
                '}';
    }
}
