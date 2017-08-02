package ability;

import parser.AbilityLogic;
import parser.Amount;
import parser.Target;

import java.util.List;

public class Search extends AbilityLogic {

    private Target target;
    private String targetCategory;
    private String source;
    private String filterType;
    private String filterCategory;
    private int filterTotal;
    private String filterTarget;
    private Amount amount;

    public Search(List<String> logic) {
        super("search");
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

        // TODO ask professor why source = cat:basic
        if (logic.get(0).equals("cat")) {
            logic.remove(0);
            this.targetCategory = logic.get(0);
            logic.remove(0);
        }

        if (!logic.get(0).equals("source")) {
            throw new IllegalArgumentException("Expecting word 'source'");
        }
        logic.remove(0);

        if (!logic.get(0).equals("deck") && !logic.get(0).equals("discard")) {
            throw new IllegalArgumentException("Search source should be deck or discard");
        }

        this.source = logic.get(0);
        logic.remove(0);

        if (logic.get(0).equals("filter")) {
            logic.remove(0);
            parseFilter();
        }

        // Parse Amount
        this.amount = new Amount(logic);
        this.logic = amount.getLogic();

    }

    public void parseFilter() {
        if (logic.get(0).equals("top")) {
            this.filterType = "top";
            logic.remove(0);
            this.filterTotal = Integer.parseInt(logic.get(0));
            logic.remove(0);
        }
        else if (logic.get(0).equals("evolves-from")) {
            this.filterType = "evolves-from";
            logic.remove(0);
            if (!logic.get(0).equals("target")) {
                throw new IllegalArgumentException("Expecting word 'target'");
            }
            logic.remove(0);
            this.filterTarget = logic.get(0);
            logic.remove(0);
        }
        else if (logic.get(0).equals("energy") || logic.get(0).equals("pokemon")) {
            this.filterType = logic.get(0);
            logic.remove(0);
        }

        if (logic.get(0).equals("cat")) {
            logic.remove(0);
            this.filterCategory = logic.get(0);
            logic.remove(0);
        }

    }

    public Target getTarget() {
        return target;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public String getSource() {
        return source;
    }

    public String getFilterType() {
        return filterType;
    }

    public String getFilterCategory() {
        return filterCategory;
    }

    public int getFilterTotal() {
        return filterTotal;
    }

    public String getFilterTarget() {
        return filterTarget;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Search{" +
                "target=" + target +
                ", targetCategory='" + targetCategory + '\'' +
                ", source='" + source + '\'' +
                ", filterType='" + filterType + '\'' +
                ", filterCategory='" + filterCategory + '\'' +
                ", filterTotal=" + filterTotal +
                ", filterTarget='" + filterTarget + '\'' +
                ", amount=" + amount +
                '}';
    }
}
