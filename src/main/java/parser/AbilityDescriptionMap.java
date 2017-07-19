package parser;

import java.util.HashMap;

public class AbilityDescriptionMap {

    public final static HashMap consts = new HashMap();
    static
    {
        consts.put("Act Cute", "Your opponent puts a card from his or her hand on the bottom of his or her deck.");
        consts.put("Act Tough", "If this Pokémon has any Energy attached to it, this attack does 20 more damage.");
        consts.put("Ambush", "Flip a coin. If heads, this attack does 30 more damage");
        consts.put("Aurora Beam", "/deal damage");
        consts.put("Beatdown", "/deal damage");
        consts.put("Bite", "/deal damage");
        consts.put("Brave Bird", "This Pokémon does 20 damage to itself.");
        consts.put("Bullet Punch", "Flip 2 coins. This attack does 20 more damage for each heads.");
        consts.put("Circle Circuit", "This attack does 20 damage times the number of your Benched Pokémon.");
        consts.put("Clamp Crush", "Flip a coin. If heads, your opponent's Active Pokémon is now Paralyzed and discard an Energy attached to that Pokémon.");
        consts.put("Clemont", "(Trainer card) Search your deck for up to 4 (electric) Energy cards, reveal them, and put them into your hand. Shuffle your deck afterward.");
        consts.put("Cut", "/deal damage");
        consts.put("Destructive Beam", "Flip a coin. If heads, discard an Energy attached to your opponent's Active Pokémon.");
        consts.put("Doduo Delivery", "Draw 2 cards.");
        consts.put("Double Stab", "Flip 2 coins. This attack does 10 damage times the number of heads.");
        consts.put("Ear Influence", "Move as many damage counters on your opponent's Pokémon as you like to any of your opponent's other Pokémon in any way you like.");
        consts.put("Earthquake", "This attack does 10 damage to each of your Benched Pokémon. (Don't apply Weakness and Resistance for Benched Pokémon.)");
        consts.put("Electroslug", "/deal damage");
        consts.put("Energy Switch", "(Trainer card) Move a basic Energy from 1 of your Pokémon to another of your Pokémon.");
        consts.put("Exhausted Tackle", "Flip a coin. If heads, this attack does 30 damage to your opponent's Active Pokémon. If tails, this Pokémon does 30 damage to itself.");
        consts.put("Fake Out", "Flip a coin. If heads, your opponent's Active Pokémon is now Paralyzed.");
        consts.put("Flail", "This attack does 10 damage times the number of damage counters on this Pokémon.");
        consts.put("Floral Crown", "(Trainer card) At the end of your opponent's turn, heal 20 damage from the Basic Pokémon this card is attached to.");
        consts.put("Flying Elekick", "/deal damage");
        consts.put("Fury Attack", "Flip 3 coins. This attack does 10 damage times the number of heads.");
        consts.put("Heart Sign", "/deal damage");
        consts.put("Hug", "The Defending Pokémon can't retreat during your opponent's next turn.");
        consts.put("Knuckle Punch", "/deal damage");
        consts.put("Lunge", "Flip a coin. If tails, this attack does nothing.");
        consts.put("Mach Cross", "/deal damage");
        consts.put("Mine", "Look at the top card of your opponent's deck. Then, you may have your opponent shuffle his or her deck.");
        consts.put("Misty's Determination", "(Trainer card) Discard a card from your hand. If you do, look at the top 8 cards of your deck and put 1 of them into your hand. Shuffle the other cards back into your deck.");
        consts.put("Mud Slap", "/deal damage");
        consts.put("Nuzzle", "Flip a coin. If heads, your opponent's Active Pokémon is now Paralyzed.");
        consts.put("Nyan Press", "Flip a coin. If heads, this attack does 40 more damage. If tails, your opponent's Active Pokémon is now Paralyzed.");
        consts.put("Poison Ring", "Your opponent's Active Pokémon is now Poisoned. That Pokémon can't retreat during your opponent's next turn");
        consts.put("Poké Ball", "(Trainer card) Flip a coin. If heads, search your deck for a Pokémon, reveal it, and put it into your hand. Then, shuffle your deck.");
        consts.put("Pokémon Center Lady", "(Trainer card) Heal 60 damage and remove all Special Conditions from 1 of your Pokémon.");
        consts.put("Pokémon Fan Club", "(Trainer card) Search your deck for up to 2 Basic Pokémon, reveal them, and put them into your hand. Shuffle your deck afterward.");
        consts.put("Potion", "(Trainer card) Heal 30 damage from 1 of your Pokémon.");
        consts.put("Pound", "/deal damage");
        consts.put("Psychic", "This attack does 10 times more damage for each Energy attached to your opponent's Active Pokémon.");
        consts.put("Quick Attack", "Flip a coin. If heads, this attack does 10 more damage.");
        consts.put("Rain Splash", "/deal damage");
        consts.put("Random Spark", "This attack does 30 damage to 1 of your opponent's Pokémon. (Don't apply Weakness and Resistance for Benched Pokémon.)");
        consts.put("Reckless Charge", "This Pokémon does 10 damage to itself.");
        consts.put("Red Card", "Your opponent shuffles his or her hand into his or her deck and draws 4 cards.");
        consts.put("Rock Tumble", "This attack's damage isn't affected by Resistance");
        consts.put("Rollout", "/deal damage");
        consts.put("Scavenge", "Search your discard pile for a Trainer card, show it to your opponent, and put it into your hand.");
        consts.put("Scratch", "/deal damage");
        consts.put("Shauna", "(Trainer card) Shuffle your hand into your deck. Then, draw 5 cards.");
        consts.put("Skill Dive", "This attack does 10 damage to 1 of your opponent's Pokémon. (Don't apply Weakness and Resistance for Benched Pokémon.)");
        consts.put("Slash", "/deal damage");
        consts.put("Sleep Poison", "Flip a coin. If heads, your opponent's Active Pokémon is now Asleep and Poisoned.");
        consts.put("Soaking Horn", "If this Pokémon was healed during this turn, this attack does 80 more damage.");
        consts.put("Spacing Out", "Flip a coin. If heads, heal 10 damage from this Pokémon.");
        consts.put("Spike Cannon", "Flip 5 coins. This attack does 30 damage times the number of heads.");
        consts.put("Spiral Drain", "Heal 20 damage from this Pokémon.");
        consts.put("Spiral Kick", "/deal damage");
        consts.put("Stretch Kick", "This attack does 30 damage to 1 of your opponent's Benched Pokémon. (Don't apply Weakness and Resistance for Benched Pokémon.)");
        consts.put("Switch", "Switch your Active Pokémon with 1 of your Benched Pokémon.");
        consts.put("Thunderbolt", "Discard all Energy attached to this Pokémon.");
        consts.put("Tierno", "Draw 3 cards");
        consts.put("Twinkle", "Your opponent's Active Pokémon is now Asleep.");
        consts.put("Wally", "(Trainer card) Search your deck for a card that evolves from 1 of your Pokémon (excluding Pokémon-EX) and put it onto that Pokémon. (This counts as evolving that Pokémon.) Shuffle your deck afterward. You can use this card during your first turn or on a Pokémon that was put into play this turn.");
        consts.put("Wing Attack", "/deal damage");
        consts.put("Wish", "Search your deck for a card and put it into your hand. Shuffle your deck afterward.");
    }

}
