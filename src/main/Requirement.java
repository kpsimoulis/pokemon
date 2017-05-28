package main;

public class Requirement {

    private String category;
    private int energyAmount;

    public Requirement(String category, int energyAmount) {
        this.category = category;
        this.energyAmount = energyAmount;
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "category='" + category + '\'' +
                ", energyAmount=" + energyAmount +
                '}';
    }
}
