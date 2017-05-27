import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cardcontainer.Deck;


public class Main {

    public static void main(String[] args) throws IOException {

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        String fileName1 = "res/deck/deck1.txt";
        String fileName2 = "res/deck/deck2.txt";

        deck1.populateDeck(fileName1);
        deck2.populateDeck(fileName2);

        System.out.println("Deck 1:");
        System.out.println(deck1);
        System.out.println("Deck 2:");
        System.out.println(deck2);

    }

}
