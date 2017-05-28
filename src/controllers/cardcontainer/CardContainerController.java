package controllers.cardcontainer;

import card.Card;
import cardcontainer.CardContainer;
import controllers.card.CardController;
import controllers.card.ControllerViewBuilder;
import javafx.util.Pair;
import views.card.CardView;
import views.cardcontainer.ContainerView;

import java.util.ArrayList;

public abstract class CardContainerController {

    private CardContainer container;
    private ContainerView view;
    private ArrayList<CardController> cardControllers;

    public CardContainerController(CardContainer newContainer, ContainerView newView, int initialCapacity){

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

        Pair<CardController, CardView> pairControllerView = ControllerViewBuilder.buildControllerView(newCard);
        container.addCard(newCard);
        view.addCardView(pairControllerView.getValue());
        cardControllers.add(pairControllerView.getKey());
        return pairControllerView;
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

    public CardContainer getContainer() {
        return container;
    }

    public void setContainer(CardContainer container) {
        this.container = container;
    }
}
