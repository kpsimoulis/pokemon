package card;

public class Energy extends Card {


    public Energy(String name, int index, String category) {
        super(name, index, category);
    }

    @Override
    public String toString() {

        return "Energy "+
                "(" + this.getIndex() + ")" +
                " {" +
                " name='" + this.getName() + '\'' +
                ", category='" + this.getCategory() + '\'' +
                '}';

    }
}
