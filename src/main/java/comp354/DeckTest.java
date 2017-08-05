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
        Pokemon p1 = helper.getPokemonByName("Purugly");
        Ability ab = p1.getAttack().get(0).getAbility();
        Dam deckLogic = (Dam) ab.getLogic().get(0);
//        Dam deckLogic1 = (Dam) ab.getLogic().get(1);
        Amount amount = deckLogic.getAmount();
//        Amount amount1 = deckLogic1.getAmount();
//        Amount amount1 = ((Deck) ab.getLogic().get(0)).getAmount();
        Target target = deckLogic.getTarget();
//        Target target1 = deckLogic1.getTarget();
        String type1 = p1.getAttack().get(1).getAbility().getLogic().get(0).getClass().getSimpleName();
//        System.out.println(
//                "type1: " + type1 + "\n"
//                        + "logic: " + deckLogic.toString() + "\n"
////                        + "logic1: " + deckLogic1.toString() + "\n"
//                        +ab.toString() + "\n"
//                        + ""
//
//                        + "amout: " +amount.getAmount() +amount.toString() + "\n"
////                        + "amout1: " +amount1.getTarget().getName()+amount1.getTarget().getArea()+amount1.getTarget().getCardType()+amount1.getMultiplier() +amount1.toString() + "\n"
////                        + "target : " + target.getName()+target.getArea() + "\n"
////                        + "target1 : " + target1.getName()+target1.getArea() + "\n"
////                        + target1.toString() + "\n"
//                        + ab.getLogic().size() + "\n");

        if (p1.getAttack().size() > 1) {
            Ability ab2 = p1.getAttack().get(1).getAbility();
            Cond deckLogic2 = (Cond) ab2.getLogic().get(1);
//            Amount amount2 = deckLogic2.getAmount();
//        Amount amount1 = ((Deck) ab.getLogic().get(0)).getAmount();
//            Target target2 = deckLogic2.getTarget();
            String type2 = p1.getAttack().get(1).getAbility().getLogic().get(1).getClass().getSimpleName();

//        String type2 = p1.getAttack().get(0).getAbility().getLogic().get(1).getClass().getSimpleName();
//        Target target2 = ((Cond) ab.getLogic().get(1)).getTarget();
//        Amount amount2 = ((Cond) ab.getLogic().get(1)).getAmount();

//            System.out.println(
////                            "type1: " + type1 + "\n"
////                            + "amout1: " + amount1.toString() + "\n"
////                            + "target1 : " + target1.getName() + "\n"
////                            + target1.toString() + "\n"
////                            + ab.getLogic().size() + "\n"
////                            +
//                    "type2: " + type2 + "\n"
////                            + "amout2: " + amount2.getAmount() + "\n"
////                            + "target2 : " + target2.getName() + "\n"
////                            + target2.toString() + "\n"
//                            + ab2.getLogic().size() + "\n"
//                    +deckLogic2.toString()
////                        + "type1: " + type1 + "\n"
////                        + "amout1: "+ ( (Dam)((Cond)p1.getAttack().get(0).getAbility().getLogic().get(1)).getConditionIsMet().get(0) ).getAmount().getAmount()+  "\n"
////                        + "target2 : " + target2.toString() + "\n"
////                +((Draw)tc2.getAbility().getLogic().get(0)).getTarget().getName()
////                        +tc3.getAbility().getLogic().get(0).getClass().getSimpleName()
//            );
        }

//        System.out.println(p1);
        Trainer tc = helper.getTrainerByName("Clemont");
        System.out.println(tc);
//        Trainer tc2 = helper.getTrainerByName("reenergize");
//        Trainer tc3 = helper.getTrainerByName("Energy Switch");

        String typet = tc.getAbility().getLogic().get(0).getClass().getSimpleName();
//        Amount amount = ((Swap) tc.getAbility().getLogic().get(0)).getAmount();
        Target targettc = ((Search) tc.getAbility().getLogic().get(0)).getTarget();
        System.out.println("typet:" + typet + "\n"
//        +"amout:"+ amount.getAmount()+"\n"
        +"type2: "+targettc.toString()+"\n"
                + "tc: " + ((Search) tc.getAbility().getLogic().get(0)).getSource()
//        +tc.getAbility().getLogic().size()+"\n"
//                +((Draw)tc2.getAbility().getLogic().get(0)).getTarget().getName()
//                +tc3.getAbility().getLogic().get(0).getClass().getSimpleName()
        );
    }

}
