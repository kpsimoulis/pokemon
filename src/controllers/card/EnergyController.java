package controllers.card;

import card.Energy;
import views.card.EnergyView;

/**
 * Created by mikce_000 on 27-May-2017.
 */
public class EnergyController extends CardController {

    public EnergyController(Energy energyCard) {
        super(energyCard, new EnergyView());
    }

}
