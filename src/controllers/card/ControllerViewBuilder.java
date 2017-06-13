package controllers.card;

import card.Card;
import card.Energy;
import card.Pokemon;
import card.Trainer;
import javafx.util.Pair;
import views.card.CardView;
import views.card.EnergyView;
import views.card.PokemonView;
import views.card.TrainerView;

public final class ControllerViewBuilder {

    private ControllerViewBuilder(){}

    public static Pair<CardController, CardView> buildControllerView(Card card){

        CardController controller = null;
        if (card.getClass() == Pokemon.class) {
            controller = new PokemonController((Pokemon) card);
        }else if (card.getClass() == Energy.class){
            controller = new EnergyController((Energy)card);
        }else if (card.getClass() == Trainer.class){
            controller = new TrainerController((Trainer)card);
        }
        assert controller != null;
        return new Pair<>(controller, controller.getView());

    }

}
