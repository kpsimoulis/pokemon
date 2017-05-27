import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cardcontainer.Deck;


public class Main {

    public static void main(String[] args) throws IOException {

        Deck deck = new Deck();
        String fileName = "files/deck1.ptcgo.txt";
        String fileName2 = "res/deck/deck1.txt";

        deck.populateDeck(fileName2);

//        System.out.println(deck);

    }

}
