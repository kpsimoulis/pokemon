package parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Amount {

    private int amount;
    private boolean calculated;
    private Target target;
    private String source;
    private int multiplier = 1;
    private List<String> logic;

    public Amount() {

    }

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
                        this.calculated = true;
                        List<String> tmpLogic = new LinkedList<String>(Arrays.asList(matcher.group(1).split(":")));

                        if (tmpLogic.get(0).equals("target")) {
                            tmpLogic.remove(0);
                        }

                        // Parse Target
                        this.target = new Target(tmpLogic);
                        tmpLogic = target.getLogic();


                        if (tmpLogic.size() > 0) {
                            if (tmpLogic.get(0).equals("source")) {
                                tmpLogic.remove(0);
                                this.source = tmpLogic.get(0);
                                tmpLogic.remove(0);
                            }
                        }
                        if (tmpLogic.size() > 0) {
                            throw new IllegalArgumentException("More left to do in Amount parser " + String.join(":", tmpLogic));
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
        if (isCalculated()) {
//            throw new IllegalArgumentException("This method is only for integer amount");
        return 0;
        }
        return amount;
    }

    public int getAmount(int amt) {
        if (!isCalculated()) {
            throw new IllegalArgumentException("This method is only for calculated amount");
        }
        return amt*multiplier;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public Target getTarget() {
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
                ", target=" + target +
                ", source='" + source + '\'' +
                ", multiplier=" + multiplier +
                ", logic=" + logic +
                '}';
    }
}
