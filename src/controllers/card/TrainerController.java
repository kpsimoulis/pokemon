package controllers.card;

import card.Trainer;
import views.card.TrainerView;

public class TrainerController extends CardController {
    public TrainerController(Trainer card){

        super(card, new TrainerView());
        TrainerView view = (TrainerView) getView();
        view.setAbility(card.getAbility().getName());
        view.setDescription(card.getAbility().getDescription());

    }
}
