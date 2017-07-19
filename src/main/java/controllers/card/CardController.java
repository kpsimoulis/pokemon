package controllers.card;

import card.Card;
import views.card.CardView;

import java.awt.event.ActionListener;

public abstract class CardController {

    private Card card;
    private CardView view;
    private ActionListener actionListener;
    private boolean blockedCard;

    public CardController(Card cardModel, CardView view){
        this.card = cardModel;
        this.view = view;
        backBtnListener();
        view.setName(card.getName());
        view.setType(card.getCategory());
    }

    public CardController(CardController cardController) {
        this.card = new Card(cardController.getCard());
        this.view = cardController.view;
        backBtnListener();
    }

    public Card getCard() {
        return card;
    }

    public CardView getView() {
        return view;
    }

    public void setView(CardView view){ this.view = view; }

    public void setBlockedCard(boolean blockedCard) {
        this.blockedCard = blockedCard;
    }

    public boolean isBlockedCard() {
        return blockedCard;
    }

    public void backBtnListener(){
        actionListener = actionEvent -> {
            if (!blockedCard){
                view.getBackSideBtn().setVisible(false);
            }
        };
        view.getBackSideBtn().addActionListener(actionListener);
    }

    public void returnBackCover() {
        view.getBackSideBtn().setVisible(false);
    }

}
