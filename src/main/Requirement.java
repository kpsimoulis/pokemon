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
