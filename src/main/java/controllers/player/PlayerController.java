package controllers.player;

import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.cardcontainer.BenchController;
import controllers.cardcontainer.HandController;
import controllers.cardpiles.DeckController;
import controllers.cardpiles.DiscardPileController;
import controllers.cardpiles.PrizeCardController;
import controllers.coin.CoinController;
import parser.Attack;
import parser.Requirement;
import player.Player;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.cardpiles.PrizeCardView;
import views.coin.CoinView;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlayerController {

    private Player player;
    private DeckController deckController;
    private HandController handController;
    private ActivePokemonController activePokemonController;
    private DiscardPileController discardPileController;
    private BenchController benchController;
    private CoinController coinController;
    private PrizeCardController prizeCardController;

    public String status;
    public boolean isPoisoned;

    public PlayerController() {

        this.player = new Player();
        activePokemonController = null;
        this.status = "normal";
        this.isPoisoned = false;

    }

    public void buildViewController() {

        if (player == null || player.getDeck() == null | player.getDeck().getNoOfCards() == 0) {
            System.out.println("Cannot build the views and controllers");
            System.exit(0);
        }

        DeckView deckView = new DeckView();
        deckController = new DeckController(player.getDeck(), deckView);

        HandView handView = new HandView();
        handController = new HandController(player.getHand(), handView);

        DiscardPileView discardPileView = new DiscardPileView();
        discardPileController = new DiscardPileController(player.getDiscardPile(), discardPileView);

        BenchView benchView = new BenchView();
        benchController = new BenchController(player.getBench(), benchView);

        CoinView coinView = new CoinView();
        coinController = new CoinController(player.getPlayerCoin(), coinView);

        PrizeCardView prizeCardView = new PrizeCardView();
        prizeCardController = new PrizeCardController(player.getPrizeCards(), prizeCardView);

    }

    public void setActivePokemonController(ActivePokemonController activePokemonController) {
        this.activePokemonController = activePokemonController;
    }

    public void initiateGame() {

        if (!player.getDeck().validate()) {
            System.out.println("Incorrect Deck for Player " + player.getName());
            System.exit(0);
        } else {
            for (int i = 0; i < 7; i++) {
                this.dealDeckHand();
            }
            for (int i = 0; i < 6; i++) {
                prizeCardController.addCard(this.dealCardDeck());
            }
        }

    }

    public Player getPlayer() {
        return player;
    }

    public DeckController getDeckController() {
        return deckController;
    }

    public HandController getHandController() {
        return handController;
    }

    public ActivePokemonController getActivePokemonController() {
        return activePokemonController;
    }

    public DiscardPileController getDiscardPileController() {
        return discardPileController;
    }

    public BenchController getBenchController() {
        return benchController;
    }

    public CoinController getCoinController() {
        return coinController;
    }

    public PrizeCardController getPrizeCardController() {
        return prizeCardController;
    }

    public boolean handHasPokemon() {

        for (CardController cardController : getHandController().getCardControllers()) {
            if (cardController.getCard() instanceof Pokemon) {
                return true;
            }
        }
        return false;

    }

    public boolean handHasEnergy() {

        for (CardController cardController : getHandController().getCardControllers()) {
            if (cardController.getCard() instanceof Energy) {
                return true;
            }
        }
        return false;
    }

    public boolean canAttack() {

        Pokemon pokemon = (Pokemon) getActivePokemonController().getPokemonController().getCard();
        HashMap<String, Integer> energyPok = getActivePokemonController().getEnergyOnCard();

        for (Attack attack : pokemon.getAttack()) {

            HashMap<String, Integer> energyPokTmp = new HashMap<>(energyPok);

            // Check with specific energies first

            if (checkAttackEnergy(attack, energyPokTmp)) {
                return true;
            }

        }

        return false;

    }

    public boolean checkAttackEnergy(Attack checkAttack, HashMap<String, Integer> energyOnCard) {

        // Checks that required energy is not higher than energy attached to pokemon
        int totalEnergyReq = 0;
        int totalEnergyOnCard = energyOnCard.values().stream().mapToInt(Number::intValue).sum();
        for (Requirement requirement : checkAttack.getRequirement()) {
            totalEnergyReq += requirement.getEnergyAmount();
        }
        if (totalEnergyOnCard < totalEnergyReq) {
            return false;
        }

        // Check for specific energies requirement first
        boolean reqSatisfied = true;
        for (Requirement requirement : checkAttack.getRequirement()) {
            String reqCat = requirement.getCategory();
            if (!reqCat.equals("colorless")) {
                if (energyOnCard.containsKey(reqCat) && energyOnCard.get(reqCat) != 0) {
                    energyOnCard.replace(reqCat, energyOnCard.get(reqCat) - requirement.getEnergyAmount());
                } else if (!energyOnCard.containsKey(reqCat)) {
                    return false;
                } else if (energyOnCard.containsKey(reqCat) && energyOnCard.get(reqCat) <= 0) {
                    return false;
                } else {
                    reqSatisfied = false;
                }
            } else {
                reqSatisfied = false;
            }
        }

        // If colorless energy required, check again for colorless
        if (!reqSatisfied) {
            return checkAttackEnergyCol(checkAttack, energyOnCard);
        }

        return true;

    }

    private boolean checkAttackEnergyCol(Attack checkAttack, HashMap<String, Integer> energyOnCard) {

        int energyAmt = energyOnCard.values().stream().mapToInt(Number::intValue).sum();

        boolean reqSatisfied = false;

        //Check for colorless requirement
        for (Requirement requirement : checkAttack.getRequirement()) {
            String reqCat = requirement.getCategory();

            if (!reqCat.equals("colorless")) {
                if (energyOnCard.containsKey(reqCat)) {
                    reqSatisfied = true;
                } else {
                    return false;
                }
            } else {
                if (energyAmt != 0 && energyAmt >= requirement.getEnergyAmount()) {
                    energyAmt -= requirement.getEnergyAmount();
                    reqSatisfied = true;
                } else if (energyAmt <= 0) {
                    return false;
                }
            }

        }

        return reqSatisfied;

    }


    public Card dealCardDeck() {
        return getDeckController().dealCard().getKey().getCard();
    }

    public void dealDeckHand() {
        getHandController().addCard(this.dealCardDeck());
    }

    public Pokemon getActivePokemonCard() {
        return (Pokemon) getActivePokemonController().getPokemonController().getCard();
    }

    public void shuffleHandInDeck() {
        ArrayList<CardController> removedCards = new ArrayList<>();
        int size = handController.getCardControllers().size();
        for (int i = 0; i < size; i++) {
            removedCards.add(handController.removeCard(handController.getCardControllers().get(0).getCard()).getKey());
        }

        for (CardController controller : removedCards) {
            deckController.addCard(controller.getCard());
        }
        deckController.shuffleDeck();
        for (int i = 0; i < removedCards.size(); i++) {
            dealDeckHand();
        }

    }

    public void discardAllHand() {
        ArrayList<CardController> removedCards = new ArrayList<>();
        int size = handController.getCardControllers().size();
        for (int i = 0; i < size; i++) {
            removedCards.add(handController.removeCard(handController.getCardControllers().get(0).getCard()).getKey());
        }

        for (CardController controller : removedCards) {
            deckController.addCard(controller.getCard());
        }
        deckController.shuffleDeck();

    }

    public void discardActivePokemon() {

        Pokemon exActive = new Pokemon((Pokemon) getActivePokemonController().getPokemonController().getCard());
        activePokemonController = null;
        player.removeActivePokemon();
        for (Energy energy : exActive.getEnergy()) {
            getDiscardPileController().addCard(energy);
        }
        exActive.emptyEnergy();

        //TODO: Detach evolve pokemon and items

        getDiscardPileController().addCard(exActive);

    }

    public boolean benchHasPokemon() {

        return getBenchController().getContainer().getNoOfCards() > 0;

    }

    public ArrayList<Pokemon> getStage1PokemonInHand() {
        //set is better, but this is a team poject so ArrayList is fine.
        ArrayList<Pokemon> stage1Pokemon = new ArrayList<>();
        ArrayList<Pokemon> result = new ArrayList<>();
        for (Card card : getHandController().getContainer().getCards()) {
            if (card.getClass().getSimpleName().equals("Pokemon")) {
                Pokemon pok = (Pokemon) card;
                if (!pok.getStage().equals("basic"))
                    stage1Pokemon.add(pok);
            }
        }
        if (stage1Pokemon.size() > 1) {
            for (int i = 0; i < stage1Pokemon.size() - 1; i++) {
                for (int j = stage1Pokemon.size() - 1; j > i; j--) {
                    if (stage1Pokemon.get(i).getName().equals(stage1Pokemon.get(j).getName())) {
                        stage1Pokemon.remove(i);
                    }

                }
            }
            for (int i = 0; i < stage1Pokemon.size(); i++) {
                if (getActivePokemonCard().getName().equals(stage1Pokemon.get(i).getEvolvesFrom())) {
                    result.add(stage1Pokemon.get(i));
                    break;
                } else {
                    for (int j = 0; j < getBenchController().getContainer().getCards().size(); j++) {
                        if (getBenchController().getContainer().getCards().get(j).getName().equals(stage1Pokemon.get(i).getEvolvesFrom())) {
                            result.add(stage1Pokemon.get(i));
                            break;
                        }
                    }
                }
            }

        }
        return result;
    }

    public boolean isEvolvable() {
        return isActivePokemonEvolvable() || isBenchPokemonEvolvable();
    }

    public boolean isActivePokemonEvolvable() {
        boolean result = false;
        ArrayList<Pokemon> stage1Pokemon = getStage1PokemonInHand();

        int numberOfStage1 = stage1Pokemon.size();
        if (numberOfStage1 == 0) {
            return result;
        } else {
            for (int i = 0; i < numberOfStage1; i++) {
                if (getActivePokemonCard().getName().equals(stage1Pokemon.get(i).getEvolvesFrom())) {
                    result = true;
                    break;
                }

            }
        }
        return result;
    }

    public boolean isBenchPokemonEvolvable() {
        boolean result = false;
        ArrayList<Pokemon> stage1Pokemon = getStage1PokemonInHand();

        int numberOfStage1 = stage1Pokemon.size();
        if (numberOfStage1 == 0) {
            return result;
        } else {
            for (Pokemon pok : stage1Pokemon) {
                for (int j = 0; j < getBenchController().getContainer().getNoOfCards(); j++) {
                    if (pok.getEvolvesFrom().equals(getBenchController().getContainer().getCards().get(j).getName())) {
                        result = true;
                        return result;

                    }
                }
            }
        }
        return result;
    }

    public boolean isTrainerCardAvailable() {
        boolean result = false;
        for (Card card : getHandController().getContainer().getCards()) {
            if (card.getClass().getSimpleName().equals("Trainer")) {
                result = true;
            }
        }
        return result;
    }

    private PokemonController sourceController;
    private PokemonController destController;

    public PokemonController getSourceController() {
        return sourceController;
    }

    public PokemonController getDestController() {
        return destController;
    }

    public void setSourceController(PokemonController sourceController) {
        this.sourceController = sourceController;
    }

    public void setDestController(PokemonController destController) {
        this.destController = destController;
    }

    public void setStatus(String string) {
        this.status = string;
    }

    public void setIsPoisoned(boolean bool) {
        this.isPoisoned = bool;
    }

    public String getStatus() {
        return status;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }


}