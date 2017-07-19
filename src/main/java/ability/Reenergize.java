package ability;

import parser.AbilityLogic;
import parser.Target;

import java.util.List;

public class Reenergize extends AbilityLogic {

    private Target source;
    private Target destination;
    private int destAmount; // TODO: @kosta Convert those two amount objects
    private int srcAmount;

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

        // Parse Source Amount
        if (logic.get(0).matches("[0-9]+")) {
            this.srcAmount = Integer.parseInt(logic.get(0));
            logic.remove(0);
        }
        else {
            throw new IllegalArgumentException("Unsupported Amount for reenergize");
        }

        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        // Parse Destination
        this.destination = new Target(logic);
        this.logic = destination.getLogic();

        // Parse Amount
        if (logic.get(0).matches("[0-9]+")) {
            this.destAmount = Integer.parseInt(logic.get(0));
            logic.remove(0);
        }
        else {
            throw new IllegalArgumentException("Unsupported Amount for reenergize");
        }

    }

    public Target getSource() {
        return source;
    }

    public Target getDestination() {
        return destination;
    }

    public int getDestAmount() {
        return destAmount;
    }

    public int getSrcAmount() {
        return srcAmount;
    }

    @Override
    public String toString() {
        return "Reenergize{" +
                "source=" + source +
                ", destination=" + destination +
                ", destAmount=" + destAmount +
                '}';
    }
}
