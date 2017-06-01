package player.AI;

import card.*;
import main.Attack;
import player.Coin;
import player.Player;

/**
 * Created by zc on 2017/6/1.
 *
 * To make AI(and human player) play, you need another method in your game engine to update their handviews, activepokemonviews and benchviews.
 */
public class AIPlayer extends player{
    protected AICards aiCards;

    public AIPlayer() {

        aiCards = new AICards();
    }

    public Pokemon getActivePokemon() {
        return this.aiCards.getActivePokemon();
    }

    public void selectActivePokemon() {
        Pokemon selectedPokemon = aiCards.getFirstPokemon();
        aiCards.setActivePokemon(selectedPokemon);
    }

    public void moveCardFromHandToBottomOfDeck() {
        Card firstCard = aiCards.getFirstCardOfHand();
        aiCards.moveCardFromHandToBottomOfAIDeck(firstCard);
    }

    public String AITurn(Player humanplayer) {
        Energy firstEnergy = aiCards.getFirstEnergy();
        if (firstEnergy != null) {
            aiCards.attachEnergy(firstEnergy, getActivePokemon());
        }

        String resultString = "";
        int numberOfAttacks = getActivePokemon().getAttacks().size();
        for (int i = numberOfAttacks - 1; i >= 0; i--) {
            resultString = attackResult(i, humanplayer);
            if (!resultString.equals("")) {
                break;
            }
        }

        return resultString;
    }

    public String attackResult(int attackIndex, Player humanPlayer) {
        String resultString = "";
        Attack attack = getActivePokemon().getAttacks().get(attackIndex);
        Player op = (Player) humanPlayer;
        Pokemon activePokemon = getActivePokemon();

        if (!activePokemon.hasEnoughEnergy(attackIndex)) {
            return resultString;
        }

        String target = attack.getTarget();
        if (target.equals("OPPONENTACTIVE")) {
            Pokemon targetObj = op.getActivePokemon();
            int damagePoints = attack.getDamagePoints();
            if (damagePoints > 0) {
                targetObj.removeHP(damagePoints);
                resultString += "Your active pokemon lost " + damagePoints + " HP. ";
            }
            if (attack.getFlipRequired()) {
                boolean flip;
                if (Coin.isHead()) flip = true;
                else flip = false;
                if (flip) {
                    resultString += "Flip returned heads. ";
                    String statusToApply = attack.getStatusToApply();
                    if (!statusToApply.equals("NONE")) {
                        resultString += "Your active pokemon now is " + statusToApply +". ";
                        //targetObj.applyStatus(statusToApply);
                    } else {
                        String additionalTarget = attack.getAdditionalTarget();

                        if (additionalTarget.equals("OPPONENTACTIVE")) {
                            Pokemon additionalTargetObj = op.getActivePokemon();
                            int additionalDamage = attack.getAdditionalDamagePoints();
                            if (additionalDamage > 0) {
                                additionalTargetObj.removeHP(additionalDamage);
                                resultString += "Your active pokemon lost another " + additionalDamage + " points. ";
                            }
                        }
                    }
                } else {
                    resultString += "Coin flip returned tails. ";
                }
            }
        } else if (target.equals("OPPONENTHAND")) {
            if (attack.getDestination().equals("BOTTOMOFDECK")) {
                resultString = "You must put a card at the bottom of your deck.";
            }
        } else {
            Object targetObj = null;
        }

        return resultString;
    }
}