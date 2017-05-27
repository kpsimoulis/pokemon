package controllers.cardcontainer;

import cardcontainer.Hand;
import views.cardcontainer.HandView;

public class HandController extends CardContainerController{

    public HandController(Hand newHand, HandView newView){

        super(newHand, newView, 7);

    }


}
