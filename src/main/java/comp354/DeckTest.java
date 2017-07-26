package comp354;

import ability.Deck;
import ability.Draw;
import ability.Heal;
import card.*;
import parser.Amount;
import parser.ParserHelper;
import parser.Target;

public class DeckTest {

    public static void main(String[] args) {

        ParserHelper helper = new ParserHelper();

        // Print all the cards that have been parsed
        helper.parse();
//        helper.printAll();

        helper.printPokemonWithoutRetreat();

        // Print Attacks of specific type
        helper.printAttacksByType("Heal");

        // Attack with Multiple Abilities are Hardest ones to implement
//        helper.printAttacksWithMultipleAbilities();

        // Getting a Specific Pokemon
        Pokemon p1 = helper.getPokemonByName("Doduo");
//        System.out.println(p1);
        Trainer tc = helper.getTrainerByName("Red Card");
        Trainer tc2 = helper.getTrainerByName("Tierno");

        String type = tc.getAbility().getLogic().get(1).getClass().getSimpleName();
        Amount amount = ((Draw)tc.getAbility().getLogic().get(2)).getAmount();
        Target target = ((Draw)tc.getAbility().getLogic().get(2)).getTarget();
        System.out.println("type:" + type +"\n"
        +"amout:"+ amount.getAmount()+"\n"
        +"type: "+target.toString()+"\n"
        +tc.getAbility().getLogic().size()+"\n"
                +((Draw)tc2.getAbility().getLogic().get(0)).getTarget().getName()
                +tc2.getCategory()
        );
    }

}
