package ability;

import parser.AbilityLogic;
import parser.Amount;
import parser.Target;

import java.util.List;

public class Deck extends AbilityLogic {

    private Target target;
    private String destination;
    private String orientation;
    private String choice;
    private Amount amount;

    public Deck(List<String> logic) {
        super("deck");
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



        if (!logic.get(0).equals("destination")) {
            throw new IllegalArgumentException("Expecting word 'destination'");
        }
        logic.remove(0);

        //
        if (!logic.get(0).equals("deck") && !logic.get(0).equals("discard")) {
            throw new IllegalArgumentException("Deck destination should be deck or discard");
        }

        logic.remove(0);
        this.destination = logic.get(0);

        if (logic.get(0).equals("top") || logic.get(0).equals("bottom")) {
            this.orientation = logic.get(0);
            logic.remove(0);
        }
        else {
            this.orientation = "top";
        }


        if (logic.get(0).equals("choice")) {
            logic.remove(0);
            if (logic.get(0).equals("you") || logic.get(0).equals("them") || logic.get(0).equals("random")) {
                this.choice = logic.get(0);
                logic.remove(0);
            }
            else {
                throw new IllegalArgumentException("choice should be followed by you or them");
            }
        }

        // Parse Amount
        this.amount = new Amount(logic);
        this.logic = amount.getLogic();

    }

    public Target getTarget() {
        return target;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getChoice() {
        return choice;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "target=" + target +
                ", destination='" + destination + '\'' +
                ", orientation='" + orientation + '\'' +
                ", choice='" + choice + '\'' +
                ", amount=" + amount +
                '}';
    }
}
