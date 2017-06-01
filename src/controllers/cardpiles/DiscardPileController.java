package controllers.cardpiles;

import cardcontainer.DiscardPile;
import views.cardpiles.DiscardPileView;

/**
 * Created by mikce_000 on 28-May-2017.
 */
public class DiscardPileController extends PileController {

    public DiscardPileController(DiscardPile pile, DiscardPileView view){
        super(pile, view);
    }

}
