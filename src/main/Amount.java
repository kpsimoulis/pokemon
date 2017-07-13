package main;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Amount {

    private int amount;
    private boolean calculated;
    private String target;
    private String source;
    private int multiplier = 1;
    private List<String> logic;

    public Amount(List<String> logic) {
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (logic.size() > 1) {
            throw new IllegalArgumentException("Invalid amount size: " + logic.size());
        }
        String tmpAmount = String.join(":", logic);
        if (isIntegerAmount(tmpAmount)) {
            this.amount = Integer.parseInt(tmpAmount);
        } else {
            for (String tmpAmount2 : String.join(":", logic).split("\\*")) {
                if (isIntegerAmount(tmpAmount2)) {
                    this.multiplier *= Integer.parseInt(tmpAmount2);
                } else {
                    Matcher matcher = Pattern.compile("^count\\((.*)\\)$").matcher(tmpAmount2);
                    if (matcher.matches()) {

                        String[] tmpAmount3 = matcher.group(1).split(":");
                        if (!tmpAmount3[0].equals("target")) {
                            throw new IllegalArgumentException("Expecting word 'target'");
                        }
                        this.calculated = true;
                        this.target = tmpAmount3[1];
                        if (tmpAmount3.length == 3) {
                            this.source = tmpAmount3[2];
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid amount " + tmpAmount);
                    }
                }

            }
        }
        logic.remove(0);
    }

    public List<String> getLogic() {
        return logic;
    }

    public int getAmount() {
        if (calculated) {
            // TODO change this to logic
            return 5;
        }
        else {
            return amount;
        }
    }

    public boolean isCalculated() {
        return calculated;
    }

    public String getTarget() {
        return target;
    }

    public String getSource() {
        return source;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public boolean isIntegerAmount(String str) {
        return str.matches("[0-9]+");
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount=" + amount +
                ", calculated=" + calculated +
                ", target='" + target + '\'' +
                ", source='" + source + '\'' +
                ", multiplier=" + multiplier +
                '}';
    }
}
