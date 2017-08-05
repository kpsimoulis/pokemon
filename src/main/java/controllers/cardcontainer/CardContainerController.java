package controllers.cardcontainer;

import card.Card;
import card.Pokemon;
import cardcontainer.CardContainer;
import controllers.card.CardController;
import controllers.card.ControllerViewBuilder;
import javafx.util.Pair;
import views.card.CardView;
import views.card.PokemonView;
import views.card.TrainerView;
import views.cardcontainer.ContainerView;

import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class CardContainerController {

    private CardContainer container;
    private ContainerView view;
    private ArrayList<CardController> cardControllers;

    public CardContainerController(CardContainer newContainer, ContainerView newView, int initialCapacity) {

        container = newContainer;
        view = newView;

        cardControllers = new ArrayList<CardController>(initialCapacity);

        for (Card card : newContainer.getCards()) {
            addCard(card);
        }

    }

    public ArrayList<CardController> getCardControllers() {
        return cardControllers;
    }

    public void addCard(Pair<CardController, CardView> pair) {

        container.addCard(pair.getKey().getCard());
        view.addCardView(pair.getValue());
        cardControllers.add(pair.getKey());

    }

    public Pair<CardController, CardView> addCard(Card newCard) {

        Pair<CardController, CardView> pairControllerView = ControllerViewBuilder.buildControllerView(newCard);
        container.addCard(newCard);
        view.addCardView(pairControllerView.getValue());
        cardControllers.add(pairControllerView.getKey());
        return pairControllerView;
    }

    public Pair<CardController, CardView> removeCard(Card cardToRemove) {

        container.removeCard(cardToRemove);
        CardController removedController = null;
        for (CardController controller : cardControllers) {
            if (controller.getCard() == cardToRemove) {
                removedController = controller;
                cardControllers.remove(controller);
                break;
            }
        }

        assert removedController != null;
        CardView returnedView = view.removeCardView(removedController.getView());
        view.revalidate();
        return new Pair<>(removedController, returnedView);

    }

    public void returnAllCards() {

        for (CardController controller : cardControllers) {
            controller.returnBackCover();
        }

    }

    public ContainerView getView() {
        return view;
    }

    public CardContainer getContainer() {
        return container;
    }

    public void setContainer(CardContainer container) {
        this.container = container;
    }


    public void setPokemonListener(KeyListener activeListener) {

        for (CardController cardController : getCardControllers()) {
            if (cardController.getView() instanceof PokemonView) {
                Pokemon pok = (Pokemon) cardController.getCard();
                if (pok.getStage().equals("basic")) {
                    cardController.getView().setListeners(activeListener);
                }
            }
        }

    }

    public void setAllPokemonListener(KeyListener activeListener) {

        for (CardController cardController : getCardControllers()) {
            if (cardController.getView() instanceof PokemonView) {
                Pokemon pok = (Pokemon) cardController.getCard();
                cardController.getView().setListeners(activeListener);

            }
        }

    }


    public void removeAllListeners(KeyListener keyListener) {

        for (CardController cardController : getCardControllers()) {
            cardController.getView().invalidateKeyListeners(keyListener);
        }

    }


    public void setTrainerListener(KeyListener activeListener) {

        for (CardController cardController : getCardControllers()) {
            if (cardController.getView() instanceof TrainerView) {
                cardController.getView().setListeners(activeListener);
            }
        }

    }

}
