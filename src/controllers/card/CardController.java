package controllers.card;

import card.Card;
import views.card.CardView;

import java.awt.event.ActionListener;

public abstract class CardController {

    private Card card;
    private CardView view;
    private ActionListener actionListener;

    public CardController(Card cardModel, CardView cardView){
        card = cardModel;
        view = cardView;
        backBtnListener();
        view.setName(card.getName());
        view.setType(card.getCategory());
    }

    public Card getCard() {
        return card;
    }

    public CardView getView() {
        return view;
    }

    public void backBtnListener(){
        actionListener = actionEvent -> {
            view.getBackSideBtn().setVisible(false);
        };
        view.getBackSideBtn().addActionListener(actionListener);
    }

    public void returnBackCover() {
        view.getBackSideBtn().doClick();
    }

}
