package main;

import java.util.ArrayList;
import java.util.List;

public class AbilityLogic {

    protected String type;
    private String target;
    private String choice;
    private List<String> amount;
    protected List<String> logic;
    private String source;
    private String destination;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public AbilityLogic() {
        this.type = null;
    }


    public AbilityLogic(String type) {
        this.type = type;
    }


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public List<String> getLogic() {
        return logic;
    }

    public void setLogic(List<String> logic) {
        this.logic = logic;
    }

    public List<String> getAmount() {
        return amount;
    }

    public void setAmount(List<String> amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AbilityLogic{" +
                "type='" + type + '\'' +
                ", target='" + target + '\'' +
                ", choice='" + choice + '\'' +
                ", amount=" + amount +
                ", logic=" + logic +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    /**
     *
     */
    public void print() {
        System.out.println(String.join(":", logic));
    }


    public void parsePokemonTarget() {
        if (logic.get(0).equals("your-active") || logic.get(0).equals("opponent-active") || logic.get(0).equals("choice") || logic.get(0).equals("your-bench")) {
            setTarget(logic.get(0));
        }
        else {
            throw new IllegalArgumentException("Invalid target " + logic.get(0) + " it must be your-active, opponent-active or choice");
        }
        logic.remove(0);

        if (getTarget().equals("choice")) {
            parseChoice();
        }
    }

    public void parsePokemonSource() {
        if (logic.get(0).equals("choice")) {
            logic.remove(0);
        }

        if (logic.get(0).equals("your-active") || logic.get(0).equals("opponent-active") || logic.get(0).equals("opponent") || logic.get(0).equals("your") || logic.get(0).equals("opponent-bench") || logic.get(0).equals("your-bench")) {
            setSource(logic.get(0));
        }
        else {
            throw new IllegalArgumentException("Invalid target " + logic.get(0)+ " , valid choices are opponent, your, opponent-bench, your-bench");
        }
        logic.remove(0);
    }

    public void parsePokemonDestination() {
        if (logic.get(0).equals("choice")) {
            logic.remove(0);
        }

        if (logic.get(0).equals("your-active") || logic.get(0).equals("opponent-active") || logic.get(0).equals("opponent") || logic.get(0).equals("your") || logic.get(0).equals("opponent-bench") || logic.get(0).equals("your-bench")) {
            setDestination(logic.get(0));
        }
        else {
            throw new IllegalArgumentException("Invalid target choice, valid choices are opponent, your, opponent-bench, your-bench");
        }
        logic.remove(0);
    }


    public void parseChoice() {
        if (logic.get(0).equals("opponent") || logic.get(0).equals("your") || logic.get(0).equals("opponent-bench") || logic.get(0).equals("your-bench")) {
            setChoice(logic.get(0));
        }
        else {
            throw new IllegalArgumentException("Invalid target choice, valid choices are opponent, your, opponent-bench, your-bench");
        }
        logic.remove(0);
    }

    public void parsePlayerTarget() {
        if (logic.get(0).equals("you") || logic.get(0).equals("them")) {
            setTarget(logic.get(0));
        }
        else {
            throw new IllegalArgumentException("Invalid target " + logic.get(0) + " it must be you or them");
        }
        logic.remove(0);
    }

    /**
     * TODO improve parseAmount logic to only parse integers and count()
     *  A more complex example would be count(target:youractive—damage) which would count the damage on your actice pokemon.
     */
    public void parseAmount() {
        this.amount = new ArrayList<String>();
        for (String tmpAmount : String.join(":", logic).split("\\*")) {
            if (isValidAmount(tmpAmount)) {
                amount.add(tmpAmount);
                this.logic.clear();
            }
            else {
                System.out.println("Amount could not be parsed " + tmpAmount);
//                for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
//                    System.out.println(ste);
//                }
//                throw new IllegalArgumentException("Invalid amount " + tmpAmount);
            }
        }
    }

    public static boolean isValidAmount(String str)
    {
//        return str.matches("[0-9]+") || str.matches("^count\\(.*\\)");
        return str.matches("[0-9]+") || str.matches("count.*");
    }

    public boolean isIntegerAmount() {
        return amount.get(0).matches("[0-9]+");
    }

}
