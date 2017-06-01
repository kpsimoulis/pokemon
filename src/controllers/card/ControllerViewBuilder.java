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
        CardView view = null;
        if (card.getClass() == Pokemon.class) {
            view = new PokemonView();
            controller = new PokemonController((Pokemon) card, (PokemonView) view);
        }else if (card.getClass() == Energy.class){
            view = new EnergyView();
            controller = new EnergyController((Energy)card, (EnergyView) view);
        }else if (card.getClass() == Trainer.class){
            view = new TrainerView();
            controller = new TrainerController((Trainer)card, (TrainerView) view);
        }
        return new Pair<>(controller, view);

    }

}
