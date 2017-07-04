package ability;

import main.AbilityLogic;

import java.util.List;

public class Deck extends AbilityLogic {

    private String destination;
    private String orientation;

    public Deck(List<String> logic) {
        super("deck");
        this.logic = logic;
        parse();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrientation() {
        if (orientation.equals("bottom")) {
            return "bottom";
        }
        else {
            return "top";
        }
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void parse() {

        if (!logic.get(0).equals("target")) {
            throw new IllegalArgumentException("Expecting word 'target'");
        }
        logic.remove(0);

        parsePlayerTarget();

        if (!logic.get(0).equals("destination")) {
            throw new IllegalArgumentException("Expecting word 'destination'");
        }
        logic.remove(0);

        if (!logic.get(0).equals("deck") && !logic.get(0).equals("discard")) {
            throw new IllegalArgumentException("Deck destination should be deck or discard");
        }

        logic.remove(0);
        setDestination(logic.get(0));

        if (logic.get(0).equals("top") || logic.get(0).equals("bottom")) {
            setOrientation(logic.get(0));
            logic.remove(0);
        }

        if (logic.get(0).equals("choice")) {
            logic.remove(0);
            if (logic.get(0).equals("you") || logic.get(0).equals("them")) {
                setChoice(logic.get(0));
                logic.remove(0);
            }
            else {
                throw new IllegalArgumentException("choice should be followed by you or them");
            }
            if (!logic.get(0).equals("target")) {
                throw new IllegalArgumentException("Expecting word 'target'");
            }
            logic.remove(0);
        }
        parseAmount();
    }

}
