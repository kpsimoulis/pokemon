package card;

import parser.Ability;

public class Trainer extends Card {

    private Ability ability;

    public Trainer(String name, int index, String category, Ability ability) {
        super(name, index, category);
        this.ability = ability;
    }

    public Trainer(Trainer trainer) {
        super(trainer);
        this.ability = trainer.ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return "Trainer " +
                "(" + this.getIndex() + ")" +
                " {" +
                " name='" + this.getName() + '\'' +
                ", category='" + this.getCategory() + '\'' +
                ", ability='" + ability + '\'' +
                '}';
    }

}
