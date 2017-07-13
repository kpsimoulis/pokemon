import card.*;
import main.ParserHelper;

public class DeckTest {

    public static void main(String[] args) {

        ParserHelper helper = new ParserHelper();

        // Print all the cards that have been parsed
        helper.parse();
        helper.print();

        // Getting a Specific Pokemon
        Pokemon p1 = helper.getPokemonByName("Doduo");
        System.out.println(p1);
    }

}
