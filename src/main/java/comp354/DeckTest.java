package comp354;

import card.*;
import parser.ParserHelper;

public class DeckTest {

    public static void main(String[] args) {

        ParserHelper helper = new ParserHelper();

        // Print all the cards that have been parsed
        helper.parse();
//        helper.printAll();

//        helper.printPokemonWithoutRetreat();

        // Print Attacks of specific type
        helper.printAttacksByType("search");

        // Attack with Multiple Abilities are Hardest ones to implement
//        helper.printAttacksWithMultipleAbilities();

        // Getting a Specific Pokemon
//        Pokemon p1 = helper.getPokemonByName("Doduo");
//        System.out.println(p1);
    }

}
