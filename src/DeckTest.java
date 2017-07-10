import card.*;
import cardcontainer.Deck;
import main.Attack;

import java.io.IOException;


public class DeckTest {

    public static void main(String[] args) {

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
//        String fileName1 = "res/deck/trainer_deck.txt";
        String fileName1 = "res/deck/deck1.txt";
        String fileName2 = "res/deck/deck2.txt";

        try {
            deck1.populateDeck(fileName1);
            deck2.populateDeck(fileName2);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(0);
        }

        for (Card card : deck1.getCards()) {
            if(card instanceof Pokemon) {
                for (Attack attack: ((Pokemon) card).getAttack()) {
                    if (!attack.isSupported()) {
                        System.out.println(attack.getAbility().getName() + " not supported");
//                        System.out.println(card);
                    }
                    else {
//                        System.out.println(attack.getAbility().getName() + " supported");

                    }
                }
            }
            else if (card instanceof Trainer) {
                if (! ((Trainer) card).getAbility().isParsed()) {
                    System.out.println(((Trainer) card).getAbility().getName() + " not supported");
                }
            }
        }

        for (Card card : deck2.getCards()) {
            if(card instanceof Pokemon) {
                for (Attack attack: ((Pokemon) card).getAttack()) {
                    if (!attack.getAbility().isParsed()) {
                        System.out.println(attack.getAbility().getName() + " not supported");
                        System.out.println(card);
                    }
                    else {
//                        System.out.println(attack.getAbility().getName() + " supported");

                    }
                }
            }
            else if (card instanceof Trainer) {
                if (! ((Trainer) card).getAbility().isParsed()) {
                    System.out.println(((Trainer) card).getAbility().getName() + " not supported");
                }

            }
        }

//        System.out.println(deck1);
//        System.out.println("Deck 2:");
//        System.out.println(deck2);

    }

}
