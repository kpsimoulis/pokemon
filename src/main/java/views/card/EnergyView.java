package views.card;

public class EnergyView extends CardView {

    public EnergyView() {
        super();
    }

    @Override
    protected String getCardDesc() {
        return  "=== ENERGY CARD ===\n\n" +
                "Type: " + getCardType();
    }

}
