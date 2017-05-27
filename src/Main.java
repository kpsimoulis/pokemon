import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cardcontainer.Deck;


public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Hello World!");
        System.out.println("Hello from Kosta");
        System.out.println("Hello from Mikiel");
        System.out.println("Hello from Polina");
        System.out.println("Hello from Edwin");
        System.out.println("Hello from Zhaoyang");
        System.out.println("Hello from Xiaofang");
        System.out.println("Hello from Simon");
        Deck deck = new Deck();
        String fileName = "files/deck1.ptcgo.txt";
        String fileName2 = "res/deck/deck1.txt";

        deck.populateDeck(fileName2);

//        System.out.println(deck);

    }

}
