package controllers.cardcontainer;

import card.Card;
import card.Energy;
import card.Pokemon;
import cardcontainer.CardContainer;
import controllers.card.CardController;
import controllers.card.EnergyController;
import controllers.card.PokemonController;
import javafx.util.Pair;
import views.card.CardView;
import views.card.EnergyView;
import views.card.PokemonView;
import views.cardcontainer.CardContainerView;

import java.util.ArrayList;

public abstract class CardContainerController {

    private CardContainer container;
    private CardContainerView view;
    private ArrayList<CardController> cardControllers;

    public CardContainerController(CardContainer newContainer, CardContainerView newView, int initialCapacity){

        container = newContainer;
        view = newView;

        cardControllers = new ArrayList<CardController>(initialCapacity);

        for (Card card : newContainer.getCards()) {
            addCard(card);
        }

    }

    public ArrayList<CardController> getCardControllers(){
        return cardControllers;
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
        container.addCard(newCard);
        view.addCardView(cardView);
        cardControllers.add(cardController);
        return new Pair<>(cardController, cardView);
    }

    public Pair<CardController, CardView> removeCard(Card cardToRemove){

        container.removeCard(cardToRemove);
        CardController removedController = null;
        for(CardController controller: cardControllers){
            if (controller.getCard() == cardToRemove){
                removedController = controller;
                cardControllers.remove(controller);
                break;
            }
        }

        assert removedController != null;
        return new Pair<>(removedController, view.removeCardView(removedController.getView()));

    }

    public void returnAllCards(){

        for(CardController controller: cardControllers){
            controller.returnBackCover();
        }

    }

}
