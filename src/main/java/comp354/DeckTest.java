package comp354;

import ability.*;
import card.*;
import parser.Ability;
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
        Pokemon p1 = helper.getPokemonByName("Glameow");
        Ability ab = p1.getAttack().get(0).getAbility();
        Deck deckLogic = (Deck) ab.getLogic().get(0);
        Amount amount1 = deckLogic.getAmount();
//        Amount amount1 = ((Deck) ab.getLogic().get(0)).getAmount();
        Target target1 = deckLogic.getTarget();
        String type1 = p1.getAttack().get(0).getAbility().getLogic().get(0).getClass().getSimpleName();

//        String type2 = p1.getAttack().get(0).getAbility().getLogic().get(1).getClass().getSimpleName();
//        Target target2 = ((Cond) ab.getLogic().get(1)).getTarget();
//        Amount amount2 = ((Cond) ab.getLogic().get(1)).getAmount();

        System.out.println("type1: " + type1 + "\n"
                        + "amout1: " + amount1.getAmount() + "\n"
                        + "target1 : " + target1.getName() + "\n"
                        + "destination: " + deckLogic.getDestination() + "\n"
                        + "from: " + deckLogic.getOrientation() + "\n"
                        + ab.getLogic().size() + "\n"
//                        + "type2: " + type2 + "\n"
//                        + "amout2: "+ ( (Dam)((Cond)p1.getAttack().get(0).getAbility().getLogic().get(1)).getConditionIsMet().get(0) ).getAmount().getAmount()+  "\n"
//                        + "target2 : " + target2.toString() + "\n"
//                +((Draw)tc2.getAbility().getLogic().get(0)).getTarget().getName()
//                        +tc3.getAbility().getLogic().get(0).getClass().getSimpleName()
        );


//        System.out.println(p1);
        Trainer tc = helper.getTrainerByName("Red Card");
        Trainer tc2 = helper.getTrainerByName("reenergize");
        Trainer tc3 = helper.getTrainerByName("Energy Switch");

        String type = tc.getAbility().getLogic().get(1).getClass().getSimpleName();
        Amount amount = ((Draw) tc.getAbility().getLogic().get(2)).getAmount();
        Target target = ((Draw) tc.getAbility().getLogic().get(2)).getTarget();
//        System.out.println("type:" + type +"\n"
//        +"amout:"+ amount.getAmount()+"\n"
//        +"type: "+target.toString()+"\n"
//        +tc.getAbility().getLogic().size()+"\n"
//                +((Draw)tc2.getAbility().getLogic().get(0)).getTarget().getName()
//                +tc3.getAbility().getLogic().get(0).getClass().getSimpleName()
//        );
    }

}
