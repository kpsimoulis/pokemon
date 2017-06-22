package controllers.cardpiles;

import card.Card;
import cardcontainer.CardContainer;
import cardcontainer.PrizeCards;
import controllers.card.CardController;
import controllers.card.ControllerViewBuilder;
import javafx.util.Pair;
import views.card.CardView;
import views.cardpiles.PrizeCardView;

/**
 * Created by mikce_000 on 02-Jun-2017.
 */
public class PrizeCardController extends PileController {

    public PrizeCardController(PrizeCards prizeCards, PrizeCardView prizeCardView) {
        super(prizeCards, prizeCardView);
    }

    public Pair<CardController, CardView> chooseCard(int cardIdx) {
        CardContainer container = this.getCardContainer();
        if (container.getNoOfCards() == 0){
            return null;
        }

        Card collectedCard;
        try{
            collectedCard = container.removeCard(cardIdx);
        }
        catch (NullPointerException e){
            return null;
        }
        this.getView().decrementNoOfCards();
        return ControllerViewBuilder.buildControllerView(collectedCard);

    }

    public String getPrizeCardsNo(){

      return getCardContainer().toString();

    }

}
