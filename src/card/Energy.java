package card;

public class Energy extends Card {


    private enum Type {COLORLESS, OTHER}
    private int ID;
    private Type type;
    private String description;

    public Energy(String name, int index, String category) {
        super(name, index, category);
    }

    public EnergyCard(String type) {
        this.name = "Energy";

        if (type.equals("COLORLESS")) {
            this.type = Type.COLORLESS;
            this.description = "Colorless";
        } else {
            this.type = Type.OTHER;
            this.description = "Other";
        }
    }

    public String getType() {
        return this.type.toString();
    }

    public void setID(int ID){
        this.ID = ID;
    }

    @Override
    public String toString() {

        return "Energy " +
                "(" + this.getIndex() + ")" +
                " {" +
                " name='" + this.getName() + '\'' +
                ", category='" + this.getCategory() + '\'' +
                '}';

    }
}
