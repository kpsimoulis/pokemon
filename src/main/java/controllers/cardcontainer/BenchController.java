package controllers.cardcontainer;

import card.Card;
import card.Pokemon;
import cardcontainer.Bench;
import controllers.card.CardController;
import javafx.util.Pair;
import views.card.CardView;
import views.cardcontainer.BenchView;

public class BenchController extends CardContainerController{

    public BenchController(Bench bench, BenchView benchView){

        super(bench, benchView, 5);

    }

    @Override
    public Pair<CardController, CardView> addCard(Card newCard) {
        if (newCard.getClass() == Pokemon.class){
            return super.addCard(newCard);
        }
        System.out.println("Non Pokemon card cannot be added to bench");
        throw new NullPointerException();
    }

    public Pair<CardController, CardView> addCard(Pokemon newCard){
        return super.addCard(newCard);
    }

    public boolean isFull() {
        return ((Bench) getContainer()).isFull();
    }
}
