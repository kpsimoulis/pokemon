package controllers.hand;

import card.Card;
import card.Energy;
import card.Pokemon;
import cardcontainer.Hand;
import controllers.card.CardController;
import controllers.card.EnergyController;
import controllers.card.PokemonController;
import javafx.util.Pair;
import views.card.CardView;
import views.card.EnergyView;
import views.card.PokemonView;
import views.hand.HandView;

public class HandController {

    private Hand hand;
    private HandView view;

    public HandController(Hand newHand, HandView newView){

        hand = newHand;
        view = newView;

        for (Card card : newHand.getCards()) {
            addCard(card);
        }

    }

    public Pair<CardController, CardView> addCard(Card newCard){

        CardController cardController = null;
        CardView cardView = null;
        if (newCard.getClass() == Pokemon.class){
            cardView = new PokemonView();
            cardController = new PokemonController((Pokemon) newCard, (PokemonView) cardView);
        }else if (newCard.getClass() == Energy.class){
            cardView = new EnergyView();
            cardController = new EnergyController((Energy) newCard, (EnergyView) cardView);
        }
        hand.addCard(newCard);
        view.addCardView(cardView);

        return new Pair<>(cardController, cardView);
    }

}
