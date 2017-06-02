package controllers.cardcontainer;

import card.Energy;
import cardcontainer.Hand;
import controllers.card.CardController;
import views.card.EnergyView;
import views.card.PokemonView;
import views.cardcontainer.HandView;

import java.awt.event.KeyListener;

public class HandController extends CardContainerController {

    public HandController(Hand newHand, HandView newView) {

        super(newHand, newView, 7);

    }


    public void setActiveListener(KeyListener activeListener) {

        for (CardController cardController : getCardControllers()) {
            if (cardController.getView() instanceof PokemonView) {
                cardController.getView().setListeners(activeListener);
            }
        }

    }

    public void removeAllListeners(KeyListener keyListener) {

        for (CardController cardController : getCardControllers()) {
            cardController.getView().invalidateKeyListeners(keyListener);
        }

    }

    public void setEnergyListener(KeyListener energyListener) {
        for (CardController cardController : getCardControllers()) {
            if (cardController.getView() instanceof EnergyView) {
                cardController.getView().setListeners(energyListener);
            }
        }
    }
}
