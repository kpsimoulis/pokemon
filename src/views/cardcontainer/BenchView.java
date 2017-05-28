package views.cardcontainer;

import views.card.CardView;
import views.card.PokemonView;

public class BenchView extends ContainerView {

    public BenchView(){

        super(5);

    }

    public void addCardView(CardView cardView){

        if (cardView.getClass() == PokemonView.class){
            super.addCardView(cardView);
        }else{
            System.out.println("Error in adding card view");
        }

    }

}
