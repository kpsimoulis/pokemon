package ability;

import parser.AbilityLogic;
import parser.Target;

import java.util.List;

public class Applystat extends AbilityLogic {

    private String status;
    private Target target;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Applystat(List<String> logic) {
        super("applystat");
        this.logic = logic;
        parse();
    }

    public void parse() {
        if (!logic.get(0).equals("status")) {
            throw new IllegalArgumentException("Expecting word 'status'");
        }
        logic.remove(0);

        if (logic.get(0).equals("paralyzed") || logic.get(0).equals("asleep") || logic.get(0).equals("stuck") || logic.get(0).equals("poisoned")) {
            setStatus(logic.get(0));
            logic.remove(0);
        }
        else {
            throw new IllegalArgumentException("Expecting status paralyzed, asleep, stuck or poisoned");
        }

        // Parse Target
        this.target = new Target(logic);
        this.logic = target.getLogic();

    }

    public Target getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Applystat{" +
                "status='" + status + '\'' +
                ", target=" + target +
                '}';
    }
}
