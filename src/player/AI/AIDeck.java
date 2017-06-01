package player.AI;

/**
 * Created by zc on 2017/6/1.
 *
 * For 1st submission, it is different from mikel's deck since I use arraylist here.
 */

import java.util.*;
import card.Card


public class AIDeck {

    private ArrayList<Card> cards;

    public AIDeck(){
        cards = new ArrayList<Card>();
    }

    public void shuffle(){
        for (int i = 0; i < cards.size(); i++){
            int rand = (int)(Math.random()*(cards.size()));
            Card temp = cards.get(i);
            cards.set(i, cards.get(rand));
            cards.set(rand, temp);
        }
    }

    public void push(Card card){
        cards.add(card);
    }

    public Card pop(){
        if (!cards.isEmpty()){
            Card card = cards.remove(0);
            return card;
        }
        return null;
    }

    public Card getCardAtIndex(int index){
        return cards.get(index);
    }

    public void removeCardAtIndex(int index){
        cards.remove(index);
    }

    public int size(){
        return cards.size();
    }

}



