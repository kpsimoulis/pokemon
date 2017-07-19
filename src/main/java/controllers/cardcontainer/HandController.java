package controllers.cardcontainer;

import cardcontainer.Hand;
import controllers.card.CardController;
import views.card.EnergyView;
import views.cardcontainer.HandView;

import java.awt.event.KeyListener;
import java.util.ArrayList;

public class HandController extends CardContainerController {

    public HandController(Hand newHand, HandView newView) {

        super(newHand, newView, 7);

    }

    public void setEnergyListener(KeyListener energyListener) {
        for (CardController cardController : getCardControllers()) {
            if (cardController.getView() instanceof EnergyView) {
                cardController.getView().setListeners(energyListener);
            }
        }
    }

    public ArrayList<CardController> removeAllCards(){
        ((Hand) getContainer()).removeAllCards();
        ArrayList<CardController> removedControllers = new ArrayList<>(getCardControllers());
        getCardControllers().clear();
        ((HandView) getView()).removeAllCardViews();
        return removedControllers;
    }

}
