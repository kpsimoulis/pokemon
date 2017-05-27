package card;

public class Trainer extends Card {

    @Override
    public String toString() {
        return "Trainer " +
                "(" + this.getIndex() + ")" +
                " {" +
                " name='" + this.getName() + '\'' +
                ", category='" + this.getCategory() + '\'' +
                ", ability='" + ability + '\'' +
//                "description='" + description + '\'' +
                '}';
    }

    private String description;
    private String ability;


    public Trainer(String name, int index, String category, String description, String ability) {
        super(name, index, category);
        this.description = description;
        this.ability = ability;
    }


    public String getDescription() {
        return description;
    }

    public String getAbility() {
        return ability;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }


    public void applyRule(Trainer obj) {

    }


}
