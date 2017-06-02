package main;

public class Requirement {

    /**
     * Energy category
     */
    private String category;

    /**
     * Energy Amount
     */
    private int energyAmount;

    /**
     *
     * @param category
     * @param energyAmount
     */
    public Requirement(String category, int energyAmount) {
        this.category = category;
        this.energyAmount = energyAmount;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryShort() {
        if (category.equals("fight")) {
            return "fgt";

        }
        else if (category.equals("water")) {
            return "wat";

        }
        else if (category.equals("psychic")) {
            return "psy";

        }
        else {
            return "col";
        }
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Requirement{" +
                "category='" + category + '\'' +
                ", energyAmount=" + energyAmount +
                '}';
    }
}
